package HomeworkCSV;

import java.lang.reflect.Array;
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
        String result="<tr> <td> " + vendorName + " </td> <td> ";

        if(hashMap.isEmpty()){  fillTheHashMap(); }

        result += hashMap.get(vendorName).intValue()+" </td> ";
        result +="<td> "+ df.format((hashMap.get(vendorName)*100)/this.totalUnits)+ "% </td> </tr>";

        return  result ;

    }
    public void fillTheHashMap(){
        for (RowCSV row : ReaderCSV.getInstance().getData()  ) {
           addVendor(new Vendor(row.getVendor(),row.getUnits()));
        }
        this.keyOrder = hashMap.keySet().toArray();
    }
    /*
    public String visualizePlainOutputTable(){
        if(hashMap.isEmpty()){  fillTheHashMap(); }
        String out = "<table> \n<tr> <td> Vendor </td> <td> Units </td> <td> Share </td> </tr> \n";
        for (String key: hashMap.keySet()) {
            out+= getUnitsAndShare(key)+ "\n";
        }
        out+= "<tr> <td bgcolor= \" #FFFF00\"> Total </td> <td bgcolor= \" #FFFF00\"> "+ String.format("%,d", (int)totalUnits) + " </td> <td bgcolor= \" #FFFF00\"> 100% </td> </tr> \n</table>";
        return out;
    }
    */
    public String visualizeOutputTableInHTML(){
        if(hashMap.isEmpty()){  fillTheHashMap(); }
        String out = "<table> \n<tr> <td> Vendor </td> <td> Units </td> <td> Share </td> </tr> \n";
        for (Object key: keyOrder) {
            out+= getUnitsAndShare((String)key)+ "\n";
        }
        out+= "<tr> <td bgcolor= \" #FFFF00\"> Total </td> <td bgcolor= \" #FFFF00\"> "+ String.format("%,d", (int)totalUnits) + " </td> <td bgcolor= \" #FFFF00\"> 100% </td> </tr> \n</table>";
        return out;
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
}



