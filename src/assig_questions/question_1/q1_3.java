package assig_questions.question_1;

import filters.LaplacianFilter;
import filters.MeanFilter;
import filters.MedianFilter;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import utils.ImageHelper;

public class q1_3 {


	/**
	 * Question 1.3 - Image Enhancement
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		ImagePlus mainImage = new ImagePlus(ImageHelper.FOLDER + ImageHelper.IMAGE_2);
		ImagePlus enhancedImage = new ImagePlus(ImageHelper.FOLDER + ImageHelper.IMAGE_2);

		ImageProcessor imageProcessor = enhancedImage.getProcessor();

		//Filter image using Laplacian Filter
		LaplacianFilter laplacianFilter= new LaplacianFilter();
		laplacianFilter.run(imageProcessor);

		//Display filtered image
		enhancedImage.show();
		mainImage.show();
	}

}
