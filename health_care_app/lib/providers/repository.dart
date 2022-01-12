import 'dart:convert';

import 'package:health_care_app/models/covid_info.dart';
import 'package:health_care_app/models/news_model.dart';
import 'package:health_care_app/providers/api.dart';
import 'package:health_care_app/utils/util.dart';
import 'package:intl/intl.dart';
import 'package:shared_preferences/shared_preferences.dart';

class Repository {
  late List<CovidInfo> data = [];
  late String lastUpdate = '';
  late List<NewsModel> listNews = [];

  Future<void> fetchCovidInfo() async {
    dynamic responseInfo = await Api.getCovidInfo(await Util.getToken());
    data = responseInfo['data']
        .map<CovidInfo>((e) => CovidInfo.fromJson(e))
        .toList();
    lastUpdate = DateFormat('MMM d').format(DateTime.now());
  }

  Future<void> fetchCovidNews() async {
    dynamic responseNews = await Api.getCovidNews(await Util.getToken());
    listNews = responseNews['data']
        .map<NewsModel>((e) => NewsModel.fromJson(e))
        .toList();
  }
}

final repository = Repository();
