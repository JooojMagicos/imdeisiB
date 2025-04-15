package pt.ulusofona.aed.deisimdb;

import javax.print.attribute.standard.PresentationDirection;

public class Result {

    boolean success;
    String error = null;
    String result = null;

    public Result(boolean success, String error, String result)
    {
        this.success = success;
        this.error = error;
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
