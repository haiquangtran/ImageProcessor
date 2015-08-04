import ij.*;
import ij.gui.ImageWindow;
import ij.io.OpenDialog;
import ij.io.Opener;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;


public class ImageProcessorApp {

	public static void main(String[] args) {
		String folder = "src/assets/";
		//Hard coded for testing
		String image1 = "test-pattern.tif";
		String image2 = "blurry-moon.tif";
		String image3 = "ckt-board-saltpep.tif";
		String image4 = "hubble.tif";

		ImagePlus mainImage = new ImagePlus(folder + image1);
		ImageProcessor imageProcessor = mainImage.getProcessor();

		int width = mainImage.getWidth();
		int height = mainImage.getHeight();

		//Filter image using Sobel operator
		SobelOperatorFilter sobelOperator = new SobelOperatorFilter();
		sobelOperator.run(imageProcessor);

		//Display filtered image
		mainImage.show();
	}


}
