package textInput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

/**
 * Class for importing a textfile and show the input in the console. <br />
 * Compare to {@link TextfileSimpleConsole} this file furthermore <b>only publish valid lines</b>.
 * 
 * @author michael sidler
 */
public class TextfileRegexSimpleConsole {

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
				// Checks integer + tab + text, where "text" contains word characters (\w)
				// However: A word character checks short for [a-zA-Z_0-9] and we have special signs
				if(Pattern.matches( "(\\d)+\\t(\\w)+", line)){
					System.out.println(line);
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			br.close();
		}
	}

}
