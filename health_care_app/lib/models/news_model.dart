class NewsModel {
  String title;
  String imageUrl;
  String abstractContent;
  String link;

  NewsModel.fromJson(Map<String, dynamic> parsedJson)
      : title = parsedJson['title'].toString(),
        imageUrl = parsedJson['img'].toString(),
        abstractContent = parsedJson['abstract'].toString(),
        link = parsedJson['link'].toString();
}
