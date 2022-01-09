import 'package:flutter/material.dart';
import 'message_bubble.dart';
class Messages extends StatefulWidget {
  const Messages({Key? key}) : super(key: key);
  static List getMessages(){
    return _MessagesState.messages;
  }

  @override
  State<Messages> createState() => _MessagesState();
}

class _MessagesState extends State<Messages> {
  static var messages = [];

  @override
  Widget build(BuildContext context) {

    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: Container(
          child: ListView.builder(itemBuilder: (ctx, index) => messages[index], itemCount: messages.length,)
      ),
    );
  }
}
