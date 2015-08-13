package assig_questions.question_2;

import java.beans.FeatureDescriptor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import feature_extraction.FeatureExtraction;
import feature_extraction.FeatureVector;
import filters.MeanFilter;
import filters.SobelOperatorFilter;
import filters.ThresholdFilter;
import utils.ImageHelper;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
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
		String testFolder = "src/assets/q2/test/";
		String facesFolder = "face/";
		String nonFacesFolder = "non-face/";
		String imageTest = "face00001.pgm";

		File[] trainingSetFaces = new File(trainingFolder + facesFolder).listFiles();
		File[] trainingSetNonFaces = new File(trainingFolder + nonFacesFolder).listFiles();

		File[] testSetFaces = new File(testFolder + facesFolder).listFiles();
		File[] testSetNonFaces= new File(testFolder + nonFacesFolder).listFiles();

		//Write features to file.
		try {

			PrintWriter writer = new PrintWriter(ImageHelper.FOLDER + "q2/" + "trainingSet.csv");

			writer.println("feature-1, feature-2, feature-3, class");

			//Write features to files used to train on bayes model
			writeFeaturesToFile(writer, trainingSetFaces, true);
			writeFeaturesToFile(writer, trainingSetNonFaces, false);

			writer.close();


			PrintWriter testWriter = new PrintWriter(ImageHelper.FOLDER + "q2/" + "testSet.csv");

			testWriter.println("feature-1, feature-2, feature-3, class");

			//Write features to files used to train on bayes model
			writeFeaturesToFile(testWriter, testSetFaces, true);
			writeFeaturesToFile(testWriter, testSetNonFaces, false);

			testWriter.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//Use features extracted on naive bayes
		runNaiveBayes();

	}

	private static void runNaiveBayes() {
		//Create features
		String trainingFile = ImageHelper.FOLDER + "q2/" + "trainingSet.csv";
		String testFile = ImageHelper.FOLDER + "q2/" + "testSet.csv";

		//load CSV
		CSVLoader loaderTrain = new CSVLoader();
		CSVLoader loaderTest = new CSVLoader();

		try {
			loaderTrain.setSource(new File(trainingFile));
			loaderTest.setSource(new File(testFile));

			//Set training set from CSV file
			Instances trainingSet = loaderTrain.getDataSet();
			trainingSet.setClassIndex(trainingSet.numAttributes()-1);
			//Set test set from CSV file
			Instances testSet = loaderTest.getDataSet();
			testSet.setClassIndex(testSet.numAttributes()-1);

			//Create a naive bayes classifier
			Classifier naiveBayes = (Classifier) new NaiveBayes();
			naiveBayes.buildClassifier(trainingSet);

			//Test the model
			Evaluation eval= new Evaluation(trainingSet);
			eval.evaluateModel(naiveBayes, testSet);

			//Print the result
			String result = eval.toSummaryString();
			System.out.println(result);

			//Get the confusion matrix
			double[][] cmMatrix = eval.confusionMatrix();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void writeFeaturesToFile(PrintWriter writer, File[] files, boolean isFace) {

		for (File file: files) {
			ImagePlus mainImage = new ImagePlus(file.getPath());
			ImageProcessor imageProcessor = mainImage.getProcessor();
			FeatureExtraction featureExtractor = new FeatureExtraction();

			//Parameters
			int threshold = (int) ImageHelper.calculateMeanImage(imageProcessor);

			//Process to binary file using threshold filter
			ThresholdFilter thresholdFilter = new ThresholdFilter();
			thresholdFilter.setThreshold(threshold);
			thresholdFilter.run(imageProcessor);

			//Extract Features and write to file
			FeatureVector imageFeatures = featureExtractor.getImageFeatureVector(imageProcessor);
			imageFeatures.writeToFile(writer, isFace);
		}

	}

}
