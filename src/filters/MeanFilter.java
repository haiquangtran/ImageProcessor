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
 * Applies 3x3 Mean Filter mask to an image using the Convolution process.
 * Used for Noise Reduction in Images.
 *
 * @author Hai Tran
 *
 */
public class MeanFilter implements PlugInFilter {
	private int maskSize = 3; //default mask size
	private double[][] meanFilter = createMeanFilter(maskSize);

    /**
     * Process the image using Mean Filter for Noise Reduction.
     */
    public void run(ImageProcessor imageProcessor) {
    	int width = imageProcessor.getWidth();
    	int height = imageProcessor.getHeight();

    	ImageProcessor image = imageProcessor.duplicate();

    	for (int row = 0; row < width; row++) {
    		for (int col = 0; col < height; col++) {
    			//Values the mask will overlay
    			double[][] matrix = ImageHelper.getMatrix(image, maskSize, row, col);
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

    /**
     * Sets the mean filter matrix with a specified size.
     * Sets the maskSize to the specified size.
     *
     * @param size
     */
    public void setMeanFilterSize(int size) {
    	this.maskSize = size;
    	this.meanFilter = createMeanFilter(size);
    }

    /**
     * Creates a mean filter matrix given the mask size.
     * For example, if a maskSize of 3 is given, then a 3x3 mean filter matrix will be created.
     *
     * @param maskSize matrix size
     * @return
     */
    public double[][] createMeanFilter(int maskSize) {
    	double[][] matrix = new double[maskSize][maskSize];

    	for (int i = 0; i < maskSize; i++) {
    		for (int j = 0; j < maskSize; j++) {
    			matrix[i][j] = (1.0 / (maskSize * maskSize));
    		}
    	}
    	return matrix;
    }

	@Override
	public int setup(String arg0, ImagePlus arg1) {
		// TODO Auto-generated method stub
		return DOES_8G;
	}

}