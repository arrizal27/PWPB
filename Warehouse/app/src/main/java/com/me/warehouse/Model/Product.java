package com.me.warehouse.Model;

public class Product {
    private int id;
    private String name;
    private String desc;
    private String quantity;
    private String date;

    public Product(int id, String productName, String productDesc, String quantity, String date) {
        this.id = id;
        this.name = productName;
        this.desc = productDesc;
        this.quantity = quantity;
        this.date = date;
    }

    public Product(String name,String desc, String quantity, String date) {
        this.name = name;
        this.desc = desc;
        this.quantity = quantity;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
