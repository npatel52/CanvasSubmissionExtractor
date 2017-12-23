public class Data {

    private String studentName;
    private String sectionNumber;

    public Data(){}

    public void setName(String name){
        //save name as lastfirst combined remove , + lowercase
    }

    public void setSectionNumber(String sectionNumber){

      //remove COP3502
    }

    public String getName(){
        return studentName;
    }

    public String getSectionNumber(){
        return sectionNumber;
    }

    @Override
    public String toString() {
        return sectionNumber + " " + studentName;
    }
}
