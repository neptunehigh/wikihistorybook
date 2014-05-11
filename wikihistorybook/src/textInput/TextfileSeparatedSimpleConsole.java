package textInput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Class for importing a textfile and show the input in the console.<br />
 * Compare to {@link TextfileSimpleConsole} this file <br />
 * furthermore <b>separates the values from the text file</b>.
 * 
 * @author michael sidler
 */
public class TextfileSeparatedSimpleConsole {

	public static void main(String[] args) throws IOException {
		System.out.println("Read content from a textfile and separate the values into id and string");

		HashMap<Integer, String> combo = new HashMap<Integer, String>();

		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(
						"src/textInput/import-files/top_120_from_people.txt"))));
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				// Split all values between the tabs
				String tmp[] = line.split("\t");
				combo.put(Integer.parseInt(tmp[0]), tmp[1]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			br.close();
		}
		
		System.out.println(combo.toString());
	}
}
