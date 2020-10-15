/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Xin Li
 * Email: helloimlixin@gmail.com
 */

package secondHandMarket.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long categoryId;

    private int categoryIdx;

    private String categoryName;
    private String categoryDescription;

    public Category() {}

    public Category(String category) {
        this.categoryIdx = getCategoryIdx(category);
        String descriptionTemplate = "This is a %s product.";
        switch (categoryIdx) {
            case 1:
                categoryName = "Clothing & Shoes";
                break;
            case 2:
                categoryName = "Beauty & Health";
                break;
            case 3:
                categoryName = "Electronics";
                break;
            case 4:
                categoryName = "Household";
                break;
            case 5:
                categoryName = "Baby & Kids";
                break;
            case 6:
                categoryName = "Car";
                break;
            case 7:
                categoryName = "Furniture";
                break;
            default:
                categoryName = "All Categories";
                break;
        }
        if (categoryName != "All Categories") {
            categoryDescription = String.format(descriptionTemplate, categoryName);
        } else {
            categoryDescription = "No product category is specified.";
        }
    }

    public Category(int categoryIdx) {
        this.categoryIdx = categoryIdx;
        String descriptionTemplate = "This is a %s product.";
        switch (categoryIdx) {
            case 1:
                categoryName = "Clothing & Shoes";
                break;
            case 2:
                categoryName = "Beauty & Health";
                break;
            case 3:
                categoryName = "Electronics";
                break;
            case 4:
                categoryName = "Household";
                break;
            case 5:
                categoryName = "Baby & Kids";
                break;
            case 6:
                categoryName = "Car";
                break;
            case 7:
                categoryName = "Furniture";
                break;
            default:
                categoryName = "All Categories";
                break;
        }
        if (categoryName != "All Categories") {
            categoryDescription = String.format(descriptionTemplate, categoryName);
        } else {
            categoryDescription = "No product category is specified.";
        }
    }

    public int getCategoryIdx() {
        return categoryIdx;
    }

    public static int getCategoryIdx(String category) {
        short categoryIdx;
        switch (category) {
            case "Clothing & Shoes":
                categoryIdx = 1;
                break;
            case "Beauty & Health":
                categoryIdx = 2;
                break;
            case "Electronics":
                categoryIdx = 3;
                break;
            case "Household":
                categoryIdx = 4;
                break;
            case "Baby & Kids":
                categoryIdx = 5;
                break;
            case "Car":
                categoryIdx = 6;
                break;
            case "Furniture":
                categoryIdx = 7;
                break;
            default:
                categoryIdx = 0;
                break;
        }

        return categoryIdx;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public static String getCategoryNameByIdx(int categoryIdx) {
        Category category = new Category(categoryIdx);
        return category.getCategoryName();
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return categoryIdx == category.categoryIdx &&
                Objects.equals(categoryId, category.categoryId) &&
                Objects.equals(categoryName, category.categoryName) &&
                Objects.equals(categoryDescription, category.categoryDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, categoryIdx, categoryName, categoryDescription);
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryIdx=" + categoryIdx +
                ", categoryName='" + categoryName + '\'' +
                ", categoryDescription='" + categoryDescription + '\'' +
                '}';
    }
}
