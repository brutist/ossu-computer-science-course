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

a = Array.new(5) {|i| (1+i)}

# an example of how to use blocks for a loop
class Tri
    def t i
        (0..i).each do |j|
            print "  " * j
            (j..i).each {|k| print k; print " "}
            print "\n"
        end
    end
end