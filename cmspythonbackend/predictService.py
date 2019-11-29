import glob
import os
import pickle

import numpy as np
import sklearn.metrics.pairwise
import tensorflow as tf
from PIL import ImageFile
from keras.applications.vgg16 import preprocess_input
from keras.models import load_model
from keras.preprocessing import image as kimage
from tensorflow.python.keras.backend import set_session
from werkzeug.utils import secure_filename

ImageFile.LOAD_TRUNCATED_IMAGES = True

graph = tf.get_default_graph()
sess = tf.Session()
set_session(sess)

resnet = 100352
vgg = 25088

file_object = open(r"pythonpaths.cfg", "r")
paths = file_object.readlines()
savedatapath = ''
vsmodelpath = ''
temppath = ''
for path in paths:
    if path.startswith('visualsearch-model'):
        path = path.split('=')
        vsmodelpath = path[1].strip('\n')
    elif path.startswith('savedfeatures'):
        path = path.split('=')
        savedatapath = path[1].strip('\n')
    elif path.startswith('tempimage'):
        path = path.split('=')
        temppath = path[1].strip('\n')
# get the latest model file
listModel = glob.glob(os.getcwd() + vsmodelpath + '\\*.h5')
if (len(listModel) == 0):
    print('No model found')
latestModel = max(listModel, key=os.path.getctime)
print('[INFO] Using model ' + latestModel + ' for predicting.')
model = load_model(latestModel)

listImage = glob.glob(os.getcwd() + savedatapath + '\\*.data')
if (len(listImage) == 0):
    print('No image list found')
latestImage = max(listImage, key=os.path.getctime)
print('[INFO] Using list image in ' + latestModel + 'for predicting.')

listPredict = glob.glob(os.getcwd() + savedatapath + '\\*.npy')
if (len(listPredict) == 0):
    print('No predict found')
latestPredict = max(listPredict, key=os.path.getctime)
print('[INFO] Using pre-predict in ' + latestModel + 'for predicting.')
predicts = np.load(latestPredict)

def predict(file, limit):
    limit = int(limit)
    filename = secure_filename(file.filename);
    imgpath = os.getcwd() + temppath + '\\' + filename
    file.save(imgpath)
    global predicts
    # img = skimage.io.imread(imgpath)
    img = kimage.load_img(imgpath, target_size=(224, 224))
    x = kimage.img_to_array(img)
    x = np.expand_dims(x, axis=0)
    x = preprocess_input(x)
    print('[INFO] Predicting ...')
    with graph.as_default():
        set_session(sess)
        p = model.predict(x, verbose=1)

    p = np.reshape(p, (1, 100352))
    print('[INFO] Starting cosine similarity ...')
    norm = sklearn.metrics.pairwise.cosine_similarity(p, predicts)
    indices = norm.argsort()[:, ::-1][:, 0:limit]
    print('[INFO] Finished cosine similarity, the best 10 result was: ')
    print(indices[:][0:10])
    indices = indices.tolist()[0]
    print(indices[0:10])
    sortednorm = np.sort(norm)[:, ::-1][:, 0:limit]
    print(sortednorm[:][0:10])
    sortednorm = sortednorm.tolist()[0]
    print(sortednorm[:][0:10])
    print('[INFO] Loading image list... ')
    with open(latestImage, 'rb') as fileread:
        imagelist = pickle.load(fileread)
    result = list()
    print('[INFO] Fetching data... ')
    for i in range(len(indices)):
        imageResult = ImageSearchResult(imagelist[indices[i]], sortednorm[i])
        result.append(imageResult)
    return result


class ImageSearchResult(object):
    def __init__(self, image, similarity):
        self.image = image
        self.similarity = similarity