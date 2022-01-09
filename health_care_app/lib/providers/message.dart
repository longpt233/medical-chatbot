import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
class Message with ChangeNotifier{
  Future<String> sendMessage(String msg, String token) async {
    final response = await http.post(
        Uri.parse('http://192.168.1.13:8081/api/gateway/medical-chatbot/chat'),
        headers: <String, String>{
          'Content-Type': 'application/json',
          'Authorization': token
        },
        body: jsonEncode(<String, String>{
          'message': msg
        })
    );
    if (response.statusCode == 200) {
      print(response.body);
      return jsonDecode(response.body);
    } else {
      throw Exception('Failed to login into system!');
    }
  }
}