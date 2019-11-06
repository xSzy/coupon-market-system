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
import tensorflow as tf;

df = pd.read_csv('C:\\Users\\Win10\\Documents\\filelist.csv', header=None);
model = load_model('C:\\Users\\Win10\\Documents\\trainedresnet.h5');
graph = tf.get_default_graph();

resnet = 100352;
vgg = 25088;

def predict(imgpath):
    predicts = np.load('C:\\Users\\Win10\\Documents\\100352imgfeattrainedresnet50.npy');
    img = skimage.io.imread(imgpath);
    img = kimage.load_img(imgpath, target_size=(224, 224));
    x = kimage.img_to_array(img);
    x = np.expand_dims(x, axis=0);
    x = preprocess_input(x);

    with graph.as_default():
        p = model.predict(x, verbose=1);

    p = np.reshape(p, (1, 100352));
    norm = sklearn.metrics.pairwise.cosine_similarity(p, predicts);
    indices = norm.argsort()[:, ::-1][:, 0:5];
    return indices.tolist();