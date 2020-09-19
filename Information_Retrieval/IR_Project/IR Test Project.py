#if something is not clear after reading comments corresponding to code related to Google Custom Search Engine then do watch this video"https://www.youtube.com/watch?v=IBhdLRheKyM&t=612s"
import nltk
#nltk.download()
import requests
from bs4 import BeautifulSoup
from nltk.tokenize import word_tokenize
#we are now going to write code so that our python application can interact with "Google custom search engine".Therefore for that we are going to use "Custom Search
#JSON API"
#The Custom Search JSON API lets you develop websites and applications to retrieve and display search results from Google Custom Search programmatically. With this API,
#you can use RESTful requests(restful requests are nothing but HTTP requests because REST API uses HTTP requests) to get either web search or image search results in
#JSON format.  
from apiclient.discovery import build#this statement is used to check whether the library is installed or not and whether other things like enabling of
#"Custom Search JSON API" service has been done or not.In short it will prompt a error if any step is not followed properly.
from nltk.tokenize import sent_tokenize
from nltk.corpus import stopwords
from nltk.text import Text
import string, re
# import SentimentIntensityAnalyzer class 
# from vaderSentiment.vaderSentiment module. 
from vaderSentiment.vaderSentiment import SentimentIntensityAnalyzer

link=[]#storing a list of links
API_key="AIzaSyAkyDSWq4bqQkicsjwBy-0sIBBs1lsRSpg"#this will be the credentials which will be used to use the "Custom Search JSON API" service
#we have also installed an important library "google-api-python-client" so that we can interact with "Custom Search JSON API" 
resource=build("customsearch",'v1',developerKey=API_key).cse()#now we will create a resource object which using "build" module where we have passed 3 things
#1)Service name 2)Version number 3)API Key and after that we have created a custom search engine object by calling ".cse()" at the end. We have created this object in
#order to send requests to Google's Custom Search Engine API .
for i in range(1,10):
    result=resource.list(q='Fadnavis',cx='008883652953486625000:fgs4ef2yo4b',start=i).execute()#list() will return me the search results here 'q' is the query and 'cx'
#search engine ID which has already created by you while enabling the service of "Custom Search JSON API" and finally we need to execute this request hence we have
#called "execute()"     
print("the title and links of a search are:")
#now we will see the response recieved using the "result[items]" and the response send is in JSON format. BY default we get 10 results 
for item in result['items']:
   print(item['title'],item['link'])#as there may be "10" results therefore we are trying we have used for loop for it.
   link.append(item['link'])
#print(link)
j=0
for i in link:
   #print(i)
   url=i
   j=j+1
   
   response = requests.get(url)#here we are storing the response given by the server for the request given
   data = response.text#here we are trying to store the source code of the webpage or we are storing the raw html code of the webpage
   soup = BeautifulSoup(data, 'lxml')#now we are creating the "beutiful soup" object which will be used to create a "parse tree" by using parser "lxml"
   text = soup.find_all(text=True)#BeautifulSoup provides a simple way to find text content (i.e. non-HTML) from the HTML: "Plz read from "https://matix.io/extract-text-from-webpage-using-beautifulsoup-and-python/""
   output = ''

   blacklist = [
      '[document]',
      'noscript',
      'style',
      'header',
      'html',
      'meta',
      'head',
      'input',
      'script',
      ',',
      ':',
      '.'
      '?'
      # there may be more elements you don't want, such as "style", etc.
         ]

   for t in text:
      if t.parent.name not in blacklist:
         output += t

   
   sentences=sent_tokenize(output)#sentence tokenizing from strings

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
       print(url,"Content is Positive ,the Compound Score is",AVGcompoundscore) 
  
   elif AVGcompoundscore <= - 0.05 : 
       print(url,"Content is Negative and the Compound Score is",AVGcompoundscore) 
  
   else : 
       print(url,"Content is Neutral and the Compound Score is",AVGcompoundscore)
    


    
    
    
  
