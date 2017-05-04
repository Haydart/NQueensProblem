/**
 * Created by r.makowiecki on 07/04/2017.
 */
public class Runner {
    private static final int N = 8;
    private static int solutionIndex = 1;
    private static int[][] board = new int[N][N];
    private static SolvingMethod solvingMethod = SolvingMethod.BACKTRACKING;

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
            if (solveNQWithBacktracking(0)) {
                System.out.print("Solution does not exist");
            }
        } else {
            if (solveNQWithForwardChecking(0)) {
                System.out.print("Solution does not exist");
            }
        }
        System.out.print("Task resolution took " + (System.currentTimeMillis() - startTime) + " ms");
    }

    private static boolean solveNQWithForwardChecking(int col) {
        if (col == N) {
            printSolution();
            return true;
        }

        for (int i = 0; i < N; i++) {
            board[i][col] = 1;
            if (isBoardValid(i, col) && isSolutionPossible(col)) {
                solveNQWithForwardChecking(col + 1);
            }
            board[i][col] = 0;
        }
        return false;
    }

    private static boolean isSolutionPossible(int col) {
        // states whether there is at least one non-threatening queen to be placed in each column
        for (int i = col; i < N; i++) {
            boolean isAbleToPlaceInCurrentColumn = false;
            for (int row = 0; row < N && !isAbleToPlaceInCurrentColumn; row++) {
                if(isBoardValid(row, i)) {
                    isAbleToPlaceInCurrentColumn = true;
                }
            }
            if(!isAbleToPlaceInCurrentColumn) return false;
        }
        return true;
    }

    static boolean solveNQWithBacktracking(int col) {
        if (col == N) {
            printSolution();
            return true;
        }

        for (int i = 0; i < N; i++) {
            board[i][col] = 1;
            if (isBoardValid(i, col)) {
                solveNQWithBacktracking(col + 1);
            }
            board[i][col] = 0;
        }
        return false;
    }

    static void printSolution() {
        System.out.println(solutionIndex++);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                System.out.print(board[i][j] + " ");
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    static boolean isBoardValid(int lastInsertedRow, int lastInsertedColumn) {
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
