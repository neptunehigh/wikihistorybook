package textInput;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import au.com.bytecode.opencsv.CSVReader;

/**
 * Class for importing a csv file and show the input in the console.<br />
 * It <b>separates the values from the csv file</b>.
 * 
 * @author michael sidler
 */
public class CSVFileSeparatedSimpleConsole {

	public static void main(String[] args) throws IOException {
		System.out.println("Read content from a csv file and separate the values into id and string");
		
		HashMap<Integer, String> combo = new HashMap<Integer, String>();
		CSVReader reader = new CSVReader(new FileReader("src/textInput/import-files/top_120_from_people.csv"));
		
		String[] line = null;
		try {
			while ((line = reader.readNext()) != null) {
				combo.put(Integer.parseInt(line[0]), line[1]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			reader.close();
		}
		
		System.out.println(combo.toString());
	}
}