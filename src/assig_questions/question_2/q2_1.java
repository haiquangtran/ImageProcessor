package assig_questions.question_2;

import filters.LaplacianFilter;
import filters.MeanFilter;
import filters.ThresholdFilter;
import ij.ImagePlus;
import ij.plugin.frame.ThresholdAdjuster;
import ij.process.ImageProcessor;
import utils.ImageHelper;

public class q2_1 {

	/**
	 * Question 2.1 - Mining Image Data: Mining Space Images
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		ImagePlus originalImage = new ImagePlus(ImageHelper.FOLDER + ImageHelper.IMAGE_4);
		ImagePlus mainImage = new ImagePlus(ImageHelper.FOLDER + ImageHelper.IMAGE_4);
		ImageProcessor imageProcessor = mainImage.getProcessor();

		//Set variables
		int matrixSize = 11;
		int threshold = 70;

		//Cleanse Image with Mean Filter
		MeanFilter meanFilter= new MeanFilter();
		meanFilter.setMeanFilterSize(matrixSize);
		meanFilter.run(imageProcessor);
		//Process using threshold filter
		ThresholdFilter thresholdFilter = new ThresholdFilter();
		thresholdFilter.setThreshold(threshold);
		thresholdFilter.run(imageProcessor);

		//Display filtered image
		mainImage.show();
		originalImage.show();
	}

}
