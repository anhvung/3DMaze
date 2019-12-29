package affichage3d;

import com.sun.j3d.utils.behaviors.keyboard.*;
import Controler.Maze;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.Transform3D;
import javax.vecmath.Vector3f;
import java.awt.BorderLayout;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.PointLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.vecmath.Point3d;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.TriangleStripArray;
import javax.vecmath.Vector3f;
import javax.vecmath.Point2f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Color3f;

import javax.media.j3d.Alpha;
import javax.media.j3d.RotationInterpolator;
import java.awt.event.*;
import java.io.PrintStream;
import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Display3d extends JFrame implements KeyListener {
	private TransformGroup objSpin = new TransformGroup();
	private final Appearance boxApp = mkAppWithTexture("src/data/sam.jpg"); // texture des murs
	private final Box basicWall = new Box(0.02f, 0.25f, 0.25f, Box.GENERATE_TEXTURE_COORDS, boxApp); // mur qui sera
	private Point3d currentPosition = new Point3d(0,0,0);
	private Point3d nextPosition = new Point3d(1, 1, 1);

	// utilisé
	// pour
	// créer les cases
	private final TriangleStripArray tri = (TriangleStripArray) (basicWall.getShape(Box.FRONT).getGeometry());
	private SimpleUniverse myWorld;

	Display3d() {
		super("Samuel & Anh-Vu : Laby3D");
		;
		this.setLayout(new BorderLayout());

		// Création de la scène en JAVA3D
		Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		myWorld = new SimpleUniverse(canvas);
		canvas.addKeyListener(this);
		BranchGroup myScene = createScene(myWorld);

		myWorld.addBranchGraph(myScene);
		myWorld.getViewingPlatform().setNominalViewingTransform();
		// fin de creation

		this.add("Center", canvas);
	}

	private BranchGroup createScene(SimpleUniverse su) {

		BranchGroup scene = new BranchGroup();
		BoundingSphere bounds = new BoundingSphere();
		objSpin = getMouseTransform(scene, bounds);
		objSpin.addChild(generateMaze());
		objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		TransformGroup tg = su.getViewingPlatform().getViewPlatformTransform();

		KeyNavigatorBehavior keyNavigatorBehavior = new KeyNavigatorBehavior(tg);

		// Champ d'action du clavier
		keyNavigatorBehavior.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000));
		scene.addChild(keyNavigatorBehavior);
		scene.compile();
		
		return scene;
	}

	private TransformGroup generateMaze() {
		TransformGroup result = new TransformGroup();
		for (Controler.Box box : Controler.Maze.getMaze().getGrid()) {

			result.addChild(gridItemToTransformGroup(box).cloneTree());

		}
		return result;
	}

	private TransformGroup gridItemToTransformGroup(Controler.Box box) {

		int[] indexes = box.getIndex();
		final float cubeInterval = 0.5f;
		Transform3D translZ = new Transform3D();

		translZ.set(new Vector3f(0f, 0f, indexes[1] * cubeInterval)); // Y

		Transform3D translY = new Transform3D();
		translY.set(new Vector3f(0f, -indexes[0] * cubeInterval, 0.0f)); // Z AXIS

		Transform3D translX = new Transform3D();
		translX.set(new Vector3f(indexes[2] * cubeInterval, 0f, 0.0f)); // X

		translZ.mul(translY);
		translZ.mul(translX);
		TransformGroup result = new TransformGroup(translZ);
		result.addChild(getCube(box.getWalls()));
		return result;
	}

	@SuppressWarnings("deprecation")
	private TransformGroup getWall(int wallType) {
		tri.setTextureCoordinate(0, new Point2f(3f, 0f)); // textures
		tri.setTextureCoordinate(1, new Point2f(3f, 3f));
		tri.setTextureCoordinate(2, new Point2f(0f, 0f));
		tri.setTextureCoordinate(3, new Point2f(0f, 3f));
		Transform3D rotation = new Transform3D();
		Transform3D translate = new Transform3D();
		switch (wallType) {
		case 5:
			translate.set(new Vector3f(0.230f, 0f, 0.0f)); // droite
			rotation.rotZ(0);
			break;
		case 4:
			translate.set(new Vector3f(-0.230f, 0f, 0.0f));// gauche
			rotation.rotZ(0);
			break;
		case 0:
			translate.set(new Vector3f(0.230f, 0f, 0.0f)); // Haut
			rotation.rotZ(Math.PI / 2);
			break;
		case 1:
			translate.set(new Vector3f(-0.230f, 0f, 0.0f));// Bas
			rotation.rotZ(Math.PI / 2);
			break;
		case 2:
			translate.set(new Vector3f(0.230f, 0f, 0.0f)); // devant
			rotation.rotY(Math.PI / 2);
			break;
		case 3:
			translate.set(new Vector3f(-0.230f, 0f, 0.0f));// derriere
			rotation.rotY(Math.PI / 2);
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
		PointLight pointLi = new PointLight(new Color3f(1f, 1f, 1f), new Point3f(2f, 2f, 2f), new Point3f(1f, 0f, 0f));
		pointLi.setInfluencingBounds(new BoundingSphere(new Point3d(), 150d));
		// cube.addChild(pointLi);
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

	private TransformGroup getMouseTransform(BranchGroup root, BoundingSphere bounds) {
		TransformGroup manipulator = new TransformGroup();
		manipulator.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		// Rotation a la souris
		MouseRotate rotateBehavior = new MouseRotate();
		rotateBehavior.setFactor(0.003);
		rotateBehavior.setTransformGroup(manipulator);
		rotateBehavior.setSchedulingBounds(bounds);
		manipulator.addChild(rotateBehavior);

		// Translation
		MouseTranslate translateBehavior = new MouseTranslate();
		translateBehavior.setTransformGroup(manipulator);
		translateBehavior.setSchedulingBounds(bounds);
		manipulator.addChild(translateBehavior);

		// Zoom Molette
		MouseWheelZoom wheelZoomBehavior = new MouseWheelZoom();
		wheelZoomBehavior.setTransformGroup(manipulator);
		wheelZoomBehavior.setSchedulingBounds(bounds);
		manipulator.addChild(wheelZoomBehavior);

		// Zoom Souris
		MouseZoom zoomBehavior = new MouseZoom();
		zoomBehavior.setTransformGroup(manipulator);
		zoomBehavior.setSchedulingBounds(bounds);
		manipulator.addChild(zoomBehavior);

		root.addChild(manipulator);
		return manipulator;
	}

	// méthode de création d'un TransformGroup pour les translations
	private TransformGroup mkTranslation(Vector3f vect) {
		Transform3D t3d = new Transform3D();
		t3d.setTranslation(vect);
		return new TransformGroup(t3d);
	}

	// méthode de création d'un TransformGroup pour les rotations
	private TransformGroup mkRotation(double angle) {
		Transform3D t3d = new Transform3D();
		t3d.rotX(angle);
		return new TransformGroup(t3d);
	}

	public static void display() {
		Display3d myApp = new Display3d();
		myApp.updateCamera(new Point3d(0,0,4),100);
		myApp.setSize(800, 600);
		myApp.setVisible(true);
	}

	public static Display3d get3DMaze() {
		Display3d myApp = new Display3d();
		myApp.setSize(800, 600);
		return myApp;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		char key = e.getKeyChar();

		if (key == 'd') {
			System.out.println("d");
			updateCamera(new Point3d(1, -1,3), 1500);
		}

	}

	public void updateCamera(Point3d next, int length) {
		Point3d[] steps = positionArray(currentPosition, next, length);
		for (Point3d step : steps) {
			CameraStep(step.getX(), step.getY(), step.getZ());
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void CameraStep(double x, double y, double z) {
		nextPosition = new Point3d(x, y, z);
		System.out.println(nextPosition);
		Vector3f vect = new Vector3f(  (float) ((float) nextPosition.getX()), (float) ((float) nextPosition.getY()),(float) ((float) nextPosition.getZ()));
		Transform3D look = new Transform3D();
		System.out.println(vect);
		look.setTranslation(vect);
		myWorld.getViewingPlatform().getViewPlatformTransform().setTransform(look);
		currentPosition = new Point3d(x, y, z);

	}

	private Point3d[] positionArray(Point3d start, Point3d finish, int length) {
		Point3d[] result = new Point3d[length+1];
		double[] current = { start.getX(), start.getY(), start.getZ() };
		double[] next = { finish.getX(), finish.getY(), finish.getZ() };
		for (int i = 0; i < length+1; i++) {
			result[i] = new Point3d(current[0] + i * (next[0] - current[0]) / length,
					current[1] + i * (next[1] - current[1]) / length, current[2] + i * (next[2] - current[2]) / length);
		}

		return result;

	}

}
