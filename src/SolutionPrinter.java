/**
 * Created by r.makowiecki on 06/05/2017.
 */
public class SolutionPrinter implements ISolutionPrinter {
    @Override
    public void printSolution(int[] placedQueens) {
        for (int i = 0; i < placedQueens.length; i++) {
            System.out.print(placedQueens[i] + ",");
        }
        System.out.print("\n");
    }
}
