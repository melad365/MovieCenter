import pyrebase
from recommender import recommendations
import pandas as pd
from collections import OrderedDict




df2 = pd.read_csv('movies.csv')


config = {
    "apiKey": "AIzaSyAOxGUnp2vSOKMOGKGyGDSa1Z_qIhcjz24",
    "authDomain": "moviecentre96.firebaseapp.com",
    "databaseURL": "https://moviecentre96.firebaseio.com",
    "storageBucket": "moviecentre96",
    'projectId': "moviecentre96"
  }

firebase = pyrebase.initialize_app(config)
db = firebase.database()


def stream_handler(message):
    recomm = {}
    movieDict = {}
    favMovies = []
    nodeExists = True

    print("******" , message)
    try:
        MovieDict = db.child().get().val()['Movies']
    except:
        nodeExists = False

    if(nodeExists == True):
        for x, y in MovieDict.items():
            favMovies.append(y)

        recomm = recommendations(favMovies)

        for movie, score in recomm.items():
            id = (df2.pipe(lambda item: item[item['Title'] == movie]['id']))
            id = id.__str__().split()[1]
            recomm[movie] = id
        print (recomm)

        db.child("Recommended").remove()

        for movie, id in recomm.items():
            testDict = {}
            testDict["title"] = movie
            try:
                db.child("Recommended").push(testDict)
            except:
                print ("Error", movie)

my_stream = db.child("Movies").stream(stream_handler,None)


b.child("posts").stream(stream_handler)

