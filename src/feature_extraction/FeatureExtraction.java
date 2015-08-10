package feature_extraction;

import ij.process.ImageProcessor;

public class FeatureExtraction {

	public FeatureVector getImageFeatureVector(ImageProcessor imageProcessor) {
		FeatureVector imageFeatures = new FeatureVector();

		int feature1 = (int) this.detectLeftEye(imageProcessor);
		int feature2 = (int) this.detectRightEye(imageProcessor);
		int feature3 = (int) this.detectNose(imageProcessor, 9);

		imageFeatures.getFeatures().add(feature1);
		imageFeatures.getFeatures().add(feature2);
		imageFeatures.getFeatures().add(feature3);

		return imageFeatures;
	}

	private double calculateMeanOfCenterArea(ImageProcessor imageProcessor, int areaSize) {
		double mean = 0;
		int centreRow = (imageProcessor.getWidth()/2);
		int centreCol = (imageProcessor.getHeight()/2);
		int startRow = centreRow - (areaSize/2);
		int startCol = centreCol - (areaSize/2);

		ImageProcessor image = imageProcessor.duplicate();

		// area size relative to center of image
		for (int row = startRow; row < startRow + areaSize; row++) {
			for (int col = startCol; col < startRow + areaSize; col++) {
				mean += image.getPixel(row, col);
			}
		}

		return mean/(areaSize*areaSize);
	}

	private double calculateMeanOfArea(ImageProcessor imageProcessor, int startRow, int startCol, int areaSize) {
		double mean = 0;
		ImageProcessor image = imageProcessor.duplicate();

		for (int row = startRow; row < (startRow + areaSize); row++) {
			for (int col = startCol; col < (startCol + areaSize); col++) {
				imageProcessor.putPixelValue(row, col, 0);

				mean += image.getPixel(row, col);
			}
		}

		return mean/(areaSize*areaSize);
	}

	private double detectNose(ImageProcessor imageProcessor, int areaSize) {

		return calculateMeanOfCenterArea(imageProcessor, areaSize);
	}

	private double detectLeftEye(ImageProcessor imageProcessor) {
		int width = imageProcessor.getWidth();
		int height = imageProcessor.getHeight();

		return calculateMeanOfArea(imageProcessor, 0, 0, width/2);
	}

	private double detectRightEye(ImageProcessor imageProcessor) {
		int width = imageProcessor.getWidth();
		int height = imageProcessor.getHeight();

		return calculateMeanOfArea(imageProcessor, width/2, width/2, width/2);
	}

	private double detectMouth(ImageProcessor imageProcessor) {
		int width = imageProcessor.getWidth();
		int height = imageProcessor.getHeight();

		return calculateMeanOfArea(imageProcessor, 0, 0, width/2);
	}

}
