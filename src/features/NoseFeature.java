package features;

import ij.process.ImageProcessor;
import utils.ImageHelper;
import feature_extraction.Feature;

public class NoseFeature extends Feature  {

	public NoseFeature(ImageProcessor imageProcessor) {
		super(imageProcessor);
		this.setValue(detectNose(imageProcessor));
	}

	/**
	 * Calculates and uses the mean of a center nxn matrix as a feature
	 * for the nose.
	 *
	 * @param imageProcessor
	 * @param areaSize matrix size n x n
	 * @return
	 */
	private double detectNose(ImageProcessor imageProcessor) {

		return ImageHelper.calculateMeanOfCenterArea(imageProcessor, 9);
	}
}
