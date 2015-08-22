package feature_extraction;

import features.EyeLevelFeature;
import features.LeftCheekFeature;
import features.LeftEyeFeature;
import features.MouthFeature;
import features.NoseBridgeFeature;
import features.NoseFeature;
import features.RightCheekFeature;
import features.RightEyeFeature;
import ij.process.ImageProcessor;

/**
 * This class extracts face features from images and stores them in a feature vector.
 *
 * @author tranhai
 *
 */
public class FeatureExtraction {
	private FeatureVector imageFeatures;

	public FeatureExtraction(ImageProcessor imageProcessor) {
		imageFeatures = new FeatureVector();

		// Extract features from the images
		Feature feature1 = new LeftEyeFeature(imageProcessor);
		Feature feature2 = new RightEyeFeature(imageProcessor);
		Feature feature3 = new NoseFeature(imageProcessor);
		Feature feature4 = new MouthFeature(imageProcessor);
		Feature feature5 = new LeftCheekFeature(imageProcessor);
		Feature feature6 = new RightCheekFeature(imageProcessor);
		Feature feature7 = new NoseBridgeFeature(imageProcessor);
		Feature feature8 = new EyeLevelFeature(imageProcessor);

		// Add features to FeatureVector
		imageFeatures.add(feature1);
		imageFeatures.add(feature2);
		imageFeatures.add(feature3);
		imageFeatures.add(feature4);
		imageFeatures.add(feature5);
		imageFeatures.add(feature6);
		imageFeatures.add(feature7);
		imageFeatures.add(feature8);
	}

	public FeatureVector getImageFeatureVector() {
		return imageFeatures;
	}

}
