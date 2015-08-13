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
		//Load Training set
		File[] trainingSetFaces = new File(ImageHelper.TRAINING_FOLDER + ImageHelper.FACES_FOLDER).listFiles();
		File[] trainingSetNonFaces = new File(ImageHelper.TRAINING_FOLDER + ImageHelper.NON_FACES_FOLDER).listFiles();
		//Load Test set
		File[] testSetFaces = new File(ImageHelper.TEST_FOLDER + ImageHelper.FACES_FOLDER).listFiles();
		File[] testSetNonFaces= new File(ImageHelper.TEST_FOLDER + ImageHelper.NON_FACES_FOLDER).listFiles();

		//Write features to file.
		try {

			PrintWriter writer = new PrintWriter(ImageHelper.TRAINING_CSV);

			writer.println("feature-1, feature-2, feature-3, class");

			//Write features from image files to csv files used to train on bayes model
			writeFeaturesToFile(writer, trainingSetFaces, true);
			writeFeaturesToFile(writer, trainingSetNonFaces, false);

			writer.close();


			PrintWriter testWriter = new PrintWriter(ImageHelper.TEST_CSV);

			testWriter.println("feature-1, feature-2, feature-3, class");

			//Write features from image files to csv files used to test on bayes model
			writeFeaturesToFile(testWriter, testSetFaces, true);
			writeFeaturesToFile(testWriter, testSetNonFaces, false);

			testWriter.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

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
