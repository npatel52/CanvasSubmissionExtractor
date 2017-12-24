
import java.util.List;

public class FileOrdering {
    public static void main(String [] args){

        ExtractSections es = new ExtractSections("2296");
        List<String> studentList =  es.ExtractSection();
        System.out.println(studentList.toString());
        FileReordering fr = new FileReordering("2296",studentList);
        fr.unzipFiles();

    }
}
