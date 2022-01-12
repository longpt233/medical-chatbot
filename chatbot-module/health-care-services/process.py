from pyvi import ViTokenizer
import math
import re

from db import data, data_structure


class TfIdf:
    def __init__(self):
        self.weighted = False
        self.documents = []
        self.corpus_dict = {}

    def add_document(self, doc_name, list_of_words):
        doc_dict = {}
        for w in list_of_words:
            doc_dict[w] = doc_dict.get(w, 0.) + 1.0
            self.corpus_dict[w] = self.corpus_dict.get(w, 0.0) + 1.0

        length = float(len(list_of_words))
        for k in doc_dict:
            doc_dict[k] = doc_dict[k] / length

        self.documents.append([doc_name, doc_dict])

    def similarities(self, list_of_words):
        query_dict = {}
        for w in list_of_words:
            query_dict[w] = query_dict.get(w, 0.0) + 1.0

        length = float(len(list_of_words))
        for k in query_dict:
            query_dict[k] = query_dict[k] / length

        sims = []
        for doc in self.documents:
            score = 0.0
            doc_dict = doc[1]
            for k in query_dict:
                if k in doc_dict:
                    score += (query_dict[k] / self.corpus_dict[k]) + (
                        doc_dict[k] / self.corpus_dict[k])
            sims.append(
                {'disease': doc[0], 'score': -99999 if score == 0 else math.log(score)})

        sims.sort(key=lambda x: x['score'], reverse=True)

        return sims


def preprocessing(list_sentence):
    '''Input: a list symptom, output: list of normalized words
    '''
    sentence = ' '.join(list_sentence)
    sentence = re.sub(
        r'^[a-zA-Z_ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂưăạảấầẩẫậắằẳẵặẹẻẽềềểỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ]',
        ' ', sentence)
    sentence = re.sub('[?.,!:]', '', sentence)
    sentence = sentence.lower()
    sentence = ViTokenizer.tokenize(ViTokenizer.tokenize(sentence))
    list_word = sentence.split()
    return list_word

tf_idf = TfIdf()
def prepare():

    print("loading mongodb .....")
    for i in range(len(data)):
        tf_idf.add_document(data[i][0]['van-de'][14:], preprocessing(data[i][3]['tra-loi']))
    
    data_structure[1]
    print("mongodb loaded .....")


def symptoms2disease(list_symptom):
    return list(map(lambda x: x, tf_idf.similarities(preprocessing(list_symptom))))[:10]

def disease2info(disease):
    for disease_info in data:
        name_disease = disease_info[0]['van-de'][14:].lower()
        if disease.lower() in name_disease:
            return disease_info[1]['tra-loi']

def disease2doing(disease):
    for data in data_structure:
        if (str(data['Tên bệnh']).strip().lower().startswith(disease)):
            data['Các biện pháp điều trị'] = data['Các biện pháp điều trị'].replace(r"'\xa0', ","")
            return '\n'.join(data['Các biện pháp điều trị'][2:-2].split(r"', '"))
    
