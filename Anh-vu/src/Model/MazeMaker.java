package Model;

import java.util.Stack;

import Controler.Maze;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Math;

public class MazeMaker extends Maze {
	private static final long serialVersionUID = 1L;
	private CreateBox[] grid;
	private int start;
	private int arrival;
	
	public MazeMaker(int length, String pathToWrite) {
		
		super(length, pathToWrite);
	}
	
	private int randint(int min, int max) {
		return (int) (Math.random() * ((max - min) + 1)) + min;
	}
	
	private String oppositeDirection(String direction) {
		switch (direction) {
		case "BELOW": return "ABOVE";
		case "ABOVE": return "BELOW";
		case "UP": return "DOWN";
		case "DOWN": return "UP";
		case "RIGHT": return "LEFT";
		case "LEFT": return "RIGHT";
		default: return null;
		}
	}
	
	private Stack<CreateBox> copyStack(Stack<CreateBox> s) {
		Stack<CreateBox> toReturn = new Stack<CreateBox>();
		Stack<CreateBox> sPrime = new Stack<CreateBox>();
		while (!s.empty()) {
			sPrime.push(s.pop());
		}
		while (!sPrime.empty()) {
			CreateBox c = sPrime.pop();
			s.push(c);
			toReturn.push(c);
		}
		return toReturn;
	}
	
	public void makeMaze() {
		grid = new CreateBox[(int) Math.pow(length, 3)];
		for (int depth = 0; depth < length; depth++) {
			for (int row = 0; row < length; row++) {
				for (int column = 0; column < length; column++) {
						int i = index(depth, row, column);
						grid[i] = new CreateBox(depth, row, column);
				}
			}
		}
		
		for (CreateBox c : grid) {
			c.setNeighbours(length, directions, grid);
		}
		
		Stack<CreateBox> stack = new Stack<CreateBox>();
		
		int i = randint(0, grid.length - 1);
		start = i;
		CreateBox current = grid[start];
		current.setVisited();
		
		stack.push(current);
		
		boolean finish = false;
		boolean last = false;
		Stack<CreateBox> path = new Stack<CreateBox>(); 
		
		while (!finish) {
			ArrayList<CreateBox> list = new ArrayList<CreateBox>();
			for (String dir : directions) {
				CreateBox neighbour = current.getNeighbour(dir);
				if (neighbour != null && !neighbour.visited) {
					list.add(current.getNeighbour(dir));
				}

			}
				
			if (list.size() != 0) {
				last = true;
				int j = randint(0, list.size()-1);
				CreateBox next = list.get(j);
				
				stack.push(next);
				
				for (String dir : directions) {
					if (current.getNeighbour(dir) == next) {
						current.deleteWall(dir);
						next.deleteWall(oppositeDirection(dir));
						current = next;
						current.setVisited();
					}
				}
				
				
				
			} else if (!stack.empty()) {
				if (last == true) {
					if (stack.size() >= path.size()) {
						path = copyStack(stack);
					}
				}
				current = stack.pop();
				current.setVisited();
			} else {
				finish = true;
			}
				
				
		}
		int[] k = path.pop().getIndex();
		arrival = index(k[0], k[1],k[2]);
		writeMaze();
	}
	
	private void writeMaze() {
		try {
			File file = new File(path);
			file.createNewFile();
			PrintWriter writer = new PrintWriter(file);
			writer.write(((Integer)length).toString());
			writer.write("\n");
			writer.write(((Integer)start).toString());
			writer.write("\n");
			writer.write(((Integer)arrival).toString());
			writer.write("\n");
			
			for (CreateBox c : grid) {
				for (boolean w : c.walls) {
					if (w == true) {
						writer.write("true ");
					} else {
						writer.write("false ");
					}
				}
				writer.write("\n");
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}	
	}
	
}
