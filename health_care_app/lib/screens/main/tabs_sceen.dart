import 'package:flutter/material.dart';
import 'package:health_care_app/screens/main/chat/chat_screen.dart';
import 'package:health_care_app/screens/main/home/home_screen.dart';
import 'package:health_care_app/screens/main/news/news_screen.dart';
import 'package:health_care_app/screens/main/profile/profile_screen.dart';

class TabScreen extends StatefulWidget {
  static const routeName = '/tab';
  const TabScreen({Key? key}) : super(key: key);

  @override
  _TabScreenState createState() => _TabScreenState();
}

class _TabScreenState extends State<TabScreen> {
  List<Widget> _pages = [];

  @override
  void initState() {
    _pages = const [HomeScreen(), NewsScreen(), ChatScreen(), ProfileScreen()];
    super.initState();
  }

  int _selectedPageIndex = 0;
  void _selectPage(int index) {
    setState(() {
      _selectedPageIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _pages[_selectedPageIndex],
      bottomNavigationBar: BottomNavigationBar(
        type: BottomNavigationBarType.fixed,
        onTap: _selectPage,
        currentIndex: _selectedPageIndex,
        items: const [
          BottomNavigationBarItem(
            icon: Icon(Icons.home),
            label: 'Home',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.book),
            label: 'News',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.message),
            label: 'Chatbot',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.account_circle_rounded),
            label: 'Profile',
          ),
        ],
      ),
    );
  }
}
