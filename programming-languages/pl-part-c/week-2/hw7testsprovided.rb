# University of Washington, Programming Languages, Homework 7, 
# hw7testsprovided.rb

require "./hw7.rb"

class Point
	def ==(other)
		self.class == other.class &&
		  @x == other.x &&
		  @y == other.y
	end
end

class LineSegment
	def ==(other)
		self.class == other.class &&
		  @x1 == other.x1 &&
		  @x2 == other.x2 &&
		  @y1 == other.y1 &&
		  @y2 == other.y2
	end
end
# Will not work completely until you implement all the classes and their methods

# Will print only if code has errors; prints nothing if all tests pass

# These tests do NOT cover all the various cases, especially for intersection

#Constants for testing
ZERO = 0.0
ONE = 1.0
TWO = 2.0
THREE = 3.0
FOUR = 4.0
FIVE = 5.0
SIX = 6.0
SEVEN = 7.0
TEN = 10.0

#Point Tests
a = Point.new(THREE,FIVE)
if not (a.x == THREE and a.y == FIVE)
	puts "Point is not initialized properly"
end
if not (a.eval_prog([]) == a)
	puts "Point eval_prog should return self"
end
if not (a.preprocess_prog == a)
	puts "Point preprocess_prog should return self"
end
a1 = a.shift(THREE,FIVE)
if not (a1.x == SIX and a1.y == TEN)
	puts "Point shift not working properly"
end
a2 = a.intersect(Point.new(THREE,FIVE))
if not (a2.x == THREE and a2.y == FIVE)
	puts "Point intersect not working properly"
end 
a3 = a.intersect(Point.new(FOUR,FIVE))
if not (a3.is_a? NoPoints)
	puts "Point intersect not working properly"
end

#Line Tests
b = Line.new(THREE,FIVE)
if not (b.m == THREE and b.b == FIVE)
	puts "Line not initialized properly"
end
if not (b.eval_prog([]) == b)
	puts "Line eval_prog should return self"
end
if not (b.preprocess_prog == b)
	puts "Line preprocess_prog should return self"
end

b1 = b.shift(THREE,FIVE) 
if not (b1.m == THREE and b1.b == ONE)
	puts "Line shift not working properly"
end

b2 = b.intersect(Line.new(THREE,FIVE))
if not (((b2.is_a? Line)) and b2.m == THREE and b2.b == FIVE)
	puts "Line intersect not working properly"
end
b3 = b.intersect(Line.new(THREE,FOUR))
if not ((b3.is_a? NoPoints))
	puts "Line intersect not working properly"
end

#VerticalLine Tests
c = VerticalLine.new(THREE)
if not (c.x == THREE)
	puts "VerticalLine not initialized properly"
end

if not (c.eval_prog([]) == c)
	puts "VerticalLine eval_prog should return self"
end
if not (c.preprocess_prog == c)
	puts "VerticalLine preprocess_prog should return self"
end
c1 = c.shift(THREE,FIVE)
if not (c1.x == SIX)
	puts "VerticalLine shift not working properly"
end
c2 = c.intersect(VerticalLine.new(THREE))
if not ((c2.is_a? VerticalLine) and c2.x == THREE )
	puts "VerticalLine intersect not working properly"
end
c3 = c.intersect(VerticalLine.new(FOUR))
if not ((c3.is_a? NoPoints))
	puts "VerticalLine intersect not working properly"
end

#LineSegment Tests
d = LineSegment.new(ONE,TWO,-THREE,-FOUR)
if not (d.eval_prog([]) == d)
	puts "LineSegement eval_prog should return self"
end
d1 = LineSegment.new(ONE,TWO,ONE,TWO)
d2 = d1.preprocess_prog
if not ((d2.is_a? Point)and d2.x == ONE and d2.y == TWO) 
	puts "LineSegment preprocess_prog should convert to a Point"
	puts "if ends of segment are real_close"
end

d = d.preprocess_prog
if not (d.x1 == -THREE and d.y1 == -FOUR and d.x2 == ONE and d.y2 == TWO)
	puts "LineSegment preprocess_prog should make x1 and y1"
	puts "on the left of x2 and y2"
end

d3 = d.shift(THREE,FIVE)
if not (d3.x1 == ZERO and d3.y1 == ONE and d3.x2 == FOUR and d3.y2 == SEVEN)
	puts "LineSegment shift not working properly"
end

d4 = d.intersect(LineSegment.new(-THREE,-FOUR,ONE,TWO))
if not (((d4.is_a? LineSegment)) and d4.x1 == -THREE and d4.y1 == -FOUR and d4.x2 == ONE and d4.y2 == TWO)	
	puts "LineSegment intersect not working properly"
end
d5 = d.intersect(LineSegment.new(TWO,THREE,FOUR,FIVE))
if not ((d5.is_a? NoPoints))
	puts "LineSegment intersect not working properly"
end

#Intersect Tests
ia = Intersect.new(Point.new(-ONE,-TWO), Point.new(-ONE,-TWO))
ia1 = ia.preprocess_prog.eval_prog([])
if not (ia1.x == -ONE and ia1.y == -TWO)
	puts "Intersect eval_prog should return the intersect between e1 and e2"
end
i = Intersect.new(LineSegment.new(-ONE,-TWO,THREE,FOUR), LineSegment.new(THREE,FOUR,-ONE,-TWO))
i1 = i.preprocess_prog.eval_prog([])
if not (i1.x1 == -ONE and i1.y1 == -TWO and i1.x2 == THREE and i1.y2 == FOUR)
	puts "Intersect eval_prog should return the intersect between e1 and e2"
end
ib = Intersect.new(Point.new(2.5,1.5),Intersect.new(LineSegment.new(2.0,1.0,3.0,2.0),Intersect.new(LineSegment.new(0.0,0.0,2.5,1.5),Line.new(1.0,-1.0))))
ib1 = ib.preprocess_prog.eval_prog([])
if not (ib1.is_a? Point and ib1.x == 2.5 and ib1.y == 1.5)
	puts "Intersect eval_prog should return the intersect between e1 and e2"
end
ic = Point.new(1.0,1.0).intersect(LineSegment.new(1.0,1.0,5.0,6.0)) 
ic1 = ic.preprocess_prog.eval_prog([])
if not (ic1.is_a? Point and ic1.x == 1.0 and ic1.y == 1.0)
	puts "Intersect eval_prog should return the intersect between e1 and e2"
end
id = Point.new(5.0,7.0).intersect(LineSegment.new(-1.0,2.0,5.0,7.0))
id1 = id.preprocess_prog.eval_prog([])
if not (id1.is_a? Point and id1.x == 5.0 and id1.y == 7.0)
	puts "Intersect eval_prog should return the intersect between e1 and e2"
end
ie = Point.new(1.0,1.0).intersectLineSegment(LineSegment.new(1.0,1.0,5.0,6.0))
ie1 = ie.preprocess_prog.eval_prog([])
if not (ie1.is_a? Point and ie1.x == 1.0 and ie1.y == 1.0)
	puts "Intersect eval_prog should return the intersect between e1 and e2"
end
ig = Point.new(5.0,5.0).intersectLineSegment(LineSegment.new(-1.0,5.0,10.0,5.0))
ig1 = ig.preprocess_prog.eval_prog([])
if not (ig1.is_a? Point and ig1.x == 5.0 and ig1.y == 5.0)
	puts "Intersect eval_prog should return the intersect between e1 and e2"
end

TEST_INPUT = [Intersect.new(Point.new(2.5,1.5),Intersect.new(LineSegment.new(2.0,1.0,3.0,2.0),Intersect.new(LineSegment.new(0.0,0.0,2.5,1.5),Line.new(1.0,-1.0)))),
			  Point.new(1.0,1.0).intersect(LineSegment.new(1.0,1.0,5.0,6.0)),
			  Point.new(5.0,7.0).intersect(LineSegment.new(-1.0,2.0,5.0,7.0)),
			  LineSegment.new(1.0,1.0,5.0,6.0).intersect(Point.new(1.0,1.0)),
			  Line.new(5.0,0.0).intersectLineSegment(LineSegment.new(1.0,5.0,2.0,2.0)),
			  LineSegment.new(-1.0,-1.0,1.0,5.0).intersectLine(Line.new(5.0,0.0)),
			  LineSegment.new(-1.0,-1.0,3.0,3.0).intersect(VerticalLine.new(2.0)),
			  LineSegment.new(-1.0,-1.0,3.0,3.0).intersect(VerticalLine.new(-1.0)),
			  LineSegment.new(5.0,7.0,9.0,9.0).intersect(LineSegment.new(5.0,7.0,6.0,-1.0)),
			  LineSegment.new(2.0,3.0,4.0,9.0).intersect(LineSegment.new(0.0,-3.0,6.0,15.0)),
			  LineSegment.new(5.0,2.0,9.0,9.0).intersectLineSegment(LineSegment.new(-2.0,-1.0,5.0,2.0)),
			  LineSegment.new(2.0,3.0,6.0,15.0).intersectLineSegment(LineSegment.new(0.0,-3.0,4.0,9.0)),
			  LineSegment.new(1.0,1.0,4.0,1.0).intersectLineSegment(LineSegment.new(3.0,0.0,3.0,2.0))]
TEST_OUTPUT = [Point.new(2.5,1.5),
			   Point.new(1.0,1.0),
			   Point.new(5.0,7.0),
			   Point.new(1.0,1.0),
			   Point.new(1.0,5.0),
			   Point.new(1.0,5.0),
			   Point.new(2.0,2.0),
			   Point.new(-1.0,-1.0),
			   Point.new(5.0,7.0),
			   LineSegment.new(2.0,3.0,4.0,9.0),
			   Point.new(5.0,2.0),
			   LineSegment.new(2.0,3.0,4.0,9.0),
			   Point.new(3.0,1.0)]

def auto_test(inputl,outputl)
	counter = 1
	status = true
	test = inputl.zip(outputl)
	test.each do|pr| 
		if not(pr[0].preprocess_prog.eval_prog([]) == pr[1])
			puts "test failed on test " + counter.to_s
			status = false
		end
		counter +=1
	end
	if status
		puts "ALL " + counter.to_s + " TESTS PASSED."
	end
end


auto_test(TEST_INPUT,TEST_OUTPUT)


#Var Tests
v = Var.new("a")
v1 = v.eval_prog([["a", Point.new(THREE,FIVE)]])
if not ((v1.is_a? Point) and v1.x == THREE and v1.y == FIVE)
	puts "Var eval_prog is not working properly"
end 
if not (v.preprocess_prog == v)
	puts "Var preprocess_prog should return self"
end

#Let Tests
l = Let.new("a", LineSegment.new(-ONE,-TWO,THREE,FOUR),
             Intersect.new(Var.new("a"),LineSegment.new(THREE,FOUR,-ONE,-TWO)))
l1 = l.preprocess_prog.eval_prog([])
if not (l1.x1 == -ONE and l1.y1 == -TWO and l1.x2 == THREE and l1.y2 == FOUR)
	puts "Let eval_prog should evaluate e2 after adding [s, e1] to the environment"
end

#Let Variable Shadowing Test
l2 = Let.new("a", LineSegment.new(-ONE, -TWO, THREE, FOUR),
              Let.new("b", LineSegment.new(THREE,FOUR,-ONE,-TWO), Intersect.new(Var.new("a"),Var.new("b"))))
l2 = l2.preprocess_prog.eval_prog([["a",Point.new(0,0)]])
if not (l2.x1 == -ONE and l2.y1 == -TWO and l2.x2 == THREE and l2.y2 == FOUR)
	puts "Let eval_prog should evaluate e2 after adding [s, e1] to the environment"
end


#Shift Tests
s = Shift.new(THREE,FIVE,LineSegment.new(-ONE,-TWO,THREE,FOUR))
s1 = s.preprocess_prog.eval_prog([])
if not (s1.x1 == TWO and s1.y1 == THREE and s1.x2 == SIX and s1.y2 == 9)
	puts "Shift should shift e by dx and dy"
end


