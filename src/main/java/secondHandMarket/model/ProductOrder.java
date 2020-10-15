/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Xin Li
 * Email: helloimlixin@gmail.com
 */

package secondHandMarket.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@Entity
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productOrderId;

    @NotNull
    @OneToOne
    Product product; // seller information can be retried from the product object

    @NotNull
    @OneToOne
    User customer; // customer for the order

    private double rating;
    private boolean confirmed;
    private String timestamp;
    private ZoneId timezoneId = ZoneId.of("US/Pacific"); // default pacific time

    /**
     * Empty constructor for Entity table creation
     */
    public ProductOrder() {}

    /**
     * Create product order entry with given product and customer information, the rating is defaulted 0.0, and
     * timestamp is also created automatically.
     * @param product product of the order, which is of class <code>Product</code>
     * @param customer customer of the order, which is of class <code>User</code>
     */
    public ProductOrder(Product product, User customer) {
        this.product = product;
        this.customer = customer;
        this.rating = 0.0;
        this.confirmed =false;
        DateTimeFormatter formatter = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.SHORT)
                .withLocale(Locale.US)
                .withZone(timezoneId);
        this.timestamp = formatter.format(Instant.now());
    }

    /**
     * Getter method for entry primary key in the backend database
     * @return Long for the primary key of current ProductOrder entity
     */
    public Long getProductOrderId() {
        return productOrderId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public ZoneId getTimezoneId() {
        return timezoneId;
    }

    /**
     * timestamp string is automatically updated upon change in timezone.
     * @param timezoneId string for timezoneId
     */
    public void setTimezoneId(String timezoneId) {
        this.timezoneId = ZoneId.of(timezoneId);
        // Update timestamp accordingly.
        DateTimeFormatter formatter = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.SHORT)
                .withLocale(Locale.US)
                .withZone(this.timezoneId);
        this.timestamp = formatter.format(Instant.now());
    }
}
