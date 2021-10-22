import flask

app = flask.Flask(__name__)

@app.route("/")
def homepage():
	return "Welcome to the flask REST API!"


if __name__ == "__main__":
	print("* Starting web service...")
	app.run(host='0.0.0.0',port=8083)