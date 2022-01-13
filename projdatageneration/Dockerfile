FROM python:3.8-slim-buster
COPY requirements.txt requirements.txt
RUN pip install --no-cache-dir -r requirements.txt
COPY main.py .
COPY simulate.py .
CMD [ "python3", "main.py" ]
