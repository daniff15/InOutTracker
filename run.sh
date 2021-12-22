docker-compose up -d
sleep 15
echo 'Done... Access at 127.0.0.1:8000'
python3 projdatageneration/main.py >/dev/null

