from flask import Flask, request, jsonify
import requests
import logging
from process import disease2doing, disease2info, symptoms2disease, prepare
import os 

URL_RASA = os.environ['URL_CHATBOT_RASA']  # http://chatbot-rasa-module-name:5005, call ok 
# URL_RASA ="http://localhost:5005"  # for dev 
URL_COVID = os.environ['URL_COVID_MODULE']

app = Flask(__name__)


@app.route('/medical-chatbot/chat', methods=['POST'])
def response():
    data = request.get_json(silent=True)

    app.logger.info('testing info log' ,data)
    app.logger.info(data)

    response = requests.post(
        URL_RASA + '/model/parse', json={"text": data["content"]}).json()
    dict = {}

    if response["intent"]["name"] == 'predict_disease':    
        dict['intent'] = 'disease_predict'
        dict['anwser'] = predict_disease(response) 

    elif response["intent"]["name"] == 'disease_info':
        dict['intent']  = 'disease_info'
        dict['anwser'] = get_disease_info(response)

    elif response["intent"]["name"] == 'ask_covid_total_case':
        dict['intent']  = "covid_total"
        call = requests.get(URL_COVID + '/covid-overview').json()
        dict['anwser'] = call['data']['internal'] 

    elif response["intent"]["name"] == 'ask_covid_domestic_case':
        dict['intent']  ="covid_domestic"
        dict['anwser'] =   get_domestic_info(response)

    elif response["intent"]["name"] == 'disease_doing':
        dict['intent']  = "disease_doing"     
        dict['anwser'] =   get_disease_doing(response)
    
    print(dict['intent'])
    return jsonify({'response': dict['anwser'] }), 200


def predict_disease(response):
    list_symptom = [entity['value'].lower()
                        for entity in response['entities']]

    predict = symptoms2disease(list_symptom)
    return f"Đó có thể là triệu chứng của bệnh: {predict[0]['disease']}"

def get_disease_info(response):
    list_symptom = response['entities'][0]['value'].lower()
    list_info = disease2info(list_symptom)
    return list_info

def get_domestic_info(response):
    if len(response['entities']) >0 :
        list_domestic = response['entities'][0]['value'].lower()
        call  = requests.get(URL_COVID + '/covid-detail').json()
        data = call['data']

        for tinh in data:
            if str(tinh['name'].lower()).startswith(list_domestic):
                retu = tinh
    else:
        retu= "khong tim thay tinh" 
    return retu

def get_disease_doing(response):
    if len(response['entities']) >0 :
        disease = response['entities'][0]['value'].lower()
        answer = disease2doing(disease)
    else:
        answer = "khong nhan duoc benh"
    
    return answer

if __name__ == '__main__':
    prepare()  # load trc data 
    app.run(port=5000, host='0.0.0.0',debug=True)