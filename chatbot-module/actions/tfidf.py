import math
from pyvi import ViTokenizer
import re


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
            sims.append([doc[0], -99999 if score == 0 else math.log(score)])

        sims.sort(key=lambda x: x[1], reverse=True)

        return sims


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
