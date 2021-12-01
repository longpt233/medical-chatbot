import tensorflow as tf
from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing.image import load_img
import numpy as np
import cv2


model = tf.keras.models.load_model('./weight/unet_mobi.h5', compile=False)
# load ok 
# model.summary()

"""
nhan dau vao la anh 256*256
ghi anh 256*256 ra file static/img 
"""
def predict(image):
    predict = model.predict(np.expand_dims(image, axis=0))[:]

    predict_max = np.argmax(predict, axis=-1)   # Returns the indices of the maximum values along an axis
    img =predict_max[0]
    test = np.zeros((256,256,3), dtype=np.float32)   # why int 64 ?
    for i in range(255):
      for j in range(255):
        if img[i,j] == 1:
          test[i,j] = [0,0,255]
        if img[i,j] == 2:
          test[i,j] = [0,255,0] 

    image = image*255.0
    test = cv2.resize(test, image.shape[1::-1])

    dst = cv2.addWeighted(image, 0.5, test, 0.5, 0) 
    return dst


def read_image(image_path):
    image = cv2.imread(image_path, cv2.IMREAD_COLOR)
    image = cv2.resize(image, (256, 256))
    image = image/255.0
    image = image.astype(np.float32)
    return image


if __name__ == '__main__':
    filename = "fdcc58c9c990b4e2a6e51d077bad31c8.jpeg"
    image = read_image('./static/img/'+filename)
    predict(image)
