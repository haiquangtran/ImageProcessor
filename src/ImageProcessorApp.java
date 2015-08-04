import ij.*;
import ij.gui.ImageWindow;
import ij.io.OpenDialog;
import ij.io.Opener;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;


public class ImageProcessorApp {

	public static void main(String[] args) {
		String folder = "src/assets/";
		String filename = "test-pattern.tif";

		ImagePlus mainImage = new ImagePlus(folder + filename);
		ImageProcessor imageProcessor = mainImage.getProcessor();

		int width = mainImage.getWidth();
		int height = mainImage.getHeight();

		//Process Sobel operator
		SobelOperatorFilter sobelOperator = new SobelOperatorFilter();
		sobelOperator.run(imageProcessor);

		//Display filtered image
		mainImage.show();
	}


}
