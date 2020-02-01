package service;

import utils.Matrix;

import java.util.Arrays;

public class MatrixService {

    // It does't matter the values of matrix , but it must contains some values ,
    //that's why I populate it with 1 :)
    public int[][] populateMatrix(Matrix matrix) {
        int matrixReturn[][] = new int[matrix.getRows()][matrix.getColumns()];
        for (int i = 0; i < matrix.getRows(); i++)
            for (int j = 0; j < matrix.getColumns(); j++) {
                matrixReturn[i][j] = 1;
            }
        return matrixReturn;
    }

    // method used for verify if entity has been explore all the locations
    public boolean areEquals(final int[][] arr1, final int[][] arr2) {
        if (arr1 == null) {
            return (arr2 == null);
        }

        if (arr2 == null) {
            return false;
        }

        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (!Arrays.equals(arr1[i], arr2[i])) {
                return false;
            }
        }
        return true;
    }

    public void printMatrix(Matrix matrix) {
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                System.out.print(matrix.getMatrix()[i][j] + " ");
            }
            System.out.println();
        }
    }
}
