package HomeworkCSV;

/**
 * just main method and some tests
 */
class Homework {
    public static void main(String[] args)
    {   HashMapOfVendors hash = new HashMapOfVendors();
        System.out.println(hash.getUnitsAndShare("Dell"));
        //System.out.println( hash.getUnitsAndShare("Dell"));
        //System.out.println( hash.visualizePlainOutputTable());
        //System.out.println( hash.sortedHashMapAlphabeticallyInHTML());
        System.out.println( hash.sortedHashMapAlphabeticallyInHTML());
        System.out.println("row number " + hash.tablePlaceVendorInfo("Apple"));
        hash.sortedHashMapAlphabeticallyInHTML();
        hash.updateGivenQuarter("2010 Q4");
        System.out.println( hash.sortedHashMapAlphabeticallyInHTML());
        System.out.println("row number " + hash.tablePlaceVendorInfo("Apple"));
        HashMapOfVendors.Exporter.toHTML(hash);
    }
}
