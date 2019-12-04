import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3f;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.BorderLayout;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.Appearance;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.TriangleStripArray;
import javax.vecmath.Vector3f;
import javax.vecmath.Point2f;

import javax.media.j3d.Alpha;
import javax.media.j3d.RotationInterpolator;
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
	
	public TransformGroup getTg(Box box) {
		
		
		return null;
	}
}
