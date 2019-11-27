import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class maze {
	private int size;
	private Bloc3d[] blocs;
	public final void initFromTextFile(String fileName) {
		BufferedReader reader;
		Bloc3d bloc;
		int mazeLine=0;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			String line = reader.readLine();
			size=Integer.parseInt(line);
			blocs =new Bloc3d[size];
			while (line != null) {
				
				mazeLine++;
				line = reader.readLine();
				bloc=toBloc(line);
				blocs[mazeLine]=bloc;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
	private Bloc3d toBloc(String line) {
		boolean [] walls = new boolean[6];
		for (int i = 0; i < 6; ++i) {
			walls[i] = line.charAt(i)=='1';
		}
		Bloc3d bloc=new Bloc3d(walls);
		
		return bloc;
	}
}
