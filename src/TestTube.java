import com.sun.media.sound.InvalidDataException;

import java.util.Objects;

public class TestTube extends Item implements Equipment,Supplier {

    private int width;
    private int height;

    public TestTube(int width, int height, double unitPrice, int availableQuantity) throws Exception {
        super(unitPrice, availableQuantity);
        if (width < 0 || width > 10) {
            throw new Exception ("(Invalid Data)-TestTube width: " + width
                    + ",  width (up to 0-10 mm)");
        }
        if (height < 0 || height > 500) {
            throw new Exception ("(Invalid Data)-TestTube height: " + height
                    + ", height (up to 50 cm) ");
        }
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestTube testTube = (TestTube) o;
        return width == testTube.width && height == testTube.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }

    @Override
    public String toString() {
        return " TestTube= " +
                "width=" + width +
                ", height=" + height +
                ", unitPrice=" + unitPrice +
                ", availableQuantity=" + availableQuantity +
                ""+"\n";
    }

    @Override
    public boolean isSupplyNeeded() {
        return availableQuantity < 100;
    }
}
