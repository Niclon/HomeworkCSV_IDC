package HomeworkCSV;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;


class Homework {
    public static void main(String[] args)
    {
        /*
        for (RowCSV rowCSV: ReaderCSV.getInstance().getData()) {
            //System.out.println("country : " + rowCSV.getCountry());
            //System.out.println("timescale : " + rowCSV.getTimescale());
            System.out.println("Vendor : " + rowCSV.getVendor());
            System.out.println("Units : " + rowCSV.getUnits());
            System.out.println("------------------------");
        }
*/

        HashMapOfVendors hash = new HashMapOfVendors();
        //hash.getUnitsAndShare("Dell");
        //System.out.println( hash.getUnitsAndShare("Dell"));
        //System.out.println( hash.visualizePlainOutputTable());
        //System.out.println( hash.sortedHashMapAlphabeticallyInHTML());
        System.out.println( hash.sortedHashMapByUnitsInHTML());
        System.out.println("row number " + hash.tablePlaceVendorInfo("Apple"));
    }
}
