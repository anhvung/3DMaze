package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Controler.Box;

public class MiniMapBox extends Box {
	private static final long serialVersionUID = 6913294075608080639L;
	private final float propWalls = 0.01f;
	private final float propStairs = 0.25f;
	private String indexString;
	private BufferedImage stairsUpImage;
	private BufferedImage stairsDownImage;
	private String special = "";
	private MiniMap container;

	public final void setSpecial(String special) {
		this.special = special;
	}

	public MiniMapBox(Box parent, MiniMap container) {
		super(parent.getIndex()[0], parent.getIndex()[1], parent.getIndex()[2]);
		walls = parent.getWalls();
		this.container = container;
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
		MiniMapBox mnb = this;
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(container.getNextDirection(mnb));
			}

		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		/*
		 * String s = ""; for (boolean wall : walls) { if (wall) s = s + '1'; else s = s
		 * + '0'; } s = s + toString();
		 */
		String s = special;
		int length = container.length;
		s += " " + ((Integer) (i * length * length + j * length + k)).toString();
		int h = getSize().height;
		int w = getSize().width;

		g.drawString(s, 10, h / 5);
		paintWalls(g, w, h);
		if (MiniMap.showSol)
			paintSol(g, w, h);
	}

	private void paintSol(Graphics g, int w, int h) {
		String nextDir = container.getNextDirection(this);
		g.setColor(new Color(1f, 0f, 0f));
		if (nextDir == "RIGHT")
			g.drawLine((int) 3 * w / 4, (int) h / 2, (int) w, (int) h / 2);
		if (nextDir == "LEFT")
			g.drawLine((int) 0, (int) h / 2, (int) w / 4, (int) h / 2);
		if (nextDir == "DOWN")
			g.drawLine((int) w / 2, (int) 3 * h / 4, (int) w / 2, (int) h);
		if (nextDir == "UP")
			g.drawLine((int) w / 2, (int) 0, (int) w / 2, (int) h / 4);
		if (nextDir == "BELLOW") {
			g.drawLine((int) w / 2, (int) h / 2, (int) 3 * w / 4, (int) 3 * h / 4);
			g.drawLine((int) w / 2, (int) 3 * h / 4, (int) 3 * w / 4, (int) h / 2);

		}
		if (nextDir == "ABOVE") {
			g.drawLine((int) w / 4, (int) h / 4, (int) w / 2, (int) h / 2);
			g.drawLine((int) w / 2, (int) h / 4, (int) w / 4, (int) h / 2);
		}
		g.setColor(new Color(0f, 0f, 0f));
	}

	private void paintWalls(Graphics g, int w, int h) {
		if (!walls[0]) {
			int l_x = (int) (w * propStairs);
			int l_y = (int) (h * propStairs);
			Image image = stairsUpImage.getScaledInstance(l_x, l_y, Image.SCALE_SMOOTH);
			g.drawImage(image, (int) w / 4, (int) h / 4, null);
		}
		if (!walls[1]) {
			int l_x = (int) (w * propStairs);
			int l_y = (int) (h * propStairs);
			Image image = stairsDownImage.getScaledInstance(l_x, l_y, Image.SCALE_SMOOTH);
			g.drawImage(image, (int) w / 2, (int) h / 2, null);
		}
		if (walls[2]) {
			int x = 0;
			int y = 0;
			int width = w;
			int height = (int) Math.round(h * propWalls);
			g.fillRect(x, y, width, height);
		}
		if (walls[3]) {
			int x = 0;
			int y = h - (int) Math.round(h * propWalls);
			int width = w;
			int height = (int) Math.round(h * propWalls);
			g.fillRect(x, y, width, height);
		}
		if (walls[4]) {
			int x = 0;
			int y = 0;
			int width = (int) Math.round(w * propWalls);
			int height = h;
			g.fillRect(x, y, width, height);
		}
		if (walls[5]) {
			int x = w - (int) Math.round(w * propWalls);
			int y = 0;
			int width = (int) Math.round(w * propWalls);
			int height = h;
			g.fillRect(x, y, width, height);
		}
	}

	public void setIndex(int i, int j, int k, int length) {
		indexString = " depth : " + Integer.toString(i) + ", row : " + Integer.toString(j) + ", column : "
				+ Integer.toString(k) + ", index : " + Integer.toString(i * length * length + j * length + k);
	}

	public String toString() {
		return indexString;
	}
}
