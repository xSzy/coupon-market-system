import skimage.io
import numpy as np
import glob
import os
import sklearn.metrics.pairwise
from werkzeug.utils import secure_filename
import pickle
import json
from IPython.display import Image, display
from keras.applications import VGG16, ResNet50
from keras.applications.vgg16 import preprocess_input
from keras.preprocessing.image import ImageDataGenerator
from keras.preprocessing import image as kimage
from keras.models import load_model
import tensorflow as tf
from PIL import ImageFile

ImageFile.LOAD_TRUNCATED_IMAGES = True

graph = tf.get_default_graph()

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
model = load_model(latestModel)

listImage = glob.glob(os.getcwd() + savedatapath + '\\*.data')
if (len(listImage) == 0):
    print('No image list found')
latestImage = max(listImage, key=os.path.getctime)

listPredict = glob.glob(os.getcwd() + savedatapath + '\\*.npy')
if (len(listPredict) == 0):
    print('No predict found')
latestPredict = max(listPredict, key=os.path.getctime)

predicts = np.load(latestPredict)

def predict(file):
    filename = secure_filename(file.filename);
    imgpath = os.getcwd() + temppath + '\\' + filename
    file.save(imgpath)
    global predicts
    # img = skimage.io.imread(imgpath)
    img = kimage.load_img(imgpath, target_size=(224, 224))
    x = kimage.img_to_array(img)
    x = np.expand_dims(x, axis=0)
    x = preprocess_input(x)

    with graph.as_default():
        p = model.predict(x, verbose=1)

    p = np.reshape(p, (1, 100352))
    norm = sklearn.metrics.pairwise.cosine_similarity(p, predicts)
    indices = norm.argsort()[:, ::-1][:, :]
    indices = indices.tolist()[0]
    sortednorm = np.sort(norm)[:, ::-1][:, :]
    sortednorm = sortednorm.tolist()[0]
    with open(latestImage, 'rb') as fileread:
        imagelist = pickle.load(fileread)
    result = list()
    for i in range(len(indices)):
        imageResult = ImageSearchResult(imagelist[indices[i]], sortednorm[i])
        result.append(imageResult)
    return result


class ImageSearchResult(object):
    def __init__(self, image, similarity):
        self.image = image
        self.similarity = similarity