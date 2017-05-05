import java.util.Arrays;

/**
 * Created by r.makowiecki on 07/04/2017.
 */
public class Runner {
    private static final int N = 14;
    private static int solutionIndex = 1;

    private static int[][] board = new int[N][N];
    private static int[] placedQueens = new int[N];
    private static int[][] columnValidPlacesArray = new int[N][N];
    private static SolvingMethod solvingMethod = SolvingMethod.FORWARD_CHECKING;

    private static DataStructure dataStructure = DataStructure.ARRAY;
    private static boolean shouldPrintSolutions = false;
    private static int nodeEntranceCount = 0;
    private static int solutionsCount = 0;

    private enum SolvingMethod {
        BACKTRACKING,
        FORWARD_CHECKING
    }

    private enum DataStructure {
        ARRAY,
        TWO_DIMENSIONAL_ARRAY
    }

    public static void main(String[] args) {
        initValidPlacesArray();
        solveNQ();
    }

    private static void initValidPlacesArray() {
        for (int row = 0; row < N; row ++)
            for (int col = 0; col < N; col++)
                columnValidPlacesArray[row][col] = 1;
    }

    static void solveNQ() {
        long startTime = System.currentTimeMillis();
        if (solvingMethod == SolvingMethod.BACKTRACKING) {
            if (dataStructure == DataStructure.ARRAY) {
                if (solveNQBacktrackingArray(0)) {
                    System.out.print("Solution does not exist");
                }
            } else {
                if (solveNQBacktracking2dArray(0)) {
                    System.out.print("Solution does not exist");
                }
            }

        } else {
            if(dataStructure == DataStructure.ARRAY) {
                if (solveNQWithForwardCheckingArray(columnValidPlacesArray,0)) {
                    System.out.print("Solution does not exist");
                }
            } else {
                if (solveNQWithForwardChecking2dArray(columnValidPlacesArray,0)) {
                    System.out.print("Solution does not exist");
                }
            }
        }
        System.out.print("Task resolution took " + (System.currentTimeMillis() - startTime) + " ms, there were " + nodeEntranceCount + " node entrances and " + solutionsCount + " solutions.");
    }

    private static boolean solveNQWithForwardCheckingArray(int[][] safePlacesInColumns, int col) {
        if (col == N) {
            solutionsCount++;
            if (shouldPrintSolutions)
                print2dSolution();
            return true;
        }

        nodeEntranceCount++;

        for (int i = 0; i < N; i++) {
            if (safePlacesInColumns[i][col] == 1) {
                placedQueens[col] = i;
                int[][] safePlaces = calculateNonThreatenedPlacesInColumns(safePlacesInColumns, i, col);
                if (isSolutionPossible(safePlaces, col)) {
                    solveNQWithForwardChecking2dArray(safePlaces, col + 1);
                }
            }

        }
        return false;
    }

    private static boolean isSolutionPossible(int col) {
        // states whether there is at least one non-threatening queen to be placed in each column
        for (int i = col; i < N; i++) {
            boolean isAbleToPlaceInCurrentColumn = false;
            for (int row = 0; row < N && !isAbleToPlaceInCurrentColumn; row++) {
                if (isBoardValid(row, i)) {
                    isAbleToPlaceInCurrentColumn = true;
                }
            }
            if (!isAbleToPlaceInCurrentColumn) return false;
        }
        return true;
    }

    private static boolean solveNQWithForwardChecking2dArray(int[][] safePlacesInColumns, int col) {
        if (col == N) {
            solutionsCount++;
            if (shouldPrintSolutions)
                print2dSolution();
            return true;
        }

        nodeEntranceCount++;

        for (int i = 0; i < N; i++) {
            if (safePlacesInColumns[i][col] == 1) {
                board[i][col] = 1;

                int[][] safePlaces = calculateNonThreatenedPlacesInColumns(safePlacesInColumns, i, col);
                if (isSolutionPossible(safePlaces, col)) {
                    solveNQWithForwardChecking2dArray(safePlaces, col + 1);
                }
                board[i][col] = 0;
            }

        }
        return false;
    }

    private static int[][] calculateNonThreatenedPlacesInColumns(int[][] safePlacesArray, int lastInsertedRow, int lastInsertedColumn) {
        final int[][] arrayDeepCopy = createDeepArrayCopy(safePlacesArray);
        boolean possiblyHasUpDiagonalToWipe = true;
        boolean possiblyHasDownDiagonalToWipe = true;

        //arrayDeepCopy[lastInsertedRow][lastInsertedColumn] = 0;
        //no need to wipe out the place the queen has been placed on, we won`t place anymore queens in this column

        for (int j = 1; j < N - lastInsertedColumn; j++) {
            arrayDeepCopy[lastInsertedRow][lastInsertedColumn + j] = 0; //wipe out all safe places horizontally

            if (possiblyHasDownDiagonalToWipe) {
                try {
                    arrayDeepCopy[lastInsertedRow + j][lastInsertedColumn + j] = 0; //try to wipe out safe places diagonally down
                } catch (ArrayIndexOutOfBoundsException ignored) {
                    possiblyHasDownDiagonalToWipe = false;
                    //bad design, but it`s optimal
                }
            }

            if (possiblyHasUpDiagonalToWipe) {
                try {
                    arrayDeepCopy[lastInsertedRow - j][lastInsertedColumn + j] = 0; //try to wipe out safe places diagonally up
                } catch (ArrayIndexOutOfBoundsException ignored) {
                    possiblyHasUpDiagonalToWipe = false;
                    //bad design, but it`s optimal
                }
            }
        }
        return arrayDeepCopy;
    }

    private static int[][] createDeepArrayCopy(int[][] original) {
        final int[][] result = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }


    private static boolean isSolutionPossible(int[][] safePlaces, int lastInsertedColumn) {
        // states whether there is at least one non-threatening queen to be placed in each column
        for (int i = lastInsertedColumn + 1; i < N; i++) {
            boolean isAbleToPlaceInCurrentColumn = false;
            for (int row = 0; row < N && !isAbleToPlaceInCurrentColumn; row++) {
                if (safePlaces[row][i] == 1) {
                    isAbleToPlaceInCurrentColumn = true;
                }
            }
            if (!isAbleToPlaceInCurrentColumn) return false;
        }
        return true;
    }

    static boolean solveNQBacktrackingArray(int col) {
        if (col == N) {
            solutionsCount++;
            if (shouldPrintSolutions)
                print1dSolution();
            return true;
        }

        nodeEntranceCount++;

        for (int i = 0; i < N; i++) {
            placedQueens[col] = i;
            if (isBoardValid(col)) {
                solveNQBacktrackingArray(col + 1);
            }
        }
        return false;
    }

    private static boolean solveNQBacktracking2dArray(int col) {
        if (col == N) {
            solutionsCount++;
            if (shouldPrintSolutions)
                print2dSolution();
            return true;
        }

        nodeEntranceCount++;

        for (int i = 0; i < N; i++) {
            board[i][col] = 1;
            if (isBoardValid(i, col)) {
                solveNQBacktracking2dArray(col + 1);
            }
            board[i][col] = 0;
        }
        return false;
    }

    static void print1dSolution() {
        for (int i = 0; i < N; i++) {
            System.out.print(placedQueens[i] + ",");
        }
        System.out.print("\n");
    }

    static void print2dSolution() {
        System.out.println(solutionIndex++);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                System.out.print(board[i][j] + " ");
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    static boolean isBoardValid(int lastInsertedColumn) {
        for (int i = 0; i < lastInsertedColumn; i++) {
            if (placedQueens[i] == placedQueens[lastInsertedColumn])
                return false;
            if (Math.abs(placedQueens[i] - placedQueens[lastInsertedColumn]) == Math.abs(lastInsertedColumn - i))
                return false;
        }
        return true;
    }

    private static boolean isBoardValid(int lastInsertedRow, int lastInsertedColumn) {
        int i, j;

    /* Check this row on left side */
        for (i = 0; i < lastInsertedColumn; i++)
            if (board[lastInsertedRow][i] != 0)
                return false;

    /* Check upper diagonal on left side */
        for (i = lastInsertedRow, j = lastInsertedColumn; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] != 0 && i != lastInsertedRow && j != lastInsertedColumn)
                return false;

    /* Check lower diagonal on left side */
        for (i = lastInsertedRow, j = lastInsertedColumn; j >= 0 && i < N; i++, j--)
            if (board[i][j] != 0 && i != lastInsertedRow && j != lastInsertedColumn)
                return false;

        return true;
    }
}
