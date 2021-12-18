# This files contains your custom actions which can be used to run
# custom Python code.
#
# See this guide on how to implement these action:
# https://rasa.com/docs/rasa/custom-actions


# This is a simple example for a custom action which utters "Hello World!"

# from typing import Any, Text, Dict, List

# from rasa_sdk import Action, Tracker
# from rasa_sdk.executor import CollectingDispatcher
# import requests

# URL = 'http://localhost:5000'


# class ActionDiseaseInfo(Action):
#     def name(self) -> Text:
#         return "action_disease_info"

#     def run(self, dispatcher: CollectingDispatcher,
#             tracker: Tracker,
#             domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

#         message = 'Sorry I don\'t know'
#         keywords = []
#         entities = tracker.latest_message['entities']
#         print(tracker.latest_message)

#         for e in entities:
#             # in any function
#             # if e['entity'] == 'type_disease':
#             keywords.append(e['value'])

#         if len(keywords) != 0:
#             message = ', '.join(keywords)

#         dispatcher.utter_message(text=message)

#         return []


# class ActionPredictDisease(Action):

#     def name(self) -> Text:
#         return "action_predict_disease"

#     def run(self, dispatcher: CollectingDispatcher,
#             tracker: Tracker,
#             domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

#         # message = 'Sorry I don\'t know'
#         # keywords = []
#         # entities = tracker.latest_message['entities']
#         # print(tracker.latest_message)

#         # for e in entities:
#         #     keywords.append(e['value'].lower())
#         # print(keywords)
#         # if len(keywords) != 0:
#         #     response = requests.post(URL + '/predict_disease', json={"list_symptom": ["đau đầu"]}).json()
#         #     print(f'res: {response}')
#         #     if len(response) == 0:
#         #         message = 'An error occurred'
#         #     else:
#         #         message = response[0]['disease']
#         dispatcher.utter_message(text='predict disease')

#         return []
