package HomeworkCSV;

import com.opencsv.bean.CsvBindByName;

/**
 * This class is for one row from CSV file
 */
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

    public String getVendor() {
        return Vendor;
    }

    public double getUnits() {
        return Units;
    }

    public String getCountry() { return Country; }

}
