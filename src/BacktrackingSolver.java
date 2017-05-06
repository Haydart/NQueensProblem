/**
 * Created by r.makowiecki on 06/05/2017.
 */
public class BacktrackingSolver implements Solver {
    private final int boardSize;
    private final ISolutionPrinter solutionPrinter;
    private int[] placedQueens;
    private int[][] fieldsThreatArray; //a cell value indicates by how many queens a particular field is threatened
    private int nodeEntranceCount = 0;
    private int solutionsCount = 0;

    public BacktrackingSolver(int boardSize, boolean shouldPrintSolutions) {
        this.boardSize = boardSize;
        placedQueens = new int[boardSize];
        fieldsThreatArray = new int[boardSize][boardSize];
        solutionPrinter = shouldPrintSolutions ?
                new SolutionPrinter() :
                placedQueens -> {
                    //no-op
                };
    }

    @Override
    public void solveNQueens() {
        long startTime = System.currentTimeMillis();
        solveNQBacktrackingArray(0);
        System.out.println("Backtracking algorithm took " + (System.currentTimeMillis() - startTime) + " ms, there were " + nodeEntranceCount + " node entrances and " + solutionsCount + " solutions.");
    }

    private boolean solveNQBacktrackingArray(int col) {
        if (col == boardSize) {
            solutionsCount++;
            solutionPrinter.printSolution(placedQueens);
            return true;
        }

        nodeEntranceCount++;

        for (int i = 0; i < boardSize; i++) {
            placedQueens[col] = i;
            if (isBoardValid(col)) {
                solveNQBacktrackingArray(col + 1);
            }
        }
        return false;
    }

    private boolean isBoardValid(int lastInsertedColumn) {
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
