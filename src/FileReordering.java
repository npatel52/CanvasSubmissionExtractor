import java.io.File;
import java.util.List;

// Keep only .java files
// create folder and sub folders

public class FileReordering {
    private final String PATH_TO_DESTINATION = "out";
    private final String PATH_TO_SOURCE = "submissions";
    private final String SECTION;
    private List<String> studentList;
    private UnzipFile ufile;
    // Array of sub-directory
    private File directory;
    private File [] SUBMISSIONS;

    public FileReordering(String section, List<String> studentList){
        this.SECTION = section;
        this.studentList = studentList;
        directory = new File(PATH_TO_SOURCE + File.separator);
        directory.mkdir();
        SUBMISSIONS = directory.listFiles();

        // Create empty folder
        createFolder();
    }

    public void unzipFiles(){
        for(String name: studentList){
            String pathToUnzip = searchPathTo(name);
            if(pathToUnzip == null) {
                createFolder(name);
            }else{
                UnzipFile zipFile = new UnzipFile(pathToUnzip,name, this.SECTION);
                zipFile.unzip();
            }


        }
    }

    // Uses binary search
    private String searchPathTo(String studentName){


      int start = 0;
      int end = SUBMISSIONS.length - 1;
      int mid = 0;
      String fileName = "";
      String justName = "";

      while(start <= end){
        mid = (start + end)/2;

        // Actual file name
        fileName = SUBMISSIONS[mid].getName();
        // Student name from file name
        justName = fileName.substring(0, fileName.indexOf('_',0));

        if( justName.compareTo(studentName) > 0 ){
            end = mid - 1;
        }else if (justName.compareTo(studentName) < 0 ){
            start = mid + 1;
        }else{
            // match found
            return PATH_TO_SOURCE + File.separator + SUBMISSIONS[mid].getName();
        }

      }
      return null;
    }



    public void unzip() {
        ufile.unzip();
    }

    // Make empty folder for missing submission

    private void createFolder(){
        // Verifies if the path exist
        if(! (new File(this.PATH_TO_DESTINATION ).exists()) )
            new File(this.PATH_TO_DESTINATION ).mkdir();

        // Create a folder named with the section number
        new File(this.PATH_TO_DESTINATION + File.separator + this.SECTION).mkdir();
    }

    private void createFolder(String folderName){
        // Create a sub folder named with the student name
        new File(this.PATH_TO_DESTINATION + File.separator + this.SECTION + File.separator +folderName + "---- Missing!").mkdirs();
    }

}
