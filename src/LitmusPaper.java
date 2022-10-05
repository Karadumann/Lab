import java.util.Objects;

public class LitmusPaper extends Item implements  Supplier,Material {

    private int length;

    public LitmusPaper(int length, double unitPrice, int availableQuantity) {
        super(unitPrice, availableQuantity);
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LitmusPaper that = (LitmusPaper) o;
        return length == that.length;
    }

    @Override
    public int hashCode() {
        return Objects.hash(length);
    }

    @Override
    public String toString() {
        return " LitmusPaper= " +
                "length=" + length +
                ", unitPrice=" + unitPrice +
                ", availableQuantity=" + availableQuantity +
                ""+"\n";
    }
// -	Interface’s method returns true if the length of available litmus paper is less than 200 cm.
    @Override
    public boolean isSupplyNeeded()
    {
        return length * availableQuantity < 200;}
}
