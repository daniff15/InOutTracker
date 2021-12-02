from flask import Flask, render_template, request, session, redirect, url_for

app = Flask(__name__)

@app.route('/login')
def login():
    pass

@app.route('/shops')
def shops():
    return render_template('layout.html')

if __name__  == '__main__':
    app.run()
