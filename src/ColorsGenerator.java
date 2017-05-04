import java.util.Random;

/**
 * Created by r.makowiecki on 07/04/2017.
 */
public class ColorsGenerator {
    private Random random;

    public ColorsGenerator() {
        this.random = new Random();
    }

    public Color[] generate(int boardSize) {
        Color[] colors = new Color[boardSize];

        for (int i = 0; i < (boardSize % 2 == 0 ? boardSize * 2 : boardSize * 2 + 1); i++) {
            colors[i] = new Color(
                    (byte) random.nextInt(256),
                    (byte) random.nextInt(256),
                    (byte) random.nextInt(256));
        }
        return colors;
    }
}
