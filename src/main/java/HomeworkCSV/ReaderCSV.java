package HomeworkCSV;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Class ReaderCSV is build on design pattern singleton. Its instance is created on start of the program to be able to access it from
 * anywhere without initializating new ReaderCSV instance and to prevent redundace reading of the file. Because im working with small data i read whole file to memory.
 */

public class ReaderCSV {
    /**
     * Path to CSV file. I used absolute path couldnt do it with relative.
     */
    public static final String CSV_PATH = "C:\\Users\\MSI\\Desktop\\IDC-project\\HomeworkCSV_IDC\\data.csv";
    /**
     * List of rows from CSV file
     */
    private List<RowCSV> data;
    /**
     * Instance of Class ReaderCSV
     */
    private static ReaderCSV instance = new ReaderCSV();

    /**
     * Constructor try to read whole CSV file to memory and each row is stored in memory as instance of RowCSV class and each instance is stored data List
     */
    private ReaderCSV(){
        try {
            Reader reader =  Files.newBufferedReader(Paths.get((CSV_PATH)), StandardCharsets.UTF_8);
            CsvToBean<RowCSV> csvReader = new CsvToBeanBuilder(reader)
                    .withType(RowCSV.class)
                    .withSeparator(',')
                    .build();
            this.data = csvReader.parse();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Return instance of ReaderCSV class
     */
    public static ReaderCSV getInstance(){
        return instance;
    }

    /**
     * Return List of data which was read from CSV file
     */
    public List<RowCSV> getData() {
        return data;
    }
}
