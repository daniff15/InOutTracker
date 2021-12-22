cd projAPI/InOutTracker
./mvnw install -Dmaven.test.skip
cd ../../
docker-compose up -d
sleep 15
cd projweb
python -m SimpleHTTPServer 5500 &
cd ../
xdg-open http://127.0.0.1:5500/shoppings.html &
python3 projdatageneration/main.py >/dev/null

