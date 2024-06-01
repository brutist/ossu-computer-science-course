class Hello

    def my_method
        puts "Hello, World"
    end

end

x = Hello.new
x.my_method


class A
    def initialize(f=0)
        @foo = f
    end
    def m1
        @foo = 0
    end
    
    def m2 (x,y)
        z = 7
        if x > y
            false
        else 
            x + y * z
        end
    end

    def m3 x
        @foo += x
    end

    def foo
        @foo
    end
end


class B
    def m1
        4
    end

    def m3(x)
        x.abs * 2 + self.m1
    end
end

class MyRational

    def initialize (num,den=1)
        if den == 0
            raise "MyRational received an inappropriate argument"
        elsif den < 0
            @num = - num
            @den = - den
        else 
            @num = num
            @den = den
        end
        reduce # i.e. self.reduce() but private
    end

    def to_s
        ans = @num.to_s
        if @den != 1
            ans += "/"
            ans += @den.to_s
        end
        ans
    end

    # uses e1 if e2 (if e2 is true then e1)
    def to_s2
        dens = ""
        dens = "/" + @den.to_s if @den != 1
        @num.to_s + dens
    end
   
    def add! r  #mutate self in-place
        a = r.num
        b = r.den
        c = @num
        d = @den
        @num = (a * d) + (c * b)
        @den = b * d
        reduce
        self    # convenient for stringing cells
    end

    def + r
        ans = MyRational.new(@num,@den)
        ans.add! r  # return a new object that adds self to given r
    end

protected
    def num
        @num
    end
    def den
        @den
    end

private
    def gcd(x,y)
        if x == y
            x
        elsif x < y
            gcd(x,y-x)
        else
            gcd(y,x)
        end
    end

    def reduce 
        if @num == 0
            @den = 1
        else
            d = gcd(@num.abs, @den)
            @num = @num / d
            @den = @den / d
        end   
    end
end


# top-level method (just part of Object class) for testing
def use_rationals
    r1 = MyRational.new(3,4)
    r2 = r1 + r1 + MyRational.new(-5,2)
    puts r2.to_s
    (r2.add! r1).add! (MyRational.new(1,-4))
    puts r2.to_s
    puts r2.to_s2
end
