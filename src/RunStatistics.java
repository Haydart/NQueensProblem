/**
 * Created by r.makowiecki on 06/05/2017.
 */
class RunStatistics {
    int solutionsCount;
    int nodesEnteredCount;
    long totalTime;

    @Override
    public String toString() {
        return totalTime + " ms, " + solutionsCount + " solutions, " + nodesEnteredCount + " nodes entered.";
    }
}
