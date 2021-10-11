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

 # lớp để bóc tách dữ liệu
    def parse(self, response):
        # Tìm tất cả các item dựa vào selector
        for item_url in response.css(".collapsible-target > li ").extract():
            item = {}
            soup = BeautifulSoup(item_url)
            item["ten_benh"] = soup.find('a').text
            item["link"] = soup.a["href"]
            yield item
            # yield scrapy.Request(response.urljoin(item_url), callback=self.parse_tablet) 
        

#  lớp để xử lý 
    def parse_tablet(self, response):
        item = {}
        # find info of product
        product = response.css(".type0")
        item["title"] = product.css(".rowtop > h1 ::text").extract_first()
        item['price'] = response.css('.area_price strong ::text').extract_first()
       
        yield item