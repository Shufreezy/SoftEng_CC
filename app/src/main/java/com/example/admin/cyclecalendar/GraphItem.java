package com.example.admin.cyclecalendar;

import java.util.Date;

// Model for GraphItem
public class GraphItem {
    private  Date date;
    private  int flowType;

    public GraphItem(Date date, int flowType) {
        this.date = date;
        this.flowType = flowType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getFlowType() {
        return flowType;
    }

    public void setFlowType(int flowType) {
        this.flowType = flowType;
    }
}
