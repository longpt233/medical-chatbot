import scrapy
from bs4 import BeautifulSoup
# from crawler.items import CrawlerItem

class Ds_Benh_Spider(scrapy.Spider):
    name = "ds_benh" # tên của spider
    allowed_domains = ["vinmec.com"] # dont't config https://www...
    # ta điền link cần cào
    start_urls = [
        'https://vinmec.com/vi/benh/',
    ]

    # for ds benh
    # def parse(self, response):
    #     # Tìm tất cả các item dựa vào selector
    #     for item_url in response.css(".collapsible-target > li ").extract():
    #         item = {}
    #         soup = BeautifulSoup(item_url)
    #         item["ten_benh"] = soup.find('a').text
    #         item["link"] = soup.a["href"]
    #         yield item
    #         # yield scrapy.Request(response.urljoin(item_url), callback=self.parse_tablet) 

    # for từng bệnh 
    def parse(self, response):

        # lay ra cac element co chua link toi benh 
        list_element = response.css(".collapsible-target > li ").extract()

        for url_element in list_element[:10]:
            soup = BeautifulSoup(url_element) 
            url_mot_benh = soup.a["href"] 
            yield scrapy.Request(response.urljoin(url_mot_benh), callback=self.parse_tablet) 
        

    # lớp để xử lý 
    def parse_tablet(self, response):
        item =[]
        # print(type(response))   # <scrapy response> 
        
        for paragraph in response.css(".collapsible-container").extract():  # scrapy.selector.unified.SelectorList

            noidung_motdoan={}
            # print(paragraph)

            # print("===========================================")
            soup = BeautifulSoup(paragraph)

            # lay ra tap the p 
            para_index= soup.find_all("h2")
            para_with_p= soup.find_all("p")

            # para_with_p = soup.select("p")
            # print(type(para_with_p))     # <class 'bs4.element.ResultSet'>
            # print(para_with_p)

            result = []
            for th in para_with_p: 
                result.append(th.text) 

            # print(type(result))
            # print(soup.find_all("p")[1:])

            noidung_motdoan["van-de"] = para_index[0].text

            # if len(result) >=1:
            noidung_motdoan["tra-loi"] = result
            item.append(noidung_motdoan)
        output ={}

        output["id-benh"] =str(response.request.url).split("/")[-2]
        output["cac-van-de-lien-quan"] = item
        yield output




# scrapy crawl ds_benh -o ./../../data/vinmec/thongtin_tungbenh/cuthe.json