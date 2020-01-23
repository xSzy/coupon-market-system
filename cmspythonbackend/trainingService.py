import glob
import os

import numpy as np
import threading
from datetime import datetime
import pickle
import keras
import tensorflow as tf
from keras.layers import Dense, Dropout, Flatten
from keras.models import Model
from keras.models import load_model
from keras.optimizers import Nadam
from keras.preprocessing.image import ImageDataGenerator
from tensorflow.python.keras.backend import set_session
from PIL import ImageFile
ImageFile.LOAD_TRUNCATED_IMAGES = True

graph = tf.get_default_graph()
sess = tf.Session()
set_session(sess)

learning_rates = [0.01, 0.001, 0.0001]
dropout_values = [0.1, 0.25, 0.4]
epoch_values = [10, 20, 50]

imagelist = []
is_generating = False
is_training = False

# read path from cfg
file_object = open(r"pythonpaths.cfg", "r")
paths = file_object.readlines()
file_object.close()
vsmodelpath = ''
savedatapath = ''
classcount = ''
classificationpath = ''
for path in paths:
    if path.startswith('visualsearch-model'):
        path = path.split('=')
        vsmodelpath = path[1].strip('\n')
    elif path.startswith('savedfeatures'):
        path = path.split('=')
        savedatapath = path[1].strip('\n')
    elif path.startswith('classes'):
        path = path.split('=')
        classcount = path[1].strip('\n')
    elif path.startswith('classification-model'):
        path = path.split('=')
        classificationpath = path[1].strip('\n')

def prepredict():
    global is_generating
    if is_generating == True:
        return 0

    # get the latest model file
    listFiles = glob.glob(os.getcwd() + vsmodelpath + '\\*.h5')
    if (len(listFiles) == 0):
        return 0
    latestModel = max(listFiles, key=os.path.getctime)
    print("[INFO] Pre-predict is now using model : " + latestModel)

    # Get images
    datagen = ImageDataGenerator()
    imagedata_directory = os.getcwd();
    imagedata_directory = imagedata_directory.replace('cmspythonbackend', 'cms\\target\\classes\\public\\static\\cmsdata')
    print(imagedata_directory)
    image_it = datagen.flow_from_directory(directory=imagedata_directory, target_size=(224, 224),
                                           class_mode=None, batch_size=1, shuffle=False)
    is_generating = True

    t1 = threading.Thread(target=predict_job, args=(latestModel, image_it))
    t1.start()
    print("[INFO] Pre-predicting started.")
    return 1


def predict_job(modelpath, imageiterator):
    global savedatapath
    with graph.as_default():
        set_session(sess)
        model = load_model(modelpath)
        predicts = model.predict_generator(imageiterator, verbose=1, steps=len(imageiterator.filepaths))

    # save predicts to file
    print("[INFO] Pre-predicting finished, attempting to save to file.")
    predicts = np.reshape(predicts, (predicts.shape[0], 100352))
    savefilename = datetime.now().strftime('%Y%m%d%H%M%S') + '_data.npy'
    np.save(os.getcwd() + savedatapath + '\\' + savefilename, predicts)
    imagelist = imageiterator.filepaths;
    imagelistfilename = datetime.now().strftime('%Y%m%d%H%M%S') + '_imagelist.data'
    with open(os.getcwd() + savedatapath + '\\' + imagelistfilename, 'wb') as filewrite:
        pickle.dump(imagelist, filewrite)
    global is_generating
    is_generating = False
    print('[INFO] Data pre-predict done')


def trainData():
    global is_training
    if is_training == True:
        return 0

    is_training = True
    t1 = threading.Thread(target=train_job, args=())
    t1.start()

    return 1


def train_job():
    datagen = ImageDataGenerator(
        # rotation_range=20,
        # width_shift_range=0.2,
        # height_shift_range=0.2,
        # horizontal_flip=True
    )
    imagedata_directory = os.getcwd();
    imagedata_directory = imagedata_directory.replace('cmspythonbackend',
                                                      'cms\\target\\classes\\public\\static\\cmsdata')
    train_it = datagen.flow_from_directory(imagedata_directory, target_size=(224, 224),
                                           class_mode='categorical', subset="training")
    val_it = datagen.flow_from_directory(imagedata_directory, target_size=(224, 224),
                                         class_mode='categorical', subset="validation")
    model = keras.applications.resnet.ResNet50(include_top=False, weights='imagenet', input_shape=(224, 224, 3))

    max_accuracy = 0,
    max_accuracy_pos = (0, 0, 0)
    for lr in learning_rates:
        for dropout in dropout_values:
            for epoch in epoch_values:
                x = model.output
                x = Flatten()(x)
                x = Dense(128, activation='relu')(x)
                x = Dropout(dropout)(x)
                x = Dense(64, activation='relu')(x)
                x = Dense(int(classcount), activation='softmax', name='softmax')(x)
                finalmodel = Model(inputs=model.input, output=x)
                finalmodel.summary()
                finalmodel.compile(optimizer=Nadam(lr=lr), loss='categorical_crossentropy', metrics=['accuracy'])

                global classificationpath
                classificationfilename = str(lr) + str(dropout) + str(epoch)\
                                         + '_classification.h5'
                classificationpath = os.getcwd() + classificationpath + '\\' + classificationfilename
                finalmodel.fit_generator(train_it, epochs=epoch, validation_data=val_it,
                                         callbacks=[keras.callbacks.ModelCheckpoint(classificationpath,
                                                                                    monitor='val_acc', save_best_only=True,
                                                                                    mode='max', period=1)])
                #evaluate
                scores = finalmodel.evaluate_generator(val_it)
                accuracy = scores[1]*100
                if accuracy > max_accuracy:
                    max_accuracy = accuracy
                    max_accuracy_pos = (lr, dropout, epoch)

    # cut the last classification layers
    classificationfilename = str(max_accuracy_pos[0]) + str(max_accuracy_pos[1]) + str(max_accuracy_pos[2]) \
                             + '_classification.h5'
    classificationpath = os.getcwd() + classificationpath + '\\' + classificationfilename
    model = load_model(classificationpath)
    newmodel = Model(inputs=model.input, outputs=model.get_layer('conv5_block3_out').output)
    newmodel.summary()
    newmodel.save(os.getcwd() + vsmodelpath + '\\' + datetime.now().strftime('%Y%m%d%H%M%S') + '_model.h5')

    global is_training
    is_training = False
