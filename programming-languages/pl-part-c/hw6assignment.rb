# University of Washington, Programming Languages, Homework 6, hw6runner.rb

# This is the only file you turn in, so do not modify the other files as
# part of your solution.


class MyPiece < Piece
    # The constant All_My_Pieces should be declared here
    All_My_Pieces = [[[[0, 0], [1, 0], [0, 1], [1, 1]]],  # square (only needs one)
                    rotations([[0, 0], [-1, 0], [1, 0], [0, -1]]), # T
                    [[[0, 0], [-1, 0], [1, 0], [2, 0]], # long (only needs two)
                    [[0, 0], [0, -1], [0, 1], [0, 2]]],
                    [[[-2, 0], [-1, 0], [0, 0], [1, 0], [2,0]], # 5-piece long (only needs two)
                    [[0, -2], [0, -1], [0, 0], [0, 1], [0,2]]],
                    rotations([[0, 0], [1, 0], [0, 1], [1, 1], [0, 2]]), # weird box piece
                    rotations([[0, 0], [0, 1], [1, 0]]), # tri piece
                    rotations([[0, 0], [0, -1], [0, 1], [1, 1]]), # L
                    rotations([[0, 0], [0, -1], [0, 1], [-1, 1]]), # inverted L
                    rotations([[0, 0], [-1, 0], [0, -1], [1, -1]]), # S
                    rotations([[0, 0], [1, 0], [0, -1], [-1, -1]])] # Z

    # your enhancements here
    def self.next_piece (board)
      MyPiece.new(All_My_Pieces.sample, board)
    end
  end
  
  class MyBoard < Board
    # your enhancements here
    def initialize(game)
      super
      @current_block = MyPiece.next_piece(self)
      @cheating = false
    end

    def store_current
      locations = @current_block.current_rotation
      displacement = @current_block.position
      (0..(locations.size-1)).each{|index| 
        current = locations[index];
        @grid[current[1]+displacement[1]][current[0]+displacement[0]] = 
        @current_pos[index]
      }
      remove_filled
      @delay = [@delay - 2, 80].max
    end

    def rotate_clockwise_180
      if !game_over? and @game.is_running?
        @current_block.move(0, 0, 2)
      end
      draw
    end

    def cheating
      @cheating
    end

    def will_cheat
      @cheating = true
    end

    def next_piece
      if @cheating and (@score > 100)
        @score -= 100
        @cheating = false
        @current_block = MyPiece.new([[[0,0]]], self)
        @current_pos = nil
      else
        @current_block = MyPiece.next_piece(self)
        @current_pos = nil
        @cheating = false
      end
    end
  end
  
  class MyTetris < Tetris
    # your enhancements here
    def set_board
      @canvas = TetrisCanvas.new
      @board = MyBoard.new(self)
      @canvas.place(@board.block_size * @board.num_rows + 3,
                    @board.block_size * @board.num_columns + 6, 24, 80)
      @board.draw
    end

    def key_bindings  
      super
      # Enhancement no. 1 - press the ’u’ key to make the piece that is falling rotate 180 degrees.
      @root.bind('u', proc {@board.rotate_clockwise_180})
      @root.bind('c', proc {@board.will_cheat})
    end
  end
  
  