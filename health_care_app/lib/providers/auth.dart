import 'dart:developer';

import 'package:flutter/cupertino.dart';
import 'package:health_care_app/providers/api.dart';
import 'package:health_care_app/providers/repository.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'dart:async';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:health_care_app/models/http_exception.dart';

class Auth with ChangeNotifier {
  String? _token;
  String? _refreshToken;
  DateTime? _expiryDate;
  Timer? _authTimer;

  bool get isAuth {
    return token != null;
  }

  String? get token {
    if (_expiryDate != null &&
        _expiryDate!.isAfter(DateTime.now()) &&
        _token != null) {
      return _token;
    }
    return null;
  }

  Future<void> login(String username, password) async {
    try {
      dynamic response = await Api.login(username, password);

      _token = response['data']['access_token'];
      _refreshToken = response['data']['refresh_token'];

      _expiryDate =
          DateTime.now().add(Duration(seconds: response['data']['expires_in']));
      // _autoLogout();
      notifyListeners();
      final prefs = await SharedPreferences.getInstance();
      final userData = json.encode({
        'token': _token,
        'refreshToken': _refreshToken,
        'expiryDate': _expiryDate!.toIso8601String(),
      });
      prefs.setString('userData', userData);
    } catch (error) {
      rethrow;
    }
  }

  Future<void> signUp(
      String username, password, email, firstName, lastName, address) async {
    Api.signUp(username, password, email, firstName, lastName, address);
  }

  Future<void> logout() async {
    _token = null;
    _expiryDate = null;
    if (_authTimer != null) {
      _authTimer!.cancel();
      _authTimer = null;
    }
    notifyListeners();
    final prefs = await SharedPreferences.getInstance();
    // prefs.remove('userData');
    prefs.clear();
  }
}
