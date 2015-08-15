package assig_questions.question_3;

import java.io.File;

import utils.ImageHelper;
import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import classification.PatternFile;
import feature_extraction.FeatureStorage;

public class q3_1 {

	/**
	 * Question 3.1 - Object Recognition: Classification of Hand-Written Digits
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		//Process features into a valid CSV file to read.
		PatternFile patternFile = new PatternFile();
		System.out.println("Creating training set and test set files...");
		patternFile.createPatternFile();
		System.out.println("Finished creating files...");
		//classify using J48 Decision Tree
		runJ48DecisionTree();
	}

	private static void runJ48DecisionTree() {
		//Create features
		String trainingFile = ImageHelper.TRAINING_CSV_Q3;
		String testFile = ImageHelper.TEST_CSV_Q3;

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

			//Create a J48 decision tree classifier
			Classifier decisionTree = (Classifier) new J48();
			decisionTree.buildClassifier(trainingSet);

			//Test the model
			Evaluation eval = new Evaluation(trainingSet);
			eval.evaluateModel(decisionTree, testSet);

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
