package classification;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;

import feature_extraction.FeatureVector;
import utils.ImageHelper;

public class PatternFile {
	//Files containing information for classification task.
	private ArrayList<File> pattFiles = new ArrayList<File>();

	public PatternFile() {
		//Pattern files for Q3
		pattFiles.add(new File(ImageHelper.PATTERN_FOLDER +  "mfeat-fac"));
		pattFiles.add(new File(ImageHelper.PATTERN_FOLDER + "mfeat-fou"));
		pattFiles.add(new File(ImageHelper.PATTERN_FOLDER +  "mfeat-kar"));
		pattFiles.add(new File(ImageHelper.PATTERN_FOLDER + "mfeat-mor"));
		pattFiles.add(new File(ImageHelper.PATTERN_FOLDER + "mfeat-pix"));
		pattFiles.add(new File(ImageHelper.PATTERN_FOLDER + "mfeat-zer"));
	}

	/**
	 * Creates a new file that contains features and output classes from the 6 pattern files.
	 */
	public void createPatternFile() {
		//output file
		String outputFile = ImageHelper.PATTERN_FOLDER + "pattern-file.csv";

		writePatternFile(ImageHelper.PATTERN_FOLDER, outputFile);
	}

	private void writePatternFile(String folder, String outputFile) {
		try {
			PrintWriter writer = new PrintWriter(outputFile);

			//Create header info in file
			writeHeaderInfo(writer);
			//Write to CSV file
			writeToFile(writer);

			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void writeHeaderInfo(PrintWriter writer) {
		String featureName = "feature";
		int numOfAttributes = 649; //distributed over 6 files.
		for (int i = 1; i <= numOfAttributes; i++) {
			writer.print(featureName + "-" + i + " ,");
		}
		writer.print("class");
	}

	private String labelClass(int i) {
		if (i < 200) {
			return "0";
		} else if (i < 400) {
			return "1";
		} else if (i < 600) {
			return "2";
		} else if (i < 800) {
			return "3";
		} else if (i < 1000) {
			return "4";
		} else if (i < 1200) {
			return "5";
		} else if (i < 1400) {
			return "6";
		} else if (i < 1600) {
			return "7";
		} else if (i < 1800) {
			return "8";
		} else {
			return "9";
		}
	}

	private void writeToFile(PrintWriter writer) {
		try {
			//Create reader for each file
			ArrayList<BufferedReader> readers = new ArrayList<BufferedReader>();
			for (int i = 0; i < pattFiles.size(); i++) {
				readers.add(new BufferedReader(new FileReader(pattFiles.get(i))));
			}

			//Read each file
			int lines = 2000;
			for (int i = 0; i < lines; i++) {
				StringBuilder mergeLines = new StringBuilder();
				for (int index = 0; index < readers.size(); index++) {
					String[] numbers = readers.get(index).readLine().split("\\s+");

					//Format for csv file
					for (int count = 0; count < numbers.length; count++) {
						mergeLines.append(numbers[count] + ", ");
					}

					mergeLines.append(labelClass(i));
				}

				//Write to csv file
				writer.println(mergeLines.toString());
			}

			//Close readers
			for (int i = 0; i < readers.size(); i++) {
				readers.get(i).close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
