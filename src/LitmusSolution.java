import java.util.Objects;

public class LitmusSolution extends Item implements Supplier,Material {

    private int volume;

    public LitmusSolution( int volume, double unitPrice, int availableQuantity) {
        super(unitPrice, availableQuantity);
        this.volume = volume;
    }

    public int getVolume() {
        return volume;
    }

    @Override
    public String toString() {
        return " LitmusSolution= " +
                "volume=" + volume +
                ", unitPrice=" + unitPrice +
                ", availableQuantity=" + availableQuantity +
                ""+"\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LitmusSolution that = (LitmusSolution) o;
        return volume == that.volume;
    }

    @Override
    public int hashCode() {
        return Objects.hash(volume);
    }
// Interface’s method returns true if the volume of available litmus solution is less than 500 ml
    @Override
    public boolean isSupplyNeeded() {
        return volume * availableQuantity < 500;
    }
}
