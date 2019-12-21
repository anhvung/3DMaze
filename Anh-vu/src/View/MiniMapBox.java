package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MiniMapBox extends JButton {
	protected boolean[] walls;
	private final float propWalls = 0.01f;
	private final float propStairs = 0.25f;
	private String indexString;
	private BufferedImage stairsUpImage;
	private BufferedImage stairsDownImage;
	
	
	
	public MiniMapBox() {
		walls = new boolean[] {true, true, true, true, true, true};
		File file = new File("src/data/stairsdown.png");
		try {
			stairsDownImage = ImageIO.read(file);
		} catch (IOException e) {
			
		}
		file = new File("src/data/stairsup.png");
		try {
			stairsUpImage = ImageIO.read(file);
		} catch (IOException e) {
			
		}
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		String s = "";
		for (boolean wall : walls) {
			if (wall) s = s + '1';
			else s = s + '0';
		}
		s = s + toString();

		int x0 = 0;
		int y0 = 0;
		int h  = getSize().height;
		int w  = getSize().width;
		
		g.drawString(s, 10, h / 5);
		
		if (! walls[0]) {
			int l_x = (int) (w * propStairs);
			int l_y = (int) (h * propStairs);
			Image image = stairsUpImage.getScaledInstance(l_x, l_y, Image.SCALE_SMOOTH);
			g.drawImage(image, (int) w/4, (int) h/4, null);
		}
		if (! walls[1]) {
			int l_x = (int) (w * propStairs);
			int l_y = (int) (h * propStairs);
			Image image = stairsDownImage.getScaledInstance(l_x, l_y, Image.SCALE_SMOOTH);
			g.drawImage(image, (int) w/2, (int) h/2, null);
		}
		if (walls[2]) {
			int x = x0;
			int y = y0;
			int width = w;
			int height = (int) Math.round(h * propWalls);
			g.fillRect(x, y, width, height);
		}
		if (walls[3]) {
			int x = x0;
			int y = y0 + h - (int) Math.round(h * propWalls);
			int width = w;
			int height = (int) Math.round(h * propWalls);
			g.fillRect(x, y, width, height);
		}
		if (walls[4]) {
			int x = x0;
			int y = y0;
			int width = (int) Math.round(w * propWalls);
			int height = h;
			g.fillRect(x, y, width, height);
		}
		if (walls[5]) {
			int x = x0 + w - (int) Math.round(w * propWalls);
			int y = y0;
			int width = (int) Math.round(w * propWalls);
			int height = h;
			g.fillRect(x, y, width, height);
		}
	}
	
	public void setIndex(int i, int j, int k, int length) {
		indexString = " depth : " +
	Integer.toString(i) +
	", row : " +
	Integer.toString(j) + 
	", column : " +
	Integer.toString(k) +
	", index : " +
	Integer.toString(i * length * length + j * length + k);
	}
	
	
	
	public String toString() {
		return indexString;
	}
	
	
}
