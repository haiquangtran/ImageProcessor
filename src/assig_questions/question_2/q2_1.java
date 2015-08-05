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
		ImagePlus mainImage = new ImagePlus(ImageHelper.FOLDER + ImageHelper.IMAGE_4);
		ImageProcessor imageProcessor = mainImage.getProcessor();

		//Cleanse Image with Mean Filter
		MeanFilter meanFilter= new MeanFilter();
		meanFilter.run(imageProcessor);
		//Process using threshold filter
		ThresholdFilter thresholdFilter = new ThresholdFilter();
		thresholdFilter.setThreshold(60);
		thresholdFilter.run(imageProcessor);

		//Display filtered image
		mainImage.show();
	}

}
