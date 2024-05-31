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


# blocks are hard

# hashes are like arrays but
# -keys can be anything

h1 = {} #empty hash, can build with Hash.new 
h1["a"] = "Found a"
# can do 
h1.keys
h1.values

h2 = {"SML"=>1, "Racket"=>2, "Ruby"=>3}
h2["SML"]
h2.size


class Point
    attr_accessor :x, :y     # defines methods x, y, x=, y=

    def initialize(x,y)
        @x = x
        @y = y
    end
    def distFromOrigin
        Math.sqrt(@x * @x + @y * @y) # uses instance variables
    end
    def distFromOrigin2
        Math.sqrt(x * x + y * y) # uses getter methods
    end
end

class ColorPoint < Point
    attr_accessor :color # color 

    def initialize(x,y,c="clear")
        super(x,y)       # keyword super calls same method in superclass
        @color = c
    end
end


class ColorPoint2 
    attr_accessor :color
    def initialize(x,y,c="clear")
        @pt = Point.new(x,y)
        @color = c
    end
    def x
        @pt.x
    end
    def y
        @pt.y
    end
end

class ThreeDPoint < Point
    attr_accessor :z
    def initialize(x,y,z)
        super(x,y)
        @z = z
    end
    def distFromOrigin
        d = super
        Math.sqrt(d * d + @z * @z)
    end
    def distFromOrigin2
        d = super
        Math.sqrt(d * d + z * z)
    end
end

class PolarPoint < Point
    def initialize(r,theta)
        @r = r
        @theta = theta
    end
    def x 
        @r * Math.cos(@theta)
    end
    def y
        @r * Math.sin(@theta)
    end
    def x= a
        b = y
        @theta = Math.atan2(b,a)
        @r = Math.sqrt(a*a + b*b)
        self
    end
    def y= a
        a = x
        @theta = Math.atan2(b,a)
        @r = Math.sqrt(a*a + b*b)
        self
    end
    def distFromOrigin
        @r
    end
end