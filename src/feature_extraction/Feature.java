package feature_extraction;

import ij.process.ImageProcessor;

public abstract class Feature {
	private ImageProcessor imageProcessor;
	private int value;

	public Feature(ImageProcessor imageProcessor) {
		this.imageProcessor = imageProcessor;
	}

	public int getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = (int) value;
	}

	public ImageProcessor getImageProcessor() {
		return imageProcessor;
	}

}
