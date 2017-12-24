import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipFile {

    byte [] buffer = new byte[1024];
    private String zipFilePath;
    private String pathToDestination;
    private ZipInputStream zis;
    private String studentFolderName;


    public UnzipFile(String pathToSource, String pathToDestination, String subFolderName){
        this.zipFilePath = pathToSource;
        this.pathToDestination = pathToDestination;
        this.studentFolderName = subFolderName;
    }

    public void unzip(){

        try {
            File folder = new File(pathToDestination);

            // Make folder if it doesn't exist
            if (!folder.exists())
                folder.mkdir();

            zis = new ZipInputStream(new FileInputStream(new File(zipFilePath)));

            // Read the entries of directories or files from the the zip file
            // It reads files in sequential order
            ZipEntry zpe = zis.getNextEntry();
            // Only interested in src directory
            while(zpe != null){
                System.out.println(zpe.getName());
                if(zpe.getName().equals("src")){
                    // This must be a Java file
                    zpe = zis.getNextEntry();


                    if(zpe.getName().contains("java")) {
                        //Creates a folder with student name
                        File studentFolder = new File(pathToDestination + File.separator + this.studentFolderName);

                        // make student folder sub directory
                       studentFolder.mkdir();

                        extract(zis, studentFolder.getAbsolutePath() + File.separator + "Lab.java");

                        break;
                    }
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
