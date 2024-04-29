package com.app.domain.product_management.model.product_category;

import java.util.Random;

public enum Category {
    A,
    B,
    C;

    public static Category randomCategory() {
        var categories = values();
        return categories[new Random().nextInt(categories.length)];
    }
}
