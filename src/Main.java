import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        Laboratory laboratory = new Laboratory();
        FileWriter writer = new FileWriter("Results");

        writer.write("--> Total price of available equipment: "  +laboratory.calculateTotalPriceOfAvailableEquipment() +"\n" +"\n" );

        writer.write("--> Items that need supply: \n"  + laboratory.getItemsThatNeedSupply()+"\n" +"\n");

        writer.write("--> Average price of available materials: "  +laboratory.calculateAveragePrice()+"\n" +"\n");

        writer.write("--> Items Ordered By Price: \n"  +laboratory.getItemsOrderedByPrice()+"\n" +"\n" );

        writer.write("--> Most Expensive Item: "  + laboratory.getMostExpensiveItem()+"\n" +"\n");
        writer.close();

        System.out.println("Inventory: ");
        System.out.println(laboratory);

        System.out.println(" <--Methods are in Results File-->\n " );
    }
}
