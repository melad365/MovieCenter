import requests # to make TMDB API calls

import pandas as pd
import numpy as np



def createCSV():
    # define column names for our new dataframe
    columns = ['Title', 'Genre', 'Director','Actors', 'Plot', 'id', 'release year']
    # create dataframe with film and revenue columns
    df = pd.DataFrame(columns=columns)
    df.to_csv('PreProcessingTest.csv')

    return df

def updateDF():
    df = createCSV()
    api_key = "3a99e9b91f001ce52e943ec17f668e45"

    for year in range(1990, 2020):

        for j in range(1, 4):
            response = requests.get('https://api.themoviedb.org/3/discover/movie?api_key=' +  api_key + '&append_to_response=credits&primary_release_year=' + str(year) + '&page=' + str(j))
            #print (response.json())

            jsonData = response.json() # store parsed json response
            #print (jsonData)

            #https://api.themoviedb.org/3/discover/movie?api_key=3a99e9b91f001ce52e943ec17f668e45

            jsonData_films = jsonData['results']

            # for each of the highest revenue films make an api call for that specific movie to return the budget and revenue
            for film in jsonData_films:
                # print(film['title'])
                film_revenue = requests.get('https://api.themoviedb.org/3/movie/'+ str(film['id']) +'?api_key='+ api_key+'&append_to_response=credits&language=en-US')
                #print (film_revenue.json())
                film_revenue = film_revenue.json()
                #print(locale.currency(film_revenue['revenue'], grouping=True ))
                #df.loc[len(df)]=[film['title'],film_revenue['revenue'], film_revenue['overview'], film_revenue['genres']] # store title and revenue in our dataframe
                genres = []
                actors = []
                #print (film_revenue['genres'])
                for i in range (len(film_revenue['genres'])):
                    genres.append(film_revenue['genres'][i]['name'])

                for i in range (len(film_revenue['credits']['crew'])):
                    if (film_revenue['credits']['crew'][i]['job'] == 'Director'):
                        director = (film_revenue['credits']['crew'][i]['name'])


                if (len(film_revenue['credits']['cast']) < 4):
                    continue
                else:
                    for i in range (4):
                        actors.append((film_revenue['credits']['cast'][i]['name']))

                df.loc[len(df)]=[film['title'],','.join(genres),director,  ','.join(actors), film_revenue['overview'], film['id'], film['release_date']]
    return df

def removeEmptyCells():
    df = updateDF()
    #replace any empty strings in the column with np.nan object
    df['Genre'].replace('', np.nan, inplace=True)
    df['Director'].replace('', np.nan, inplace=True)
    df['Plot'].replace('', np.nan, inplace=True)
    df['Actors'].replace('', np.nan, inplace=True)
    #drop the null values i.e all rows:
    df.dropna(subset=['Genre'], inplace=True)
    df.dropna(subset=['Director'], inplace=True)
    df.dropna(subset=['Plot'], inplace=True)
    df.dropna(subset=['Actors'], inplace=True)

def writeToCSV():
    df = removeEmptyCells()
    with open('movies.csv', 'a') as f:
        df.to_csv(f, header = False)
    #df.to_csv(r'test.csv')

'''
https://api.themoviedb.org/3/discover/movie?api_key=3a99e9b91f001ce52e943ec17f668e45&append_to_response=credits&primary_release_year=2017&sort_by=revenue.desc
'''
