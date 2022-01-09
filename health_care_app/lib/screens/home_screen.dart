import 'package:flutter/material.dart';

class HomePage extends StatelessWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    String _username = 'Tran Duy';
    return Scaffold(
      appBar: AppBar(
        title: Center(child: Text('Covid Information', style: Theme.of(context).textTheme.headline6,))
        ),
      body: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          children: [

          ],
        ),
      ),
      );
  }
}
