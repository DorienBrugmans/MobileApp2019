package model;

public class CartItem {
    private String productId;
    private String productCount;

    public CartItem() {
    }

    public CartItem(String productId, String productCount) {
        this.productId = productId;
        this.productCount = productCount;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCount() {
        return productCount;
    }

    public void setProductCount(String productCount) {
        this.productCount = productCount;
    }
}
