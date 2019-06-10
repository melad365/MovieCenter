import unittest
import recommender
import pandas as pd
from pandas.util.testing import assert_frame_equal

class TestRecommender(unittest.TestCase):


    def test_PreProcessingMethod_EQUAL(self):


        expected = pd.DataFrame(
                    {
                    'Title':
                        ['GoodFellas'],
                    'Genre':
                        [['drama', 'crime']],
                    'Director':
                        ["martinscorsese"],
                    'Actors':
                        [['rayliotta', 'robertdeniro', 'joepesci', 'lorrainebracco']],
                    'Plot':
                        ["The true story of Henry Hill, a half-Irish, half-Sicilian Brooklyn kid who is adopted by neighbourhood gangsters at an early age and climbs the ranks of a Mafia family under the guidance of Jimmy Conway."],
                    'Key_words':
                        [""]
                    })

        input = recommender.dataPreProcessing()
        assert_frame_equal(input, expected)

    def test_PreProcessingMethod_NEQUAL(self):
        expected = pd.DataFrame(
                    {
                    'Title':
                        ['GoodFellas'],
                    'Genre':
                        [['drama', 'crime']],
                    'Director':
                        ["martinscorsese"],
                    'Actors':
                        [['rayliotta', 'robertdeniro', 'joepesci', 'lorrainebracco']],
                    'Plot':
                        ["The true story of Henry Hill, a half-Irish, half-Sicilian Brooklyn kid who is adopted by neighbourhood gangsters at an early age and climbs the ranks of a Mafia family under the guidance of Jimmy Conway."],
                    'Key_words':
                        ["0"]
                    })
        input = recommender.dataPreProcessing()
        try:
            assert_frame_equal(input, expected)
        except AssertionError:
            # frames are not equal
            pass
        else:
            # frames are equal
            raise AssertionError

if __name__ == '__main__': 
    unittest.main()
