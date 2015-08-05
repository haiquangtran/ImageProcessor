package filters;
import ij.*;
import ij.process.*;
import ij.gui.*;

import java.util.*;
import java.awt.*;

import ij.plugin.filter.*;
import ij.process.*;

import java.lang.Math.*;

import utils.ImageHelper;

/**
 * Applies 3x3 Laplacian Filter mask to an image using the Convolution process.
 * Used for Image Enhancement of Images.
 *
 * @author Hai Tran
 *
 */
public class LaplacianFilter implements PlugInFilter {
	private double[][] laplacianFilter =
		{
			{0, -1, 0},
			{-1, 5, -1},
			{0, -1, 0},
		};
	private double[][] laplacianFilter2 =
		{
			{1, -2, 1},
			{-2, 5, -2},
			{1, -2, 1},
		};

    /**
     * Process the image using Laplacian Filter for Image Enhancement.
     */
    public void run(ImageProcessor imageProcessor) {
    	int width = imageProcessor.getWidth();
    	int height = imageProcessor.getHeight();

    	ImageProcessor image = imageProcessor.duplicate();

    	for (int row = 1; row < width-1; row++) {
    		for (int col = 1; col < height-1; col++) {
    			//Values the mask will overlay
    			double[][] matrix = ImageHelper.getMatrix3x3(image, row, col);
    			//Apply filter
    			int resultValue = ImageHelper.innerProduct(matrix, laplacianFilter);

    			//Grey-scale
                if (resultValue < 0) {
                	resultValue = 0;
                } else if (resultValue > 255) {
                	resultValue = 255;
                }

    			imageProcessor.putPixelValue(row, col, resultValue);
    		}
    	}

    }


	@Override
	public int setup(String arg0, ImagePlus arg1) {
		// TODO Auto-generated method stub
		return DOES_8G;
	}

}