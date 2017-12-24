import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExtractSections {

    private final String EXCEL_FILE_PATH = "src/test.xlsx";
    private String section;


    public ExtractSections(String sectionNumber){
            this.section = sectionNumber;
    }

    /**
     * Reads excel file and create a list of student name
     * that corresponds to the given section number.
     *
     * @return list of student name
     */
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


    /**
     * Formats student name to match file names. Removes a non-word character such as
     * '-', ',', ''', whitespace etc. Also converts uppercase to lowercase.
     * @param studentName name of a student as retrieved from excel spreadsheet
     * @return a formatted string where student's last name and first name are appended
     */
    private String getStudentName(String studentName){
        if(studentName == null)
            return null;
        // removes non-word character by replacing it with empty string
        return studentName.replaceAll("\\W+","").toLowerCase();
    }

    /**
     * Removes some unnecessary characters.
     * @param sectionNumber section number as retrieved from excel spreadsheet
     * @return formatted string with extra characters removed.
     */
    private String getSection(String sectionNumber){
        if(sectionNumber == null)
            return null;
        // Excludes extra characters as all sections belong to COP3502
        return sectionNumber.substring("COP3502-".length());
    }


}
