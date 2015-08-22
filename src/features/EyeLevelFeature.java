package features;

import utils.ImageHelper;
import ij.process.ImageProcessor;
import feature_extraction.Feature;

public class EyeLevelFeature extends Feature {

	public EyeLevelFeature(ImageProcessor imageProcessor) {
		super(imageProcessor);
		this.setValue(detectEyeLevelRow(imageProcessor));
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
		return ImageHelper.calculateMeanOfRect(imageProcessor, startX, startY, width, eyeHeight);
	}

}
