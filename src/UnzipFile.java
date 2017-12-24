import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipFile {

    byte [] buffer = new byte[1024];
    private String zipFilePath;
    private final String PATH_TO_DESTINATION = "out" + File.separator ;
    private ZipInputStream zis;
    private String studentFolderName;
    private String section;


    public UnzipFile(String pathToSource, String subFolderName, String sectionNumber){
        this.zipFilePath = pathToSource;
        this.studentFolderName = subFolderName;
        this.section  = sectionNumber;
    }

    public void unzip(){

        try {

            zis = new ZipInputStream(new FileInputStream(new File(zipFilePath)));

            // Read the entries of directories or files from the the zip file
            // It reads files in sequential order
            ZipEntry zpe = zis.getNextEntry();
            // Only interested in src directory
            while(zpe != null){
                System.out.println(zpe.getName());

                    if(zpe.getName().contains("java")) {
                        //Creates a folder with student name
                        File studentFolder = new File(this.PATH_TO_DESTINATION + this.section + File.separator + this.studentFolderName );

                        // make student folder sub directory
                       studentFolder.mkdirs();
                       extract(zis, studentFolder.getAbsolutePath() + File.separator + "Lab.java");
                       break;
                    }

                zpe = zis.getNextEntry();

            }
            zis.close();
        }catch (IOException ioe){
            System.err.println("Error reading zip file in UnzipFile " + ioe.getMessage() );
        }
    }

    private void extract(ZipInputStream zis, String extractTo){
        try {
            FileOutputStream fos = new FileOutputStream(extractTo);
            System.out.println(extractTo);
            int read;
            while((read = zis.read(buffer)) != -1)
                fos.write(buffer,0,read);
            fos.close();
        }catch (IOException ioe){
            System.err.println("Error while writing " + ioe.getMessage());
        }

    }
}
