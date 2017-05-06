/**
 * Created by r.makowiecki on 07/04/2017.
 */
public class Runner {
    private static final int N = 14;
    private static int[] placedQueens = new int[N];
    private static int[][] fieldsThreatArray = new int[N][N]; //a cell value indicates by how many queens a particular field is threatened
    private static SolvingMethod solvingMethod = SolvingMethod.FORWARD_CHECKING;
    private static boolean shouldPrintSolutions = false;
    private static int nodeEntranceCount = 0;
    private static int solutionsCount = 0;

    private enum SolvingMethod {
        BACKTRACKING,
        FORWARD_CHECKING
    }

    public static void main(String[] args) {
        solveNQ();
    }

    static void solveNQ() {
        long startTime = System.currentTimeMillis();
        if (solvingMethod == SolvingMethod.BACKTRACKING) {
            if (solveNQBacktrackingArray(0)) {
                System.out.print("Solution does not exist");
            }
        } else {
            if (solveNQWithForwardCheckingArray(0)) {
                System.out.print("Solution does not exist");
            }
        }
        System.out.print("Task resolution took " + (System.currentTimeMillis() - startTime) + " ms, there were " + nodeEntranceCount + " node entrances and " + solutionsCount + " solutions.");
    }

    private static boolean solveNQWithForwardCheckingArray(int col) {
        if (col == N) {
            solutionsCount++;
            if (shouldPrintSolutions)
                printSolution();
            return true;
        }

        nodeEntranceCount++;

        for (int i = 0; i < N; i++) {
            if (fieldsThreatArray[i][col] == 0) {
                placedQueens[col] = i;
                addThreatenedPlacesInColumns(i, col);
                if (isSolutionPossible(col)) {
                    solveNQWithForwardCheckingArray(col + 1);
                }
                removeThreatenedPlacesInColumns(i, col);
            }

        }
        return false;
    }
    private static void addThreatenedPlacesInColumns(int lastInsertedRow, int lastInsertedColumn) {
        recalculateThreatenedFields(1, lastInsertedRow, lastInsertedColumn);
    }

    private static void removeThreatenedPlacesInColumns(int lastInsertedRow, int lastInsertedColumn) {
        recalculateThreatenedFields(-1, lastInsertedRow, lastInsertedColumn);
    }

    private static void recalculateThreatenedFields(int valueModificator, int lastInsertedRow, int lastInsertedColumn) {
        boolean possiblyHasUpDiagonalToMark = true;
        boolean possiblyHasDownDiagonalToMark = true;

        for (int j = 1; j < N - lastInsertedColumn; j++) {
            fieldsThreatArray[lastInsertedRow][lastInsertedColumn + j] += valueModificator; //modify all places horizontally

            if (possiblyHasDownDiagonalToMark) {
                try {
                    fieldsThreatArray[lastInsertedRow + j][lastInsertedColumn + j] += valueModificator; //try to modify places diagonally down
                } catch (ArrayIndexOutOfBoundsException ignored) {
                    possiblyHasDownDiagonalToMark = false;
                    //bad design, but it`s optimal
                }
            }

            if (possiblyHasUpDiagonalToMark) {
                try {
                    fieldsThreatArray[lastInsertedRow - j][lastInsertedColumn + j] += valueModificator; //try to modify places diagonally up
                } catch (ArrayIndexOutOfBoundsException ignored) {
                    possiblyHasUpDiagonalToMark = false;
                    //bad design, but it`s optimal
                }
            }
        }
    }

    private static boolean isSolutionPossible(int lastInsertedColumn) {
        // states whether there is at least one non-threatening queen to be placed in each column
        for (int i = lastInsertedColumn + 1; i < N; i++) {
            boolean isAbleToPlaceInCurrentColumn = false;
            for (int row = 0; row < N && !isAbleToPlaceInCurrentColumn; row++) {
                if (fieldsThreatArray[row][i] == 0) { // if no queens threat that field
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
                printSolution();
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

    static void printSolution() {
        for (int i = 0; i < N; i++) {
            System.out.print(placedQueens[i] + ",");
        }
        System.out.print("\n");
    }

    static boolean isBoardValid(int lastInsertedColumn) {
        //check if last inserted queens is in the way of any other queen
        for (int i = 0; i < lastInsertedColumn; i++) {
            if (placedQueens[i] == placedQueens[lastInsertedColumn])
                return false;
            if (Math.abs(placedQueens[i] - placedQueens[lastInsertedColumn]) == Math.abs(lastInsertedColumn - i))
                return false;
        }
        return true;
    }
}
