from flask import Flask
import requests
from bs4 import BeautifulSoup
import json
from flask import jsonify 


app = Flask(__name__)

URL = 'https://static.pipezero.com/covid/data.json'

@app.route('/covid-overview', methods=['GET'])
def get_overview():
    data = requests.get(URL) 
    return jsonify(get_overview_data(data.json())) 

@app.route('/covid-detail', methods=['GET'])
def get_detail():
    data = requests.get(URL)
    return jsonify(get_detail_internal(data.json()))

@app.route('/covid-news', methods=['GET'])
def get_new(): 
    return json.dumps(get_news())

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


def get_news():
    data = requests.get("https://covid19.gov.vn/ban-tin-covid-19.htm") 
    soup = BeautifulSoup(data.text, "lxml")
    table = soup.find('div', attrs = {'class':'box-stream timeline_list'})  
    quotes=[]
    for row in table.find_all_next('div', attrs = {'class': 'box-stream-item'}):
        quote = {} 
        quote["link"] = row.a["href"]
        quote["img"] = row.img['src']
        quote["title"] = row.h2.a.text
        quote["abstract"] = row.p.text 
        quotes.append(quote)

    return {"data": quotes}

if __name__ == '__main__':
    app.run(port=5006,host='0.0.0.0',debug=True)