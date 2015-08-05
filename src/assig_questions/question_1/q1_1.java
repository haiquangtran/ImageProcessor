package assig_questions.question_1;

import utils.ImageHelper;
import filters.SobelOperatorFilter;
import ij.ImagePlus;
import ij.process.ImageProcessor;

public class q1_1 {

	/**
	 * Question 1.1 - Edge Detection
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		ImagePlus normalImage = new ImagePlus(ImageHelper.FOLDER + ImageHelper.IMAGE_1);
		ImagePlus sobelImage = new ImagePlus(ImageHelper.FOLDER + ImageHelper.IMAGE_1);
		
		ImageProcessor imageProcessor = sobelImage.getProcessor();
		
		//Filter image using Sobel operator
		SobelOperatorFilter sobelOperator = new SobelOperatorFilter();
		sobelOperator.run(imageProcessor);

		//Display filtered image
		sobelImage.show();
		normalImage.show();
		
	}

}
