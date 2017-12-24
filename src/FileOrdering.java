
import java.util.List;

public class FileOrdering {
    public static void main(String [] args){
        /*
        TO DO
        First return the list for section, close file input stream
        Then use unzip utility along with file ordering to order files

         */

        ExtractSections es = new ExtractSections("11H4");
        List<String> studentList =  es.ExtractSection();
        System.out.println(studentList.toString());
        FileReordering fr = new FileReordering("11H4",studentList);
        fr.unzipFiles();


       // System.out.println(es.ExtractSection("11H4"));

        /*
        try{
            ZipInputStream zis = new ZipInputStream(
                    new FileInputStream("submissions/ackermannjeffrey_983200_35231679_src-7.zip"));
            ZipEntry zpe = zis.getNextEntry();
            while(zpe != null){
                System.out.println(zpe.getName());
                zpe = zis.getNextEntry();
            }

        }catch (IOException ioe){
            System.out.println("Error");
        }
*/
        /*TO DO
         * Add command line feature for one section
         * Read from Excel create folders and subfolder with name
         * Read from submissions map it to appropriate sections and folder
         *
         */
    }
}
