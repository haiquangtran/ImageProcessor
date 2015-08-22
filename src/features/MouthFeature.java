package features;

import utils.ImageHelper;
import ij.process.ImageProcessor;
import feature_extraction.Feature;

public class MouthFeature extends Feature {

	public MouthFeature(ImageProcessor imageProcessor) {
		super(imageProcessor);
		this.setValue(detectMouth(imageProcessor));
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
		return ImageHelper.calculateMeanOfRect(imageProcessor, x, y, width - (quarter*2), quarter);
	}

}
