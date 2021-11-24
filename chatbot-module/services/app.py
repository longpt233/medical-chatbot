import re
import time
from flask import Flask, request, jsonify

from process import preprocessing, symptoms2disease


app = Flask(__name__)


@app.route('/predict_disease', methods=['POST'])
def predict_disease():
    data = request.get_json()
    now = time.time()
    list_symptom = data['list_symptom']
    print(f'Time: {time.time() - now}s')
    return jsonify(symptoms2disease(list_symptom)), 200


if __name__ == '__main__':
    app.run(port=5000)
