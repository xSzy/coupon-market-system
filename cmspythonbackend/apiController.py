from flask import Flask, request
from flask_restful import Resource, Api
from json import dump
from flask import jsonify
from flask.json import JSONEncoder

from predictService import predict, ImageSearchResult
from trainingService import prepredict

class MyJSONEncoder(JSONEncoder):
    def default(self, obj):
        if isinstance(obj, ImageSearchResult):
            return {
                'image': obj.image,
                'similarity': obj.similarity
            }
        return super(MyJSONEncoder, self).default(obj)

app = Flask(__name__)
app.json_encoder = MyJSONEncoder

@app.route('/visualSearch', methods=['POST'])
def visualSearch():
    file = request.files['file']
    return jsonify(result=predict(file))

@app.route('/updateData', methods=['POST'])
def updateData():
    result = prepredict()
    if result == 0:
        return jsonify("generating")
    elif result == 1:
        return jsonify("started")


@app.route('/trainData', methods=['POST'])
def trainData():
    return
