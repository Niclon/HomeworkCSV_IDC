package HomeworkCSV;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;



public class ReaderCSV {

    public static final String CSV_PATH = "C:\\Users\\MSI\\Desktop\\IDC-project\\homeworkIDC\\Main\\data.csv";
    private List<RowCSV> data;
    private static ReaderCSV instance = new ReaderCSV();

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

    public static ReaderCSV getInstance(){
        return instance;
    }


    public List<RowCSV> getData() {
        return data;
    }
}
