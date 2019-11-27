
public class Bloc3d {
		private boolean[] walls;
	public Bloc3d(boolean[] walls) {
		this.walls=walls;
	}
	public boolean[] getWalls() {
		return this.walls;
	}
	public String getType() {
		String res=new String();
		for (boolean wall:walls) {
			if (wall) {
				res+="1";
			}
			else {
				res+="0";
			}
		}
		return res;
	}
}
