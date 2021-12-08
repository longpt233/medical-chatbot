from flask import Flask
import requests

app = Flask(__name__)

URL = 'https://static.pipezero.com/covid/data.json'

@app.route('/covid-info', methods=['GET'])
def test():
    data = requests.get(URL)
    return data.json()

if __name__ == '__main__':
    app.run(port=5006,host='0.0.0.0',debug=True)