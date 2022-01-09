import 'package:flutter/material.dart';
import '../widgets/messages.dart';
import '../widgets/new_message.dart';


class ChatScreen extends StatelessWidget{
  static const routeName = '/chat';

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            SizedBox(
              width: 30,
              child: IconButton(
                  onPressed: (){
                    // Navigator.of(context).pushReplacementNamed(WelcomeScreen.routeName);
                  },
                  icon: const Icon(Icons.arrow_back_ios)
              ),
            ),
            const Text('Medical Bot'),
            const SizedBox(width: 30)
          ],
        ),
      ),
      body: Column(
        children: [
          Expanded(
            child: const Messages(),
          ),
          const NewMessage(),
        ],
      )
    );
  }

}