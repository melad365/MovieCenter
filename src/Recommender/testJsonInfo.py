from nose.tools import assert_true
from nose.tools import assert_false
import pandas as pd
from pandas.util.testing import assert_frame_equal
import jsonInfo
import requests
import unittest


class APItesting(unittest.TestCase):
    def test_request_response_true(self):
        # Send a request to the API server and store the response.
        response = requests.get('https://api.themoviedb.org/3/discover/movie?api_key=3a99e9b91f001ce52e943ec17f668e45')

        # Confirm that the request-response cycle completed successfully.
        assert_true(response.ok)

    def test_request_response_true_false(self):
        # Send a request to the API server and store the response.
        response = requests.get('https://api.themoviedb.org/3/discover/movie?api_key=notAnApi')

        # Confirm that the request-response cycle completed successfully.
        assert_false(response.ok)

    def test_createCSV(self):
        df = jsonInfo.createCSV()
        df2 = pd.read_csv('PreProcessingTest.csv')
        df.insert(loc=0, column='Unnamed: 0', value = None)
        assert_frame_equal(df, df2)
