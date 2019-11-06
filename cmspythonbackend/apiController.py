from flask import Flask, request;
from flask_restful import Resource, Api;
from json import dump;
from flask import jsonify;
from predictService import predict;
from trainingService import prepredict;

app = Flask(__name__);

@app.route('/', methods=['GET', 'POST'])
def index():
    return 'Server works!';

@app.route('/greet', methods=['GET', 'POST'])
def greet():
    response = jsonify(result = predict('C:\\testimg\\monitor1.jpg'));
    return response;

@app.route('/visualSearch', methods=['POST'])
def visualSearch():
    return;

@app.route('/updateData', methods=['POST'])
def updateData():
    return prepredict();