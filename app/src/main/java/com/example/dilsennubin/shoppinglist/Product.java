package com.example.dilsennubin.shoppinglist;

public class Product {

    private String id;
    private String productName;
    private String quantity;
    private boolean checked;

    public Product() {

    }

    public Product(String id, String productName, String quantity, boolean checked) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.checked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
