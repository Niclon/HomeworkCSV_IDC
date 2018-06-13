package HomeworkCSV;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;



public class HashMapOfVendors {
    private double totalUnits;
    private HashMap<String, Double> hashMap = new HashMap<String ,Double>();
    private Object[] keyOrder;

    public HashMapOfVendors(){
        hashMap.clear();
        totalUnits=0;
        fillTheHashMap();
    }


    private void addVendor(Vendor vendor){
        if(!hashMap.containsKey(vendor.getName())){
            hashMap.put(vendor.getName(),vendor.getUnits());
        }
        else{
            hashMap.put(vendor.getName(),hashMap.get(vendor.getName())+ vendor.getUnits());
        }

        this.totalUnits+= vendor.getUnits();
    }
    public String getUnitsAndShare(String vendorName){
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        String result=vendorName + ", ";

        if(hashMap.isEmpty()){  fillTheHashMap(); }

        result += hashMap.get(vendorName).intValue()+", ";
        result +=df.format((hashMap.get(vendorName)*100)/this.totalUnits)+ "% ";

        return  result ;

    }
    public void fillTheHashMap(){
        for (RowCSV row : ReaderCSV.getInstance().getData()  ) {
           addVendor(new Vendor(row.getVendor(),row.getUnits()));
        }
        this.keyOrder = hashMap.keySet().toArray();
    }
    public void fillTheHashMap(String timescale){
        for (RowCSV row : ReaderCSV.getInstance().getData()  ) {
            if(row.getTimescale().equals(timescale))
                addVendor(new Vendor(row.getVendor(),row.getUnits()));
        }
        this.keyOrder = hashMap.keySet().toArray();
    }
    public void updateGivenQuater(String timescale){
        hashMap.clear();
        totalUnits = 0;
        keyOrder=null;
        fillTheHashMap(timescale);
    }
    public String getUnitsAndShareForHTML(String vendorName){
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.CEILING);
        String result="<tr align=\"center\"> <td> " + vendorName + " </td> <td> ";

        if(hashMap.isEmpty()){  fillTheHashMap(); }

        result += hashMap.get(vendorName).intValue()+" </td> ";
        result +="<td> "+ df.format((hashMap.get(vendorName)*100)/this.totalUnits)+ "% </td> </tr>";

        return  result ;
    }
    public String visualizeOutputTableInHTML(){
        if(hashMap.isEmpty()){  fillTheHashMap(); }
        StringBuilder sb = new StringBuilder();
        for (Object key: keyOrder) {
            sb.append(getUnitsAndShareForHTML((String)key)+ "\n");
        }
        sb.append("<tr align=\"center\"> <td bgcolor= \" #FFFF00\"> Total </td> <td bgcolor= \" #FFFF00\"> "+ String.format("%,d", (int)totalUnits) + " </td> <td bgcolor= \" #FFFF00\"> 100% </td> </tr> \n");
        return sb.toString();
    }
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
    public String sortedHashMapAlphabeticallyInHTML(){
        Object[] keys = hashMap.keySet().toArray();
        Arrays.sort(keys,new alphabetComparator());
        this.keyOrder = keys;
        return visualizeOutputTableInHTML();
    }
    public String sortedHashMapByUnitsInHTML(){
        Object[] values = hashMap.values().toArray();
        Arrays.sort(values,new unitsComparator());

        for (int i = 0; i<values.length;i++){
            keyOrder[i]=getKeyFromValue(hashMap,values[i]);
        }
        return visualizeOutputTableInHTML();
    }


    private static Object getKeyFromValue(HashMap hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }
    private class alphabetComparator implements Comparator<Object> {

        @Override
        public int compare(Object o1, Object o2) {
            String s1 = o1.toString().toLowerCase();
            String s2 = o2.toString().toLowerCase();
            return (s1.compareTo(s2));
        }
    }
    private class unitsComparator implements Comparator<Object> {

        @Override
        public int compare(Object o1, Object o2) {
            if((double)o1>(double)o2){
                return -1;
            }else {
                return 1;
            }
        }
    }
    public static final class Exporter{
        private Exporter(){ }
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
        public static void toCSV(HashMapOfVendors hashMapOfVendors){

        }
        public static void toExcel(HashMapOfVendors hashMapOfVendors){

        }

    }
}



