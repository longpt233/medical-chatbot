from pymongo import MongoClient

client = MongoClient(
    'mongodb+srv://admin:admin@cluster0.fm4xh.mongodb.net/test')
db = client['integrated-db']
data_collection = db['raw-disease']
data = [item['cac-van-de-lien-quan'] for item in list(data_collection.find())]

data_structure_collection = db['structure-disease']
data_structure = [item for item in list(data_structure_collection.find())] 
