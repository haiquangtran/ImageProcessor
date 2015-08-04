import ij.*;
import ij.process.*;
import ij.gui.*;

import java.util.*;
import java.awt.*;

import ij.plugin.filter.*;
import ij.process.*;

import java.lang.Math.*;

public class SobelOperatorFilter implements PlugInFilter {
	private int[][] rowMask =
		{
			{-1, 0, 1},
			{-100, 0, 100},	//Can modify for weighting to mask
			{-1, 0, 1}
		};

	private int[][] colMask =
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

    	for (int row = 1; row < width-1; row++) {
    		for (int col = 1; col < height-1; col++) {
    			//Calculates values that the mask will overlay
    			int[][] matrix =
    				{
    					{image.getPixel(row-1, col-1), image.getPixel(row, col-1), image.getPixel(row+1, col-1)},
    					{image.getPixel(row-1, col), image.getPixel(row, col), image.getPixel(row+1, col)},
    					{image.getPixel(row-1, col+1), image.getPixel(row, col+1), image.getPixel(row+1, col+1)}
    				};
    			//Detect horizontal direction
    			int pixelX = innerProduct(matrix, rowMask);
    			//Detect vertical direction
    			int pixelY = innerProduct(matrix, colMask);

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

    /**
     * Calculates inner product of two matrices that have the same length
     * @param matrix1
     * @param matrix2
     * @return
     */
    public int innerProduct(int[][] matrix1, int[][] matrix2) {
    	int result = 0;

    	for (int i = 0; i < matrix1.length; i++) {
    		for (int j = 0; j < matrix1.length; j++) {
    			result += (matrix1[i][j] * matrix2[i][j]);
    		}
    	}

    	return result;
    }

	@Override
	public int setup(String arg0, ImagePlus arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

}