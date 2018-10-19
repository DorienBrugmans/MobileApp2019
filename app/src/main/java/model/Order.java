package model;

import java.util.List;

public class Order {
    private String tableId;
    private List<CartItem> foods;
    private String totalPrice;

    public Order() {
    }

    public Order(String tableId, List<CartItem> foods, String totalPrice) {
        this.tableId = tableId;
        this.foods = foods;
        this.totalPrice = totalPrice;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public List<CartItem> getFoods() {
        return foods;
    }

    public void setFoods(List<CartItem> foods) {
        this.foods = foods;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
