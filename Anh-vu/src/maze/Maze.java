package maze;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Maze extends JFrame {

	private static final long serialVersionUID = 1L;
	protected int length;
	protected String path;
	protected String[] directions;
	private Box[] grid;
	private JPanel[] panels;
	public Box start;
	public Box arrival;
	public int startIndex;
	public int arrivalIndex;
	
	public Maze(String path) {		
		directions = new String[] {"ABOVE", 
				"BELOW", 
				"UP", 
				"DOWN", 
				"RIGHT", 
				"LEFT"};
		this.path = path;
        this.setTitle("Grid Layout");
        this.setSize(1000, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		initMazeFromTextFile();
		
		this.setContentPane(panels[start.getIndex()[0]]);
        setVisible(true);
		
        


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
	
	private void initMazeFromTextFile() {
		FileReader FR;
		ArrayList<String> lignes = new ArrayList<>();
		try {
			FR = new FileReader(this.path);
			BufferedReader in = new BufferedReader(FR);
			String line = in.readLine();
			while (line != null) {
				lignes.add(line);
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
		length = Integer.parseInt(lignes.get(0));
		startIndex = Integer.parseInt(lignes.get(1));
		arrivalIndex = Integer.parseInt(lignes.get(2));
		grid = new Box[(int)Math.pow(length, 3)];
		panels = new JPanel[length];
		for (int depth = 0; depth < length; depth++) {
			panels[depth] = new JPanel();
			panels[depth].setLayout(new GridLayout(0,length,0,0));
			for (int row = 0; row < length; row++) {
				for (int column = 0; column < length; column++) {
						int i = index(depth, row, column);
						grid[i] = new Box(depth, row, column);
						String[] walls = lignes.get(i + 3).split(" ");
						for (String dir : directions) {
							int x = grid[i].sideToInt(dir);
							boolean y = Boolean.parseBoolean(walls[x]);
							if (y == false) {
								grid[i].deleteWall(dir);
							}
						}
						panels[depth].add(grid[i]);
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
}
