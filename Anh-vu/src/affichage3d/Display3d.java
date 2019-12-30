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
import javax.media.j3d.ViewPlatform;
import javax.media.j3d.View;
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
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
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
	private Box basicWall = new Box(0.02f, 0.25f, 0.25f, Box.GENERATE_TEXTURE_COORDS, new Appearance()); // unique mur
	private static final float stepSize = 0.1f;																					// du jeu
	private Point3d currentPosition = new Point3d(0.5f, -0 / 2f, 0.5f);
	private Point3d nextPosition = new Point3d(1, 1, 1);
	private double[] forwardVect = { 0, 0, -zoom };
	private double currentAngle = 0d;
	private static final double zoom = 10000.0;
	private static final float scaleSize = 2.5f;
	// utilis�
	// pour
	// cr�er les cases
	private final TriangleStripArray tri = (TriangleStripArray) (basicWall.getShape(Box.FRONT).getGeometry());
	private SimpleUniverse myWorld;

	Display3d() {
		super("Samuel & Anh-Vu : Laby3D");
		initializeTextures("src/data/sam.jpg", "src/data/wall.jpg");
		this.setLayout(new BorderLayout());

		// Cr�ation de la sc�ne en JAVA3D
		Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		myWorld = new SimpleUniverse(canvas);
		canvas.addKeyListener(this);
		BranchGroup myScene = createScene(myWorld);

		myWorld.addBranchGraph(myScene);
		myWorld.getViewingPlatform().setNominalViewingTransform();
		// fin de creation

		this.add("Center", canvas);
	}

	private void initializeTextures(final String string, final String string2) {
		final Appearance sides = mkAppWithTexture(string);
		final Appearance walls = mkAppWithTexture(string2);
		basicWall.setAppearance(Box.BACK, walls);
		basicWall.setAppearance(Box.TOP, walls);
		basicWall.setAppearance(Box.BOTTOM, walls);
		basicWall.setAppearance(Box.FRONT, walls);
		basicWall.setAppearance(Box.LEFT, sides);
		basicWall.setAppearance(Box.RIGHT, sides);

	}

	private BranchGroup createScene(SimpleUniverse su) {

		BranchGroup scene = new BranchGroup();
		BoundingSphere bounds = new BoundingSphere();
		ViewPlatform vp = new ViewPlatform();
		vp.setViewAttachPolicy(View.RELATIVE_TO_FIELD_OF_VIEW);

		Transform3D scale = new Transform3D();
		scale.setScale(scaleSize);
		TransformGroup BigTG = new TransformGroup(scale);
		BigTG.addChild(generateMaze());
		objSpin = getMouseTransform(scene, bounds);
		objSpin.addChild(BigTG);
		objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		TransformGroup tg = su.getViewingPlatform().getViewPlatformTransform();

		View view = new View();
		PhysicalBody pb = new PhysicalBody();
		PhysicalEnvironment pe = new PhysicalEnvironment();
		view.setPhysicalEnvironment(pe);
		view.setPhysicalBody(pb);

		view.setFieldOfView(Math.PI / 2.2);
		// attach the View to the ViewPlatform

		su.getViewingPlatform().getViewers()[0].getView().setFieldOfView(Math.PI / 2.2);

		// KeyNavigatorBehavior keyNavigatorBehavior = new KeyNavigatorBehavior(tg);

		// Champ d'action du clavier
		// keyNavigatorBehavior.setSchedulingBounds(new BoundingSphere(new Point3d(),
		// 1000));
		// scene.addChild(keyNavigatorBehavior);

		scene.compile();

		return scene;
	}

	public static void display() {
		Display3d myApp = new Display3d();
		myApp.updateCameraPos(new Point3d(0.5f, -0 / 2f, 0.5f), 100);
		double[] forwardVect = { 0, 0, -zoom };
		myApp.updateCameraRot(forwardVect, 100);
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
		if (key == 'z') {
			System.out.println("z");
			goForward();
			// updateCameraRot(new Point3d(-1,0,0),1500);
		}
		if (key == 'd') {
			System.out.println("d");
			turnRight();
			// updateCameraRot(new Point3d(-1,0,0),1500);
		}
		if (key == 'q') {
			System.out.println("q");
			// updateCameraRot(1, 0, 1500);
			turnLeft();
		}
		if (key == 's') {
			System.out.println("q");
			// updateCameraRot(1, 0, 1500);
			goBack();
		}

	}

	private void goBack() {
		final int speed = 100;
		updateCameraPos(add(getLeft(getLeft(forwardVect)), currentPosition, (float) (stepSize * scaleSize / zoom)), speed);

	}

	private void goForward() {
		final int speed = 100;
		
		System.out.println("Starting at : " + currentPosition.toString());
		System.out.println("ForwqrdVect : " + forwardVect[0] + " " + forwardVect[1] + " " + forwardVect[2] + " ");

		// final double[] forwardVect = { 0, 0, -1 };
		updateCameraPos(add(forwardVect, currentPosition, (float) (stepSize * scaleSize / zoom)), speed);

	}

	private void turnLeft() {
		final int speed = 100;
		System.out.println("ForwqrdVect : " + forwardVect[0] + " " + forwardVect[1] + " " + forwardVect[2] + " ");
		if (forwardVect[2] < 0) { // north-->west
			double[] dir = { -zoom, 0, 0 };
			updateCameraRot(dir, speed);
			System.out.println("this is now facing east");
		} else if (forwardVect[0] < 0) { // X west-->south
			double[] dir = { 0, 0, zoom };
			updateCameraRot(dir, speed);
			System.out.println("this is now facing south");
		} else if (forwardVect[2] > 0) { // Y South-->east
			double[] dir = { zoom, 0, 0 };
			updateCameraRot(dir, speed);
			System.out.println("this is now facing west");
		} else if (forwardVect[0] > 0) { // X east-->North
			double[] dir = { 0, 0, -zoom };
			updateCameraRot(dir, speed);
			System.out.println("this is now facing North");

		}
		// updateCurrentPos();
	}

	private double[] getLeft(double[] vect) {
		if (vect[2] < 0) { // north-->west
			double[] dir = { -zoom, 0, 0 };
			return dir;
		} else if (vect[0] < 0) { // X west-->south
			double[] dir = { 0, 0, zoom };
			return dir;

		} else if (vect[2] > 0) { // Y South-->east
			double[] dir = { zoom, 0, 0 };
			return dir;

		} else if (vect[0] > 0) { // X east-->North
			double[] dir = { 0, 0, -zoom };
			return dir;
		}
		return null;
	}

	private void updateCurrentPos() {
		if (forwardVect[2] > 0) {// south
			currentPosition = new Point3d(currentPosition.getX(), currentPosition.getY(), -currentPosition.getZ());
		} else if (forwardVect[0] > 0) {// east
			currentPosition = new Point3d(-currentPosition.getY(), currentPosition.getY(), currentPosition.getX());
		} else if (forwardVect[0] < 0) {// west
			currentPosition = new Point3d(currentPosition.getY(), currentPosition.getZ(), -currentPosition.getX());
		}

	}

	private void turnRight() {
		final int speed = 100;
		System.out.println("ForwqrdVect : " + forwardVect[0] + " " + forwardVect[1] + " " + forwardVect[2] + " ");
		if (forwardVect[2] < 0) { // devant Y north-->east
			double[] dir = { zoom, 0, 0 };
			updateCameraRot(dir, speed);
			System.out.println("this is now facing east");
		} else if (forwardVect[0] > 0) { // devant X east-->south
			double[] dir = { 0, 0, zoom };
			updateCameraRot(dir, speed);
			System.out.println("this is now facing south");
		} else if (forwardVect[2] > 0) { // devant Y South-->west
			double[] dir = { -zoom, 0, 0 };
			updateCameraRot(dir, speed);
			System.out.println("this is now facing west");
		} else if (forwardVect[0] < 0) { // devant X West-->North
			double[] dir = { 0, 0, -zoom };
			updateCameraRot(dir, speed);
			System.out.println("this is now facing North");
		}
		// updateCurrentPos();

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

	public void UpdateViewerGeometryJ3D(Point3d start, Point3d center) {
		TransformGroup viewingTransformGroup = myWorld.getViewingPlatform().getViewPlatformTransform();
		Transform3D viewingTransform = new Transform3D();

		final Vector3d up = new Vector3d(0, 1, 0);
		if (currentPosition.getX() != currentPosition.getX() || currentPosition.getY() != center.getY()
				|| currentPosition.getZ() != center.getZ()) {
			viewingTransform.lookAt(currentPosition, center, up);
			viewingTransform.invert();
			Vector3f vect = new Vector3f((float) ((float) currentPosition.getX()),
					(float) ((float) currentPosition.getY()), (float) ((float) currentPosition.getZ()));
			Transform3D look = new Transform3D();
			Transform3D pers = new Transform3D();
			look.setTranslation(vect);
			look.mul(viewingTransform);
			
			viewingTransformGroup.setTransform(look);
			

		}

	}

	public void updateCameraRot(double[] nextForward, int length) {
		Point3d gaze1 = add(forwardVect, currentPosition);
		Point3d gaze2 = add(nextForward, currentPosition);
		System.out.println("Currentpos : " + currentPosition.toString());
		System.out.println("gaze1 : " + gaze1.toString());
		System.out.println("gaze2 : " + gaze2.toString());
		Point3d[] steps = positionArray(gaze1, gaze2, length);
		for (Point3d step : steps) {
			UpdateViewerGeometryJ3D(gaze1, step);

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		forwardVect = nextForward;
	}

	private Point3d add(double[] pos, Point3d position, float scaleStep) {
		double[] gaze = magicTransf(pos);
		double x = gaze[0] * scaleStep + position.getX();
		double y = gaze[1] * scaleStep + position.getY();
		double z = gaze[2] * scaleStep + position.getZ();
		return new Point3d(x, y, z);
	}

	private double[] magicTransf(double[] pos) {
		double[] res = { 0, 0, -zoom };

		return pos;
	}

	private Point3d add(double[] gaze, Point3d position) {
		double x = gaze[0] + position.getX();
		double y = gaze[1] + position.getY();
		double z = gaze[2] + position.getZ();
		return new Point3d(x, y, z);
	}

	public void updateCameraPos(Point3d next, int length) {

		Point3d[] steps = positionArray(currentPosition, next, length);
		for (Point3d step : steps) {
			CameraStep(step.getX(), step.getY(), step.getZ());
			System.out.println("getting to : " + step.toString());
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void CameraStep(double x, double y, double z) {
		nextPosition = new Point3d(x, y, z);
		Vector3d up = new Vector3d(0, 1, 0);
		Transform3D viewingTransform = new Transform3D();
		viewingTransform.lookAt(currentPosition, add(forwardVect, currentPosition), up);
		viewingTransform.invert();

		Vector3f vect = new Vector3f((float) ((float) nextPosition.getX()), (float) ((float) nextPosition.getY()),
				(float) ((float) nextPosition.getZ()));
		Transform3D look = new Transform3D();
		look.setTranslation(vect);
		look.mul(viewingTransform);
		myWorld.getViewingPlatform().getViewPlatformTransform().setTransform(look);
		currentPosition = new Point3d(x, y, z);

	}

	private Point3d[] positionArray(Point3d start, Point3d finish, int length) {
		Point3d[] result = new Point3d[length + 1];
		double[] current = { start.getX(), start.getY(), start.getZ() };
		double[] next = { finish.getX(), finish.getY(), finish.getZ() };
		for (int i = 0; i < length + 1; i++) {
			result[i] = new Point3d(current[0] + i * (next[0] - current[0]) / length,
					current[1] + i * (next[1] - current[1]) / length, current[2] + i * (next[2] - current[2]) / length);
		}

		return result;

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

}
