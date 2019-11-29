from flask import Flask, request, jsonify
from json import dump
from flask.json import JSONEncoder

import predictService
import trainingService


class MyJSONEncoder(JSONEncoder):
    def default(self, obj):
        if isinstance(obj, predictService.ImageSearchResult):
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
    limit = request.form['limit']
    return jsonify(result=predictService.predict(file, limit))


@app.route('/updateData', methods=['POST'])
def updateData():
    result = trainingService.prepredict()
    if result == 0:
        return jsonify(status="data update process is already running")
    elif result == 1:
        return jsonify(status="data update process started")


@app.route('/trainModel', methods=['POST'])
def trainData():
    result = trainingService.trainData()
    if result == 0:
        return jsonify(status="model training process is already running")
    elif result == 1:
        return jsonify(status="model training process started")
