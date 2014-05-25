package textInput;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import au.com.bytecode.opencsv.CSVReader;

/**
 * Class for importing a 50000 entry strong csv file and show the input in the console.<br />
 * The problem however is of the quotation marks. They need to be extracted manually in Excel.<br />
 * It <b>separates the values from the csv file</b>.
 * 
 * @author michael sidler
 */
public class CSVFileSeparatedSimpleConsole50000 {

	public static void main(String[] args) throws IOException {
		System.out.println("Read content from a csv file and separate the values into id and string");
		
		HashMap<Integer, String> combo = new HashMap<Integer, String>();
		CSVReader reader = new CSVReader(new FileReader("textInput/import-files/top_50000_from_people_without_quotation_marks.csv"));
		
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