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


		File[] listOfFiles = new File("src/assets/q2/training/face/").listFiles();

		ImagePlus mainImage = new ImagePlus(trainingFolder + facesFolder + imageTest);
		ImageProcessor imageProcessor = mainImage.getProcessor();

		//Parameters
		int threshold = (int) ImageHelper.calculateMeanImage(imageProcessor);
		int matrixSize = 3;

		//Process using threshold filter
		ThresholdFilter thresholdFilter = new ThresholdFilter();
		thresholdFilter.setThreshold(threshold);
		thresholdFilter.run(imageProcessor);


		//Write features to file.
		try {
			PrintWriter writer = new PrintWriter(ImageHelper.FOLDER + "q2/" + "training.csv");

			//Extract Features and write to file
			FeatureExtraction featureExtractor = new FeatureExtraction();
			FeatureVector imageFeatures = featureExtractor.getImageFeatureVector(imageProcessor);
			imageFeatures.writeToFile(writer, true);

			writer.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Display filtered image
		mainImage.show();
	}

}
