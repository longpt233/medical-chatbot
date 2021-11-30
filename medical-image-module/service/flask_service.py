import imp
import flask
import model
from flask import request, jsonify, send_file
from flask_uploads import UploadSet, configure_uploads, IMAGES
import os
import cv2
import numpy as np



app = flask.Flask(__name__)

photos = UploadSet('photos', IMAGES)

# path for saving uploaded images
app.config['UPLOADED_PHOTOS_DEST'] = './static/img'
configure_uploads(app, photos)


@app.route("/hello")
def homepage():
	return "Welcome to the flask REST API!"


@app.route('/upload', methods=['GET', 'POST'])
def upload():
	if request.method == 'POST' and 'photo' in request.files:

		# save the image
		filename = photos.save(request.files['photo'])
		# load the image
		image = model.read_image('./static/img/'+filename)
		
		# process the image
		image_with_mask = model.predict(image)
		# save predict model
		cv2.imwrite('./static/predict/'+filename,image_with_mask)
		
		# return jsonify(filename)

		response_image =  send_file('./static/predict/'+filename, mimetype='image/gif')

		# del image 
		os.remove('./static/img/'+filename)
		os.remove('./static/predict/'+filename)

		# return image
		return response_image
	

if __name__ == "__main__":
	print("* Starting web service...")
	app.run(host='0.0.0.0',port=8083)