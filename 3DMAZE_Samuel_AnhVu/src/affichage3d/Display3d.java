package affichage3d;

import com.sun.j3d.utils.universe.SimpleUniverse;

import Controler.Maze;
import game.Player;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.image.ImageException;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.Texture;
import javax.media.j3d.Texture2D;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Material;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.TriangleStripArray;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.media.j3d.PointLight;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Text3D;
import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;

import java.awt.Font;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;
import javax.vecmath.Point2f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Color3f;

//PANEL 3D AFFICHANT LE LABY EN 3D 
public class Display3d extends Canvas3D implements Display3dInterface {

	private static final long serialVersionUID = 12345L;
	private TransformGroup objSpin = new TransformGroup(); // TransformGroup Général
	// Unique mur qui sera réutilisé pour des raisons de perf
	private Box basicWall = new Box(0.02f, 0.25f, 0.25f, Box.GENERATE_TEXTURE_COORDS, new Appearance());
	private static final float stepSize = 0.25f; // longueur de chaque pas dans le jeu
	private Point3d currentPosition = new Point3d(0.5f, -0 / 2f, 0.5f); // position de la caméra
	private double[] forwardVect = { -zoom, 0, 0 }; // direction de la caméra
	private double[] previousVect = { 0, 0, -zoom };// direction précédente de la cam
	private static final double zoom = 1.0; // zoom
	private static final float scaleSize = 2.5f; // échelle du jeu
	private final TriangleStripArray tri = (TriangleStripArray) (basicWall.getShape(Box.FRONT).getGeometry()); // Textures
	private SimpleUniverse myWorld;
	private static int speed = 300; // vitesse de la caméra
	public static Display3d maze3d;

	Display3d() {
		// Création de l'univers pour le laby
		super(SimpleUniverse.getPreferredConfiguration());
		initializeTextures("src/data/grid.jpg", "src/data/wall.jpg"); // Texturation des murs
		myWorld = new SimpleUniverse(this);

		BranchGroup myScene = createScene(myWorld);

		myWorld.addBranchGraph(myScene);
		myWorld.getViewingPlatform().setNominalViewingTransform();

	}

	private void initializeTextures(final String string, final String string2) {
		// Texturation des murs
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
		// création du labyrinthe
		BranchGroup scene = new BranchGroup();
		BoundingSphere bounds = new BoundingSphere();
		Transform3D scale = new Transform3D();
		scale.setScale(scaleSize);
		TransformGroup BigTG = new TransformGroup(scale);
		BigTG.addChild(generateMaze());

		Transform3D trslt = new Transform3D();
		trslt.setTranslation(
				new Vector3f((float) 2.36 * scaleSize, (float) -1.58 * scaleSize, (float) 6.25 * scaleSize));
		TransformGroup startTg = new TransformGroup(trslt);
		startTg.addChild(getArrival("src/data/press.jpg"));

		BigTG.addChild(startTg);
		objSpin = getMouseTransform(scene, bounds, false);
		objSpin.addChild(BigTG);
		objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		su.getViewingPlatform().getViewers()[0].getView().setFieldOfView(Math.PI / 2);// angle de vue de la caméra
		scene.compile();
		return scene;
	}

	public static void display(boolean show) {
		// Création de la vue 3d et initialisation de la caméra
		maze3d = new Display3d();
		maze3d.setSize(700, 700);
		if (show)
			maze3d.setVisible(true);
		maze3d.updateCameraPos(new Point3d((float) 3 * scaleSize, (float) -2 * scaleSize, (float) 8 * scaleSize), 10);
		double[] forwardVect = { zoom, 0, 0 };
		maze3d.updateCameraRot(forwardVect, 10);
		double[] v = { 0, 0, -zoom };
		maze3d.updateCameraRot(v, 10);

	}

	public static void animate(int[] ind) {
		// Animation de début de la caméra (zoom sur laby)

		maze3d.updateCameraPos(new Point3d((float) ind[2] * scaleSize * stepSize,
				(float) -ind[0] * scaleSize * stepSize, (float) ind[1] * scaleSize * stepSize), 4000);

	}

	public void goForward() {
		// Avancer d'un pas dans la direction de la caméra
		updateCameraPos(add(forwardVect, currentPosition, (float) (stepSize * scaleSize / zoom)), speed);
		checkEnd();

	}

	private static void checkEnd() {
		if (Player.getIndex() == Maze.getMaze().arrivalIndex) {
			JFrame f = new JFrame();
			JOptionPane.showMessageDialog(f, "Arrivé !");
		}

	}

	public void turnUp() {
		// Voir en haut si on n'est pas déjà en train de regarder en haut
		if (forwardVect[1] <= 0) {

			double[] dir = getUp(forwardVect);
			previousVect = forwardVect;
			updateCameraRot(dir, speed);
		}

	}

	public void turnDown() {
		// Voir en bas si on n'est pas déjà en train de regarder en bas
		if (forwardVect[1] >= 0) {

			double[] dir = getDown(forwardVect);
			previousVect = forwardVect;
			updateCameraRot(dir, speed);
		}

	}

	public void turnLeft() {
		// Tourner la caméra à gauche
		if (forwardVect[1] == 0) {
			previousVect = forwardVect;
			double[] dir = getLeft(forwardVect);
			updateCameraRot(dir, speed);
		}
	}

	public void turnRight() {
		// Tourner la caméra à droite (gauche x3)
		if (forwardVect[1] == 0) {
			previousVect = forwardVect;
			double[] dir = getLeft(getLeft(getLeft(forwardVect)));
			updateCameraRot(dir, speed);
		}
	}

	private double[] getLeft(double[] vect) {
		// retourne la direction à gauche
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

	private double[] getDown(double[] vect) {
		// retourne la direction d'en bas ATTENTION!!!! ne pas prendre une direction
		// ortho au plan horizontal pour que la matrice reste inversible!
		if (vect[1] == 0) { // plat-->haut
			double[] dir = { vect[0] / 10000, -zoom, vect[2] / 10000 };// matrice reste inversible
			return dir;
		} else if (vect[1] > 0) { // bas-->plat

			return previousVect;
		}
		return null;
	}

	private double[] getUp(double[] vect) {
		// retourne la direction d'en haut ATTENTION!!!! ne pas prendre une direction
		// ortho au plan horizontal pour que la matrice reste inversible!
		if (vect[1] == 0) { // plat-->bas
			double[] dir = { vect[0] / 10000, zoom, vect[2] / 10000 };// matrice inversible
			return dir;
		} else if (vect[1] < 0) { // haut-->plat
			return previousVect;
		}
		return null;
	}

	private void UpdateViewerGeometryJ3D(Point3d start, Point3d center, double[] vect2, double[] vect1) {
		// Mettre à jour la direction de pointage élémentaire de la caméra
		TransformGroup viewingTransformGroup = myWorld.getViewingPlatform().getViewPlatformTransform();
		Transform3D viewingTransform = new Transform3D();

		Vector3d up = new Vector3d(0, 1, 0);
		if (vect1[0] == vect2[0] && vect1[0] == 0) {
			up = new Vector3d(0, 1, 0);
		} else if (vect1[2] == vect2[2] && vect1[2] == 0) {
			up = new Vector3d(0, 1, 0);
		}

		if ((currentPosition.getX() != currentPosition.getX() || currentPosition.getY() != center.getY()
				|| currentPosition.getZ() != center.getZ())) {
			viewingTransform.lookAt(currentPosition, center, up);
			viewingTransform.invert();
			Vector3f vect = new Vector3f((float) ((float) currentPosition.getX()),
					(float) ((float) currentPosition.getY()), (float) ((float) currentPosition.getZ()));
			Transform3D look = new Transform3D();

			look.setTranslation(vect);
			look.mul(viewingTransform);
			viewingTransformGroup.setTransform(look);

		}

	}

	private void updateCameraRot(double[] nextForward, int length) {
		// Faire tourner la caméra d'une direction à une autre avec une animation fluide
		Point3d gaze1 = add(forwardVect, currentPosition);
		Point3d gaze2 = add(nextForward, currentPosition);
		Point3d[] steps = positionArray(gaze1, gaze2, length);
		for (Point3d step : steps) {
			UpdateViewerGeometryJ3D(gaze1, step, nextForward, forwardVect);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		forwardVect = nextForward;
	}

	private Point3d add(double[] gaze, Point3d position, float scaleStep) {
		// prend la position et direction pour donner le point vers lequel la caméra va
		// regarder
		// avec normalisation pour le pas
		double x = gaze[0] * scaleStep + position.getX();
		double y = gaze[1] * scaleStep + position.getY();
		double z = gaze[2] * scaleStep + position.getZ();
		return new Point3d(x, y, z);
	}

	private Point3d add(double[] gaze, Point3d position) {
		// prend la position et direction pour donner le point vers lequel la caméra va
		// regarder
		double x = gaze[0] + position.getX();
		double y = gaze[1] + position.getY();
		double z = gaze[2] + position.getZ();
		return new Point3d(x, y, z);
	}

	private void updateCameraPos(Point3d next, int length) {
		// faire avancer la caméra d'un point à un autre
		// avec fluidité
		Point3d[] steps = positionArray(currentPosition, next, length);
		for (Point3d step : steps) {
			CameraStep(step.getX(), step.getY(), step.getZ());
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void CameraStep(double x, double y, double z) {
		// faire avancer la cam d'un point à un autre
		// pas élémentaire
		Point3d nextPosition = new Point3d(x, y, z);
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
		// Donne les points de transition pour les movements fluides de la caméra
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
		// ajoute les cases (cubes) du laby pour le générer
		TransformGroup result = new TransformGroup();
		for (Controler.Box box : Controler.Maze.getMaze().getGrid()) {

			result.addChild(gridItemToTransformGroup(box).cloneTree());

		}
		return result;
	}

	private TransformGroup gridItemToTransformGroup(Controler.Box box) {
		// Donne le cube 3d avec les murs correspondant à la case box
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
		if (box == Maze.getMaze().start) {
			result.addChild(text("Start"));

		}
		if (box == Maze.getMaze().arrival) {
			result.addChild(getArrival("src/data/end.png"));

		}
		return result;
	}

	private TransformGroup text(String txt) {
		//Donne le texte en 3D qui tourne
		TransformGroup objSpin = new TransformGroup();
		objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		Appearance textAppear = new Appearance();
		ColoringAttributes textColor = new ColoringAttributes();
		textColor.setColor(1.0f, 0.0f, 0.0f);
		textAppear.setColoringAttributes(textColor);
		textAppear.setMaterial(new Material());

		Font3D font3D = new Font3D(new Font("Helvetica", Font.PLAIN, 1), new FontExtrusion());
		Text3D textGeom = new Text3D(font3D, new String("Start"));
		textGeom.setAlignment(Text3D.ALIGN_CENTER);
		Shape3D textShape = new Shape3D();
		textShape.setGeometry(textGeom);
		textShape.setAppearance(textAppear);
		objSpin.addChild(textShape);

		Alpha rotationAlpha = new Alpha(-1, 10000);

		RotationInterpolator rotator = new RotationInterpolator(rotationAlpha, objSpin);

		BoundingSphere bounds = new BoundingSphere();
		rotator.setSchedulingBounds(bounds);
		objSpin.addChild(rotator);
		Transform3D scale = new Transform3D();
		scale.setScale(0.15f);
		TransformGroup BigTG = new TransformGroup(scale);
		BigTG.addChild(objSpin);
		return BigTG;
	}

	@SuppressWarnings("deprecation")
	private TransformGroup getWall(int wallType) {
		// donne le mur correspondant (haut bas gauche droite ....)
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
		// donne le cube 3d avec les murs donnés
		TransformGroup cube = new TransformGroup();
		for (int i = 0; i < 6; i++) {
			if (solidWalls[i]) {
				cube.addChild(getWall(i));
			}
		}
		PointLight pointLi = new PointLight(new Color3f(1f, 1f, 1f), new Point3f(2f, 2f, 2f), new Point3f(1f, 0f, 0f));
		pointLi.setInfluencingBounds(new BoundingSphere(new Point3d(), 150d));
		// éclairage
		return cube;
	}

	@SuppressWarnings("deprecation")
	private TransformGroup getArrival(String str) {
		//Cube End
		TransformGroup res = new TransformGroup();
		Appearance boxArrival = mkAppWithTexture(str);
		Box box = new Box(0.12f, 0.12f, 0.12f, Box.GENERATE_TEXTURE_COORDS, boxArrival);
		TriangleStripArray tri = (TriangleStripArray) (box.getShape(Box.FRONT).getGeometry());
		tri.setTextureCoordinate(0, new Point2f(3f, 0f));
		tri.setTextureCoordinate(1, new Point2f(3f, 3f));
		tri.setTextureCoordinate(2, new Point2f(0f, 0f));
		tri.setTextureCoordinate(3, new Point2f(0f, 3f));

		TransformGroup objSpin = new TransformGroup();
		objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Alpha rotationAlpha = new Alpha(-1, 4000);
		RotationInterpolator rotator = new RotationInterpolator(rotationAlpha, objSpin);
		BoundingSphere bounds = new BoundingSphere();
		rotator.setSchedulingBounds(bounds);
		objSpin.addChild(rotator);
		res.addChild(objSpin);
		objSpin.addChild(box);
		return res;
	}

	private Appearance mkAppWithTexture(String textureName) {
		// permet d'appliquer les textures
		Appearance app = new Appearance();
		try {
			TextureLoader loader = new TextureLoader(textureName, this);
			ImageComponent2D image = loader.getImage();
			Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA, image.getWidth(), image.getHeight());
			texture.setImage(0, image);
			texture.setEnable(true);
			texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
			texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);

			app.setTexture(texture);
			app.setTextureAttributes(new TextureAttributes());
		} catch (ImageException e) {
			System.out.println("cannot read texture files");
		}

		return app;
	}

	private TransformGroup getMouseTransform(BranchGroup root, BoundingSphere bounds, boolean mouseSupport) {
		// Rotation de la cam et zoom avec la souri si activé
		TransformGroup manipulator = new TransformGroup();
		if (mouseSupport) {
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

		}

		root.addChild(manipulator);
		return manipulator;
	}

	public static void setSpeed(final int fast) {
		speed = fast;
	}

}
