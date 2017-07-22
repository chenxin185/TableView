package com.chenxin.tableviewdemo;

/**
 * Created by chenxin on 2017/5/17.
 * O(∩_∩)O~
 */

public class HistoryData {

    public HistoryData(String data, String id, String recordId, String result, String time) {
        this.data = data;
        this.id = id;
        this.recordId = recordId;
        this.result = result;
        this.time = time;
    }

    private String data;
    private String id;
    private String recordId;
    private String result;
    private String time;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
