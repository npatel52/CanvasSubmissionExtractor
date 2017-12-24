import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipFile {

    byte [] buffer = new byte[1024];
    private String zipFilePath;
    private String pathToDestination;


    public UnzipFile(String pathToSource, String pathToDestination){
        this.zipFilePath = pathToSource;
        this.pathToDestination = pathToDestination;
    }

    /**
     * Extract files of a given type from source and place it to the destination path.
     * @param fileType an extension of a file ex: ".java", ".zip" etc
     */
    public void unzip(String fileType, String filename){

        try {

            ZipInputStream zis = new ZipInputStream(new FileInputStream(new File(zipFilePath)));

            // Read the entries of directories or files from the the zip  in sequential order
            ZipEntry zpe = zis.getNextEntry();

            while(zpe != null){

                // If zip entry contains the given file type then extract it
                if(zpe.getName().contains(fileType)) {

                    //Creates a folder with student name
                    File studentFolder = new File(this.pathToDestination);

                    // make student folder sub directory
                   studentFolder.mkdirs();

                   // extract it
                   extract(zis, studentFolder.getAbsolutePath() + File.separator + filename + fileType);

                }

                // Keep looking for the given file type
                zpe = zis.getNextEntry();
            }

            zis.close();
        }catch(FileNotFoundException fnfe){
            System.err.println("Error in file path in unzip " + fnfe.getMessage());
        }catch (IOException ioe){
            System.err.println("Error reading zip file in UnzipFile " + ioe.getMessage() );
        }
    }

    private void extract(ZipInputStream zis, String extractTo){
        try {
            FileOutputStream fos = new FileOutputStream(extractTo);
            int read;
            while((read = zis.read(buffer)) != -1)
                fos.write(buffer,0,read);
            fos.close();
        }catch (IOException ioe){
            System.err.println("Error while writing " + ioe.getMessage());
        }

    }
}
