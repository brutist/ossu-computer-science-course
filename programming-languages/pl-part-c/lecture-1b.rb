# Arrays are most common data structure in ruby
# get via a[i] ; set via a[i] = e
# tuples are represented using arrays in ruby

# If there is a method that you think you can use
#   with an array, it's probably in the docs

3.times {puts "hello"}
[4,6,7].each {puts "hi"}
[4,6,7].each {|x| puts x}

i = 5
[4,5,6].each {|x| if i < x then puts (x+1) end}