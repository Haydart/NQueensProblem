/**
 * Created by r.makowiecki on 07/04/2017.
 */
public final class Color {
    private final byte red;
    private final byte green;
    private final byte blue;

    public Color(byte red, byte green, byte blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public byte getRed() {
        return red;
    }

    public byte getGreen() {
        return green;
    }

    public byte getBlue() {
        return blue;
    }

    @Override
    public String toString() {
        return "R: " + (red & 0xFF) + ", G: " + green + ", B: " + blue;
    }
}
