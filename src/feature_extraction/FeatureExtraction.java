package feature_extraction;

import filters.SobelOperatorFilter;
import ij.process.ImageProcessor;

/**
 * This class extracts face features from images returning them in a feature vector.
 *
 * @author tranhai
 *
 */
public class FeatureExtraction {

	public FeatureVector getImageFeatureVector(ImageProcessor imageProcessor) {
		FeatureVector imageFeatures = new FeatureVector();

		int feature1 = (int) this.detectLeftEye(imageProcessor);
		int feature2 = (int) this.detectRightEye(imageProcessor);
		int feature3 = (int) this.detectNose(imageProcessor);
		int feature4 = (int) this.detectMouth(imageProcessor);
		int feature5 = (int) this.detectLeftCheek(imageProcessor);
		int feature6 = (int) this.detectRightCheek(imageProcessor);
		int feature7 = (int) this.detectNoseBridge(imageProcessor);
		int feature8 = (int) this.detectEyeLevelRow(imageProcessor);

		imageFeatures.getFeatures().add(feature1);
		imageFeatures.getFeatures().add(feature2);
		imageFeatures.getFeatures().add(feature3);
		imageFeatures.getFeatures().add(feature4);
		imageFeatures.getFeatures().add(feature5);
		imageFeatures.getFeatures().add(feature6);
		imageFeatures.getFeatures().add(feature7);
		imageFeatures.getFeatures().add(feature8);

		return imageFeatures;
	}

	/**
	 * Calculates and uses the mean of an area that encapsulates the nose bridge as a feature.
	 *
	 * @param imageProcessor
	 * @return
	 */
	private double detectNoseBridge(ImageProcessor imageProcessor) {
		int width = imageProcessor.getWidth();
		int noseWidth = 3;
		int noseHeight = width/2;
		int startX = width/2-(noseWidth/2);
		int startY = 0;
		return calculateMeanOfRect(imageProcessor, startX, startY, noseWidth, noseHeight);
	}

	/**
	 * Calculates and uses the mean of an area that encapsulates both eyes as a feature.
	 *
	 * @param imageProcessor
	 * @return
	 */
	private double detectEyeLevelRow(ImageProcessor imageProcessor) {
		int width = imageProcessor.getWidth();
		int startX = 0;
		int startY = 1;
		int eyeHeight = 4;
		return calculateMeanOfRect(imageProcessor, startX, startY, width, eyeHeight);
	}

	/**
	 * Calculates and uses the mean of a center nxn matrix as a feature
	 * for the nose.
	 *
	 * @param imageProcessor
	 * @param areaSize matrix size n x n
	 * @return
	 */
	private double detectNose(ImageProcessor imageProcessor) {

		return calculateMeanOfCenterArea(imageProcessor, 9);
	}

	/**
	 * Calculates and uses the mean of the top left half of the image as a feature
	 * for the left eye.
	 *
	 * @param imageProcessor
	 * @return
	 */
	private double detectLeftEye(ImageProcessor imageProcessor) {
		int width = imageProcessor.getWidth();
		int eyeHeight = width/2 - 3;

		return calculateMeanOfRect(imageProcessor, 0, 1, width/2, eyeHeight);
	}

	/**
	 * Calculates and uses the mean of the top right half of the image as a feature
	 * for the right eye.
	 *
	 * @param imageProcessor
	 * @return
	 */
	private double detectRightEye(ImageProcessor imageProcessor) {
		int width = imageProcessor.getWidth();
		int eyeHeight = width/2 - 3;

		return calculateMeanOfRect(imageProcessor, width/2, 1, width/2, eyeHeight);
	}

	/**
	 * Calculates and uses the mean of a region as a feature
	 * for the left cheek.
	 *
	 * @param imageProcessor
	 * @return
	 */
	private double detectLeftCheek(ImageProcessor imageProcessor) {
		int width = imageProcessor.getWidth();
		int half = width/2;
		int area = 3;

		return calculateMeanOfSquare(imageProcessor, area , half, area);
	}

	/**
	 * Calculates and uses the mean of a region as a feature
	 * for the right cheek.
	 *
	 * @param imageProcessor
	 * @return
	 */
	private double detectRightCheek(ImageProcessor imageProcessor) {
		int width = imageProcessor.getWidth();
		int half = width/2;
		int area = 3;

		return calculateMeanOfSquare(imageProcessor, width - (area * 2), half, area);
	}

	/**
	 * Calculates and uses the local area of the mouth region as a feature.
	 *
	 * @param imageProcessor
	 * @return
	 */
	private double detectMouth(ImageProcessor imageProcessor) {
		int width = imageProcessor.getWidth();
		int quarter = width/4;
		int x = quarter;
		int y = width - quarter;
		return calculateMeanOfRect(imageProcessor, x, y, width - (quarter*2), quarter);
	}

	/**
	 * Calculates and uses the standard deviation of the top left half of the image as a feature
	 * for the left eye.
	 *
	 * @param imageProcessor
	 * @return
	 */
	private double detectLeftEyeStd(ImageProcessor imageProcessor) {
		int width = imageProcessor.getWidth();
		int startX = 0;
		int startY = 0;
		int size = width/2;

		return calculateStd(imageProcessor, startX, startY, size);
	}

	/**
	 * Calculates and uses the standard deviation of the top right half of the image as a feature
	 * for the left eye.
	 *
	 * @param imageProcessor
	 * @return
	 */
	private double detectRightEyeStd(ImageProcessor imageProcessor) {
		int width = imageProcessor.getWidth();
		int startX = width/2;
		int startY = 0;
		int size = width/2;

		return calculateStd(imageProcessor, startX, startY, size);
	}

	/**
	 * Calculates and uses the global mean as a feature.
	 *
	 * @param imageProcessor
	 * @return
	 */
	private double detectGlobalMean(ImageProcessor imageProcessor) {
		int width = imageProcessor.getWidth();

		return calculateMeanOfSquare(imageProcessor, 0, 0, width);
	}

	/**
	 * Calculates and uses the global standard deviation as a feature.
	 *
	 * @param imageProcessor
	 * @return
	 */
	private double detectGlobalStd(ImageProcessor imageProcessor) {
		int width = imageProcessor.getWidth();

		return calculateStd(imageProcessor, 0, 0, width);
	}

	/**
	 * Calculates the standard deviation of an area given the starting location
	 * and the size of an nxn area.
	 *
	 * @param imageProcessor
	 * @param startX
	 * @param startY
	 * @param size
	 * @return
	 */
	private double calculateStd(ImageProcessor imageProcessor, int startX, int startY, int size) {
		ImageProcessor image = imageProcessor.duplicate();
		double mean = calculateMeanOfSquare(imageProcessor, startX, startY, size);
		double val = 0;

		for (int row = startX; row < size; row++) {
			for (int col = startY; col < size; col++) {
				val += Math.pow((image.getPixel(row, col) - mean), 2);
			}
		}

		return Math.sqrt(val/(size * size));
	}

	/**
	 * Calculates the mean of a square n x n matrix at the centre of an image.
	 * @param imageProcessor
	 * @param areaSize
	 * @return
	 */
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

	/**
	 * Calculates the mean of a square n x n matrix.
	 *
	 * @param imageProcessor
	 * @param startRow
	 * @param startCol
	 * @param areaSize
	 * @return
	 */
	private double calculateMeanOfSquare(ImageProcessor imageProcessor, int startRow, int startCol, int areaSize) {
		double mean = 0;
		ImageProcessor image = imageProcessor.duplicate();

		for (int row = startRow; row < (startRow + areaSize); row++) {
			for (int col = startCol; col < (startCol + areaSize); col++) {
				mean += image.getPixel(row, col);
			}
		}

		return mean/(areaSize*areaSize);
	}

	/**
	 * Calculates the mean of a n x m rectangle matrix.
	 *
	 * @param imageProcessor
	 * @param startRow
	 * @param startCol
	 * @param width
	 * @param height
	 * @returns
	 */
	private double calculateMeanOfRect(ImageProcessor imageProcessor, int startRow, int startCol, int width, int height) {
		double mean = 0;
		ImageProcessor image = imageProcessor.duplicate();

		for (int row = startRow; row < (startRow + width); row++) {
			for (int col = startCol; col < (startCol + height); col++) {
				mean += image.getPixel(row, col);
			}
		}

		return mean/(width*height);
	}

}
