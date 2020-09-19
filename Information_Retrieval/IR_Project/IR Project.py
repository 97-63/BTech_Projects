import nltk
from nltk.tokenize import word_tokenize
from nltk.tokenize import sent_tokenize
from nltk.tokenize import RegexpTokenizer
from nltk.corpus import stopwords
from apiclient.discovery import build
import requests
from bs4 import BeautifulSoup
from nltk.text import Text
import string, re
# import SentimentIntensityAnalyzer class 
# from vaderSentiment.vaderSentiment module. 
from vaderSentiment.vaderSentiment import SentimentIntensityAnalyzer 



speech=open('t.txt','r',encoding="utf8").read()
sentences=sent_tokenize(speech)#sentence tokenizing from strings

# function to print sentiments 
# of the sentence. 
def sentiment_scores(sentence): 
  
    # Create a SentimentIntensityAnalyzer object. 
    sid_obj = SentimentIntensityAnalyzer() 
  
    # polarity_scores method of SentimentIntensityAnalyzer 
    # oject gives a sentiment dictionary. 
    # which contains pos, neg, neu, and compound scores. 
    return sid_obj.polarity_scores(sentence) 
      

# Driver code
COMPOUNDSCORE=0#for adding the compound scores of each sentence.
POSITIVE=0
NEGATIVE=0
NEUTRAL=0
for x in sentences:
    sentiment_dict=sentiment_scores(x)
    COMPOUNDSCORE=COMPOUNDSCORE+sentiment_dict['compound']
    POSITIVE=POSITIVE+sentiment_dict['pos']
    NEGATIVE=NEGATIVE+sentiment_dict['neg']
    NEUTRAL=NEUTRAL+sentiment_dict['neu']

AVGcompoundscore=(COMPOUNDSCORE/len(sentences))*100
AVGpositivescore=(POSITIVE/len(sentences))*100
AVGnegativescore=(NEGATIVE/len(sentences))*100
AVGneutralscore=(NEUTRAL/len(sentences))*100

# decide sentiment as positive, negative and neutral 
if AVGcompoundscore >= 0.05 : 
    print("Content is Positive ,the Compound Score is",AVGcompoundscore) 
  
elif AVGcompoundscore <= - 0.05 : 
    print("Content is Negative and the Compound Score is",AVGcompoundscore) 
  
else : 
    print("Content is Neutral and the Compound Score is",AVGcompoundscore)
    
    
    
    
  
