package features;

import ij.process.ImageProcessor;
import utils.ImageHelper;
import feature_extraction.Feature;

public class RightEyeFeature extends Feature {

	public RightEyeFeature(ImageProcessor imageProcessor) {
		super(imageProcessor);
		this.setValue(detectRightEye(imageProcessor));
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

		return ImageHelper.calculateMeanOfRect(imageProcessor, width/2, 1, width/2, eyeHeight);
	}

}
