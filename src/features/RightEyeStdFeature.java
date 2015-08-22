package features;

import utils.ImageHelper;
import ij.process.ImageProcessor;
import feature_extraction.Feature;

public class RightEyeStdFeature extends Feature {

	public RightEyeStdFeature(ImageProcessor imageProcessor) {
		super(imageProcessor);
		this.setValue(detectRightEyeStd(imageProcessor));
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

		return ImageHelper.calculateStd(imageProcessor, startX, startY, size);
	}

}
