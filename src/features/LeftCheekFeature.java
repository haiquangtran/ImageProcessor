package features;

import utils.ImageHelper;
import ij.process.ImageProcessor;
import feature_extraction.Feature;

public class LeftCheekFeature extends Feature {

	public LeftCheekFeature(ImageProcessor imageProcessor) {
		super(imageProcessor);
		this.setValue(detectLeftCheek(imageProcessor));
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

		return ImageHelper.calculateMeanOfSquare(imageProcessor, area , half, area);
	}

}
