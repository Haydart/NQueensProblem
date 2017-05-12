/**
 * Created by r.makowiecki on 07/04/2017.
 */
public class Runner {
    private static final int N = 14;

    public static void main(String[] args) {
        new ForwardCheckingSolver(N, false).solveNQueens();
        new BacktrackingSolver(N, false).solveNQueens();
    }
}
