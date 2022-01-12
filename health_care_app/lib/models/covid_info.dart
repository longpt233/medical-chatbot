class CovidInfo {
  final String location;
  final String casesToday;
  final String death;
  final String recovered;

  CovidInfo.fromJson(Map<String, dynamic> parsedJson)
      : location = parsedJson['name'].toString(),
        casesToday = parsedJson['casesToday'].toString(),
        death = parsedJson['death'].toString(),
        recovered = parsedJson['recovered'].toString();
}
