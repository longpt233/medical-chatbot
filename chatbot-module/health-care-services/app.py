from flask import Flask, request, jsonify
import requests

from process import disease2info, preprocessing, symptoms2disease

URL_RASA = 'http://localhost:5005'


app = Flask(__name__)


@app.route('/medical-chatbot/chat', methods=['POST'])
def response():
    data = request.get_json()
    response = requests.post(
        URL_RASA + '/model/parse', json={"text": data["content"][1:]}).json()
    rep = []
    if response["intent"]["name"] == 'predict_disease':
        rep.extend(predict_disease(response))
    elif response["intent"]["name"] == 'disease_info':
        rep.extend(get_disease_info(response))

    return jsonify({'response': rep}), 200


def predict_disease(response):
    list_symptom = [entity['value'].lower()
                        for entity in response['entities']]

    predict = symptoms2disease(list_symptom)
    return f"Đó có thể là triệu chứng của bệnh: {predict[0]['disease']}"

def get_disease_info(response):
    list_symptom = response['entities'][0]['value'].lower()
    list_info = disease2info(list_symptom)
    return list_info

if __name__ == '__main__':
    app.run(port=5000, host='0.0.0.0')