public abstract class Item implements Supplier {

    protected double unitPrice;
    protected int availableQuantity;

    public Item(double unitPrice, int availableQuantity) {
        this.unitPrice = unitPrice;
        this.availableQuantity = availableQuantity;
    }



    public double getUnitPrice() {
        return unitPrice;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    @Override
    public boolean isSupplyNeeded() {
        return false;
    }


    @Override
    public String toString() {
        return "Item= " +
                "unitPrice= " + unitPrice +
                ", availableQuantity= " + availableQuantity +
                ""+"\n";
    }
}
