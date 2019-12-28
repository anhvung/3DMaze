package Controler;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import View.MiniMap;
import View.MiniMapBox;

public class Maze extends JFrame {

	protected int length;
	protected String path;
	protected String[] directions;
	private Box[] grid;
	public Box start;
	public Box arrival;
	public int startIndex;
	public int arrivalIndex;
	private static Maze uniqueMaze=new Maze("src/data/3Dmaze.txt"); //INSTANCE DE MAZE SUR LAQUELLE ON TRAVAILLE DANS LA CLASSE Display3D
	private Maze(String path) {
		directions = new String[] {"ABOVE", 
				"BELOW", 
				"UP", 
				"DOWN", 
				"RIGHT", 
				"LEFT"};
		this.path = path;
        
		initMazeFromTextFile();
	}
	
	public Maze(int length, String path) {
		this.length = length;
		this.path = path;
		directions = new String[] {"ABOVE", 
				"BELOW", 
				"UP", 
				"DOWN", 
				"RIGHT", 
				"LEFT"};
	}
	
	private ArrayList<String> getLines(){
		FileReader FR;
		ArrayList<String> lines = new ArrayList<>();
		try {
			FR = new FileReader(this.path);
			BufferedReader in = new BufferedReader(FR);
			String line = in.readLine();
			while (line != null) {
				lines.add(line);
				line = in.readLine();
			}
			in.close();
		} catch (IOException e) {
			System.out.println("IOException maze creation");
			System.out.println(e);
			System.exit(0);
		} catch (Exception e) {
			System.out.println("Exception maze creation");
			System.out.println(e);
			System.exit(0);
		}
		return lines;
	}
	
	private void initMazeFromTextFile() {
		ArrayList<String> lines = getLines();
		length = Integer.parseInt(lines.get(0));
		startIndex = Integer.parseInt(lines.get(1));
		arrivalIndex = Integer.parseInt(lines.get(2));
		grid = new Box[(int)Math.pow(length, 3)];
		for (int depth = 0; depth < length; depth++) {
			for (int row = 0; row < length; row++) {
				for (int column = 0; column < length; column++) {
					int i = index(depth, row, column);
					grid[i] = new Box(depth, row, column);
					String[] walls = lines.get(i + 3).split(" ");
					for (String dir : directions) {
						int x = grid[i].sideToInt(dir);
						boolean y = Boolean.parseBoolean(walls[x]);
						if (y == false) {
							grid[i].deleteWall(dir);
						}
					}
				}
			}
		}
		start = grid[startIndex];
		arrival = grid[arrivalIndex];
	}



	public int getLength() {
		return length;
	}

	
	public int index(int depth, int row, int column) {
		return depth * length * length + row * length + column;
	}

	
	public ArrayList<Integer> getNeighbours(int boxIndex) {
		ArrayList<Integer> neighbours = new ArrayList<Integer>();
		for (String dir : directions) {
			if (grid[boxIndex].getWall(dir) == false) {
				neighbours.add(grid[boxIndex].getNeighbourIndex(dir, length));
			}
		}
		return neighbours;
	}
	
	public Box getBox(int index) {
		return grid[index];
	}
}
