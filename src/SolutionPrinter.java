/**
 * Created by r.makowiecki on 06/05/2017.
 */
public class SolutionPrinter implements ISolutionPrinter {
    @Override
    public void printSolution(int[] placedQueens) {
        for (int placedQueen : placedQueens) {
            System.out.print(placedQueen + ",");
        }
        System.out.print("\n");
    }
}
