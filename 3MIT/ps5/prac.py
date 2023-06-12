import pylab

get_features = pylab.array([89.0, 1.0, 0.0, 66.0])
get_features2 = pylab.array([100.0, 1.0, 1.0, 70.0])
examples = [get_features, get_features2]

vals = pylab.array([0.0] * len(examples[0]))
for e in examples:
    vals += e

centroid = vals / len(examples)
print(centroid)


for i in range(4, 0, -1):
    print(i)




