import java.util.Objects;

public class Microscope extends Item implements Equipment,Supplier {

    private Type type;
    public enum Type{
        Electronic,
        Optical,
    }

    private String brand;

    public Microscope(double unitPrice, int availableQuantity, Type type, String brand) {
        super(unitPrice, availableQuantity);
        this.type = type;
        this.brand = brand;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Microscope that = (Microscope) o;
        return type == that.type && Objects.equals(brand, that.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, brand);
    }

    @Override
    public String toString() {
        return " Microscope= " +
                "type=" + type +
                " brand='" + brand + '\'' +
                " unitPrice=" + unitPrice +
                " availableQuantity=" + availableQuantity +
                ""+"\n";
    }
    @Override
    public boolean isSupplyNeeded() {
        return type.equals(Type.Electronic) && availableQuantity == 0;
    }
}
