# University of Washington, Programming Languages, Homework 6, hw6runner.rb

# This is the only file you turn in, so do not modify the other files as
# part of your solution.

class MyPiece < Piece
    # The constant All_My_Pieces should be declared here
    All_Pieces = [[[[0, 0], [1, 0], [0, 1], [1, 1]]],  # square (only needs one)
                 rotations([[0, 0], [-1, 0], [1, 0], [0, -1]]), # T
                 [[[0, 0], [-1, 0], [1, 0], [2, 0]], # long (only needs two)
                 [[0, 0], [0, -1], [0, 1], [0, 2]]],
                 rotations([[0, 0], [0, -1], [0, 1], [1, 1]]), # L
                 rotations([[0, 0], [0, -1], [0, 1], [-1, 1]]), # inverted L
                 rotations([[0, 0], [-1, 0], [0, -1], [1, -1]]), # S
                 rotations([[0, 0], [1, 0], [0, -1], [-1, -1]])]

    # your enhancements here
    def initialize(point_array, board)
      super(point_array, board)
    end
  end
  
  class MyBoard < Board
    # your enhancements here
    def initialize(game)
      super(game)
    end
  end
  
  class MyTetris < Tetris
    # your enhancements here
    def initialize
      super
    end

    def key_bindings  
      @root.bind('n', proc {self.new_game}) 
  
      @root.bind('p', proc {self.pause}) 
  
      @root.bind('q', proc {exitProgram})
      
      @root.bind('a', proc {@board.move_left})
      @root.bind('Left', proc {@board.move_left}) 
      
      @root.bind('d', proc {@board.move_right})
      @root.bind('Right', proc {@board.move_right}) 
  
      @root.bind('s', proc {@board.rotate_clockwise})
      @root.bind('Down', proc {@board.rotate_clockwise})
      # Enhancement no. 1 - press the ’u’ key to make the piece that is falling rotate 180 degrees.
      @root.bind('u', proc {@board.rotate_clockwise})
  
      @root.bind('w', proc {@board.rotate_counter_clockwise})
      @root.bind('Up', proc {@board.rotate_counter_clockwise}) 
      
      @root.bind('space' , proc {@board.drop_all_the_way}) 
    end
  end
  
  