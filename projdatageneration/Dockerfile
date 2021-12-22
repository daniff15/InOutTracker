FROM python:3.8
 ADD main.py /
 ADD simulate.py /
 ADD requirements.txt /
 RUN pip install --no-cache-dir --upgrade pip && \
    pip install --no-cache-dir -r requirements.txt
 CMD [ "python3", "main.py" ]
