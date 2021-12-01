## medical-chatbox
midterm information system integration


## How to run 

```bash
docker-compose build
docker-compose up
```

## how to build 

- build image spring

```bash
cd spring-service
docker build -t spring-service:v1 .
```

- build imagg-medical 
```bash
docker build -t medical-module:v1 .
```

- run service: 

```bash
docker-compose up 
```

## on dev env 

- chay spring boot app bthg, can them gi vao db thi lien he Long 
- viet dich vu truy nhap thong tin ca nhan, thong tin covid vao spring app nay 


## how to use mongodb 

- sử dụng cluster mongo atlas : có thể tìm hiểu ở link : ```https://www.mongodb.com/cloud/atlas/register```
- tải mongodb compass  (gui) 
- url connect ```mongodb+srv://<user>:<pass>@cluster-longpt.ocem8.mongodb.net/test```
- user, pass 

## module port
- spring : 8081
- image medical : 8083 : spring se call module nay


## key-cloak 

- la 1 server auth
- port 8080  

**Use** : sau khi docker compose up len thi tien hanh init mot so config 
- from : https://medium.com/devops-dudes/securing-spring-boot-rest-apis-with-keycloak-1d760b2004e
> ----

- *add reaml* : **Tichhop-Reaml**. sau buoc nay no se tao san cho minh auth o ```http://localhost:8080/auth/realms/Tichhop-Reaml/account/```
> ----

- *add client* :
> name :spring-service.   
> protocol: openid-connect  
> root url : http://spring-service:8081 . la service va port giong trong docker  

> ----
- sau khi tao thanh cong client : set 2 muc Access Type = confidential, Service Account Enabled to ON  va Save lai 
- qua tab cradentials lay secretkey, chinh lai trong properties spring cho dung 

> ----
- qua tab role spring-service add 2 role user-role va admin-role, 
- qua tab role Realm          add 2 role user-reaml-role va admin-reaml-role,  add composite role voi spring service tren 

=> role service != role reaml  . mot user reaml co the nam tren nhieu service 
- create sample user : vao muc user > add 2 user : user:user va admin:admin va add role cho no tuong ung 2 cai role vua tao buoc tren 

