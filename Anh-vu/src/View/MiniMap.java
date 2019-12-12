package View;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import Controler.Box;

public class MiniMap extends JPanel {
	private static final long serialVersionUID = 1L;
	public int length;
	private ArrayList<MiniMapBox> list;
	private Graphics g;
	
	public MiniMap(Box[] grid, int length) {
		this.length = length;
		g = super.getGraphics();
		setLayout(new GridLayout(length,length));
		list = new ArrayList<MiniMapBox>();
	}
	
	/*@Override
	public Component add(Component comp) {
		Component toReturn = super.add(comp);
		MiniMapBox mnb = (MiniMapBox) comp;
		list.add(mnb);
		return toReturn;
	}*/
	
	public void addList(MiniMapBox mnb) {
		list.add(mnb);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (MiniMapBox mnb : list) {
			mnb.repaint();
		}
	}
	
}
