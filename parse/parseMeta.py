import json
import gzip

def parse(path):
  g = gzip.open(path, 'r')
  for l in g:
    yield json.dumps(eval(l))

with open("adsorptionresults.json") as data:
  data = json.load(data)
ids = []

for element in data['recs']:
  ids.append(element['id'])

data = None

#print(ids)


f = open("filtered_meta.json", 'w')
count = 0
for l in parse("meta_Clothing_Shoes_and_Jewelry.json"):
  obj = json.loads(l)
  if(obj['asin'] in ids):
    try:
      del obj['related']
    except:
      pass
    nl = json.dumps(obj)
    f.write(nl + "\n")
  count+=1
  if count % 1000 == 0:
    print(count)
print("done")

