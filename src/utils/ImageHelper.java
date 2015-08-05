package utils;

import ij.process.ImageProcessor;

public class ImageHelper {
	//Hard coded for assignment
	public static final String FOLDER = "src/assets/";
	public static final String IMAGE_1 = "test-pattern.tif";
	public static final String IMAGE_2 = "blurry-moon.tif";
	public static final String IMAGE_3 = "ckt-board-saltpep.tif";
	public static final String IMAGE_4 = "hubble.tif";


	  /**
     * Calculates inner product of two matrices that have the same length
     * @param matrix1
     * @param matrix2
     * @return
     */
    public static int innerProduct(double[][] matrix1, double[][] matrix2) {
    	int result = 0;

    	for (int i = 0; i < matrix1.length; i++) {
    		for (int j = 0; j < matrix1.length; j++) {
    			result += (matrix1[i][j] * matrix2[i][j]);
    		}
    	}

    	return result;
    }


    /**
     * Returns 3x3 matrix of surrounding pixel values of an image given the row, and col.
     * @param image
     * @param row
     * @param col
     * @return
     */
    public static double[][] getMatrix3x3(ImageProcessor image, int row, int col) {
		double[][] matrix =
			{
				{image.getPixel(row-1, col-1), image.getPixel(row, col-1), image.getPixel(row+1, col-1)},
				{image.getPixel(row-1, col), image.getPixel(row, col), image.getPixel(row+1, col)},
				{image.getPixel(row-1, col+1), image.getPixel(row, col+1), image.getPixel(row+1, col+1)}
			};
		return matrix;
    }

    /**
     * Returns 5x5 matrix of surrounding pixel values of an image given the row, and col.
     * @param image
     * @param row
     * @param col
     * @return
     */
    public static double[][] getMatrix5x5(ImageProcessor image, int row, int col) {
		double[][] matrix =
			{
				{image.getPixel(row-1, col-1), image.getPixel(row, col-1), image.getPixel(row+1, col-1)},
				{image.getPixel(row-1, col-1), image.getPixel(row, col-1), image.getPixel(row+1, col-1)},
				{image.getPixel(row-1, col), image.getPixel(row, col), image.getPixel(row+1, col)},
				{image.getPixel(row-1, col+1), image.getPixel(row, col+1), image.getPixel(row+1, col+1)}
			};
		return matrix;
    }
}
