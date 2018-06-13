package HomeworkCSV;

import com.opencsv.bean.CsvBindByName;

public class RowCSV {
    @CsvBindByName(column = "Country")
    private String Country;

    @CsvBindByName(column = "Timescale")
    private String Timescale;

    @CsvBindByName(column = "Vendor")
    private String Vendor;

    @CsvBindByName(column = "Units")
    private double Units;

    public String getTimescale() {
        return Timescale;
    }

    public void setTimescale(String timescale) {
        Timescale = timescale;
    }

    public String getVendor() {
        return Vendor;
    }

    public void setVendor(String vendor) {
        Vendor = vendor;
    }

    public double getUnits() {
        return Units;
    }

    public void setUnits(double units) {
        Units = units;
    }

    public String getCountry() {

        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }
}
