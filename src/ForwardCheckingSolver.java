/**
 * Created by r.makowiecki on 06/05/2017.
 */
public class ForwardCheckingSolver implements ISolver {
    private final int boardSize;
    private int[] placedQueens;
    private ISolutionPrinter solutionPrinter;
    private int[][] fieldsThreatArray; //a cell value indicates by how many queens a particular field is threatened
    private RunStatistics statistics;

    ForwardCheckingSolver(int boardSize, boolean shouldPrintSolutions) {
        this.boardSize = boardSize;
        placedQueens = new int[boardSize];
        fieldsThreatArray = new int[boardSize][boardSize];
        statistics = new RunStatistics();
        solutionPrinter = shouldPrintSolutions ?
                new SolutionPrinter() :
                placedQueens -> {
                    //no-op
                };
    }

    @Override
    public RunStatistics solveNQueens() {
        long startTime = System.currentTimeMillis();
        solveNQWithForwardCheckingArray(0);
        statistics.totalTime = System.currentTimeMillis() - startTime;
        System.out.println("Forward checking algorithm: " + statistics.toString());
        return statistics;
    }

    private boolean solveNQWithForwardCheckingArray(int col) {
        if (col == boardSize) {
            statistics.solutionsCount++;
            solutionPrinter.printSolution(placedQueens);
            return true;
        }

        statistics.nodesEnteredCount++;

        for (int i = 0; i < boardSize; i++) {
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
    private void addThreatenedPlacesInColumns(int lastInsertedRow, int lastInsertedColumn) {
        recalculateThreatenedFields(1, lastInsertedRow, lastInsertedColumn);
    }

    private void removeThreatenedPlacesInColumns(int lastInsertedRow, int lastInsertedColumn) {
        recalculateThreatenedFields(-1, lastInsertedRow, lastInsertedColumn);
    }

    private void recalculateThreatenedFields(int valueModificator, int lastInsertedRow, int lastInsertedColumn) {
        boolean possiblyHasUpDiagonalToMark = true;
        boolean possiblyHasDownDiagonalToMark = true;

        for (int j = 1; j < boardSize - lastInsertedColumn; j++) {
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

    private boolean isSolutionPossible(int lastInsertedColumn) {
        // states whether there is at least one non-threatening queen to be placed in each column
        for (int i = lastInsertedColumn + 1; i < boardSize; i++) {
            boolean isAbleToPlaceInCurrentColumn = false;
            for (int row = 0; row < boardSize && !isAbleToPlaceInCurrentColumn; row++) {
                if (fieldsThreatArray[row][i] == 0) { // if no queens threat that field
                    isAbleToPlaceInCurrentColumn = true;
                }
            }
            if (!isAbleToPlaceInCurrentColumn) return false;
        }
        return true;
    }
}
