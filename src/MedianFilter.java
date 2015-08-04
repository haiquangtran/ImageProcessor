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
 * Applies 3x3 Median Filter mask to an image using the Convolution process.
 * Used for Noise Reduction in Images.
 * 
 * @author Hai Tran
 *
 */
public class MedianFilter implements PlugInFilter {

    /**
     * Process the image using Median Filter for Noise Reduction.
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
    			//Apply median filter
    			int resultValue = (int) calculateMedian(matrix);

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
    
    /**
     * Returns the median value of a matrix.
     * @param matrix
     * @return median value
     */
    public double calculateMedian(double[][] matrix) {
    	ArrayList<Double> matrixValues = getMatrixValuesList(matrix);
    	Collections.sort(matrixValues);
    	
    	int n = matrixValues.size();
    	double median;
    	
    	if (n % 2 == 0) {
    		median = (matrixValues.get(n/2)) + (matrixValues.get(n/2 -1)) / 2;
    	} else {
    		median = matrixValues.get(n/2);
    	}
    	return median;
    }
    
    /**
     * Returns a array list containing the values in a matrix.
     * @param matrix containing doubles
     * @return ArrayList<Double>
     */
    public ArrayList<Double> getMatrixValuesList(double[][] matrix) {
    	ArrayList<Double> matrixValues = new ArrayList<Double>();
    	
    	for (int i = 0; i < matrix.length; i++) {
    		for (int j = 0; j < matrix[0].length; j++) {
    			matrixValues.add(matrix[i][j]);
    		}
    	}
    	
    	return matrixValues;
    }

	@Override
	public int setup(String arg0, ImagePlus arg1) {
		// TODO Auto-generated method stub
		return DOES_8G;
	}

}