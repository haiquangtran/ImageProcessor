package features;

import utils.ImageHelper;
import ij.process.ImageProcessor;
import feature_extraction.Feature;

public class GlobalStdFeature extends Feature {

	public GlobalStdFeature(ImageProcessor imageProcessor) {
		super(imageProcessor);
		this.setValue(detectGlobalStd(imageProcessor));
	}

	/**
	 * Calculates and uses the global standard deviation as a feature.
	 *
	 * @param imageProcessor
	 * @return
	 */
	private double detectGlobalStd(ImageProcessor imageProcessor) {
		int width = imageProcessor.getWidth();

		return ImageHelper.calculateStd(imageProcessor, 0, 0, width);
	}

}
