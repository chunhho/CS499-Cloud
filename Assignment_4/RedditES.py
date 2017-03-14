import json
import requests
import threading
import random
from elasticsearch import Elasticsearch, RequestsHttpConnection

def insertDataToES(*args):

    es = Elasticsearch(
        hosts=[{'host': 'search-cs499-a4-fttrsankja6yyuq5uaazbgtaza.us-west-1.es.amazonaws.com', 'port': 443}],
        use_ssl=True,
        verify_certs=True,
        connection_class=RequestsHttpConnection
    )

    my_index_name = 'reddit'

    if es.exists(index=my_index_name, doc_type=args[0], id=args[1]):
        es.update(index=my_index_name, doc_type=args[0], id=args[1],
                  body={"doc": {'num_comments': args[4], 'score': args[5]}})
        print "Updated entry of id: " + args[1]

    else:
        es.index(index=my_index_name, doc_type=args[0], id=args[1], body={
            'id': args[1],
            'author': args[2],
            'title': args[3],
            'num_comments': args[4],
            'score': args[5]
        })

        print "Added entry in " + args[0] + " with its id: " + args[1]


def pullDataFromReddit():

    baseurl = "https://www.reddit.com/"
    categories = ["", "new/", "rising/", "controversial/"]

    urltext = baseurl + random.choice(categories) + ".json"

    print "Getting info from: " + urltext

    request = requests.get(urltext, headers={'User-agent': 'myRedditScrapper'})
    page = json.loads(request.text)

    for entry in page['data']['children']:

       #Upload data to ES
       insertDataToES(entry['data']['subreddit'],entry['data']['id'], entry['data']['author'], entry['data']['title'], entry['data']['num_comments'], entry['data']['score'])

    threading.Timer(600, pullDataFromReddit).start()



if __name__ ==  "__main__":
    pullDataFromReddit()