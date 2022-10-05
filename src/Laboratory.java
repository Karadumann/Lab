import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Laboratory {
    private List <Item> Inventory = new ArrayList<>();
    public Laboratory() {
        try {
            Inventory.add (new Microscope(32, 1, Microscope.Type.Electronic, "Zeiss"));
            Inventory.add (new Microscope(15, 33, Microscope.Type.Optical, "Nikon"));
            Inventory.add (new Microscope(22, 15, Microscope.Type.Optical, "Olympus"));
            Inventory.add (new Microscope(18, 10, Microscope.Type.Electronic, "Oly"));
            Inventory.add (new TestTube(6, 30, 50, 3));
            Inventory.add (new TestTube(3, 23, 25, 10));
            Inventory.add (new TestTube(5, 27, 17, 7));
            Inventory.add (new VolumeFlask(330, 15, 40));
            Inventory.add (new VolumeFlask(220, 11, 30));
            Inventory.add (new VolumeFlask(200, 9, 35));
            Inventory.add (new LitmusPaper(50, 47, 10));
            Inventory.add (new LitmusPaper(40, 30, 7));
            Inventory.add (new LitmusPaper(33, 25, 5));
            Inventory.add (new LitmusSolution(400, 12, 3));
            Inventory.add (new LitmusSolution(300, 10, 2));
            Inventory.add (new LitmusSolution(600, 5, 1));
            Inventory.add (new LitmusSolution(700, 8, 8));
        }
        catch (Exception e) { throw new RuntimeException(e);  }
    }


    public int calculateTotalPriceOfAvailableEquipment() {
        int totalPriceOfAvailableEquipment = 0;
        for (Item current : Inventory) {
            totalPriceOfAvailableEquipment += current.getUnitPrice();
        }
        return totalPriceOfAvailableEquipment;
    }
    public List <Item> getItemsOrderedByPrice() {
        Inventory.sort(Comparator.comparing(o -> o.getUnitPrice()));
        return Inventory;
    }

    public List<Item> getItemsThatNeedSupply()
    {
        return Inventory.stream().filter(Item::isSupplyNeeded).collect(Collectors.toList());
    }

    public double calculateAveragePrice() {
        double totalPrice = 0;
        for (Item item : Inventory) {
            totalPrice += item.unitPrice;}
        return totalPrice/ Inventory.size();
    }


    public Item getMostExpensiveItem() {
        Collections.sort(Inventory, new Comparator<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                return (int) (o1.getUnitPrice() - o2.getUnitPrice());}
        });
        return Inventory.get(Inventory.size() - 1);
    }



    @Override
    public String toString() {
        return " " +
                "" +"\n" + Inventory +
                ""+"\n";
    }
}

