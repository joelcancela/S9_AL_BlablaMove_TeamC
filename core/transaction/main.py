from flask import Flask
from flask_restful import reqparse, abort, Api, Resource

app = Flask(__name__)
api = Api(app)

numberOfPostCalls = 0

class Counter(Resource):
    def get(self):
        return numberOfPostCalls

    def post(self):
        global numberOfPostCalls
        numberOfPostCalls = numberOfPostCalls + 1
        return "OK"


api.add_resource(Counter, '/')

if __name__ == '__main__':
    app.run(debug=True, port=8080)
