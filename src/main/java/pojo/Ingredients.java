package pojo;

import java.util.ArrayList;

public class Ingredients {
    public boolean success;
    public ArrayList<Datum> data;

    public Ingredients() {
    }

    public Ingredients(boolean success, ArrayList<Datum> data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }
}
