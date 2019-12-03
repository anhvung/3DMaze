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









/* Chapitre 4: Section1, texturation de primitives
 * Auteur    : daboul
 * Date      : 04/00
 */

//Java standart API
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.BorderLayout;
//Java 3d API
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
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.LineArray;
import javax.media.j3d.TriangleArray;
import javax.media.j3d.TriangleStripArray;
import javax.media.j3d.TriangleFanArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.QuadArray;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.Appearance;
import javax.media.j3d.PolygonAttributes;
import javax.vecmath.Point3f;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Alpha;
import javax.media.j3d.RotationInterpolator;
class ColorCube3D extends Frame implements WindowListener
{
	ColorCube3D()
	{
		super("- Chapitre 4: texturation de primitives -");
		this.addWindowListener(this);
		this.setLayout(new BorderLayout());
		
		//creation de la scene java3d
		Canvas3D canvas=new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		SimpleUniverse myWorld=new SimpleUniverse(canvas);
		BranchGroup myScene=createScene(myWorld);
		myWorld.addBranchGraph(myScene);
		myWorld.getViewingPlatform().setNominalViewingTransform();
		//fin de creation
		
		this.add("Center",canvas);
	}
	
	private BranchGroup createScene(SimpleUniverse su)
	{
		BranchGroup scene=new BranchGroup();
		TransformGroup objSpin=new TransformGroup();

		objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		Alpha rotationAlpha=new Alpha(1,5000);

		// on cr�e un comportement qui va appliquer la rotation � l'objet voulu
		RotationInterpolator rotator=new RotationInterpolator(rotationAlpha,objSpin);

		// on d�finit la zone sur laquelle va s'appliquer la rotation
		BoundingSphere bounds=new BoundingSphere();
		rotator.setSchedulingBounds(bounds);
		objSpin.addChild(rotator);
		
	
		//Box textur�e
		Appearance boxApp=mkAppWithTexture("src/data/rock.gif");
		Box box=new Box(0.05f,0.25f,0.25f,Box.GENERATE_TEXTURE_COORDS,boxApp);
		TriangleStripArray tri=(TriangleStripArray)(box.getShape(Box.FRONT).getGeometry());
		tri.setTextureCoordinate(0,new Point2f(3f,0f));
		tri.setTextureCoordinate(1,new Point2f(3f,3f));
		tri.setTextureCoordinate(2,new Point2f(0f,0f));
		tri.setTextureCoordinate(3,new Point2f(0f,3f));	
		Box box2=new Box(0.25f,0.05f,0.25f,Box.GENERATE_TEXTURE_COORDS,boxApp);
		TriangleStripArray tri2=(TriangleStripArray)(box2.getShape(Box.FRONT).getGeometry());
		tri2.setTextureCoordinate(0,new Point2f(3f,0f));
		tri2.setTextureCoordinate(1,new Point2f(3f,3f));
		tri2.setTextureCoordinate(2,new Point2f(0f,0f));
		tri2.setTextureCoordinate(3,new Point2f(0f,3f));	
		
		//Sphere textur�e
		Appearance sphereApp=mkAppWithTexture("src/data/stripe.gif");
		Sphere sphere=new Sphere(0.4f,Sphere.GENERATE_TEXTURE_COORDS,sphereApp);	
				
		//mise en place des objets 
		TransformGroup bigCube = new TransformGroup();
		TransformGroup cubeLine = new TransformGroup();
		TransformGroup cubeSquare = new TransformGroup();
		TransformGroup cubeCube = new TransformGroup();
		TransformGroup tg0=mkTranslation(new Vector3f(-0.25f,0f,0f));
		TransformGroup ttest=mkTranslation(new Vector3f(-0.5f,0f,0f));
		TransformGroup tg1=mkTranslation(new Vector3f(0.25f,0f,0f));
		TransformGroup tg3=mkTranslation(new Vector3f(0.0f,0.20f,0.0f));
		TransformGroup tg4=mkTranslation(new Vector3f(0.0f,-0.2f,0.0f));
		
		tg0.addChild(box);
		bigCube.addChild(tg0);
		tg1.addChild(box.cloneTree());
		cubeLine.addChild(tg1);
		tg3.addChild(box2);
		cubeLine.addChild(tg3);
		tg4.addChild(box2.cloneTree());
		bigCube.addChild(tg4);
		cubeLine.addChild(bigCube);
		ttest.addChild(bigCube.cloneTree());
		
		for (int i=-5;i<5;i++) {
			ttest=mkTranslation(new Vector3f(-i*0.5f,0f,0f));
			ttest.addChild(bigCube.cloneTree());
			cubeLine.addChild(ttest.cloneTree());
		}
		for (int i=-5;i<5;i++) {
			ttest=mkTranslation(new Vector3f(0f,-i*0.5f,0f));
			ttest.addChild(cubeLine.cloneTree());
			cubeSquare.addChild(ttest.cloneTree());
		}
		
		for (int i=-5;i<5;i++) {
			ttest=mkTranslation(new Vector3f(0f,0.0f,-i*0.5f));
			ttest.addChild(cubeSquare.cloneTree());
			cubeCube.addChild(ttest.cloneTree());
		}
		//TransformGroup tg2=mkTranslation(new Vector3f(0.52f,0f,0f));
		
		
		
		//tg2.addChild(sphere);
		
		
		//objSpin.addChild(tg2);
		objSpin.addChild(cubeCube);
		scene.addChild(objSpin);
		scene.compile();
		return scene;
	}
	
	//creation d'une apparence qui permet la texturation
	private Appearance mkAppWithTexture(String textureName)
	{
		Appearance app=new Appearance();
	        TextureLoader loader=new TextureLoader(textureName,this);
        	ImageComponent2D image=loader.getImage();
        	
        	Texture2D texture=new Texture2D(Texture.BASE_LEVEL,Texture.RGBA,image.getWidth(),image.getHeight());
        	texture.setImage(0, image);
        	texture.setEnable(true);
        	texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
        	texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
        	        	
        	app.setTexture(texture);
        	app.setTextureAttributes(new TextureAttributes());
        	return app;
    	}
    	
    	//m�thode de cr�ation d'un TransformGroup pour les translations
    	private TransformGroup mkTranslation(Vector3f vect)
    	{
    		Transform3D t3d=new Transform3D();
    		t3d.setTranslation(vect);
    		return new TransformGroup(t3d);
    	}
    	
    	//m�thode de cr�ation d'un TransformGroup pour les rotations
    	private TransformGroup mkRotation(double angle)
    	{
    		Transform3D t3d=new Transform3D();
    		t3d.rotY(angle);
    		return new TransformGroup(t3d);
    	}
		
	public void windowActivated(WindowEvent e){}
	public void windowClosed(WindowEvent e){}
	public void windowDeactivated(WindowEvent e){}
	public void windowDeiconified(WindowEvent e){}
	public void windowIconified(WindowEvent e){}
	public void windowOpened(WindowEvent e){}
	
	public void windowClosing(WindowEvent e)
	{
		System.exit(1);
	}
	
	public static void main(String args[])
	{
		ColorCube3D myApp=new ColorCube3D();
		myApp.setSize(500,300);
		myApp.setVisible(true);
	}
}




/*

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
}*/