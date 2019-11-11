<<<<<<< HEAD
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
=======
import numpy as np;
import keras;
from keras.layers import Dense, Dropout, Flatten;
from keras.optimizers import Nadam, SGD;
from keras.models import Model;
from keras.preprocessing.image import ImageDataGenerator;
>>>>>>> a75647489322773e18671b923adc952b0940e110
from keras.models import load_model;
import glob;
import os;
import tensorflow as tf;
from datetime import datetime;
import threading;

graph = tf.get_default_graph();

imagelist = [];
is_generating = False;
<<<<<<< HEAD
=======
is_training = False;
>>>>>>> a75647489322773e18671b923adc952b0940e110

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
<<<<<<< HEAD
        return "generating";
=======
        return 0;
>>>>>>> a75647489322773e18671b923adc952b0940e110

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

<<<<<<< HEAD
    return "starting";
=======
    return 1;
>>>>>>> a75647489322773e18671b923adc952b0940e110

def predict_job(modelpath, imageiterator):
    with graph.as_default():
        model = load_model(modelpath);
        predicts = model.predict_generator(imageiterator, verbose=1);

    # save predicts to file
    predicts = np.reshape(predicts, (predicts.shape[0], 100352));
    savefilename = datetime.now().strftime('%Y%m%d%H%M%S') + '_data.npy';
    np.save('D:\\cmsdata\\savedata\\' + savefilename, predicts);
    global is_generating;
<<<<<<< HEAD
    is_generating = False;
=======
    is_generating = False;

def trainData():
    global is_training;
    if is_training == True:
        return 0;
    datagen = ImageDataGenerator(
        rotation_range=20,
        width_shift_range=0.2,
        height_shift_range=0.2,
        horizontal_flip=True
    );
    train_it = datagen.flow_from_directory('drive/My Drive/cmsdata/trainingset', target_size=(224, 224),
                                           class_mode='categorical');
    val_it = datagen.flow_from_directory('drive/My Drive/cmsdata/validationset', target_size=(224, 224),
                                         class_mode='categorical');
    model = keras.applications.resnet.ResNet50(include_top=False, weights='imagenet', input_shape=(224, 224, 3));
    x = model.output;
    x = Flatten()(x);
    x = Dense(128, activation='relu')(x);
    x = Dropout(0.25)(x);
    x = Dense(64, activation='relu')(x);
    x = Dense(11, activation='softmax', name='softmax')(x);
    finalmodel = Model(inputs=model.input, output=x);
    finalmodel.summary();
    finalmodel.compile(optimizer=Nadam(lr=0.0001), loss='categorical_crossentropy', metrics=['accuracy']);

    is_training = True;
    t1 = threading.Thread(target=train_job, args=(finalmodel, train_it, val_it));
    t1.start();

    return;

def train_job(finalmodel, train_it, val_it):
    finalmodel.fit_generator(train_it, epochs=25, validation_data=val_it,
                             callbacks=[keras.callbacks.ModelCheckpoint('drive/My Drive/cmsfullweightautosave.h5',
                                                                        monitor='val_acc', save_best_only=True,
                                                                        mode='max', period=1)]);

    # cut the last classification layers
    model = load_model('C:\\Users\\Win10\\Documents\\50epochs.h5');
    newmodel = Model(inputs=model.input, outputs=model.get_layer('conv5_block3_out').output);
    newmodel.summary();
    newmodel.save(vsmodelpath + '\\' + datetime.now().strftime('%Y%m%d%H%M%S') + '_model.h5');

    global is_training;
    is_training = False;
>>>>>>> a75647489322773e18671b923adc952b0940e110
