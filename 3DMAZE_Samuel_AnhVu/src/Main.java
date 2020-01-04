import org.jogamp.java3d.VirtualUniverse;

public class Main {
	public static String version;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new VirtualUniverse();
		version= (String) VirtualUniverse.getProperties().get("j3d.version");
		System.out.println("JAVA3D version " +version);
		
		game.MainExecuteHere.main();
		 
		
	}

}
