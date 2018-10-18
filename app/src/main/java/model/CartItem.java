package model;

public class CartItem {
    private Food product;
    private String productCount;

    public CartItem(Food product, String productCount) {
        this.product = product;
        this.productCount = productCount;
    }

    public Food getProduct() {
        return product;
    }

    public void setProduct(Food productId) {
        this.product = product;
    }

    public String getProductCount() {
        return productCount;
    }

    public void setProductCount(String productCount) {
        this.productCount = productCount;
    }
}
