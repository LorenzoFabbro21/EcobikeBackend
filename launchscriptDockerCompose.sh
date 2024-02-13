cd service-discovery-server
docker build -t service-discovery-server .

cd ../api-gateway
docker build -t api-gateway .

cd ../user-service
docker build -t user-service .

cd ../recensione-service
docker build -t recensione-service .
cd ..
