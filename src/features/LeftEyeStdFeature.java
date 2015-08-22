package features;

import feature_extraction.Feature;
import ij.process.ImageProcessor;
import utils.ImageHelper;

public class LeftEyeStdFeature extends Feature {

	public LeftEyeStdFeature(ImageProcessor imageProcessor) {
		super(imageProcessor);
		this.setValue(detectLeftEyeStd(imageProcessor));
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

		return ImageHelper.calculateStd(imageProcessor, startX, startY, size);
	}

}
