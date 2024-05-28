class Hello

    def my_method
        puts "Hello, World"
    end

end

x = Hello.new
x.my_method


class A
    def m1
        34
    end

    def m2 (x,y)
        z = 7
        if x > y
            false
        else 
            x + y * z
        end
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



