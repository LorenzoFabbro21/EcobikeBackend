cd BackEnd/service-discovery-server
docker build -t service-discovery-server .

cd ../api-gateway
docker build -t api-gateway .

cd ../user-service
docker build -t user-service .

cd ../recensione-service
docker build -t recensione-service .

cd ../shop-service
docker build -t shop-service .

cd ../ad-service
docker build -t ad-service .

cd ../booking-service
docker build -t booking-service .

cd ../appointment-service
docker build -t appointment-service .

cd ../bike-service
docker build -t bike-service .



cd ..

#cd Frontend
#docker build -t frontend .




