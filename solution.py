board = [
    [8, 0, 9, 3, 0, 1, 0, 0, 0],
    [0, 6, 0, 9, 0, 7, 3, 0, 0],
    [0, 0, 0, 0, 0, 0, 1, 6, 9],
    [3, 0, 5, 6, 0, 0, 0, 1, 4],
    [1, 0, 6, 2, 0, 4, 5, 0, 3],
    [4, 2, 0, 0, 0, 3, 8, 0, 6],
    [5, 7, 8, 0, 0, 0, 0, 0, 0],
    [0, 0, 4, 7, 0, 2, 0, 5, 0],
    [0, 0, 0, 5, 0, 9, 7, 0, 8]
]


def solve(board):
    """
    Solves the sudoku grid using backtracking
    :param board: The Sudoku grid i.e. 2D list of integers
    :return: solution (bool)
    """
    empty = find_empty_block(board)
    if not empty:
        return True
    else:
        r, c = empty  # r=Row, c=Column

    for i in range(1, 10):
        if valid_move(board, i, (r, c)):
            board[r][c] = i
            if solve(board):
                return True

            board[r][c] = 0

    return False


def valid_move(board, num, pos):
    """
    checks whether the given input is a valid move or not
    :param board: The Sudoku grid i.e. 2D list of integers
    :param num: int
    :param pos: (row, column) int
    :return: bool
    """
    # Check row
    for i in range(len(board)):
        if board[pos[0]][i] == num and pos[1] != i:
            return False

    # Check column
    for i in range(len(board)):
        if board[i][pos[1]] == num and pos[0] != i:
            return False

    # Check box
    box_X = pos[0] // 3
    box_Y = pos[1] // 3

    for i in range(box_X * 3, box_X * 3 + 3):
        for j in range(box_Y * 3, box_Y * 3 + 3):
            if board[i][j] == num and (i, j) != pos:
                return False

    return True


def print_board(board):
    """
    prints the Sudoku board
    :param board: The Sudoku grid i.e. 2D list of integers
    :return: None
    """
    for i in range(len(board)):
        if i % 3 == 0 and i != 0:
            print("- - - - - - - - - - - - -")
        for j in range(len(board[0])):
            if j % 3 == 0 and j != 0:
                print(" | ", end='')
            if j == 8:
                print(board[i][j])
            else:
                print(str(board[i][j]) + " ", end='')


def find_empty_block(board):
    """
    finds an empty block in the Sudoku grid
    :param board:The Sudoku grid i.e. 2D list of integers
    :return: (int, int) row, column
    """
    for i in range(len(board)):
        for j in range(len(board[0])):
            if board[i][j] == 0:
                return (i, j)  # row, column

    return None


print_board(board)
solve(board)
print("solution")
print_board(board)
