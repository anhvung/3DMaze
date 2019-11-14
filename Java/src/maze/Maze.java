package maze;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Maze {
	private int length;
	private char[][] matrix;
	private Box start;
	private Box arrival;
	
	public Maze(String name) {
		FileReader FR;
		ArrayList<String> lignes = new ArrayList<>();
		try {
			FR = new FileReader(name);
			BufferedReader in = new BufferedReader(FR);
			
			String line = in.readLine();
			while (line != null) {
				lignes.add(line);
				line = in.readLine();
				in.close();
			}
		} catch (IOException e) {
			System.out.println("exit 1");
			System.exit(0);
		} catch (Exception e) {
			System.out.println("exit 2");
			System.exit(0);
		} 
		length = lignes.size();
		matrix = new char[length][length];
		for (int i = 0; i < length; i++) {
			matrix[i] = lignes.get(i).toCharArray();
		}
	}
	public int getLength() {
		return length;
	}
	
	public char[][] getMatrix(){
		return matrix;
	}
	

	public boolean isAccessible(int i, int j) {
		return (matrix[i][j] == 'E' || matrix[i][j] == 'D' || matrix[i][j] == 'A');
	}
	
	public int index(int i, int j) {
		return i * length + j;
	}
}
