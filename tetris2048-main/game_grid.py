import stddraw  # the stddraw module is used as a basic graphics library
from color import Color  # used for coloring the game grid
import numpy as np  # fundamental Python module for scientific computing
import copy as cp


# Class used for modelling the game grid
class GameGrid:
    # Constructor for creating the game grid based on the given arguments
    def __init__(self, grid_h, grid_w):
        # set the dimensions of the game grid as the given arguments
        self.grid_height = grid_h
        self.grid_width = grid_w
        # create the tile matrix to store the tiles placed on the game grid
        self.tile_matrix = np.full((grid_h, grid_w), None)
        # the tetromino that is currently being moved on the game grid
        self.current_tetromino = None
        # game_over flag shows whether the game is over/completed or not
        self.game_over = False
        # set the color used for the empty grid cells
        # self.empty_cell_color = Color(203, 194, 179)
        self.empty_cell_color = Color(213, 204, 199)
        # set the colors used for the grid lines and the grid boundaries
        self.line_color = Color(187, 173, 160)
        self.boundary_color = Color(187, 173, 160)
        # thickness values used for the grid lines and the grid boundaries
        self.line_thickness = 0.006
        self.box_thickness = 2 * self.line_thickness
        self.ghost_tetromino = None
        self.scr = 0

    # Method used for displaying the game grid
    def display(self, SCORE, game_over=False, paused=False, draw_current=True):
        self.scr = SCORE
        # clear the background canvas to empty_cell_color
        stddraw.clear(self.empty_cell_color)
        # draw the game grid
        self.draw_grid()
        if draw_current:
            # Draw the ghost guide
            if self.ghost_tetromino is not None:
                self.ghost_tetromino.draw(True)
            # draw the current (active) tetromino
            if self.current_tetromino is not None:
                self.current_tetromino.draw()
        # draw a box around the game grid
        self.draw_boundaries()
        # show the resulting drawing with a pause duration = 250 ms

        self.score(SCORE)
        self.next_piece_box()
        # if game over, print game over screen
        if game_over:
            self.game_over_screen()
        if paused:
            self.paused()
        stddraw.show(16.7)

    # Method for drawing the cells and the lines of the grid
    def draw_grid(self):
        # draw each cell of the game grid
        for row in range(self.grid_height):
            for col in range(self.grid_width):
                # draw the tile if the grid cell is occupied by a tile
                if self.tile_matrix[row][col] is not None:
                    self.tile_matrix[row][col].draw()
                    # draw the inner lines of the grid
        stddraw.setPenColor(self.line_color)
        stddraw.setPenRadius(self.line_thickness)
        # x and y ranges for the game grid
        start_x, end_x = -0.5, self.grid_width - 0.5
        start_y, end_y = -0.5, self.grid_height - 0.5
        for x in np.arange(start_x + 1, end_x, 1):  # vertical inner lines
            stddraw.line(x, start_y, x, end_y)
        for y in np.arange(start_y + 1, end_y, 1):  # horizontal inner lines
            stddraw.line(start_x, y, end_x, y)
        stddraw.setPenRadius()  # reset the pen radius to its default value

    # Method for drawing the boundaries around the game grid
    def draw_boundaries(self):
        # draw a bounding box around the game grid as a rectangle
        stddraw.setPenColor(self.boundary_color)  # using boundary_color
        # set the pen radius as box_thickness (half of this thickness is visible
        # for the bounding box as its lines lie on the boundaries of the canvas)
        stddraw.setPenRadius(self.box_thickness)
        # coordinates of the bottom left corner of the game grid
        pos_x, pos_y = -0.5, -0.5
        stddraw.rectangle(pos_x, pos_y, self.grid_width, self.grid_height)
        stddraw.setPenRadius()  # reset the pen radius to its default value

    # Method used for checking whether the grid cell with given row and column
    # indexes is occupied by a tile or empty
    def is_occupied(self, row, col):
        # return False if the cell is out of the grid
        if not self.is_inside(row, col):
            return False
        # the cell is occupied by a tile if it is not None
        return self.tile_matrix[row][col] is not None

    # Method used for checking whether the cell with given row and column indexes
    # is inside the game grid or not
    def is_inside(self, row, col):
        if row < 0 or row >= self.grid_height:
            return False
        if col < 0 or col >= self.grid_width:
            return False
        return True

    # Method for updating the game grid by placing the given tiles of a stopped
    # tetromino and checking if the game is over due to having tiles above the
    # topmost game grid row. The method returns True when the game is over and
    # False otherwise.
    def update_grid(self, tiles_to_place):
        # place all the tiles of the stopped tetromino onto the game grid
        n_rows, n_cols = len(tiles_to_place), len(tiles_to_place[0])
        for col in range(n_cols):
            for row in range(n_rows):
                # place each occupied tile onto the game grid
                if tiles_to_place[row][col] is not None:
                    pos = tiles_to_place[row][col].get_position()
                    if self.is_inside(pos.y, pos.x):
                        self.tile_matrix[pos.y][pos.x] = tiles_to_place[row][col]
                    # the game is over if any placed tile is out of the game grid
                    else:
                        self.game_over = True
        # return the game_over flag
        return self.game_over

    # Looks at the grid and clears full lines, then updates the places of upper tiles.
    def clear(self, row, col):
        number_of_pushes = 0
        has_clearing_started = False  # If there is a full line this frame, then the clearing process is started.
        tile_matrix_before_clear = cp.deepcopy(self.tile_matrix)
        rows_to_clear = []
        # Going through the rows from bottom to top.
        for y in range(col):
            is_full = False
            tile_counter = 0
            # Count the number of tiles in the line
            for x in range(row):  # Going through each tile from left to right
                if self.tile_matrix[y][x] is not None:
                    tile_counter += 1
            # If it is equal to row count then the line is full
            if tile_counter == row:
                is_full = True
                has_clearing_started = True
            if has_clearing_started:
                if is_full:
                    number_of_pushes += 1
                    rows_to_clear.append(y)
                    for x in range(row):  # Going through each tile from left to right
                        self.tile_matrix[y][x] = None
                else:
                    for x in range(row):  # Going through each tile from left to right
                        # Updating both the positions and the tile arrays
                        if self.tile_matrix[y][x] is not None:
                            self.tile_matrix[y][x].position.y -= number_of_pushes
                            self.tile_matrix[y - number_of_pushes][x] = self.tile_matrix[y][x]
                            self.tile_matrix[y][x] = None
        if number_of_pushes > 0:
            self.clear_effect(tile_matrix_before_clear, rows_to_clear)
        # Return the number of pushes, which is equal to the number of lines cleared at the end of the process
        return number_of_pushes

    def clear_2048(self, row, col):
        counter = 0
        for y in range(col - 1):
            for x in range(row):
                if self.tile_matrix[y][x] != None and self.tile_matrix[y + 1][x] != None:
                    if self.tile_matrix[y][x].get_number() == self.tile_matrix[y + 1][x].get_number():
                        tile_matrix_before_clear = cp.deepcopy(self.tile_matrix)
                        self.tile_matrix[y + 1][x] = None
                        self.tile_matrix[y][x].set_number(self.tile_matrix[y][x].get_number() << 1)
                        counter += self.tile_matrix[y][x].get_number()
                        for i in range(y + 2, col - 1):
                            if self.tile_matrix[i][x] != None:
                                self.tile_matrix[i][x].move(0, -1)
                                self.tile_matrix[i - 1][x] = self.tile_matrix[i][x]
                                self.tile_matrix[i][x] = None
                        self.clear_2048_effect(tile_matrix_before_clear, x, y)
                        counter += self.clear_2048(row, col)
                        return counter
        return 0

    def clear_2048_effect(self, tile_matrix_before_clear, x, y):
        tile_matrix_before_clear[y][x].draw(is_cleared=True)
        tile_matrix_before_clear[y + 1][x].draw(is_cleared=True)
        stddraw.show(125)
        self.display(self.scr, draw_current=False)
        stddraw.show(125)

    # If there is a tile that doesn't have any 4-connected neighbours, delete the tile
    def delete_alone(self, row, col):
        to_add = 0
        for y in range(col - 1):
            for x in range(row):
                if self.tile_matrix[y][x] != None:
                    if y > 0:  # if the tile doesn't touch the bottommost place
                        if x == 11:  # if the tile is at the righmost place, don't look for the right neighbour
                            if self.tile_matrix[y + 1][x] == None and self.tile_matrix[y - 1][x] == None and \
                                    self.tile_matrix[y][x - 1] == None:
                                to_add += int(self.tile_matrix[y][x].get_number())
                                self.tile_matrix[y][x] = None
                        elif x == 0:  # if the tile is at the leftmost place, don't look dot the left neighnour
                            if self.tile_matrix[y + 1][x] == None and self.tile_matrix[y - 1][x] == None and \
                                    self.tile_matrix[y][x + 1] == None:
                                to_add += int(self.tile_matrix[y][x].get_number())
                                self.tile_matrix[y][x] = None
                        # belki lazım olur
                        elif y == 19:
                            if self.tile_matrix[y - 1][x] == None and self.tile_matrix[y][x + 1] == None and \
                                    self.tile_matrix[y][x - 1] == None:
                                to_add += int(self.tile_matrix[y][x].get_number())
                                self.tile_matrix[y][x] = None

                        else:  # if the tile is not at the rightmost or leftmost place, look for, up, down, lef and right neighbours
                            if self.tile_matrix[y + 1][x] == None and self.tile_matrix[y - 1][x] == None and \
                                    self.tile_matrix[y][x + 1] == None and self.tile_matrix[y][x - 1] == None:
                                to_add += int(self.tile_matrix[y][x].get_number())
                                self.tile_matrix[y][x] = None
        return to_add

    def delete_floating(self):
        labels = np.full((self.grid_height, self.grid_width), 0)
        to_add = 0

        for y in range(self.grid_height):
            for x in range(self.grid_width):
                if self.tile_matrix[y][x] is not None:
                    not_connected = True
                    if y < self.grid_height:
                        if y == 0:
                            labels[y][x] = 1
                            if self.tile_matrix[y + 1][x] is not None:
                                labels[y + 1][x] = 1
                            not_connected = False
                        elif labels[y][x] != 1:
                            if x != 0:
                                if self.tile_matrix[y][x - 1] is not None:
                                    if labels[y][x - 1] == 1:
                                        labels[y][x] = 1
                                        if self.tile_matrix[y + 1][x] is not None:
                                            labels[y + 1][x] = 1
                                        not_connected = False
                            for i in range(x, self.grid_width - 1):
                                if self.tile_matrix[y][i] is not None:
                                    if labels[y][i] == 1:
                                        labels[y][x] = 1
                                        if self.tile_matrix[y + 1][x] is not None:
                                            labels[y + 1][x] = 1
                                        not_connected = False
                                    else:
                                        continue
                                break
                        else:
                            not_connected = False
                            if self.tile_matrix[y + 1][x] is not None:
                                labels[y + 1][x] = 1
                    if not_connected:
                        labels[y][x] = 2

        for y in reversed(range(1, self.grid_height)):
            for x in reversed(range(self.grid_width)):
                if self.tile_matrix[y][x] is not None:
                    if labels[y][x] == 1:
                        if self.tile_matrix[y - 1][x] is not None:
                            labels[y - 1][x] = 1

        for y in range(self.grid_height):
            for x in range(self.grid_width):
                if self.tile_matrix[y][x] is not None:
                    if labels[y][x] == 2:
                        to_add += int(self.tile_matrix[y][x].get_number())
                        self.tile_matrix[y][x] = None
        return to_add

    def clear_everything(self, row, col):
        for y in range(col):
            for x in range(row):
                self.tile_matrix[y][x] = None

    def score(self, SCORE):
        text_color = Color(0, 0, 0)
        stddraw.setFontFamily("Arial")
        stddraw.setFontSize(25)
        stddraw.setPenColor(text_color)
        text_to_display = "Score"
        stddraw.text(self.grid_width + 1.15, self.grid_height - 1.1, text_to_display)
        text_to_score = str(SCORE).rjust(8)
        stddraw.text(self.grid_width + 0.92, self.grid_height - 2, text_to_score)

    def clear_effect(self, tile_matrix_before_clear, rows_to_clear):
        for y in range(self.grid_height):
            for x in range(self.grid_width):
                if tile_matrix_before_clear[y][x] is not None:
                    tile_matrix_before_clear[y][x].draw()
                    if y in rows_to_clear:
                        tile_matrix_before_clear[y][x].draw(is_cleared=True)
        stddraw.show(250)

    # game over splash screen
    def game_over_screen(self):
        text_color = Color(0, 0, 0)
        nice_color = Color(188, 143, 143)
        stddraw.setFontFamily("Arial")
        stddraw.setFontSize(30)
        stddraw.setPenColor(nice_color)
        text_to_display = "GAME OVER"
        stddraw.filledRectangle(self.grid_width / 2 - 5, self.grid_height / 2 - 3.5, 10, 5)
        stddraw.setPenColor(text_color)
        stddraw.boldText(self.grid_width / 2, self.grid_height / 2, text_to_display)
        text_to_continue = "To continue press 'Y'"
        stddraw.text(self.grid_width / 2, self.grid_height / 2 - 1, text_to_continue)
        text_to_not_continue = "To exit press 'N'"
        stddraw.text(self.grid_width / 2, self.grid_height / 2 - 2, text_to_not_continue)

    # box for next piece
    def next_piece_box(self):
        text_color = Color(187, 173, 160)
        stddraw.setPenColor(text_color)
        stddraw.rectangle(self.grid_width - 0.1, -0.4, 2.33, 2.33)
        text_color = Color(0, 0, 0)
        stddraw.setPenColor(text_color)
        text_to_display = "Next "
        stddraw.text(self.grid_width + 0.5, 3, text_to_display)
        text_to_display = "Piece"
        stddraw.text(self.grid_width + 0.5, 2.3, text_to_display)

        text_color = Color(187, 173, 160)
        stddraw.setPenColor(text_color)
        stddraw.rectangle(self.grid_width - 0.1, self.grid_height / 2, 2.33, 2.33)
        text_color = Color(0, 0, 0)
        stddraw.setPenColor(text_color)
        text_to_display = "Hold "
        stddraw.text(self.grid_width + 0.5, self.grid_height / 2 + 3, text_to_display)

    # game over splash screen
    def paused(self):
        text_color = Color(0, 0, 0)
        nice_color = Color(188, 143, 143)
        stddraw.setFontFamily("Arial")
        stddraw.setFontSize(30)
        stddraw.setPenColor(nice_color)
        text_to_display = "Paused"
        stddraw.filledRectangle(self.grid_width / 2 - 2.5, self.grid_height / 2 - 0.8, 5, 1.7)
        stddraw.setPenColor(text_color)
        stddraw.boldText(self.grid_width / 2, self.grid_height / 2, text_to_display)
