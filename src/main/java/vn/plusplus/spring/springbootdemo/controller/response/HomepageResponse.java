package vn.plusplus.spring.springbootdemo.controller.response;

import java.util.List;

public class HomepageResponse {
    List<String> categories;
    List<Product> products;

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}

