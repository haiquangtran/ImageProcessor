package feature_extraction;

import java.io.PrintWriter;
import java.util.ArrayList;

public class FeatureVector {
	private ArrayList<Feature> features = new ArrayList<Feature>();

	public void add(Feature feature) {
		this.getFeatures().add(feature);
	}

	public ArrayList<Feature> getFeatures() {
		return features;
	}

	public void writeToFile(PrintWriter writer, boolean isFace) {
		String featureValues = "";

		//Each line is instance vector corresponding to one image
		for (int i = 0; i < features.size(); i++) {
			if (i != 0) {
				featureValues += ", ";
			}
			featureValues += features.get(i).getValue();
		}
		//output part is class label
		featureValues += ", " + (isFace? "face": "non-face");

		writer.println(featureValues);
		writer.flush();
	}

}
