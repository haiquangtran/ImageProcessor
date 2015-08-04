package utils;

public class ImageHelper {
	
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


}
