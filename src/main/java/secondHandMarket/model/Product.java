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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Entity Class for product postings.
 * References:
 * 1. String formatting guide: https://dzone.com/articles/java-string-format-examples
 * 2. Time Zone IDs: https://docs.oracle.com/javase/8/docs/api/java/tiwme/ZoneId.html
 * 3. Display Time Zone IDs: https://mkyong.com/java8/java-display-all-zoneid-and-its-utc-offset/
 */
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @ManyToOne
    @NotNull(message = "Seller user is required")
    private User user; // seller

    @NotNull(message = "Product name is required.")
    private String productName;

    private String category;

    private double price = 0.0;

    private boolean favorite;
    private boolean availability;

    private String description;

    @Column(columnDefinition = "LONGTEXT")
    private String imageUrls;

    private String timestamp;
    private ZoneId timezoneId = ZoneId.of("US/Pacific"); // default pacific time
    private double lat = 0.0;
    private double lon = 0.0;
    private boolean GPSEnabled = false;

    @NotNull(message = "State is required")
    private String state;

    private String city = "";

    @OneToOne
    private Favorite favoriteItem;

    public Product() {}

    public Product(User user, String postName, String state) {
        this.user = user;
        this.productName = postName;
        this.state = state;
        DateTimeFormatter formatter = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.SHORT)
                .withLocale(Locale.US)
                .withZone(timezoneId);
        this.timestamp = formatter.format(Instant.now());
        this.availability = false;
        this.favorite = false;
        this.category = "All Categories";
    }

    public Long getProductId() {
        return productId;
    }

    /**
     * Get seller object of product.
     * @return User object representing seller
     */
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String postName) {
        this.productName = postName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return String.format("%.2f", price);
    }

    public void setPrice(String price) {
        this.price = Double.valueOf(price);
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getTimezoneId() {
        return timezoneId.toString();
    }

    public void setTimezoneId(String timezoneId) {
        this.timezoneId = ZoneId.of(timezoneId);
        // Update timestamp accordingly.
        DateTimeFormatter formatter = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.SHORT)
                .withLocale(Locale.US)
                .withZone(this.timezoneId);
        this.timestamp = formatter.format(Instant.now());
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public void setLocation(double lat, double lon, String city) {
        if (lat == 0.0 && lon == 0.0) {
            System.out.println("GPS is disabled");
            this.GPSEnabled = false;
        } else {
            this.lat = lat;
            this.lon = lon;
            this.GPSEnabled = true;
        }

        if (city != null && city.length() != 0) {
            this.city = city;
        }
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isGPSEnabled() {
        return this.GPSEnabled;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getCity() {
        return city;
    }

    public String getLocation() {
        String locationTemplate =
                "Coordinates (Lat, Lon): %s\n" +
                        "State: %s\nCity: %s";
        String location = "";
        if (isGPSEnabled()) {
            String coordinates = "(" + String.valueOf(lat) + "," + String.valueOf(lon) + ")";
            if (city.length() != 0) {
                location = String.format(locationTemplate,
                        coordinates,
                        state,
                        city);
            } else {
                location = String.format(locationTemplate,
                        coordinates,
                        state,
                        "N/A");
            }
        } else {
            if (city.length() != 0) {
                location = String.format(locationTemplate,
                        "N/A",
                        state,
                        city);
            } else {
                location = String.format(locationTemplate,
                        "N/A",
                        state,
                        "N/A");
            }
        }
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 &&
                availability == product.availability &&
                Double.compare(product.lat, lat) == 0 &&
                Double.compare(product.lon, lon) == 0 &&
                GPSEnabled == product.GPSEnabled &&
                Objects.equals(productId, product.productId) &&
                Objects.equals(user, product.user) &&
                Objects.equals(productName, product.productName) &&
                Objects.equals(category, product.category) &&
                Objects.equals(description, product.description) &&
                Objects.equals(imageUrls, product.imageUrls) &&
                Objects.equals(timestamp, product.timestamp) &&
                Objects.equals(timezoneId, product.timezoneId) &&
                Objects.equals(state, product.state) &&
                Objects.equals(city, product.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, user, productName, category, price, availability, description, imageUrls, timestamp, timezoneId, lat, lon, GPSEnabled, state, city);
    }

    @Override
    public String toString() {
        return "Product{" +
                "postId=" + productId +
                ", user=" + user +
                ", postName='" + productName + '\'' +
                ", category=" + category +
                ", price=" + price +
                ", availability=" + availability +
                ", description='" + description + '\'' +
                ", imageUrls=" + imageUrls +
                ", timestamp='" + timestamp + '\'' +
                ", timezoneId=" + timezoneId +
                ", lat=" + lat +
                ", lon=" + lon +
                ", GPSEnabled=" + GPSEnabled +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
