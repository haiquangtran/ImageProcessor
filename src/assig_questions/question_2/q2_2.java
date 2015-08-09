package assig_questions.question_2;

import java.io.File;

import filters.MeanFilter;
import filters.ThresholdFilter;
import utils.ImageHelper;
import ij.ImagePlus;
import ij.process.ImageProcessor;

public class q2_2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String trainingFolder = "src/assets/q2/training/";
		String facesFolder = "face/";
		String nonFacesFolder = "non-face/";
		String imageTest = "face00001.pgm";


		File[] listOfFiles = new File("src/assets/q2/training/face/").listFiles();

		ImagePlus mainImage = new ImagePlus(trainingFolder + facesFolder + imageTest);
		ImageProcessor imageProcessor = mainImage.getProcessor();

		int threshold = (int) ImageHelper.getMeanValue(imageProcessor);
		int matrixSize = 3;

//		MeanFilter meanFilter= new MeanFilter();
//		meanFilter.setMeanFilterSize(matrixSize);
//		meanFilter.run(imageProcessor);
		//Process using threshold filter
		ThresholdFilter thresholdFilter = new ThresholdFilter();
		thresholdFilter.setThreshold(threshold);
		thresholdFilter.run(imageProcessor);

		//Display filtered image
		mainImage.show();
	}

}
