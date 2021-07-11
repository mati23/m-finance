package model;

import java.util.List;

public class MData {
    private List<Object> data;

    public MData(List<Object> data) {
        this.data = data;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
