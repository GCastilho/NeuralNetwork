package NeuralNetwork;

import java.util.Arrays;
import java.util.stream.IntStream;

public class SimpleMatrixLibrary {
	public static double[] multiply(double[][] matrix, double[] vector){
		//TODO: understand how this works OR rewrite as the other multiply function
		return(Arrays.stream(matrix).mapToDouble(row -> IntStream.range(0, row.length).mapToDouble(col -> row[col] * vector[col]).sum()).toArray());
	}

	public static double[][] multiply(double[] array, double[] vector){
		double[][] matrix = new double[array.length][vector.length];
		for(int m = 0; m<array.length; ++m){
			for(int n = 0; n<vector.length; ++n){
				matrix[m][n] = array[m] * vector[n];
			}
		}
		return(matrix);
	}

	public static double[][] transposeMatrix(double[][] m){
		double[][] matrix = new double[m[0].length][m.length];
		for(int i = 0; i < m.length; ++i){
			for(int j = 0; j < m[0].length; ++j){
				matrix[j][i] = m[i][j];
			}
		}
		return(matrix);
	}
}
