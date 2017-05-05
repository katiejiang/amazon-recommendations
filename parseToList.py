import json

data = []

with open("filtered_meta2.json") as file:
  for line in file:
    data.append(json.loads(line))

obj = {'products' : data}
f = open("metadata_results.json", 'w')

f.write(json.dumps(obj))
