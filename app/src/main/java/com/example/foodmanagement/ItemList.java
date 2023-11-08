package com.example.foodmanagement;

import java.io.Serializable;

public class ItemList implements Serializable {
    private String name;
    private String qty;

    public ItemList(String name, String qty) {
        this.name = name;
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
