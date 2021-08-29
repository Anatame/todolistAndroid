package com.example.yolaman.model;

public class Item {
    private int id;
    private String itemName;
    private String itemColor;
    private int itemQuantity;
    private int itemSize;
    private String dataItemAdded;

    public Item(){

    }

    public Item(int id, String itemName, String itemColor, int itemQuantity, int itemSize, String dataItemAdded) {
        this.id = id;
        this.itemName = itemName;
        this.itemColor = itemColor;
        this.itemQuantity = itemQuantity;
        this.itemSize = itemSize;
        this.dataItemAdded = dataItemAdded;
    }

    public Item(String itemName, String itemColor, int itemQuantity, int itemSize, String dataItemAdded) {
        this.itemName = itemName;
        this.itemColor = itemColor;
        this.itemQuantity = itemQuantity;
        this.itemSize = itemSize;
        this.dataItemAdded = dataItemAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemColor() {
        return itemColor;
    }

    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public int getItemSize() {
        return itemSize;
    }

    public void setItemSize(int itemSize) {
        this.itemSize = itemSize;
    }

    public String getDataItemAdded() {
        return dataItemAdded;
    }

    public void setDataItemAdded(String dataItemAdded) {
        this.dataItemAdded = dataItemAdded;
    }
}
