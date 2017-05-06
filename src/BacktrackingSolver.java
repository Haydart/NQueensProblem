/**
 * Created by r.makowiecki on 06/05/2017.
 */
public class BacktrackingSolver implements ISolver {
    private final int boardSize;
    private final ISolutionPrinter solutionPrinter;
    private int[] placedQueens;
    private int[][] fieldsThreatArray; //a cell value indicates by how many queens a particular field is threatened
    private RunStatistics statistics;

    BacktrackingSolver(int boardSize, boolean shouldPrintSolutions) {
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
        solveNQBacktrackingArray(0);
        statistics.totalTime = System.currentTimeMillis() - startTime;
        System.out.println("Backtracking algorithm: " + statistics.toString());
        return statistics;
    }

    private boolean solveNQBacktrackingArray(int col) {
        if (col == boardSize) {
            statistics.solutionsCount++;
            solutionPrinter.printSolution(placedQueens);
            return true;
        }

        statistics.nodesEnteredCount++;

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
