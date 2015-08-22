package features;

import utils.ImageHelper;
import ij.process.ImageProcessor;
import feature_extraction.Feature;

public class RightCheekFeature extends Feature {

	public RightCheekFeature(ImageProcessor imageProcessor) {
		super(imageProcessor);
		this.setValue(detectRightCheek(imageProcessor));
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

		return ImageHelper.calculateMeanOfSquare(imageProcessor, width - (area * 2), half, area);
	}
}
