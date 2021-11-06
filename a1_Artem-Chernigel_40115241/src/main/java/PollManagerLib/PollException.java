package PollManagerLib;

public class PollException extends Exception {
    private String error;
    private String where;
    PollException(String where, String error){
        this.where = where;
        this.error = error;
    }

    public String toString(){
        return "EXCEPTION ON [" + where + "] : " + error;
    }

}
