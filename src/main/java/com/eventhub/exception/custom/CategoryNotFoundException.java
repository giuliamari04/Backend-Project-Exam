package com.eventhub.exception.custom;

public class CategoryNotFoundException extends BaseException {

    public CategoryNotFoundException() {
        super("CATEGORY_NOT_FOUND", "Category not found");
    }
}