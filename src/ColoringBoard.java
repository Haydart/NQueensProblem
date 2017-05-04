/**
 * Created by r.makowiecki on 07/04/2017.
 */
public class ColoringBoard {
    private int size;
    private int[][] cells;
    private Color[] graphColors;

    public ColoringBoard(int size) {
        this.size = size;
        this.cells = new int[size][size];
        this.graphColors = new ColorsGenerator().generate(size);
    }

    public void drawRandomBoard() {

    }
}
