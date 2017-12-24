import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileOrdering {
    public static void main(String [] args){
        ExtractSections es = new ExtractSections("src/test.xlsx","out/production","11H4");
        es.ExtractSection();
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
