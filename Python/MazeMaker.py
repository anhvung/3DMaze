"""Training6Maze"""
import random, pygame, sys 
from pygame.locals import *

pygame.init()
w=50
nb=9
finish=False
arrival=None
last=False
window=pygame.display.set_mode((nb*w, nb*w))
clock=pygame.time.Clock()
LEFT='LEFT'
UP='UP'
RIGHT='RIGHT'
DOWN='DOWN'
DIRECTIONS=[LEFT,UP,RIGHT,DOWN]
OPPDIRS=[RIGHT,DOWN,LEFT,UP]
BLACK    = (  0,   0,   0)
NAVYBLUE = ( 60,  60, 100)
WHITE    = (255, 255, 255)
PURPLE   = (255,   0, 255)
CYAN     = (  0, 255, 255)

def index(point):
    i,j=point
    if i in (-1,nb) or j in (-1,nb):
        return None
    return i+j*nb

def opp(dir): return OPPDIRS[DIRECTIONS.index(dir)]

class Cell:
    def __init__(self, i, j):
        self.i=i
        self.j=j
        self.x=i*w
        self.y=j*w
        self.visited=False
        self.highlight=False
        self.walls=dict()
        self.neighbours=dict()
        self.directions={LEFT:(i-1,j),RIGHT:(i+1,j),UP:(i,j-1),DOWN:(i,j+1)}
        for dir in DIRECTIONS:
            self.walls[dir]=True
        self.path=None
        
    def get_index(self):
        self.index=grid.index(self)
    
    def show(self):
        x=self.x
        y=self.y
        self.rect=pygame.Rect((x,y,w,w))
        if self.visited:
            pygame.draw.rect(window, PURPLE, (x,y,w,w))
        if self.highlight:
            pygame.draw.rect(window, NAVYBLUE, (x,y,w,w))
        topleft=(x,y)
        topright=(x+w,y)
        bottomleft=(x,y+w)
        bottomright=(x+w,y+w)
        if self.walls[RIGHT]:
            pygame.draw.line(window, BLACK, topright, bottomright,3)
        if self.walls[LEFT]:
            pygame.draw.line(window, BLACK, topleft, bottomleft,3)
        if self.walls[UP]:
            pygame.draw.line(window, BLACK, topleft, topright,3)
        if self.walls[DOWN]:
            pygame.draw.line(window, BLACK, bottomleft, bottomright,3)
        
    def get_neighbours(self):
        for dir in DIRECTIONS:
            spot=index(self.directions[dir])
            self.neighbours[dir]=grid[spot] if spot is not None else None

grid=list()
for j in range(nb):
    for i in range(nb):
        grid.append(Cell(i,j))
for cell in grid: cell.get_neighbours(); cell.get_index()
stack=list()
current=grid[0]
grid[0].visited=True
grid[0].highlight=True

Continue=True
while Continue:
    pygame.display.update()
    window.fill(WHITE)
    for cell in grid:
        cell.show()
    
    if not finish:
        liste=[elt for elt in list(current.neighbours.values()) if elt is not None and not elt.visited]
        if liste!=[]:
            last=True
            
            # 1
            next=random.choice(liste)
            
            # 2
            stack.append(current)
            
            # 3
            for dir in DIRECTIONS:
                if next==current.neighbours[dir]:
                    current.walls[dir]=False
                    next.walls[opp(dir)]=False
            
            # 4
            current.highlight=False
            current=next
            current.visited=True
            current.highlight=True
        
        elif stack!=[]:
            if last:
                current.path=stack.copy()
                last=False
                
            next=stack.pop()
            current.highlight=False
            current=next
            current.highlight=True
        
        else: finish=True

    if finish and arrival is None:
        for cell in grid:
            if cell.path is not None: cell.highlight=True
    
    if arrival is not None:
        for cell in grid: cell.highlight=False
        for cell in arrival.path: cell.highlight=True
        pygame.draw.rect(window, WHITE, grid[0].rect)
        pygame.draw.rect(window, WHITE, arrival.rect)

    
    for event in pygame.event.get():
        if event.type==QUIT:
            Continue=False
        if finish and arrival is None and event.type==MOUSEBUTTONDOWN:
            for cell in grid:
                if cell.highlight and cell.rect.collidepoint(event.pos):
                    arrival=cell
    
    clock.tick(5)
pygame.quit()