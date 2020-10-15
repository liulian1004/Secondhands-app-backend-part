/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Xin Li
 * Email: helloimlixin@gmail.com
 */

package secondHandMarket.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class ProductStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postStatusId;

    private boolean available = false;

    public ProductStatus() {}

    public ProductStatus(boolean available) {
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductStatus that = (ProductStatus) o;
        return available == that.available &&
                Objects.equals(postStatusId, that.postStatusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postStatusId, available);
    }

    @Override
    public String toString() {
        return "ProductStatus{" +
                "postStatusId=" + postStatusId +
                ", available=" + available +
                '}';
    }
}
