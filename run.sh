cd projAPI/InOutTracker
./mvnw clean install -Dmaven.test.skip

cd ../../
docker-compose down
docker volume rm $(docker volume ls -q)
docker-compose build
docker-compose up -d
