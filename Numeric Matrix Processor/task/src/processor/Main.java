package processor;

import java.util.Scanner;

public class Main {
    static Scanner scanner;
    static  boolean isShowMenu  = true;
    public static void main(String[] args) {

        while (isShowMenu) {
            showMenu();
            scanner = new Scanner(System.in);
            System.out.print("You choice: ");
            int choice = scanner.nextInt(); //TODO deal with other types
            switch (choice) {
                case 1:
                    addMatrices(); // 1
                    break;
                case 2:
                    multiplyMatrixByConst(); //2
                    break;
                case 3:
                    multiplyMatrices(); //3
                    break;
                case 4:
                    transposeMatrix();
                    break;
                case 5:
                    calculateMatrices(); //Calculate a determinant
                    break;
                case 6:
                    inverseMatrix();
                    break;
                case 0:
                    isShowMenu = false;
            }
        }
    }

    private static void inverseMatrix() {
        System.out.print("Enter matrix size: > ");
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        System.out.println("Enter matrix:");
        double[][] matrix = new double[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = scanner.nextDouble();
            }
        }
        double det = calcDeterminant(matrix);
        if ( det == 0.0) {
            System.out.println("This matrix doesn't have an inverse.");
        }  else {
            double[][] adjoint = adjointMatrix(matrix);
            multipyMatrixWithConstant(adjoint, 1.0 / det);
            printMatrix(adjoint);
        }
    }

    private static void multipyMatrixWithConstant(double[][] matrix, double constant) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = matrix[i][j] * constant;
            }
        }
    }

    private static double[][] adjointMatrix(double[][] matrix) {
        double[][] adjoint = new double[matrix.length][matrix[0].length];
        for (int j = 0; j < matrix.length; j++) {
            for (int i = 0; i < matrix[0].length; i++) {
                adjoint[j][i] = cofactor(matrix, i, j);
            }
        }
        return mainTransposeMatrix(adjoint);
    }

    public static double cofactor(double[][] matrix, int i, int j) {
        int minorSize = matrix.length - 1;
        double[][] minor = new double[minorSize][minorSize];
        int skipRow;
        int skipColumn;
        for (int jMinor = 0; jMinor < minorSize; jMinor++) {
            if (jMinor < j) {
                skipRow = 0;
            } else {
                skipRow = 1;
            }
            for (int iMinor = 0; iMinor < minorSize; iMinor++) {
                if (iMinor < i) {
                    skipColumn = 0;
                } else {
                    skipColumn = 1;
                }
                minor[jMinor][iMinor] = matrix[jMinor + skipRow][iMinor + skipColumn];
            }
        }
        return Math.pow(-1, 2 + j + i) * calcDeterminant(minor);
    }


    private static void calculateMatrices() {
        System.out.print("Enter matrix size: > ");
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        System.out.println("Enter matrix:");
        double[][] matrix = new double[n][m];
        double result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = scanner.nextDouble();
            }
        }
        result = calcDeterminant(matrix);

        System.out.println("The result is:");
        System.out.println(result);


    }

    private static Double calcDeterminant(double[][] matrix) {
        double temporary[][];
        double result = 0;

        if (matrix.length == 1) {
            result = matrix[0][0];
            return (result);
        }

        if (matrix.length == 2) {
            result = ((matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]));
            return (result);
        }

//        for (int i = 0; i < matrix[0].length; i++) {
//            temporary = new double[matrix.length - 1][matrix[0].length - 1];
//
//            for (int j = 1; j < matrix.length; j++) {
//                for (int k = 0; k < matrix[0].length; k++) {
//                    if (k < i) {
//                        temporary[j - 1][k] = matrix[j][k];
//                    } else if (k > i) {
//                        temporary[j - 1][k - 1] = matrix[j][k];
//                    }
//                }
//            }
//
//            result += matrix[0][i] * Math.pow (-1,  i) * calcDeterminant (temporary);
//        }
        int jMatrix = 0;
        int size = matrix.length;
        for (int iMatrix = 0; iMatrix < size; iMatrix++) {
            result += matrix[jMatrix][iMatrix] * cofactor(matrix, iMatrix, jMatrix);
        }
        return (result);

    }

    private static void transposeMatrix() {
        System.out.println("1. Main diagonal\n" +
                "2. Side diagonal\n" +
                "3. Vertical line\n" +
                "4. Horizontal line");
        System.out.print("Your choice: ");
        int choice = scanner.nextInt();
        System.out.println("Enter matrix size: ");
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        double[][] matrix = new double[n][m];
        System.out.println("Enter matrix:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix[i][j] = scanner.nextDouble();
            }
        }
        double[][] transposedMatrix = new double[matrix.length][matrix[0].length];
        switch (choice) {
            case 1:
                transposedMatrix = mainTransposeMatrix(matrix);
                break;
            case 2:
                transposedMatrix = sideTransposeMatrix(matrix);
                break;
            case 3:
                transposedMatrix = verticalTransposeMatrix(matrix);
                break;
            case 4:
                transposedMatrix = horizontalTransposeMatrix(matrix);
                break;
        }
        printMatrix(transposedMatrix);
    }

    private static double[][] horizontalTransposeMatrix(double[][] matrix) {
        double[][] transposedMatrix = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                transposedMatrix[i] = matrix[matrix.length - 1 - i];;
            }
        }
        return transposedMatrix;
    }

    private static double[][] verticalTransposeMatrix(double[][] matrix) {
        double[][] transposedMatrix = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                transposedMatrix[i][j] = matrix[i][(matrix[i].length - 1) - j];
            }
        }
        return transposedMatrix;
    }

    private static double[][] sideTransposeMatrix(double[][] matrix) {
        double[][] transposedMatrix = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                transposedMatrix[i][j] = matrix[matrix[i].length - 1 - j][matrix.length - 1 - i];
            }
        }
        return transposedMatrix;
    }

    private static double[][] mainTransposeMatrix(double[][] matrix) {
        double[][] transposedMatrix = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                transposedMatrix[i][j] = matrix[j][i];
            }
        }
        return transposedMatrix;
    }

    private static void multiplyMatrices() {
        System.out.print("Enter size of first matrix: ");
        int firstRow = scanner.nextInt();
        int firstColumn = scanner.nextInt();
        System.out.println("Enter first matrix:");
        double[][] firstMatrix = new double[firstRow][firstColumn];
        for (int i = 0; i < firstRow; i++) {
            for (int j = 0; j < firstColumn; j++) {
                firstMatrix[i][j] = scanner.nextDouble();
            }
        }
        System.out.print("Enter size of second matrix: ");
        int secondRow = scanner.nextInt();
        int secondColumn = scanner.nextInt();
        System.out.println("Enter second matrix:");
        double[][] secondMatrix = new double[secondRow][secondColumn];
        for (int i = 0; i < secondRow; i++) {
            for (int j = 0; j < secondColumn; j++) {
                secondMatrix[i][j] = scanner.nextDouble();
            }
        }
        double[][] multiplyMatrix = new double[firstRow][secondColumn];

        for (int row = 0; row < multiplyMatrix.length; row++) {
            for (int col = 0; col < multiplyMatrix[row].length; col++) {
                multiplyMatrix[row][col] = multiplyMatricesCell(firstMatrix, secondMatrix, row, col);
            }
        }

        System.out.println("The result is:");
        printMatrix(multiplyMatrix);

    }

    private static double multiplyMatricesCell(double[][] firstMatrix, double[][] secondMatrix,
                                            int row, int col) {
        double cell = 0;
        for (int i = 0; i < secondMatrix.length; i++) {
            cell += firstMatrix[row][i] * secondMatrix[i][col];
        }
        return cell;
    }

    private static void multiplyMatrixByConst() {
        System.out.print("Enter size of matrix: ");
        int row = scanner.nextInt();
        int column = scanner.nextInt();
        System.out.println("Enter matrix:");
        double[][] matrix = new double[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                matrix[i][j] = scanner.nextDouble();
            }
        }
        System.out.print("Enter constant: ");
        double constant = scanner.nextDouble();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                matrix[i][j] = matrix[i][j] * constant;
            }
        }
        printMatrix(matrix);
    }

    private static void addMatrices() {
        System.out.print("Enter size of first matrix: ");
        int row1 = scanner.nextInt();
        int column1 = scanner.nextInt();
        System.out.println("Enter first matrix:");
        double[][] matrix1 = new double[row1][column1];
        for (int i = 0; i < row1; i++) {
            for (int j = 0; j < column1; j++) {
                matrix1[i][j] = scanner.nextDouble();
            }
        }

        //second matrix
        System.out.print("Enter size of second matrix: ");
        int row2 = scanner.nextInt();
        int column2 = scanner.nextInt();
        System.out.println("Enter second matrix:");
        double[][] matrix2 = new double[row2][column2];
        for (int i = 0; i < row2; i++) {
            for (int j = 0; j < column2; j++) {
                matrix2[i][j] = scanner.nextDouble();
            }
        }
        if ((row1 != row2) || (column1 != column2)) {
            System.out.println("The operation cannot be performed.");
            System.exit(0);
        }

        double[][] sumMatrix = new double[row1][column1];
        for (int i = 0; i < row1; i++) {
            for (int j = 0; j < column1; j++) {
                sumMatrix[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }

        System.out.println("The result is:");
        for (int i = 0; i < row1; i++) {
           for (int j = 0; j < column1; j++) {
               System.out.print(sumMatrix[i][j]);
               System.out.print(" ");
           }
           System.out.println();
       }
    }

    private static void showMenu() {
        System.out.println("1. Add matrices\n" +
                "2. Multiply matrix by a constant\n" +
                "3. Multiply matrices\n" +
                "4. Transpose matrix\n" +
                "5. Calculate a determinant\n" +
                "6. Inverse matrix\n" +
                "0. Exit");
    }

    private static void printMatrix(double[][] matrix) {
        System.out.println("The result is: ");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }



}
