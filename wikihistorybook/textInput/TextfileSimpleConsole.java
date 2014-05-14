package textInput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class for <b>importing a textfile</b> and show the input in the console.
 * 
 * @author michael sidler
 */
public class TextfileSimpleConsole {

	public static void main(String[] args) {
		System.out.println("Read content from a textfile");
		try {
			readInput(new File(
					"src/textInput/import-files/top_120_from_people.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void readInput(File fileName) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(fileName)));
		String line;
		try {
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			br.close();
		}
	}

}