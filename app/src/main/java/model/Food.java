package model;

import java.util.Objects;

public class Food {
    private String name;
    private String image;
    private String description;
    private String price;
    private String discount;
    private String categoryId;

    public Food() {
    }

    public Food(String name, String image, String description, String price, String discount, String categoryId) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return Objects.equals(name, food.name) &&
                Objects.equals(image, food.image) &&
                Objects.equals(description, food.description) &&
                Objects.equals(price, food.price) &&
                Objects.equals(discount, food.discount) &&
                Objects.equals(categoryId, food.categoryId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, image, description, price, discount, categoryId);
    }
}
