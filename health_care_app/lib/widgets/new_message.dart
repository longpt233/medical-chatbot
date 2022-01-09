import 'package:flutter/material.dart';
import 'package:health_care_app/providers/message.dart';
import 'message_bubble.dart';
import 'messages.dart';
class NewMessage extends StatefulWidget {
  const NewMessage({Key? key}) : super(key: key);

  @override
  State<NewMessage> createState() => _NewMessageState();
}

class _NewMessageState extends State<NewMessage> {
  // Message messageProvider = Message();
  final _messageTextController = TextEditingController();
  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.only(top: 8),
      child: Container(
        decoration: const BoxDecoration(border: Border.symmetric(horizontal: BorderSide(width: 1,  color: Colors.grey))),
        child: Row(
          children: [
            Expanded(
              child: TextField(
                controller: _messageTextController,
                autocorrect: true,
                textCapitalization: TextCapitalization.sentences,
                decoration: InputDecoration(
                  label: Text('Send a message ...', style: Theme.of(context).textTheme.headline6,)
                ),

              ),
            ),
            IconButton(onPressed: (){
              String? msg = _messageTextController.text;
              setState(() {
                // messageProvider.sendMessage(msg, token);
                Messages.getMessages().add(MessageBubble(msg, 'Duy', true));
              });
            }, icon: const Icon(Icons.mic))
          ],
        ),
      ),
    );
  }
}
