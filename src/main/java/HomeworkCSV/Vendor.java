package HomeworkCSV;

/**
 * Vendor class
 */
public class Vendor {
    /**
     * name of the Vendor
     */
    private String name;
    /**
     * units of the Vendor
     */
    private double units;
    /**
     * his share on totalUnits
     */
    private double share;

    /**
     * Constructor of the new vendor its name and units
     */
    public Vendor(String name, double units){
        this.name = name;
        this.units = units;
    }

    public String getName() {
        return name;
    }

    public double getUnits() {
        return units;
    }

    public double getShare() {
        return share;
    }

    public void setShare(double share) {
        this.share = share;
    }
}
