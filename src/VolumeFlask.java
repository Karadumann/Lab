import java.util.Objects;

public class VolumeFlask extends Item implements Equipment,Supplier {

    private int volume;

    public VolumeFlask(int volume, double unitPrice, int availableQuantity) {
        super(unitPrice, availableQuantity);
        this.volume = volume;
    }

    public int getVolume() {
        return volume;
    }

    @Override
    public String toString() {
        return " VolumeFlask= " +
                "volume=" + volume +
                ", unitPrice=" + unitPrice +
                ", availableQuantity=" + availableQuantity +
                ""+"\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VolumeFlask that = (VolumeFlask) o;
        return volume == that.volume;
    }

    @Override
    public int hashCode() {
        return Objects.hash(volume);
    }

    @Override
    public boolean isSupplyNeeded() {
        return availableQuantity < 100;
    }
}
