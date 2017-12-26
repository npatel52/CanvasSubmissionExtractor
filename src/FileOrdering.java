import java.io.File;
import java.util.List;

public class FileOrdering {

    private final String PATH_TO_DESTINATION = System.getProperty("user.dir") + File.separator;
    private final String PATH_TO_SOURCE = System.getProperty("user.dir") + File.separator + "submissions" + File.separator;
    private final String SECTION;

    private List<String> studentList;
    private File directory;
    private File [] SUBMISSIONS;

    /**
     * Constructor
     * @param section a section number in the course
     * @param studentList a list of names in the section
     */
    public FileOrdering(String section, List<String> studentList){
        this.SECTION = section;
        this.studentList = studentList;

        // Object to access submissions
        directory = new File(PATH_TO_SOURCE);
        // Create an array of files containing submissions
        SUBMISSIONS = directory.listFiles();

        // Create empty folder where output will be stored
        createFolder();
    }

    /**
     * Unzip zip files found in submissions for the students.
     * Creates empty folder for students whose submission is missing.
     *
     */
    public void unzipFiles(String fileType){
        for(String name: studentList){
            String pathToZipFile = searchPathTo(name);
            if(pathToZipFile == null) {
                createFolder(name + "-Missing!");
            }else{
              extractFile(pathToZipFile,
                        PATH_TO_DESTINATION + this.SECTION + File.separator + name, fileType, "Lab");
            }
        }
    }

    /**
     * Searches for student name in alphabetically ordered list of strings and returns
     * path to the file submitted by that student.
     * @param studentName name of student to search in the list of strings
     * @return path to the location of file submitted by this student
     */
    private String searchPathTo(String studentName){

      int start = 0;
      int end = SUBMISSIONS.length - 1;
      int mid = 0;
      String fileName;
      String justName;

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
            return PATH_TO_SOURCE + SUBMISSIONS[mid].getName();
        }

      }
      return null;
    }


    /**
     * Uses unzip file class to extract file.
     * @param pathToZipFile a path to submission
     * @param pathToDestination a path to store the extracted file
     * @param fileType a file extension
     * @param fileName a submission file name
     */
    private void extractFile(String pathToZipFile, String pathToDestination, String fileType, String fileName){
        UnzipFile ufile = new UnzipFile(pathToZipFile, pathToDestination);
        ufile.unzip(fileType, fileName);
    }


    /**
     * Creates an empty folder in out named after section number.
     */
    private void createFolder(){
        // Verifies if the path exist
        if(! (new File(this.PATH_TO_DESTINATION ).exists()) )
            new File(this.PATH_TO_DESTINATION ).mkdir();

        // Create a folder named with the section number
        new File(this.PATH_TO_DESTINATION + this.SECTION).mkdir();
    }

    /**
     * Creates a sub folder in output folder named after student name.
     * @param folderName the name of the folder
     */
    private void createFolder(String folderName){
        // Create a sub folder named with the student name
        new File(this.PATH_TO_DESTINATION +  this.SECTION + File.separator + folderName).mkdirs();
    }

}
