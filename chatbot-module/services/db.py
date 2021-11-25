from pymongo import MongoClient

client = MongoClient(
    'mongodb+srv://readonly:readonly@cluster-longpt.ocem8.mongodb.net/test')
db = client['benh']
data_collection = db['raw_data']
data = [item['cac-van-de-lien-quan'] for item in list(data_collection.find())]
