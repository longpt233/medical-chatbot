import 'package:flutter/material.dart';
import 'package:health_care_app/screens/chat_screen.dart';
import 'package:health_care_app/screens/home_screen.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Shop App',
      theme: ThemeData(
        textTheme: const TextTheme(
            bodyText1: TextStyle(
              color: Colors.black,
              fontWeight: FontWeight.bold,
            ),
            headline6: TextStyle(
              color: Colors.white,
              fontWeight: FontWeight.bold,
            )
        ),
        colorScheme:
        ColorScheme.fromSwatch(primarySwatch: Colors.lightBlue)
            .copyWith(secondary: Colors.yellow),
      ),
      home: HomePage(),
      // routes: {
      //   ProductOverviewScreen.routeName: (ctx) => ProductOverviewScreen(),
      // },
    );
  }
}