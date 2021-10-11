from bs4 import BeautifulSoup

soup = BeautifulSoup("<li><a href=""/vi/benh/xo-vua-dong-mach-3030/"">Xơ vữa động mạch</a></li>")

print(soup.find("a").text)