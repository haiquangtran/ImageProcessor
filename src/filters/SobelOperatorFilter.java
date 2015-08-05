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

public class SobelOperatorFilter implements PlugInFilter {
	private double[][] rowMask =
		{
			{-1, 0, 1},
			{-5, 0, 5},	//Can add weighting to mask
			{-1, 0, 1}
		};

	private double[][] colMask =
		{
			{-1,-2,-1},
			{0, 0, 0},
			{1, 2, 1}
		};

    /**
     * Process the image using Sobel Operator Filter for edge detection.
     */
    public void run(ImageProcessor imageProcessor) {
    	int width = imageProcessor.getWidth();
    	int height = imageProcessor.getHeight();

    	ImageProcessor image = imageProcessor.duplicate();

    	for (int row = 0; row < width; row++) {
    		for (int col = 0; col < height; col++) {
    			//Values the mask will overlay
    			double[][] matrix = ImageHelper.getMatrix(image, 3, row, col);
    			//Detect horizontal direction
    			double pixelX = ImageHelper.innerProduct(matrix, rowMask);
    			//Detect vertical direction
    			double pixelY = ImageHelper.innerProduct(matrix, colMask);

    			//Edge magnitude
    			int magnitude = (int) Math.sqrt((pixelX * pixelX) + (pixelY * pixelY));

    			//Grey-scale
                if (magnitude < 0) {
                	magnitude = 0;
                } else if (magnitude > 255) {
                	magnitude = 255;
                }

    			imageProcessor.putPixel(row, col, magnitude);
    		}
    	}
    }

	@Override
	public int setup(String arg0, ImagePlus arg1) {
		// TODO Auto-generated method stub
		return DOES_8G;
	}

}