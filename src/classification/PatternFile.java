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
	 * Create a new file that contains features and output classes from the 6 pattern files.
	 * Create a training set containing 50% instances from above file.
	 * Create a testing set containing 50% instances from above file.
	 */
	public void createPatternFile() {
		createDataSets();
	}

	private void createDataSets() {
		//output files
		String outputFile = ImageHelper.PATTERN_FILE_Q3;
		String outputTrainingFile = ImageHelper.TRAINING_CSV_Q3;
		String outputTestFile = ImageHelper.TEST_CSV_Q3;

		try {
			PrintWriter writer = new PrintWriter(outputFile);
			PrintWriter trainingWriter = new PrintWriter(outputTrainingFile);
			PrintWriter testWriter = new PrintWriter(outputTestFile);

			//Create header info in file
			writeHeaderInfo(writer);
			writeHeaderInfo(trainingWriter);
			writeHeaderInfo(testWriter);
			//Write to CSV file that contains all features
			writeToFiles(writer, trainingWriter, testWriter);

			writer.close();
			trainingWriter.close();
			testWriter.close();
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
		writer.print("class \n");
	}

	private String labelClass(int i) {
		if (i < 200) {
			return "class-0";
		} else if (i < 400) {
			return "class-1";
		} else if (i < 600) {
			return "class-2";
		} else if (i < 800) {
			return "class-3";
		} else if (i < 1000) {
			return "class-4";
		} else if (i < 1200) {
			return "class-5";
		} else if (i < 1400) {
			return "class-6";
		} else if (i < 1600) {
			return "class-7";
		} else if (i < 1800) {
			return "class-8";
		} else {
			return "class-9";
		}
	}

	private void writeToFiles(PrintWriter writer, PrintWriter trainingWriter, PrintWriter testWriter) {
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

				//Read all files at same position
				for (int index = 0; index < readers.size(); index++) {
					String[] numbers = readers.get(index).readLine().trim().split("\\s+");

					//Format for csv file
					for (int count = 0; count < numbers.length; count++) {
						mergeLines.append(numbers[count] + ", ");
					}

					mergeLines.append(labelClass(i));
				}

				//Write to csv file
				writer.println(mergeLines.toString());

				//Split dataset
				if (i % 2 == 0) {
					trainingWriter.println(mergeLines.toString());
				} else {
					testWriter.println(mergeLines.toString());
				}
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
