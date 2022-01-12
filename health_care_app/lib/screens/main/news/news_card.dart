import 'package:flutter/material.dart';
import 'package:health_care_app/models/news_model.dart';
import 'package:url_launcher/url_launcher.dart';

class NewsCard extends StatelessWidget {
  NewsModel newsModel;
  NewsCard({Key? key, required this.newsModel}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        _launchUrl('https://suckhoedoisong.vn' + newsModel.link);
      },
      child: Padding(
        padding: const EdgeInsets.symmetric(vertical: 8.0),
        child: ListTile(
          leading: Image.network(newsModel.imageUrl),
          title: Text(newsModel.title),
        ),
      ),
    );
  }

  _launchUrl(String url) async {
    if (await canLaunch(url)) {
      await launch(url, forceWebView: true);
    } else {
      throw 'Could not launch $url';
    }
  }
}
