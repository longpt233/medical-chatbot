import 'package:flutter/material.dart';
import 'package:health_care_app/providers/repository.dart';
import 'package:health_care_app/screens/main/tabs_sceen.dart';
import 'package:provider/provider.dart';
import 'package:health_care_app/providers/auth.dart';
import 'package:health_care_app/screens/auth/auth_screen.dart';
import 'package:health_care_app/screens/welcome/welcome_screen.dart';
import 'package:shared_preferences/shared_preferences.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    // SystemChrome.setSystemUIOverlayStyle(
    //     const SystemUiOverlayStyle(statusBarColor: Colors.white));
    return MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (ctx) => Auth()),
      ],
      child: MaterialApp(
        title: 'Health Care App',
        theme: ThemeData(
          colorScheme: ColorScheme.fromSwatch(primarySwatch: Colors.blue)
              .copyWith(secondary: Colors.green),
          textTheme: const TextTheme(
            bodyText1: TextStyle(
              color: Colors.black,
              fontWeight: FontWeight.bold,
            ),
            headline6: TextStyle(
              color: Colors.white,
              fontWeight: FontWeight.bold,
              fontSize: 26,
            ),
          ),
          scaffoldBackgroundColor: Colors.grey[100],
        ),
        home: const WelcomeScreen(),
        routes: {
          WelcomeScreen.routeName: (ctx) => const WelcomeScreen(),
          TabScreen.routeName: (ctx) => const TabScreen(),
          AuthScreen.routeName: (ctx) => const AuthScreen(),
        },
      ),
    );
  }
}
