package feature_extraction;

import java.io.PrintWriter;
import java.util.ArrayList;

public class FeatureVector {

	private ArrayList<Integer> features = new ArrayList<Integer>();;

	public ArrayList<Integer> getFeatures() {
		return features;
	}

	public void writeToFile(PrintWriter writer, boolean isFace) {
		String featureValues = "";

		//Each line is instance vector corresponding to one image
		for (int i = 0; i < features.size(); i++) {
			if (i != 0) {
				featureValues += ", ";
			}
			featureValues += features.get(i);
		}
		//output part is class label
		featureValues += ", " + (isFace? 1 : 0) + ",";

		writer.println(featureValues);
		writer.flush();
	}

}
