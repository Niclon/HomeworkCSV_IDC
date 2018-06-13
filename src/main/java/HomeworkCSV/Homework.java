package HomeworkCSV;

/**
 * just main method and some tests
 */
class Homework {
    public static void main(String[] args)    {
        HashMapOfVendors hash = new HashMapOfVendors();
        System.out.println(hash.getUnitsAndShare("Dell"));
        System.out.println(hash.visualizeOutputTable());
        System.out.println("_____________________________");

        hash.sortHashMapAlphabetically();
        System.out.println(hash.visualizeOutputTable());
        System.out.println("row number " + hash.tablePlaceVendorInfo("Apple"));
        System.out.println("_____________________________");

        hash.updateGivenQuarter("2010 Q4");
        hash.sortHashMapByUnits();
        System.out.println(hash.visualizeOutputTable());
        System.out.println("row number " + hash.tablePlaceVendorInfo("Apple"));
        HashMapOfVendors.Exporter.toHTML(hash);
    }
}
