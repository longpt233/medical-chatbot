from pymongo import MongoClient
from pprint import pprint
from tfidf import TfIdf
from pyvi import ViTokenizer
import re

client = MongoClient('mongodb+srv://readonly:readonly@cluster-longpt.ocem8.mongodb.net/test')
db = client['benh']
data_collection = db['raw_data']
data = [item['cac-van-de-lien-quan'] for item in list(data_collection.find())]


def preprocessing(list_symptom):
    list_symptom = ' '.join(list_symptom)
    list_symptom = re.sub(
        r'^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]',
        ' ', list_symptom)
    list_symptom = re.sub('[?.,!:]', '', list_symptom)
    list_symptom = list_symptom.lower()
    list_symptom = ViTokenizer.tokenize(ViTokenizer.tokenize(list_symptom))
    list_symptom = list_symptom.split()
    return list_symptom


tf_idf = TfIdf()
for i in range(len(data)):
    tf_idf.add_document(data[i][0]['van-de'][14:], preprocessing(data[i][3]['tra-loi']))


def predict_disease(list_symptom):
    return list(map(lambda x: x[0], tf_idf.similarities(list_symptom)[:10]))

import time
now = time.time()
print(predict_disease(['chóng_mặt', 'quay_cuồng', 'choáng_váng']))
print('Time: ' + time.time() - now)
