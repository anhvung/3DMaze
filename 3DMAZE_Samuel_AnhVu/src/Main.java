import org.jogamp.java3d.VirtualUniverse;

public class Main {
	public static String version;
	public static boolean debug = false;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (debug) {

			// java 9+ déclenchent des warnings à cause des .jar
			new VirtualUniverse();
			version = (String) VirtualUniverse.getProperties().get("j3d.version");
			System.out.println("JAVA3D version : " + version);
			System.out.println("jogl version : " + com.jogamp.opengl.JoglVersion.getInstance());
			System.out.println("Tested on java 8 (1.8.0_221) and 11.0.5 on windows. You are currently running : "
					+ System.getProperty("java.version"));
		}
		
		game.MainExecuteHere.main();

	}

}
