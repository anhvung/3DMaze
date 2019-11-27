package threeDimension;

import java.applet.Applet;
import java.awt.*;

import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3f;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.applet.MainFrame;


public class ColorCube3D extends Applet {

  public ColorCube3D() {
    this.setLayout(new BorderLayout());


    Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());

    this.add(canvas3D, BorderLayout.CENTER);


    SimpleUniverse simpleU = new SimpleUniverse(canvas3D);


    simpleU.getViewingPlatform().setNominalViewingTransform();

    BranchGroup scene = createSceneGraph();


    scene.compile();


    simpleU.addBranchGraph(scene);
  }

  /**
   * Creation de la scene 3D qui contient tous les objets 3D
   * @return scene 3D
   */
  public BranchGroup createSceneGraph() {
	  BranchGroup objRoot=new BranchGroup();
	Transform3D rotate = new Transform3D();
	rotate.rotX(Math.PI/3.0d);//rotation d'angle Pi/3
	TransformGroup objRotate = new TransformGroup(rotate);
	objRotate.addChild(new ColorCube(0.5));// de rayon 50 cm
	objRoot.addChild(objRotate);
//DEUXIEME CUBE
	
	Transform3D translate1 = new Transform3D();
	translate1.set(new Vector3f(0.6f, 0.0f, 0.0f));
	translate1.mul(rotate);
	TransformGroup TG1 = new TransformGroup(translate1);
	TG1.addChild(new ColorCube(0.5));// de rayon 30 cm
	objRoot.addChild(TG1);
	return objRoot;
  }

  public static void main(String[] args) {
    Frame frame = new MainFrame(new ColorCube3D(), 256, 256);
  }
}