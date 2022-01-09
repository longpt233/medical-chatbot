from flask import Flask
import requests

app = Flask(__name__)

URL = 'https://static.pipezero.com/covid/data.json'

@app.route('/covid-overview', methods=['GET'])
def get_overview():
    data = requests.get(URL)
    return get_overview_data(data.json())

@app.route('/covid-detail', methods=['GET'])
def get_detail():
    data = requests.get(URL)
    return get_detail_internal(data.json())

def get_overview_data(raw_data):
    today_internal = raw_data['today']['internal']
    today_internal['casesToday']=today_internal['cases']
    today_internal.pop('treating')

    today_world = raw_data['today']['world']
    today_world['casesToday']=today_world['cases']
    today_world.pop('treating')
    return {"data":{"world":today_world, "internal":today_internal}}
def get_detail_internal(raw_data):
    detail_internal = raw_data['locations']
    for item in detail_internal:
        item.pop('treating')
        item.pop('cases')
    return {"data": detail_internal}

if __name__ == '__main__':
    app.run(port=5006,host='0.0.0.0',debug=True)