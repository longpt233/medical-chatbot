from pymongo import MongoClient
import json  

def push_thong_tin_benh():
    myclient = MongoClient("mongodb+srv://admin:admin@cluster0.fm4xh.mongodb.net/test")
    db = myclient["integrated-db"]

    cate_list =[]
    f = open('./vinmec/thongtin_tungbenh/cuthe.json')
    data = json.load(f)
    for benh in data[1:2]: 
        row = json.dumps(benh,ensure_ascii=False)
        print(row)

    stock_name_json = {
        "name" :"cate_all", 
        "description" :"danh sach tat ca cac nhom nganh",
        "data" : cate_list,
        "length" : len(cate_list),
    }

    # db["stock_info"].insert_one(stock_name_json)
    # myclient.close() 
    pass  


if __name__ == '__main__':
    push_thong_tin_benh()

