import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:health_care_app/models/covid_info.dart';
import 'package:health_care_app/providers/repository.dart';
import 'package:health_care_app/widgets/counter.dart';
import 'package:health_care_app/widgets/my_header.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  String? dropdownValue;
  CovidInfo? currentCountry;
  Future<void> fetch() {
    return repository.fetchCovidInfo().then((value) {
      if (dropdownValue == null) {
        dropdownValue = repository.data.first.location;
        currentCountry = repository.data.first;
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
      future: fetch(),
      builder: (ctx, snapshot) => snapshot.connectionState ==
              ConnectionState.waiting
          ? const Center(child: CircularProgressIndicator())
          : SingleChildScrollView(
              child: Column(
                children: [
                  const MyHeader(
                    image: "assets/icons/Drcorona.svg",
                    textTop: "All you need",
                    textBottom: "is stay at home.",
                  ),
                  Container(
                    margin: const EdgeInsets.symmetric(
                      horizontal: 20,
                    ),
                    padding: const EdgeInsets.symmetric(
                      vertical: 10,
                      horizontal: 20,
                    ),
                    height: 60,
                    width: double.infinity,
                    decoration: BoxDecoration(
                      color: Colors.white,
                      borderRadius: BorderRadius.circular(25),
                      border: Border.all(
                        color: const Color(0xFFE5E5E5),
                      ),
                    ),
                    child: Row(
                      children: [
                        SvgPicture.asset("assets/icons/maps-and-flags.svg"),
                        const SizedBox(width: 20),
                        Expanded(
                          child: DropdownButton(
                            isExpanded: true,
                            underline: const SizedBox(),
                            icon: SvgPicture.asset("assets/icons/dropdown.svg"),
                            value: dropdownValue,
                            items: repository.data
                                .map((e) => e.location)
                                .toList()
                                .map<DropdownMenuItem<String>>((String value) {
                              return DropdownMenuItem<String>(
                                value: value,
                                child: Text(value),
                              );
                            }).toList(),
                            onChanged: (String? value) {
                              dropdownValue = value!;
                              currentCountry = repository.data
                                  .where(
                                    (_) => _.location == value,
                                  )
                                  .first;
                              setState(() {});
                            },
                          ),
                        ),
                      ],
                    ),
                  ),
                  const SizedBox(height: 20),
                  Padding(
                    padding: const EdgeInsets.symmetric(
                      horizontal: 20,
                    ),
                    child: Column(
                      children: [
                        Row(
                          children: [
                            RichText(
                              text: TextSpan(
                                children: [
                                  const TextSpan(
                                    text: "Case Update\n",
                                    style: TextStyle(
                                      fontSize: 18,
                                      color: Color(0xFF303030),
                                      fontWeight: FontWeight.bold,
                                    ),
                                  ),
                                  TextSpan(
                                    text: "Newest update at Jan 12",
                                    style: const TextStyle(
                                      color: Color(0xFF959595),
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ],
                        ),
                        const SizedBox(height: 20),
                        Container(
                          padding: const EdgeInsets.symmetric(horizontal: 20),
                          decoration: BoxDecoration(
                            borderRadius: BorderRadius.circular(20),
                            color: Colors.white,
                            boxShadow: [
                              BoxShadow(
                                offset: const Offset(0, 4),
                                blurRadius: 30,
                                color:
                                    const Color(0xFFB7B7B7).withOpacity(0.16),
                              ),
                            ],
                          ),
                          child: SingleChildScrollView(
                            scrollDirection: Axis.horizontal,
                            child: Container(
                              padding: const EdgeInsets.all(20),
                              child: Row(
                                children: [
                                  Counter(
                                    color: const Color(0xFFFF8748),
                                    number: currentCountry!.casesToday,
                                    title: 'Cases',
                                  ),
                                  const SizedBox(width: 20),
                                  Counter(
                                    color: const Color(0xFFFF4848),
                                    number: currentCountry!.death,
                                    title: "Deaths",
                                  ),
                                  const SizedBox(width: 20),
                                  Counter(
                                    color: const Color(0xFF36C12C),
                                    number: currentCountry!.recovered,
                                    title: "Recovered",
                                  ),
                                ],
                              ),
                            ),
                          ),
                        ),
                        const SizedBox(height: 20),
                        Row(
                          children: const [
                            Text(
                              "Spread of Virus",
                              style: TextStyle(
                                fontSize: 18,
                                color: Color(0xFF303030),
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                          ],
                        ),
                        Container(
                          margin: const EdgeInsets.only(top: 20),
                          height: 178,
                          width: double.infinity,
                          decoration: BoxDecoration(
                            borderRadius: BorderRadius.circular(20),
                            color: Colors.white,
                            boxShadow: [
                              BoxShadow(
                                offset: const Offset(0, 10),
                                blurRadius: 30,
                                color:
                                    const Color(0xFFB7B7B7).withOpacity(0.16),
                              )
                            ],
                          ),
                          child: Image.asset(
                            "assets/images/map.png",
                            fit: BoxFit.contain,
                          ),
                        )
                      ],
                    ),
                  ),
                ],
              ),
            ),
    );
  }
}
