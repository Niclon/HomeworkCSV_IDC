package HomeworkCSV;

public class Vendor {
    private String name;
    private double units;
    //private double percantage;
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

    public void setName(String name) {

        this.name = name;
    }

    public void setUnits(double units) {
        this.units = units;
    }

}
