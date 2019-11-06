import skimage.io;
import numpy as np;
import pandas as pd;
import sklearn.metrics.pairwise;
from scipy.sparse import coo_matrix;
from IPython.display import Image, display
from keras.applications import VGG16, ResNet50;
from keras.applications.vgg16 import preprocess_input;
from keras.preprocessing.image import ImageDataGenerator;
from keras.preprocessing import image as kimage;
from keras.models import load_model;
import glob;
import os;
import tensorflow as tf;
from datetime import datetime;
import threading;

graph = tf.get_default_graph();

imagelist = [];
is_generating = False;

# read path from cfg
file_object = open(r"pythonpaths.cfg","r");
paths = file_object.readlines();
file_object.close();
vsmodelpath = '';
for path in paths:
    if path.startswith('visualsearch-model'):
        path = path.split('=');
        vsmodelpath = path[1].strip('\n');

def prepredict():
    global is_generating;
    if is_generating == True:
        return "generating";

    # get the latest model file
    listFiles = glob.glob(vsmodelpath + '\\*.h5');
    if(len(listFiles) == 0):
        return "no model found";
    latestModel = max(listFiles, key=os.path.getctime);

    # Get images
    datagen = ImageDataGenerator();
    image_it = datagen.flow_from_directory(directory='D:\\cmsdata\\trainingset', target_size=(224, 224),
                                           class_mode=None, batch_size=1, shuffle=False);
    # print(image_it.filepaths);
    global imagelist;
    imagelist = image_it.filepaths;

    is_generating = True;

    t1 = threading.Thread(target=predict_job, args=(latestModel, image_it));
    t1.start();

    return "starting";

def predict_job(modelpath, imageiterator):
    with graph.as_default():
        model = load_model(modelpath);
        predicts = model.predict_generator(imageiterator, verbose=1);

    # save predicts to file
    predicts = np.reshape(predicts, (predicts.shape[0], 100352));
    savefilename = datetime.now().strftime('%Y%m%d%H%M%S') + '_data.npy';
    np.save('D:\\cmsdata\\savedata\\' + savefilename, predicts);
    global is_generating;
    is_generating = False;