public class FileOrdering {
    public static void main(String [] args){
        ExtractSections es = new ExtractSections("src/test.xlsx","src/out");
        es.ExtractAllSections();
        System.out.println(es.ExtractSection("11H4"));
        /*TO DO
         * Add command line feature for one section
         * Read from Excel create folders and subfolder with name
         * Read from submissions map it to appropriate sections and folder
         *
         */
    }
}
