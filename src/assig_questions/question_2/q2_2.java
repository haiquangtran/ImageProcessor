package assig_questions.question_2;

import java.beans.FeatureDescriptor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.plaf.synth.SynthSeparatorUI;

import feature_extraction.FeatureExtraction;
import feature_extraction.FeatureStorage;
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
		//Write features to a file
		System.out.println("Writing extracted features to csv files...");
		FeatureStorage featureStorage = new FeatureStorage();
		featureStorage.save();
		System.out.println("Finished writing features to csv files...");
		//Use features extracted on naive bayes
		runNaiveBayes();
	}

	private static void runNaiveBayes() {
		//Create features
		String trainingFile = ImageHelper.TRAINING_CSV;
		String testFile = ImageHelper.TEST_CSV;

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

}
