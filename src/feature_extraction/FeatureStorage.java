package feature_extraction;

import filters.ThresholdFilter;
import ij.ImagePlus;
import ij.process.ImageProcessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import utils.ImageHelper;

/**
 * This class creates csv files of features extracted from images.
 * It creates a training-set.csv and a test-set.csv.
 * These files are used in the model for Naive Bayes classifier.
 *
 * @author tranhai
 *
 */
public class FeatureStorage {

	/**
	 *  Creates csv files of extracted image features.
	 */
	public void save() {
		//Output folders
		String outputTrainingFile = ImageHelper.TRAINING_CSV;
		String outputTestFile = ImageHelper.TEST_CSV;

		//Training set
		String trainingFolder = ImageHelper.TRAINING_FOLDER;
		String testFolder = ImageHelper.TEST_FOLDER;

		//Write features to file.
		writeCSVFile(trainingFolder, outputTrainingFile);
		writeCSVFile(testFolder, outputTestFile);
	}


	/**
	 * Creates a new CSV file and writes extracted image features to it.
	 *
	 * @param folder
	 * @param outputFile
	 */
	private void writeCSVFile(String folder, String outputFile) {
		try {
			PrintWriter writer = new PrintWriter(outputFile);

			//Load training set or test set
			File[] folderFaces = new File(folder + ImageHelper.FACES_FOLDER).listFiles();
			File[] folderNonFaces = new File(folder + ImageHelper.NON_FACES_FOLDER).listFiles();

			writer.println("feature-1, feature-2, feature-3, class");

			//Write features from image files to csv files
			writeFeaturesToFile(writer, folderFaces, true);
			writeFeaturesToFile(writer, folderNonFaces, false);

			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes image features from a image folder to a csv file.
	 *
	 * @param writer
	 * @param files
	 * @param isFace
	 */
	private void writeFeaturesToFile(PrintWriter writer, File[] files, boolean isFace) {

		for (File file: files) {
			FeatureVector imageFeatures = extractImageFeatures(file);
			//Write to file
			imageFeatures.writeToFile(writer, isFace);
		}
	}

	/**
	 * Returns a feature vector of extracted features from an image file.
	 *
	 * @param file image
	 * @return FeatureVector containing extracted features
	 */
	private FeatureVector extractImageFeatures(File file) {
		ImagePlus mainImage = new ImagePlus(file.getPath());
		ImageProcessor imageProcessor = mainImage.getProcessor();
		FeatureExtraction featureExtractor = new FeatureExtraction();

		//Parameters
		int threshold = (int) ImageHelper.calculateMeanImage(imageProcessor);

		//Process to binary file using threshold filter
		ThresholdFilter thresholdFilter = new ThresholdFilter();
		thresholdFilter.setThreshold(threshold);
		thresholdFilter.run(imageProcessor);

		//Extract Features
		FeatureVector imageFeatures = featureExtractor.getImageFeatureVector(imageProcessor);

		return imageFeatures;
	}

}
