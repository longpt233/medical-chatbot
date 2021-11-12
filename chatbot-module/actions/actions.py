# This files contains your custom actions which can be used to run
# custom Python code.
#
# See this guide on how to implement these action:
# https://rasa.com/docs/rasa/custom-actions


# This is a simple example for a custom action which utters "Hello World!"

from typing import Any, Text, Dict, List

from rasa_sdk import Action, Tracker
from rasa_sdk.executor import CollectingDispatcher
from actions.db import data
from actions.tfidf import TfIdf, preprocessing


class ActionDiseaseInfo(Action):
    def name(self) -> Text:
        return "action_disease_info"

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        message = 'Sorry I don\'t know'
        keywords = []
        entities = tracker.latest_message['entities']
        print(tracker.latest_message)

        for e in entities:
            # in any function
            # if e['entity'] == 'type_desease':
            keywords.append(e['value'])

        if len(keywords) != 0:
            message = ', '.join(keywords)

        dispatcher.utter_message(text=message)

        return []


tf_idf = TfIdf()
for i in range(len(data)):
    tf_idf.add_document(data[i][0]['van-de'][14:], preprocessing(data[i][3]['tra-loi']))


class ActionPredictDisease(Action):

    def name(self) -> Text:
        return "action_predict_disease"

    def run(self, dispatcher: CollectingDispatcher,
            tracker: Tracker,
            domain: Dict[Text, Any]) -> List[Dict[Text, Any]]:

        message = 'Sorry I don\'t know'
        keywords = []
        entities = tracker.latest_message['entities']
        print(tracker.latest_message)

        for e in entities:
            keywords.append(e['value'].lower())

        if len(keywords) != 0:
            message = list(map(lambda x: x[0], tf_idf.similarities(keywords)[:10]))

        dispatcher.utter_message(text=message)

        return []
