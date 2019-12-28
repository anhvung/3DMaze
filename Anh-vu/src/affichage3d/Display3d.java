package affichage3d;

import Controler.Maze;
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

class Display3d extends Frame implements WindowListener {
	private final TransformGroup[] walls = new TransformGroup[6];
	private final Appearance boxApp = mkAppWithTexture("src/data/rock.gif"); // texture des murs
	private final Box basicWall = new Box(0.02f, 0.25f, 0.25f, Box.GENERATE_TEXTURE_COORDS, boxApp); // mur qui sera
																										// utilis�
	// pour
	// cr�er les cases
	private final TriangleStripArray tri = (TriangleStripArray) (basicWall.getShape(Box.FRONT).getGeometry());

	Display3d() {
		super("Samuel & Anh-Vu : Laby3D");
		this.addWindowListener(this);
		this.setLayout(new BorderLayout());

		// Cr�ation de la sc�ne en JAVA3D
		Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		SimpleUniverse myWorld = new SimpleUniverse(canvas);
		BranchGroup myScene = createScene(myWorld);
		myWorld.addBranchGraph(myScene);
		myWorld.getViewingPlatform().setNominalViewingTransform();
		// fin de creation

		this.add("Center", canvas);
	}

	private BranchGroup createScene(SimpleUniverse su) {

		BranchGroup scene = new BranchGroup();
		TransformGroup objSpin = new TransformGroup();

		objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		Alpha rotationAlpha = new Alpha(-1, 16000);

		// on cr�e un mouvement de rotation
		RotationInterpolator rotator = new RotationInterpolator(rotationAlpha, objSpin);

		// on d�finit la zone sur laquelle va s'appliquer la rotation
		BoundingSphere bounds = new BoundingSphere();
		rotator.setSchedulingBounds(bounds);
		objSpin.addChild(rotator);
		
		
		
		boolean[] b = { true, false, true, false, true, false };
		objSpin.addChild(getCube(b));
		scene.addChild(getCube(b));
		scene.compile();
		return scene;
	}

	private TransformGroup getWall(int wallType) {
		tri.setTextureCoordinate(0, new Point2f(3f, 0f)); //textures
		tri.setTextureCoordinate(1, new Point2f(3f, 3f));
		tri.setTextureCoordinate(2, new Point2f(0f, 0f));
		tri.setTextureCoordinate(3, new Point2f(0f, 3f));
		Transform3D rotation = new Transform3D();
		Transform3D translate = new Transform3D();
		switch (wallType) {
		case 0:
			translate.set(new Vector3f(0.230f, 0f, 0.0f)); //droite
			rotation.rotZ(0);
			break;
		case 1:
			translate.set(new Vector3f(-0.230f, 0f, 0.0f));//gauche
			rotation.rotZ(0);
			break;
		case 2:
			translate.set(new Vector3f(0.230f, 0f, 0.0f)); //Haut
			rotation.rotZ(Math.PI/2d);
			break;
		case 3:
			translate.set(new Vector3f(-0.230f, 0f, 0.0f));//Bas
			rotation.rotZ(Math.PI/2);
			break;
		case 4:
			translate.set(new Vector3f(0.230f, 0f, 0.0f)); // devant
			rotation.rotY(Math.PI/2);
			break;
		case 5:
			translate.set(new Vector3f(-0.230f, 0f, 0.0f));//derriere
			rotation.rotY(Math.PI/2);
			break;

		}
		
		rotation.mul(translate);
		TransformGroup wall = new TransformGroup(rotation);
		wall.addChild(basicWall.cloneTree());
		return wall;
	}

	private TransformGroup getCube(boolean[] solidWalls) {
		TransformGroup cube = new TransformGroup();
		for (int i = 0; i < 6; i++) {
			if (solidWalls[i]) {
				cube.addChild(getWall(i));

			}
		}
		return cube;
	}

	// Appearance permet d'appliquer les textures
	private Appearance mkAppWithTexture(String textureName) {
		Appearance app = new Appearance();
		TextureLoader loader = new TextureLoader(textureName, this);
		ImageComponent2D image = loader.getImage();

		Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
		texture.setImage(0, image);
		texture.setEnable(true);
		texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
		texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);

		app.setTexture(texture);
		app.setTextureAttributes(new TextureAttributes());
		return app;
	}

	// m�thode de cr�ation d'un TransformGroup pour les translations
	private TransformGroup mkTranslation(Vector3f vect) {
		Transform3D t3d = new Transform3D();
		t3d.setTranslation(vect);
		return new TransformGroup(t3d);
	}

	// m�thode de cr�ation d'un TransformGroup pour les rotations
	private TransformGroup mkRotation(double angle) {
		Transform3D t3d = new Transform3D();
		t3d.rotX(angle);
		return new TransformGroup(t3d);
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowOpened(WindowEvent e) {
	}

	public void windowClosing(WindowEvent e) {
		System.exit(1);
	}

	public static void main(String args[]) {
		Display3d myApp = new Display3d();
		myApp.setSize(800, 600);
		myApp.setVisible(true);
	}
}
