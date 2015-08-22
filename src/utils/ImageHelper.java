package utils;

import ij.process.ImageProcessor;

public class ImageHelper {
	//Hard coded for assignment
	public static final String FOLDER = "src/assets/";
	public static final String IMAGE_1 = "q1/test-pattern.tif";
	public static final String IMAGE_2 = "q1/blurry-moon.tif";
	public static final String IMAGE_3 = "q1/ckt-board-saltpep.tif";
	public static final String IMAGE_4 = "q2/hubble.tif";
	public static final String TRAINING_FOLDER = "src/assets/q2/training/";
	public static final String TEST_FOLDER = "src/assets/q2/test/";
	public static final String FACES_FOLDER = "face/";
	public static final String NON_FACES_FOLDER = "non-face/";
	public static final String TRAINING_CSV_Q2 = FOLDER + "q2/" + "training-set.csv";
	public static final String TEST_CSV_Q2 = FOLDER + "q2/" + "test-set.csv";
	public static final String PATTERN_FOLDER = "src/assets/q3/";
	public static final String TRAINING_CSV_Q3 = PATTERN_FOLDER + "training-set.csv";
	public static final String TEST_CSV_Q3 = PATTERN_FOLDER + "test-set.csv";
	public static final String PATTERN_FILE_Q3 = PATTERN_FOLDER + "pattern-file.csv";

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
	 * Calculates the mean value of all the pixels in an image.
	 *
	 * @param imageProcessor
	 * @return
	 */
	public static double calculateMeanImage(ImageProcessor imageProcessor) {
		int width = imageProcessor.getWidth();
		int height = imageProcessor.getHeight();
		double meanValue = 0;

		ImageProcessor image = imageProcessor.duplicate();

		for (int row = 0; row < width; row++) {
			for (int col = 0; col < height; col++) {
				meanValue += image.getPixel(row, col);
			}
		}

		return meanValue/(width*height);
	}

	/**
	 * Calculates the mean value of the given n x n matrix.
	 *
	 * @param matrix
	 * @return
	 */
	public static double calculateMeanMatrix(double[][] matrix) {
		double meanValue = 0;

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				meanValue += matrix[i][j];
			}
		}

		return meanValue/(matrix.length * matrix[0].length);
	}

	/**
	 * Calculates the standard deviation of an area given the starting location
	 * and the size of an nxn area.
	 *
	 * @param imageProcessor
	 * @param startX
	 * @param startY
	 * @param size
	 * @return
	 */
	public static double calculateStd(ImageProcessor imageProcessor, int startX, int startY, int size) {
		ImageProcessor image = imageProcessor.duplicate();
		double mean = calculateMeanOfSquare(imageProcessor, startX, startY, size);
		double val = 0;

		for (int row = startX; row < size; row++) {
			for (int col = startY; col < size; col++) {
				val += Math.pow((image.getPixel(row, col) - mean), 2);
			}
		}

		return Math.sqrt(val/(size * size));
	}

	/**
	 * Calculates the mean of a square n x n matrix at the centre of an image.
	 * @param imageProcessor
	 * @param areaSize
	 * @return
	 */
	public static double calculateMeanOfCenterArea(ImageProcessor imageProcessor, int areaSize) {
		double mean = 0;
		int centreRow = (imageProcessor.getWidth()/2);
		int centreCol = (imageProcessor.getHeight()/2);
		int startRow = centreRow - (areaSize/2);
		int startCol = centreCol - (areaSize/2);

		ImageProcessor image = imageProcessor.duplicate();

		// area size relative to center of image
		for (int row = startRow; row < startRow + areaSize; row++) {
			for (int col = startCol; col < startRow + areaSize; col++) {
				mean += image.getPixel(row, col);
			}
		}

		return mean/(areaSize*areaSize);
	}

	/**
	 * Calculates the mean of a square n x n matrix.
	 *
	 * @param imageProcessor
	 * @param startRow
	 * @param startCol
	 * @param areaSize
	 * @return
	 */
	public static double calculateMeanOfSquare(ImageProcessor imageProcessor, int startRow, int startCol, int areaSize) {
		double mean = 0;
		ImageProcessor image = imageProcessor.duplicate();

		for (int row = startRow; row < (startRow + areaSize); row++) {
			for (int col = startCol; col < (startCol + areaSize); col++) {
				mean += image.getPixel(row, col);
			}
		}

		return mean/(areaSize*areaSize);
	}

	/**
	 * Calculates the mean of a n x m rectangle matrix.
	 *
	 * @param imageProcessor
	 * @param startRow
	 * @param startCol
	 * @param width
	 * @param height
	 * @returns
	 */
	public static double calculateMeanOfRect(ImageProcessor imageProcessor, int startRow, int startCol, int width, int height) {
		double mean = 0;
		ImageProcessor image = imageProcessor.duplicate();

		for (int row = startRow; row < (startRow + width); row++) {
			for (int col = startCol; col < (startCol + height); col++) {
				mean += image.getPixel(row, col);
			}
		}

		return mean/(width*height);
	}

}

