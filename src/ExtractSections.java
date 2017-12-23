import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ExtractSections {

    private String sectionNumber;
    private String excelFilePath;
    private String pathToDestination;
    private HashMap<String, List<String>> sectionToStudentMap;

    // File manipulation
    private File excelFile;
    private FileInputStream fin;
    private Workbook excelWorkbook;


    public ExtractSections(String excelFilePath, String pathToDestination){

        // may use this for future reference
        this.pathToDestination = pathToDestination;
        this.excelFilePath = excelFilePath;

        sectionToStudentMap = new HashMap<>();


    }

    public List<String> ExtractSection(String section){
            return (sectionToStudentMap.containsKey(section)) ? sectionToStudentMap.get(section) : null;
    }

    public void ExtractAllSections(){
        try{
            excelFile = new File(excelFilePath);
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

    public String findPathToJavaFile(String sourcePath){
        return null;
    }


    private String getStudentName(String studentName){
        // Format stored in Excel LastName, FirstName
        StringBuilder result = new StringBuilder(studentName);
        result.delete(result.indexOf(","), result.indexOf(",") + 2);
        return result.toString();
    }

    private String getSection(String sectionNumber){
        return sectionNumber.substring("COP3502-".length());
    }


}
