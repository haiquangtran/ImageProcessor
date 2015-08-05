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


public class ThresholdFilter implements PlugInFilter {
	private int threshold = 50;	//default value

    public void run(ImageProcessor imageProcessor) {
    	int width = imageProcessor.getWidth();
    	int height = imageProcessor.getHeight();

    	ImageProcessor image = imageProcessor.duplicate();

    	for (int row = 1; row < width-1; row++) {
    		for (int col = 1; col < height-1; col++) {
    			int resultValue = image.getPixel(row, col);

    			//threshold filter
                if (resultValue < threshold || resultValue < 0) {
                	resultValue = 0;
                } else if (resultValue >= threshold || resultValue > 255) {
                	resultValue = 255;
                }

    			imageProcessor.putPixelValue(row, col, resultValue);
    		}
    	}

    }

    /**
     * Sets the threshold field
     * @param threshold
     */
    public void setThreshold(int threshold) {
    	this.threshold = threshold;
    }

	@Override
	public int setup(String arg0, ImagePlus arg1) {
		// TODO Auto-generated method stub
		return DOES_8G;
	}

}