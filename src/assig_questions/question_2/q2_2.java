package assig_questions.question_2;

import java.beans.FeatureDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import feature_extraction.FeatureExtraction;
import feature_extraction.FeatureVector;
import filters.MeanFilter;
import filters.SobelOperatorFilter;
import filters.ThresholdFilter;
import utils.ImageHelper;
import ij.ImagePlus;
import ij.process.ImageProcessor;

public class q2_2 {

	/**
	 * Question 2.2 - Face Detection: Feature Extraction
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		String trainingFolder = "src/assets/q2/training/";
		String facesFolder = "face/";
		String nonFacesFolder = "non-face/";
		String imageTest = "face00001.pgm";

		File[] trainingSetFace = new File(trainingFolder + facesFolder).listFiles();
		File[] trainingSet = new File(trainingFolder + nonFacesFolder).listFiles();

		ImagePlus mainImage = new ImagePlus(trainingFolder + facesFolder + imageTest);
		ImageProcessor imageProcessor = mainImage.getProcessor();

		FeatureExtraction featureExtractor = new FeatureExtraction();

		//Parameters
		int threshold = (int) ImageHelper.calculateMeanImage(imageProcessor);

		//Process using threshold filter
		ThresholdFilter thresholdFilter = new ThresholdFilter();
		thresholdFilter.setThreshold(threshold);
		thresholdFilter.run(imageProcessor);

		//Write features to file.
		try {
			PrintWriter writer = new PrintWriter(ImageHelper.FOLDER + "q2/" + "training.csv");

			//Extract Features and write to file
			FeatureVector imageFeatures = featureExtractor.getImageFeatureVector(imageProcessor);
			imageFeatures.writeToFile(writer, true);

			writer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//Display filtered image
		mainImage.show();
	}

}
