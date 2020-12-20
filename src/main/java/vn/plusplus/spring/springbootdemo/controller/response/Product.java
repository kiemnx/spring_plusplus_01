package vn.plusplus.spring.springbootdemo.controller.response;

public class Product{
    String name;
    String price;
    String avatar;

    public Product(String name, String price, String avatar) {
        this.name = name;
        this.price = price;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}