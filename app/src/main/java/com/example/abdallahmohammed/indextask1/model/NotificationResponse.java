package com.example.abdallahmohammed.indextask1.model;

import android.content.Intent;

/**
 * Created by Abdallah Mohammed on 12/4/2017.
 */

public class NotificationResponse {
    private Integer notificationId ;
    private String content ;
    private Long date ;
    private String type ;
    private Integer orderId ;
    private Integer employeeId ;

    public NotificationResponse(Integer notificationId, String content, Long date, String type, Integer orderId, Integer employeeId) {
        this.notificationId = notificationId;
        this.content = content;
        this.date = date;
        this.type = type;
        this.orderId = orderId;
        this.employeeId = employeeId;
    }

    public NotificationResponse() {
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }
}
