import pygame
from SudokuSolver import solve, valid_move
import time

pygame.font.init()


class Grid:
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

    def __init__(self, rows, cols, width, height):
        self.rows = rows
        self.cols = cols
        self.width = width
        self.height = height
        self.cubes = [[Cube(self.board[i][j], i, j, width, height) for j in range(cols)] for i in range(rows)]
        self.model = None
        self.selected = None

    def update_model(self):
        """
        update model after insertion
        """
        self.model = [[self.cubes[i][j].value for j in range(self.cols)] for i in range(self.rows)]

    def place(self, val):
        """
        place value in the cell and check validity of the number
        :param val: int
        :return: bool
        """
        row, col = self.selected
        if self.cubes[row][col].value == 0:
            self.cubes[row][col].set_value(val)
            self.update_model()

        if valid_move(self.model, val, (row, col)) and solve(self.model):
            return True
        else:
            self.cubes[row][col].set_value(0)
            self.cubes[row][col].set_temp(0)
            self.update_model()
            return False

    def sketch(self, val):
        """
        to insert the temp value
        :param val: int
        """
        row, col = self.selected
        self.cubes[row][col].set_temp(val)

    def draw(self, win):
        """
        Draw grid lines and cubes
        :param win: window
        """
        # draw grid
        gap = self.width // 9
        for i in range(self.rows + 1):
            if i % 3 == 0 and i != 0:
                thick = 4
            else:
                thick = 1
            pygame.draw.line(win, (0, 0, 0), (0, i * gap), (self.width, i * gap), thick)
            pygame.draw.line(win, (0, 0, 0), (i * gap, 0), (i * gap, self.width), thick)

        # draw cubes
        for i in range(self.rows):
            for j in range(self.cols):
                self.cubes[i][j].draw(win)

    def select(self, row, col):
        """
        Reset all others
        :param row: int
        :param col: int
        """
        for i in range(self.rows):
            for j in range(self.cols):
                self.cubes[i][j].selected = False

        self.cubes[row][col].selected = True
        self.selected = (row, col)

    def clear(self):
        """
        clear the value in selected cell
        """
        row, col = self.selected
        if self.cubes[row][col].value == 0:
            self.cubes[row][col].set_temp(0)

    def click(self, pos):
        """
        :param pos: tuple
        :return: (row, col)
        """
        if pos[0] < self.width and pos[1] < self.height:
            gap = self.width / 9
            x = pos[0] // gap
            y = pos[1] // gap
            return (int(y), int(x))
        else:
            return None

    def is_finished(self):
        """
        check if all the cells are filled or not
        :return: bool
        """
        for i in range(self.rows):
            for j in range(self.cols):
                if self.cubes[i][j].value == 0:
                    return False
        return True


class Cube:
    """
    class to create 9x9 cubes for a 3x3 sudoku
    """
    rows = 9
    cols = 9

    def __init__(self, value, rows, cols, width, height):
        self.value = value
        self.rows = rows
        self.cols = cols
        self.width = width
        self.height = height
        self.temp = 0  # temporary value that is inserted with the pencil
        self.selected = False

    def draw(self, win):
        """
        draw window
        :param win: window int
        """
        font = pygame.font.SysFont("georgia", 30)

        gap = self.width // 9
        x = self.cols * gap
        y = self.rows * gap

        if self.temp != 0 and self.value == 0:
            txt = font.render(str(self.temp), 1, (128, 128, 128))
            win.blit(txt, (x + 5, y + 5))
        elif not self.value == 0:
            txt = font.render(str(self.value), 1, (0, 0, 0))
            win.blit(txt, (x + (gap // 2 - txt.get_width() // 2), y + (gap // 2 - txt.get_height() // 2)))

        if self.selected:
            pygame.draw.rect(win, (255, 0, 0), (x, y, gap, gap), 3)

    def set_value(self, val):
        self.value = val

    def set_temp(self, val):
        self.temp = val


def redraw_window(win, board, playtime, strikes):
    """
    redraw window after each insertion
    :param win: window
    :param board: 2d array
    :param playtime: time elapsed
    :param strikes: counter keeping count of inserted wrong number
    """
    win.fill((250, 250, 250))

    # Display time
    font = pygame.font.SysFont("georgia", 30)
    txt = font.render("Time Elapsed: " + str(format_time(playtime)), 1, (0, 0, 0))
    win.blit(txt, (540 - 300, 560))

    # Display strikes
    txt = font.render("X " * strikes, 1, (255, 0, 0))
    win.blit(txt, (10, 560))

    # Draw grid lines and board
    board.draw(win)


def format_time(secs):
    """
    change the time format to hrs:min:sec
    :param secs: time in seconds
    :return: str, formatted time
    """
    sec = secs % 60
    min = secs // 60
    hrs = min // 60

    t = " " + str(hrs) + ":" + str(min) + ":" + str(sec)
    return t


def main():
    win = pygame.display.set_mode((540, 600))
    pygame.display.set_caption("3X3 SUDOKU")
    board = Grid(9, 9, 540, 540)
    key = None
    run = True
    start = time.time()
    strikes = 0
    while run:
        playtime = round(time.time() - start)
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                run = False  # game finished
            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_1:
                    key = 1
                if event.key == pygame.K_2:
                    key = 2
                if event.key == pygame.K_3:
                    key = 3
                if event.key == pygame.K_4:
                    key = 4
                if event.key == pygame.K_5:
                    key = 5
                if event.key == pygame.K_6:
                    key = 6
                if event.key == pygame.K_7:
                    key = 7
                if event.key == pygame.K_8:
                    key = 8
                if event.key == pygame.K_9:
                    key = 9
                if event.key == pygame.K_DELETE:
                    board.clear()
                    key = None
                if event.key == pygame.K_RETURN:
                    i, j = board.selected
                    if board.cubes[i][j].temp != 0:
                        if board.place(board.cubes[i][j].temp):
                            print("Correct")
                        else:
                            print("Wrong")
                            strikes += 1
                        key = None

                        if board.is_finished():
                            print("Game Over")
                            run = False

            if event.type == pygame.MOUSEBUTTONDOWN:
                pos = pygame.mouse.get_pos()
                clicked = board.click(pos)
                if clicked:
                    board.select(clicked[0], clicked[1])
                    key = None

        if board.selected and key is not None:
            board.sketch(key)

        redraw_window(win, board, playtime, strikes)
        pygame.display.update()


main()
pygame.quit()
