package assig_questions.question_2;

import java.awt.BorderLayout;
import java.beans.FeatureDescriptor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

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
import weka.classifiers.evaluation.ThresholdCurve;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.gui.visualize.PlotData2D;
import weka.gui.visualize.ThresholdVisualizePanel;
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
		String trainingFile = ImageHelper.TRAINING_CSV_Q2;
		String testFile = ImageHelper.TEST_CSV_Q2;

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
			Evaluation eval = new Evaluation(trainingSet);
			eval.evaluateModel(naiveBayes, testSet);
			//			eval.crossValidateModel(naiveBayes, testSet, 10, new Random(1));

			//Print the result
			String result = eval.toSummaryString();
			System.out.println(result);

			//Generate ROC curve
			generateROC(eval);

			//Get the confusion matrix
			double[][] cmMatrix = eval.confusionMatrix();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Generates and displays a ROC curve from a dataset. Uses a default
	 * NaiveBayes to generate the ROC data.
	 *
	 */
	private static void generateROC(Evaluation eval) throws Exception {
		// generate curve
		ThresholdCurve tc = new ThresholdCurve();
		int classIndex = 0;
		Instances result = tc.getCurve(eval.predictions(), classIndex);

		// plot curve
		ThresholdVisualizePanel vmc = new ThresholdVisualizePanel();
		vmc.setROCString("(Area under ROC = " +
				Utils.doubleToString(tc.getROCArea(result), 4) + ")");
		vmc.setName(result.relationName());
		PlotData2D tempd = new PlotData2D(result);
		tempd.setPlotName(result.relationName());
		tempd.addInstanceNumberAttribute();

		// specify which points are connected
		boolean[] cp = new boolean[result.numInstances()];
		for (int n = 1; n < cp.length; n++)
			cp[n] = true;
		tempd.setConnectPoints(cp);

		// add plot
		vmc.addPlot(tempd);

		// display curve
		String plotName = vmc.getName();
		final javax.swing.JFrame jf =
				new javax.swing.JFrame("Weka Classifier Visualize: " + plotName);
		jf.setSize(500,400);
		jf.getContentPane().setLayout(new BorderLayout());
		jf.getContentPane().add(vmc, BorderLayout.CENTER);
		jf.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				jf.dispose();
			}
		});
		jf.setVisible(true);
	}

}
