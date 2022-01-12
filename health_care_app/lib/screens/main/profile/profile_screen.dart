import 'package:flutter/material.dart';
import 'package:health_care_app/providers/api.dart';
import 'package:health_care_app/utils/util.dart';

class ProfileScreen extends StatefulWidget {
  const ProfileScreen({Key? key}) : super(key: key);

  @override
  State<ProfileScreen> createState() => _ProfileScreenState();
}

class _ProfileScreenState extends State<ProfileScreen> {
  dynamic _userInfo;

  Future<void> _getUserInfo() async {
    final response = await Api.getUserInfo(await Util.getToken());
    _userInfo = response['data'];
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
      future: _getUserInfo(),
      builder: (ctx, snapshot) => snapshot.connectionState ==
              ConnectionState.waiting
          ? const Center(child: CircularProgressIndicator())
          : Center(
              child: SingleChildScrollView(
                child: Column(
                  children: [
                    const SizedBox(height: 50),
                    const Text(
                      'Profile',
                      style:
                          TextStyle(fontSize: 22, fontWeight: FontWeight.bold),
                    ),
                    const SizedBox(height: 25),
                    const CircleAvatar(
                      backgroundImage: NetworkImage(
                          'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png'),
                      radius: 50,
                    ),
                    const SizedBox(height: 5),
                    Text(
                      '${_userInfo["firstName"]} ${_userInfo["lastName"]}',
                      style: TextStyle(
                        fontSize: 20,
                        color: Theme.of(context).colorScheme.secondary,
                        fontWeight: FontWeight.w500,
                      ),
                    ),
                    const SizedBox(height: 25),
                    const CustomButton(
                      Icons.account_box_sharp,
                      'My Account',
                    ),
                    const SizedBox(height: 15),
                    const CustomButton(
                      Icons.settings,
                      'Setting',
                    ),
                    const SizedBox(height: 15),
                    const CustomButton(
                      Icons.help,
                      'Help Center',
                    ),
                    const SizedBox(height: 15),
                    const CustomButton(
                      Icons.logout,
                      'Log Out',
                    ),
                    const SizedBox(height: 25),
                  ],
                ),
              ),
            ),
    );
  }
}

class CustomButton extends StatelessWidget {
  final IconData icon;
  final String text;
  const CustomButton(
    this.icon,
    this.text, {
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
        style: ElevatedButton.styleFrom(
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(20),
          ),
          primary: Colors.grey[300],
        ),
        onPressed: () {},
        child: Container(
          width: 290,
          height: 65,
          padding: const EdgeInsets.symmetric(
            horizontal: 10,
          ),
          child: Row(
            children: [
              Icon(
                icon,
                color: Theme.of(context).colorScheme.primary,
              ),
              const SizedBox(width: 10),
              Text(
                text,
                style: const TextStyle(fontSize: 18, color: Colors.black87),
              ),
              const Spacer(),
              const Icon(
                Icons.keyboard_arrow_right,
                color: Colors.black,
              ),
            ],
          ),
        ));
  }
}
