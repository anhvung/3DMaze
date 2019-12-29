
package affichage3d;
import java.applet.Applet;
import java.awt.*;

// Etape 2 :
// Importation des packages Java 3D
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.behaviors.keyboard.*;

public class KeyBehaviorTest extends Applet {

  public KeyBehaviorTest() {

    this.setLayout(new BorderLayout());

    // Etape 3 :
    // Creation du Canvas 3D
    Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
    this.add(canvas3D, BorderLayout.CENTER);

    // Etape 4 :
    // Creation d'un objet SimpleUniverse
    SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

    // Etape 5 :
    // Positionnement du point d'observation pour avoir une vue correcte de la
    // scene 3D
    simpleU.getViewingPlatform().setNominalViewingTransform();

    // Etape 6 :
    // Creation de la scene 3D qui contient tous les objets 3D que l'on veut visualiser
    BranchGroup scene = createSceneGraph(simpleU);

    // Etape 7 :
    // Compilation de la scene 3D
    scene.compile();

    // Etape 8:
    // Attachement de la scene 3D a l'objet SimpleUniverse
    simpleU.addBranchGraph(scene);
  }

  /**
   * Creation de la scene 3D qui contient tous les objets 3D
   * @return scene 3D
   */
  public BranchGroup createSceneGraph(SimpleUniverse su) {
    // Creation de l'objet parent qui contiendra tous les autres objets 3D
    BranchGroup parent = new BranchGroup();

    // Creation du groupe de transformation a partir de l'objet SimpleUniverse
    // On recupere en fait la position de la camera qui sera modifiee au
    // le clavier grace au TransformGroup tg
    TransformGroup tg = su.getViewingPlatform().getViewPlatformTransform();

    // On associe l'objet TransformGroup tg au clavier
    KeyNavigatorBehavior keyNavigatorBehavior = new KeyNavigatorBehavior(tg);

    // Champ d'action du clavier
    keyNavigatorBehavior.setSchedulingBounds(
      new BoundingSphere(new Point3d(), 1000));

    // Ajout du comportement du clavier a l'objet parent de la scene 3D
    parent.addChild(keyNavigatorBehavior);

    // Creation du cube
    ColorCube cube = new ColorCube(0.3);

    // Ajout du cube a l'objet racine de la scene 3D
    parent.addChild(cube);

    return parent;
  }

  /**
   * Etape 9 :
   * Methode main() nous permettant d'utiliser cette classe comme une applet
   * ou une application.
   * @param args arguments de la ligne de commande
   */
  public static void mained(String[] args) {
    Frame frame = new MainFrame(new KeyBehaviorTest(), 256, 256);
  }
}