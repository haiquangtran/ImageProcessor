package features;

import utils.ImageHelper;
import ij.process.ImageProcessor;
import feature_extraction.Feature;

public class NoseBridgeFeature extends Feature {

	public NoseBridgeFeature(ImageProcessor imageProcessor) {
		super(imageProcessor);
		this.setValue(detectNoseBridge(imageProcessor));
	}

	/**
	 * Calculates and uses the mean of an area that encapsulates the nose bridge as a feature.
	 *
	 * @param imageProcessor
	 * @return
	 */
	private double detectNoseBridge(ImageProcessor imageProcessor) {
		int width = imageProcessor.getWidth();
		int noseWidth = 3;
		int noseHeight = width/2;
		int startX = width/2-(noseWidth/2);
		int startY = 0;
		return ImageHelper.calculateMeanOfRect(imageProcessor, startX, startY, noseWidth, noseHeight);
	}

}
