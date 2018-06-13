package HomeworkCSV;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Class HashMapOfVendors is output class for faster working with cashed values
 */

public class HashMapOfVendors {
    /**
     * Sum of all units on output
     */

    private double totalUnits;
    /**
     * HashMap<String - name of the Vendor , Double - units> this hashmap is there for storing data for each vendor
     */
    private HashMap<String, Double> hashMap = new HashMap<String ,Double>();
    /**
     * Array of objects is for storing order of output data to prevent re-creating new cashed hashmaps. Its stores key order. In this case VendorName
     */
    private Object[] keyOrder;
    /**
     * Constructor for clearing hashMap and setting totalUnits to zero and filling hashMap with data without any restriction
     */
    public HashMapOfVendors(){
        hashMap.clear();
        totalUnits=0;
        fillTheHashMap();
    }

    /**
     * This method is for adding Vendor to HashMap. If Vendor already is in HashMap it only add his new units to his older units in has which already is stored in HashMap.
     * Add every new units to class prop totalUnits
     * @params instance of class vendor
     */
    private void addVendor(Vendor vendor){
        if(!hashMap.containsKey(vendor.getName())){
            hashMap.put(vendor.getName(),vendor.getUnits());
        }
        else{
            hashMap.put(vendor.getName(),hashMap.get(vendor.getName())+ vendor.getUnits());
        }

        this.totalUnits+= vendor.getUnits();
    }
    /**
     * This method is for calculating units of vendor and its percantage share rounded on one decimal place
     * @params vendorName  is name of the Vendor
     */
    public String getUnitsAndShare(String vendorName){
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        StringBuilder sb = new StringBuilder();
        sb.append(vendorName + ", ");

        if(hashMap.isEmpty()){  fillTheHashMap(); }

        sb.append( String.format("%,d", hashMap.get(vendorName).intValue())+", ");
        sb.append( df.format((hashMap.get(vendorName)*100)/this.totalUnits)+ "% ");

        return  sb.toString() ;

    }
    /**
     * This method fill HashMap with all data from CSV file without any restriction.
     * setting keyOrder for default order of Vendors in HashMap
     */
    public void fillTheHashMap(){
        for (RowCSV row : ReaderCSV.getInstance().getData()  ) {
           addVendor(new Vendor(row.getVendor(),row.getUnits()));
        }
        this.keyOrder = hashMap.keySet().toArray();
    }
    /**
     * Overloaded method with restriction of given timesclace.
     * Fill HashMap only with values which meet the conditions of given timeScale
     * This method only  works for given year and quarter. It should have 2 timeScales from and to and own comparator
     * for better results now it only for one year and one quarter and only if its correctly written. This should be solved with UI. For example slider.
     * setting keyOrder for default order of Vendors in HashMap
     * @params timeScale  year and quarter for example ( 2010 Q4 )
     */
    public void fillTheHashMap(String timescale){
        for (RowCSV row : ReaderCSV.getInstance().getData()  ) {
            if(row.getTimescale().equals(timescale))
                addVendor(new Vendor(row.getVendor(),row.getUnits()));
        }
        this.keyOrder = hashMap.keySet().toArray();
    }
    /**
     * This method is for updating HashMap if user changes quarter. It clear the HashMap, set totalUnits to zero, clear keyOrder and call method described one above
     * @params timeScale  year and quarter for example ( 2010 Q4 )
     */
    public void updateGivenQuarter(String timescale){
        hashMap.clear();
        totalUnits = 0;
        keyOrder=null;
        fillTheHashMap(timescale);
    }
    /**
     * This method is doing same as getUnitsAndShare but to result string adding some html tags
     * returns string with html tags vendors name number of units and share percentage
     * @params vendorName name of the Vendor
     */
    public String getUnitsAndShareForHTML(String vendorName){
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        StringBuilder sb = new StringBuilder();
        sb.append("<tr align=\"center\"> <td> " + vendorName + " </td> <td> ");

        if(hashMap.isEmpty()){  fillTheHashMap(); }

        sb.append( String.format("%,d", hashMap.get(vendorName).intValue()) +" </td> ");
        sb.append( "<td> "+ df.format((hashMap.get(vendorName)*100)/this.totalUnits)+ "% </td> </tr>");

        return  sb.toString() ;
    }
    /**
     * This method returns string with html tags and all info about Vendors etc.
     */
    public String visualizeOutputTableInHTML(){
        if(hashMap.isEmpty()){  fillTheHashMap(); }
        StringBuilder sb = new StringBuilder();
        for (Object key: keyOrder) {
            sb.append(getUnitsAndShareForHTML((String)key)+ "\n");
        }
        sb.append("<tr align=\"center\"> <td bgcolor= \" #FFFF00\"> Total </td> <td bgcolor= \" #FFFF00\"> "+ String.format("%,d", (int)totalUnits) + " </td> <td bgcolor= \" #FFFF00\"> 100% </td> </tr> \n");
        return sb.toString();
    }
    /**
     * Returns number of row where is given vendor in result table. It search for key in array Key order and returns id of row in result
     * @params vendorName name of the Vendor
     */
    public int tablePlaceVendorInfo(String vendorName){
        int rowId=1;
        for (Object key: keyOrder) {
            if(key.equals(vendorName)){
                return rowId;
            }
            rowId++;
        }
        return -1;
    }

    /**
     * This method create string for output to commandline from cached hashMap in keyOrder
     * @return string of output
     */
    public String visualizeOutputTable(){
        if(hashMap.isEmpty()){  fillTheHashMap(); }
        StringBuilder sb = new StringBuilder();
        for (Object key: keyOrder) {
            sb.append(getUnitsAndShare((String)key)+ "\n");
        }
        sb.append("Total  "+ String.format("%,d", (int)totalUnits) + " 100%  \n");
        return sb.toString();
    }
    /**
     * This method change order of keys in keyOrder and sort it with alphabetComparator by name and then call method visualizeOutputTableInHTML
     */
    public void sortHashMapAlphabetically(){
        Object[] keys = hashMap.keySet().toArray();
        Arrays.sort(keys,new alphabetComparator());
        this.keyOrder = keys;
    }
    /**
     * This method sort keyOrder by Vendor units and then call method visualizeOutputTableInHTML
     * these two methods are quite not important
     */
    public String sortedHashMapByUnitsInHTML(){
        sortHashMapByUnits();
        return visualizeOutputTableInHTML();
    }
    /**
     * This method call method to sort HashMap by name and then call method visualizeOutputTableInHTML()
     */
    public String sortedHashMapAlphabeticallyInHTML(){
        sortHashMapAlphabetically();
        return visualizeOutputTableInHTML();
    }

    /**
     * This method change order of keys in keyOrder and sort it by Units
     */
    public void sortHashMapByUnits(){
        Object[] values = hashMap.values().toArray();
        Arrays.sort(values,new unitsComparator());

        for (int i = 0; i<values.length;i++){
            keyOrder[i]=getKeyFromValue(hashMap,values[i]);
        }
    }

    /**
     * This method return key of the given value
     * @param hm casched hashmap
     * @param value units
     * @return
     */
    private static Object getKeyFromValue(HashMap hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }
    /**
     * Private class which implements Comparator to sort by name
     */
    private class alphabetComparator implements Comparator<Object> {

        /**
         * this method comparing two objects in our case two vendorNames alphabetically
         */
        @Override
        public int compare(Object o1, Object o2) {
            String s1 = o1.toString().toLowerCase();
            String s2 = o2.toString().toLowerCase();
            return (s1.compareTo(s2));
        }
    }
    /**
     * Private class which implements comparator to sort by units
     */
    private class unitsComparator implements Comparator<Object> {

        /**
         * this method comparing two objects value
         */
        @Override
        public int compare(Object o1, Object o2) {
            if((double)o1>(double)o2){
                return -1;
            }else {
                return 1;
            }
        }
    }

    /**
     * Class Exporter is class for exporting cashed values from hashtable
     */
    public static final class Exporter{
        private Exporter(){ }
        /**
         * this method takes template named tableTemplate and replace its body with a string method called hashMapOfVendors.visualizeOutputTableInHTML(), which return
         * string with html tags. Create new html file with new body and result is in table.html
         * In this method we should probably wrap the whole result with table body in json and send it to already going page
         */
        public static void toHTML(HashMapOfVendors hashMapOfVendors){
            try {
                File htmlTemplateFile = new File("C:\\Users\\MSI\\Desktop\\IDC-project\\HomeworkCSV_IDC\\src\\main\\resources\\Templates\\tableTemplate.html");
                String htmlString = FileUtils.readFileToString(htmlTemplateFile);
                String body =  hashMapOfVendors.visualizeOutputTableInHTML();
                htmlString = htmlString.replace("$body", body);
                File newHtmlFile = new File("C:\\Users\\MSI\\Desktop\\IDC-project\\HomeworkCSV_IDC\\src\\main\\resources\\Templates\\table.html");
                FileUtils.writeStringToFile(newHtmlFile, htmlString);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        /**
         * In this method i would use writer and opencsv  StatefulBeanToCsv<Vendor>. Created a list of Vendors List<Vendor> from cached hasMapOfVendors and then i would use
         *  name of statefulbeantocsv .write( name of the list)
         * @param hashMapOfVendors cashed hashMap with key vendorName and value units
         */
        public static void toCSV(HashMapOfVendors hashMapOfVendors){

        }
        public static void toExcel(HashMapOfVendors hashMapOfVendors){

        }

    }
}



