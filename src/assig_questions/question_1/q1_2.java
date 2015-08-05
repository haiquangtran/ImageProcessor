package assig_questions.question_1;

import filters.MeanFilter;
import filters.MedianFilter;
import filters.SobelOperatorFilter;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import utils.ImageHelper;

public class q1_2 {

	/**
	 * Question 1.2 - Noise Cancellation
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		ImagePlus meanImage = new ImagePlus(ImageHelper.FOLDER + ImageHelper.IMAGE_3);
		ImagePlus medImage = new ImagePlus(ImageHelper.FOLDER + ImageHelper.IMAGE_3);

		ImageProcessor meanProcessor = meanImage.getProcessor();
		ImageProcessor medianProcessor = medImage.getProcessor();

		//Filter image using Mean Filter
		MeanFilter meanFilter= new MeanFilter();
		meanFilter.run(meanProcessor);
		//Filter image using Median Filter
		MedianFilter medianFilter = new MedianFilter();
		medianFilter.run(medianProcessor);

		//Display filtered image
		meanImage.show();
		medImage.show();
	}
}
