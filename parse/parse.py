import json
import gzip

def parse(path):
  g = gzip.open(path, 'r')
  for l in g:
    yield json.dumps(eval(l))

f = open("output", 'w')
for l in parse("reviews_Clothing_Shoes_and_Jewelry_5.json.gz"):
  obj = json.loads(l)
  f.write(obj['reviewerID'] + ' ' + obj['asin'] + ' ' + str(obj['overall']) + '\n')

print("done")
