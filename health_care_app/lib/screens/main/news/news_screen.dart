import 'package:flutter/material.dart';
import 'package:health_care_app/models/news_model.dart';
import 'package:health_care_app/providers/repository.dart';
import 'package:health_care_app/screens/main/news/news_card.dart';

class NewsScreen extends StatefulWidget {
  const NewsScreen({Key? key}) : super(key: key);

  @override
  _NewsScreenState createState() => _NewsScreenState();
}

class _NewsScreenState extends State<NewsScreen> {
  List<NewsModel>? _listNews;

  Future<void> _fetch() {
    return repository.fetchCovidNews().then((value) {
      if (_listNews == null) {
        _listNews = repository.listNews;
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
      future: _fetch(),
      builder: (ctx, snapshot) =>
          snapshot.connectionState == ConnectionState.waiting
              ? const Center(child: CircularProgressIndicator())
              : Column(
                  children: [
                    Row(
                      children: const [
                        Padding(
                          padding: const EdgeInsets.only(
                              left: 12.0, right: 12.0, top: 75.0),
                          child: Text(
                            'Covid News',
                            style: TextStyle(
                              fontSize: 22,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ),
                      ],
                    ),
                    Expanded(
                      child: ListView.builder(
                        padding: EdgeInsets.only(top: 15.0),
                        itemCount: _listNews!.length,
                        itemBuilder: (ctx, index) =>
                            NewsCard(newsModel: _listNews![index]),
                      ),
                    ),
                  ],
                ),
    );
  }
}
