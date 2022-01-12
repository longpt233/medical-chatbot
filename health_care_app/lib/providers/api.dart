import 'package:flutter/material.dart';
import 'package:health_care_app/models/http_exception.dart';
import 'package:health_care_app/providers/auth.dart';
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:image_picker/image_picker.dart';
import 'package:provider/provider.dart';
import 'package:async/async.dart';
import 'package:path/path.dart';

class Api {
  static const domain = 'http://192.168.1.6:8081';

  static Future<dynamic> signUp(
      String username, password, email, firstName, lastName, address) async {
    final url = Uri.parse('$domain/api/auth/signup');
    try {
      final response = await http.post(
        url,
        headers: {'Content-Type': 'application/json'},
        body: json.encode({
          'username': username,
          'password': password,
          'email': email,
          'firstName': firstName,
          'lastName': lastName,
          'address': address,
        }),
      );
      if (response.statusCode >= 400) {
        throw HttpException('Signup failed!');
      }
    } catch (error) {
      rethrow;
    }
  }

  static Future<dynamic> login(String username, password) async {
    final url = Uri.parse(
        '$domain/api/auth/login?username=$username&password=$password');
    try {
      final response = await http.post(url);

      final decodedResponse = json.decode(response.body);
      if (response.statusCode >= 400) {
        throw HttpException('Login failed!');
      }

      return decodedResponse;
    } catch (error) {
      rethrow;
    }
  }

  static Future<dynamic> getCovidInfo(String token) async {
    // Overview
    final urlOverview = Uri.parse('$domain/api/gateway/covid-overview');
    final responseOverview = await http.get(
      urlOverview,
      headers: {
        'Authorization': 'Bearer $token',
      },
    );
    final decodedResponseOverview =
        json.decode(utf8.decode(responseOverview.bodyBytes));

    // Province
    final urlProvince = Uri.parse('$domain/api/gateway/covid-detail');
    final responseProvince = await http.get(
      urlProvince,
      headers: {
        'Authorization': 'Bearer $token',
      },
    );
    final decodedResponseProvince =
        json.decode(utf8.decode(responseProvince.bodyBytes));

    // Combine
    (decodedResponseProvince['data'] as List<dynamic>)
        .sort((a, b) => a['name'].compareTo(b['name']));

    for (var scope in ['internal', 'world']) {
      final dataOverview = decodedResponseOverview['data'][scope];
      decodedResponseProvince['data'].insert(0, {
        "casesToday": dataOverview['casesToday'],
        "death": dataOverview['death'],
        "name": scope == 'internal' ? 'Việt Nam' : 'World',
        "recovered": dataOverview['recovered'],
      });
    }

    return decodedResponseProvince;
  }

  static Future<dynamic> chatWithBot(String token, message) async {
    final url = Uri.parse('$domain/api/gateway/medical-chatbot/chat');
    try {
      final response = await http.post(
        url,
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer $token',
        },
        body: json.encode({
          'message': message,
        }),
      );

      if (response.statusCode >= 400) {
        throw HttpException('Signup failed!');
      }

      final decodedResponse = json.decode(utf8.decode(response.bodyBytes));
      return decodedResponse;
    } catch (error) {
      rethrow;
    }
  }

  static Future<dynamic> analysisImage(String token, XFile imageFile) async {
    final url = Uri.parse('$domain/api/gateway/medical-image/predict');
    try {
      var request = http.MultipartRequest('GET', url)
        ..headers['Authorization'] = 'Bearer $token'
        ..files.add(http.MultipartFile(
          'photo',
          http.ByteStream(DelegatingStream.typed(imageFile.openRead())),
          await imageFile.length(),
          filename: basename(imageFile.path),
        ));

      var response = await request.send();

      if (response.statusCode < 400) {
        final responseBody = await response.stream.toBytes();
        return responseBody;
      }
    } catch (error) {
      rethrow;
    }
  }

  static Future<dynamic> getUserInfo(String token) async {
    final url = Uri.parse('$domain/api/user-info/getUserProfile');
    try {
      final response = await http.get(
        url,
        headers: {
          'Authorization': 'Bearer $token',
        },
      );
      final decodedResponse = json.decode(utf8.decode(response.bodyBytes));

      if (decodedResponse['code'] >= 400) {
        throw HttpException(decodedResponse['message']);
      }

      return decodedResponse;
    } catch (error) {
      rethrow;
    }
  }

  static Future<dynamic> getCovidNews(String token) async {
    final url = Uri.parse('$domain/api/gateway/covid-news');
    try {
      final response = await http.get(
        url,
        headers: {
          'Authorization': 'Bearer $token',
        },
      );
      final decodedResponse = json.decode(utf8.decode(response.bodyBytes));

      if (decodedResponse['code'] >= 400) {
        throw HttpException(decodedResponse['message']);
      }

      return decodedResponse;
    } catch (error) {
      rethrow;
    }
  }
}
