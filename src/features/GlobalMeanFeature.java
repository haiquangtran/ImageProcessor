package features;

import utils.ImageHelper;
import ij.process.ImageProcessor;
import feature_extraction.Feature;

public class GlobalMeanFeature  extends Feature {

	public GlobalMeanFeature(ImageProcessor imageProcessor) {
		super(imageProcessor);
		this.setValue(detectGlobalMean(imageProcessor));
	}

	/**
	 * Calculates and uses the global mean as a feature.
	 *
	 * @param imageProcessor
	 * @return
	 */
	private double detectGlobalMean(ImageProcessor imageProcessor) {
		int width = imageProcessor.getWidth();

		return ImageHelper.calculateMeanOfSquare(imageProcessor, 0, 0, width);
	}

}
