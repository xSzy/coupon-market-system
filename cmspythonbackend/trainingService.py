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
from PIL import ImageFile
ImageFile.LOAD_TRUNCATED_IMAGES = True

graph = tf.get_default_graph()

imagelist = []
is_generating = False
is_training = False

# read path from cfg
file_object = open(r"pythonpaths.cfg", "r")
paths = file_object.readlines()
file_object.close()
vsmodelpath = ''
savedatapath = ''
for path in paths:
    if path.startswith('visualsearch-model'):
        path = path.split('=')
        vsmodelpath = path[1].strip('\n')
    elif path.startswith('savedfeatures'):
        path = path.split('=')
        savedatapath = path[1].strip('\n')

def prepredict():
    global is_generating
    if is_generating == True:
        return 0

    # get the latest model file
    listFiles = glob.glob(os.getcwd() + vsmodelpath + '\\*.h5')
    if (len(listFiles) == 0):
        return 0
    latestModel = max(listFiles, key=os.path.getctime)

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

    return 1


def predict_job(modelpath, imageiterator):
    global savedatapath
    with graph.as_default():
        model = load_model(modelpath)
        predicts = model.predict_generator(imageiterator, verbose=1)

    # save predicts to file
    predicts = np.reshape(predicts, (predicts.shape[0], 100352))
    savefilename = datetime.now().strftime('%Y%m%d%H%M%S') + '_data.npy'
    np.save(os.getcwd() + savedatapath + '\\' + savefilename, predicts)
    imagelist = imageiterator.filepaths;
    imagelistfilename = datetime.now().strftime('%Y%m%d%H%M%S') + '_imagelist.data'
    with open(os.getcwd() + savedatapath + '\\' + imagelistfilename, 'wb') as filewrite:
        pickle.dump(imagelist, filewrite)
    global is_generating
    is_generating = False
    print('Data pre-predict done')


def trainData():
    global is_training
    if is_training == True:
        return 0
    datagen = ImageDataGenerator(
        rotation_range=20,
        width_shift_range=0.2,
        height_shift_range=0.2,
        horizontal_flip=True
    )
    train_it = datagen.flow_from_directory('drive/My Drive/cmsdata/trainingset', target_size=(224, 224),
                                           class_mode='categorical')
    val_it = datagen.flow_from_directory('drive/My Drive/cmsdata/validationset', target_size=(224, 224),
                                         class_mode='categorical')
    model = keras.applications.resnet.ResNet50(include_top=False, weights='imagenet', input_shape=(224, 224, 3))
    x = model.output
    x = Flatten()(x)
    x = Dense(128, activation='relu')(x)
    x = Dropout(0.25)(x)
    x = Dense(64, activation='relu')(x)
    x = Dense(11, activation='softmax', name='softmax')(x)
    finalmodel = Model(inputs=model.input, output=x)
    finalmodel.summary()
    finalmodel.compile(optimizer=Nadam(lr=0.0001), loss='categorical_crossentropy', metrics=['accuracy'])

    is_training = True
    t1 = threading.Thread(target=train_job, args=(finalmodel, train_it, val_it))
    t1.start()

    return


def train_job(finalmodel, train_it, val_it):
    finalmodel.fit_generator(train_it, epochs=25, validation_data=val_it,
                             callbacks=[keras.callbacks.ModelCheckpoint('drive/My Drive/cmsfullweightautosave.h5',
                                                                        monitor='val_acc', save_best_only=True,
                                                                        mode='max', period=1)])

    # cut the last classification layers
    model = load_model('C:\\Users\\Win10\\Documents\\50epochs.h5')
    newmodel = Model(inputs=model.input, outputs=model.get_layer('conv5_block3_out').output)
    newmodel.summary()
    newmodel.save(vsmodelpath + '\\' + datetime.now().strftime('%Y%m%d%H%M%S') + '_model.h5')

    global is_training
    is_training = False
