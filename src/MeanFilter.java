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
 * Applies 3x3 Mean Filter mask to an image using the Convolution process.
 * Used for Noise Reduction in Images.
 * 
 * @author Hai Tran
 *
 */
public class MeanFilter implements PlugInFilter {
	private double[][] meanFilter =
		{
			{1.0/9, 1.0/9, 1.0/9},
			{1.0/9, 1.0/9, 1.0/9},
			{1.0/9, 1.0/9, 1.0/9},
		};

    /**
     * Process the image using Mean Filter for Noise Reduction.
     */
    public void run(ImageProcessor imageProcessor) {
    	int width = imageProcessor.getWidth();
    	int height = imageProcessor.getHeight();

    	ImageProcessor image = imageProcessor.duplicate();
    	
    	for (int row = 1; row < width-1; row++) {
    		for (int col = 1; col < height-1; col++) {
    			//Values the mask will overlay
    			double[][] matrix =
    				{
    					{image.getPixel(row-1, col-1), image.getPixel(row, col-1), image.getPixel(row+1, col-1)},
    					{image.getPixel(row-1, col), image.getPixel(row, col), image.getPixel(row+1, col)},
    					{image.getPixel(row-1, col+1), image.getPixel(row, col+1), image.getPixel(row+1, col+1)}
    				};
    			//Apply mean filter
    			int resultValue = ImageHelper.innerProduct(matrix, meanFilter);

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