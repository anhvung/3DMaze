package maze;

import java.util.Stack;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Math;

public class MazeMaker {

	/*
	 * Java implementation of the python algorithm to create a 3D maze
	 * Index : i=depth, j=row, k=column
	 */
	int length;
	CreateBox[] grid;
	
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
	
	public MazeMaker(int length) {
		this.length = length;
	}
	
	private int index(int depth, int row, int column) {
		return depth * length * length + row * length + column;
	}
	
	public void makeMaze() {
		grid = new CreateBox[(int) Math.pow(length, 3)];
		String[] directions = new String[] {"ABOVE", 
				"BELOW", 
				"UP", 
				"DOWN", 
				"RIGHT", 
				"LEFT"};
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
		
		CreateBox current = grid[0];
		current.setVisited();
		
		stack.push(current);
		
		boolean finish = false;
		
		while (!finish) {
			ArrayList<CreateBox> list = new ArrayList<CreateBox>();
			for (String dir : directions) {
				CreateBox neighbour = current.getNeighbour(dir);
				if ( neighbour != null && !neighbour.visited) {
					list.add(current.getNeighbour(dir));
				}
				
				
				if (list.size() != 0) {
					int i = randint(0, list.size()-1);
					CreateBox next = list.get(i);
					
					stack.push(next);
					
					current.deleteWall(dir);
					next.deleteWall(oppositeDirection(dir));
					current = next;
					current.setVisited();
					
					
				} else if (stack.size() != 0) {
					current = stack.pop();
					current.setVisited();
				} else {
					finish = true;
				}
				
			}
		}
		writeMaze(grid);
		for (CreateBox c : grid) {
			for (boolean w : c.walls) {
				if (w) {
					System.out.print("true  ");
				} else {
					System.out.print("false ");
				}
			}
			System.out.println();
		}
	}
	
	private void writeMaze(CreateBox[] grid) {
		try {
			String path = "data/3Dmaze.txt";
			File file = new File(path);
			file.createNewFile();
			PrintWriter writer = new PrintWriter(file);
			writer.write(((Integer)length).toString());
			writer.write("\n");
			
			for (CreateBox c : grid) {
				for (boolean w : c.walls) {
					if (w) {
						writer.write("true  ");
					} else {
						writer.write("false ");
					}
				}
				writer.write("\n");
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}	
	}
	
}
