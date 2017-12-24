import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ExtractSections {

    private final String EXCEL_FILE_PATH = "src/test.xlsx";
    private String section;


    public ExtractSections(String sectionNumber){
            this.section = sectionNumber;
    }

    public List<String> ExtractSection(){

        File excelFile;
        FileInputStream fin;
        Workbook excelWorkbook;
        List<String> result = new ArrayList<>();

        try{

            excelFile = new File(EXCEL_FILE_PATH);
            fin = new FileInputStream(excelFile);
            // XSSF denotes operations realated to Excel 2007 or later
            excelWorkbook = new XSSFWorkbook(fin);
            // Gets the first excel spreedsheet
            Sheet studentSheet = excelWorkbook.getSheetAt(0);

            // Iterator to traverse row by row
            Iterator<Row> row = studentSheet.rowIterator();

            // Loop while there is data in a row
            while(row.hasNext()) {
                // Gets current row
                Row currentRow = row.next();

                // Iterator for reading cell by cell in a row
                Iterator<Cell> cellIterator = currentRow.cellIterator();

                // cell corresponding to two columns
                Cell nameCell = cellIterator.next();
                Cell sectionCell = cellIterator.next();

                // Temporaries
                String sName = getStudentName(nameCell.getStringCellValue());
                String sectionNumber= getSection(sectionCell.getStringCellValue());

                if(this.section.equals(sectionNumber)){
                    result.add(sName);
                }
            }

            // Close streams
            excelWorkbook.close();
            fin.close();

        }catch (FileNotFoundException fnfe){
            System.err.println("Error in File path " + fnfe.getMessage());
        }
        catch (IOException ioe){
            System.err.println("Error in opening excel file. Check file type! " + ioe.getMessage());
        }

        return result;

    }

    /*



        FileReordering fileReordering;
        File [] submissions;
        int fileCounter = 0;
        boolean hit = false;
        int previousCount = fileCounter;

            // Load all the files under submission [All files must be zip]
            submissions = (new File("submissions/")).listFiles();
     if (sectionNumber.equals(this.section)) {

                    String zipFileName = submissions[fileCounter].getName();

                    // Not good
                  if(!hit){
                      fileCounter = previousCount;
                  }else{
                      previousCount = fileCounter;
                  }

                    // Iterate until last name's first letter changes
                   while(!sName.equals(zipFileName.substring(0,zipFileName.indexOf('_',0))) ){
                       zipFileName = submissions[fileCounter].getName();
                       if(zipFileName.charAt(0) > sName.charAt(0))
                           break;
                       ++fileCounter;
                   }

                    // Checks if student submission is missing
                    // File naming format studentname_digits
                    if(sName.equals(zipFileName.substring(0,zipFileName.indexOf('_',0)))){
                        // Submission found
                        // Adding it to appropriate folder
                        hit = true;
                        System.out.println(sName);
                        fileReordering = new FileReordering(this.pathToSource, this.section, nameCell.getStringCellValue());

                        System.out.println("Sent for ordering " + sName);
                        fileReordering.unzip();
                    }else{ // Submission Missing
                        hit = false;
                        System.out.println("Submission Missing " + sName + " " + zipFileName);
                    }



     */
    /*
    public void ExtractAllSections(){
        try{
            excelFile = new File(EXCEL_FILE_PATH);
            fin = new FileInputStream(excelFile);
            // XSSF denotes operations realated to Excel 2007 or later
            excelWorkbook = new XSSFWorkbook(fin);
            // Gets the first excel spreedsheet
            Sheet studentSheet = excelWorkbook.getSheetAt(0);

            // Iterator to traverse row by row
            Iterator<Row> row = studentSheet.rowIterator();

            // Loop while there is data in a row
            while(row.hasNext()){
                // Gets current row
                Row currentRow = row.next();

                // Iterator for reading cell by cell in a row
                Iterator<Cell> cellIterator = currentRow.cellIterator();

                // cell corresponding to two columns
                Cell nameCell = cellIterator.next();
                Cell sectionCell = cellIterator.next();

                // Temporaries
                String sName = getStudentName(nameCell.getStringCellValue());
                String section = getSection(sectionCell.getStringCellValue());

                if(sectionToStudentMap == null){
                    List<String> temp = new ArrayList<>();
                    temp.add(sName);
                    sectionToStudentMap.put(section,temp);
                }else {
                    if (sectionToStudentMap.containsKey(section)) {
                        sectionToStudentMap.get(section).add(sName);
                    } else {
                        List<String> temp = new ArrayList<>();
                        temp.add(sName);
                        sectionToStudentMap.put(section, temp);
                    }
                }
            }

            // Close streams
            excelWorkbook.close();
            fin.close();

        }catch (FileNotFoundException fnfe){
            System.err.println("Error in File path " + fnfe.getMessage());
        }
        catch (IOException ioe){
            System.err.println("Error in opening excel file. Check file type! " + ioe.getMessage());
        }
    }
*/



    private String getStudentName(String studentName){
        // Format stored in Excel LastName, FirstName
        StringBuilder result = new StringBuilder(studentName);
        result.delete(result.indexOf(","), result.indexOf(",") + 2);
        return result.toString().toLowerCase();
    }

    private String getSection(String sectionNumber){
        return sectionNumber.substring("COP3502-".length());
    }


}
