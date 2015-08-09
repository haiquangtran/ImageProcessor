package utils;

import ij.process.ImageProcessor;

public class ImageHelper {
	//Hard coded for assignment
	public static final String FOLDER = "src/assets/";
	public static final String IMAGE_1 = "q1/test-pattern.tif";
	public static final String IMAGE_2 = "q1/blurry-moon.tif";
	public static final String IMAGE_3 = "q1/ckt-board-saltpep.tif";
	public static final String IMAGE_4 = "q2/hubble.tif";


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
	 * Returns a matrixSize x matrixSize of surrounding pixel values of an image, given the row and col.
	 * For example, if matrixSize of 3 is specified it will give a 3x3 matrix of the surrounding pixel values of an image
	 * given the row and col.
	 *
	 * @param image the image being processed
	 * @param matrixSize the size of matrix for the mask
	 * @param row current x pixel of image
	 * @param col current y pixel of image
	 * @return
	 */
	public static double[][] getMatrix(ImageProcessor image, int matrixSize, int row, int col) {
		double[][] matrix = new double[matrixSize][matrixSize];

		int startIndex = (int) Math.floor(matrixSize/2);

		//go through matrix to fill in matrix with image pixels
		for (int i = 0; i < matrixSize; i++) {
			for (int j = 0; j < matrixSize; j++) {
				//fill in matrix values from image
				int pixelRow = i + row - startIndex;
				int pixelCol = j + col - startIndex;

				if (row >= startIndex && row < image.getWidth()-startIndex
						&& col >= startIndex && col < image.getHeight()-startIndex) {
					matrix[i][j] = image.getPixel(pixelRow, pixelCol);
				}
			}
		}

		return matrix;
	}


	/**
	 * Returns the mean value of all the pixels in an image.
	 *
	 * @param imageProcessor
	 * @return
	 */
	public static double getMeanValue(ImageProcessor imageProcessor) {
		int width = imageProcessor.getWidth();
		int height = imageProcessor.getHeight();
		double meanValue = 0;

		ImageProcessor image = imageProcessor.duplicate();

		for (int row = 0; row < width; row++) {
			for (int col = 0; col < height; col++) {
				meanValue += image.getPixel(row, col);
			}
		}

		meanValue = meanValue/(width*height);

		return meanValue;
	}

}

