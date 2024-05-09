fun count_from_1 (x : int) =
    let
      fun count_up (from : int) =
        if from = x
        then from :: []
        else from :: count_up (from + 1)
    in
      count_up 1
    end