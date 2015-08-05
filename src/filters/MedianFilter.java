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
 * Applies Median Filter to an image.
 * Median filters are nonlinear filters, where the result can not be predicted by a weighted sum of the neighbourhood pixels.
 * Used for Noise Reduction in Images.
 * @author Hai Tran
 *
 */
public class MedianFilter implements PlugInFilter {
	private int medianFilterSize = 3; //default 
	
    /**
     * Process the image using Median Filter for Noise Reduction.
     */
    public void run(ImageProcessor imageProcessor) {
    	int width = imageProcessor.getWidth();
    	int height = imageProcessor.getHeight();

    	ImageProcessor image = imageProcessor.duplicate();

    	for (int row = 0; row < width; row++) {
    		for (int col = 0; col < height; col++) {
    			//Values the mask will overlay (3x3)
    			double[][] matrix = ImageHelper.getMatrix(image, medianFilterSize, row, col);
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
     * Sets the median filter size which is used to determines
     * the area of pixels to be considered by the filter. 
     * For example, a size of 3 would indicate an area of 3x3 matrix.
     *
     * @param size
     */
    public void setMedianFilterSize(int size) {
    	this.medianFilterSize = size;
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
    private ArrayList<Double> getMatrixValuesList(double[][] matrix) {
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