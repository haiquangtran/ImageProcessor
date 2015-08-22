package features;

import ij.process.ImageProcessor;
import utils.ImageHelper;
import feature_extraction.Feature;

public class LeftEyeFeature extends Feature {

	public LeftEyeFeature(ImageProcessor imageProcessor) {
		super(imageProcessor);
		this.setValue(detectLeftEye(imageProcessor));
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

		return ImageHelper.calculateMeanOfRect(imageProcessor, 0, 1, width/2, eyeHeight);
	}
}
