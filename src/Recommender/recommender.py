#!/usr/bin/env python
# -*- coding: utf-8 -*-

import codecs
import io
import pandas as pd
from rake_nltk import Rake
import numpy as np
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.feature_extraction.text import CountVectorizer



# This reads a csv file, removes unnecassary information and processes columns for recommendations
def dataPreProcessing():

    df = pd.read_csv('movies.csv')
    #df = pf.read_csv('PreProcessingTest.csv')
    df = df[['Title','Genre','Director','Actors','Plot']]

    # discarding the commas between the actors' full names and getting only the first three names
    df['Actors'] = df['Actors'].map(lambda x: x.split(',')[:4])

    # putting the genres in a list of words
    df['Genre'] = df['Genre'].map(lambda x: x.lower().split(','))

    df['Director'] = df['Director'].map(lambda x: x.split(' '))

    for index, row in df.iterrows():
        row['Actors'] = [x.lower().replace(' ','') for x in row['Actors']]
        row['Director'] = ''.join(row['Director']).lower()

        # initialise the new column
        df['Key_words'] = ""

    return df

# This function creates a matrix with each movie on both row and column and finds the ciosine similarity
def similarityFunction():
    df = dataPreProcessing()
    for index, row in df.iterrows():
        plot = str(row['Plot'])

        # instantiating Rake to remove stop words through NLTK
        # and discards all puntuation characters as well
        r = Rake()

        # extracting the key words in plot
        r.extract_keywords_from_text(plot)
        # getting the dictionary whith key words as keys and their scores as values
        key_words_dict_scores = r.get_word_degrees()
        row['Key_words'] = list(key_words_dict_scores.keys())

    # dropping the Plot column
    df.drop(columns = ['Plot'], inplace = True)

    df.set_index('Title', inplace = True)
    df['movieDetails'] = ''
    columns = df.columns
    for index, row in df.iterrows():
        words = ''
        for col in columns:
            if col != 'Director':
                words = words + ' '.join(row[col])+ ' '
            else:
                words = words + row[col]+ ' '
        row['movieDetails'] = words

    df.drop(columns = [col for col in df.columns if col!= 'movieDetails'], inplace = True)
    # instantiating and generating the count matrix
    count = CountVectorizer()
    count_matrix = count.fit_transform(df['movieDetails'])

    indices = pd.Series(df.index)

    # generating the cosine similarity matrix
    global cosine_sim
    cosine_sim = cosine_similarity(count_matrix, count_matrix)
    return df, indices


# find most similar movies to ones inserter as parameter
def recommendations(titles):

    df, indices = similarityFunction()
    allMovieDict = {}
    topDict = {}

    recommended_movies = []
    top_indexes = []


    for title in titles:
        existing = False
        for i in indices:
            if (title == i):
                existing = True
        if (existing == True):
            # gettin the index of the movie that matches the title
            idx = indices[indices == title].index[0]

            # creating a Series with the similarity scores in descending order
            score_series = pd.Series(cosine_sim[idx]).sort_values(ascending = False)

            for x, y in score_series.items():
                allMovieDict[x] = y

            # getting the indexes of the 10 most similar movies
            top_10_indexes = list(score_series.iloc[1:11].index)
            #print (top_10_indexes)
            for i in top_10_indexes:
                topDict[i] = allMovieDict[i]
                top_indexes.append(i)
            #print (topDict)
            #print top_indexes
            # populating the list with the titles of the best 10 matching movies
            for i in top_10_indexes:
                #print (df.index[i])
                topDict[df.index[i]] = topDict.pop(i)
                recommended_movies.append(list(df.index)[i])

            #topDict[list(df.index)[i]] =
    return (topDict)

#print (recommendations(["Die Hard 2", "Shrek"]))
