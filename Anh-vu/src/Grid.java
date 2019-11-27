import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class Grid extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Maze maze;
	public Grid(Maze maze){
        JPanel container = new JPanel();
        this.setTitle("Grid Layout");
        this.setSize(1000, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.setLayout(new GridLayout(0,10,0,0));
        this.maze=maze;
        InitFromMaze(maze,container);
        this.setContentPane(container);
       
        setVisible(true);
    }
    private void InitFromMaze(Maze maze2,  JPanel container) {
    	 for (int i=0;i<100;i++) {
 			container.add(new JButton(maze2.blocs[i].getType()));
 		}
 		
	}
	public static void main(String[] args) {
		Maze maze = new Maze();
		maze.initFromTextFile("src/data/maze.txt");
        Grid gl = new Grid(maze);
        gl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }  
    }

