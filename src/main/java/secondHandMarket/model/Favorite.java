/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Xin Li
 * Email: helloimlixin@gmail.com
 */

package secondHandMarket.model;

import javax.persistence.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Objects;


@Entity
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long favoriteId;

    @OneToOne
    private Product product;

    @ManyToOne
    private User user;

    private String timestamp;
    private ZoneId timezoneId = ZoneId.of("US/Pacific"); // default pacific time

    public Favorite() {}

    public Favorite(Product product, User user) {
        this.product = product;
        this.user = user;
        DateTimeFormatter formatter = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.SHORT)
                .withLocale(Locale.US)
                .withZone(timezoneId);
        this.timestamp = formatter.format(Instant.now());
    }

    public Product getProduct() {
        return product;
    }

    public void setProducts(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public ZoneId getTimezoneId() {
        return timezoneId;
    }

    public void setTimezoneId(String timezoneId) {
        this.timezoneId = ZoneId.of(timezoneId);
        DateTimeFormatter formatter = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.SHORT)
                .withLocale(Locale.US)
                .withZone(this.timezoneId);
        // Update timestamp accordingly.
        this.timestamp = formatter.format(Instant.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favorite favorite = (Favorite) o;
        return Objects.equals(favoriteId, favorite.favoriteId) &&
                Objects.equals(product, favorite.product) &&
                Objects.equals(user, favorite.user) &&
                Objects.equals(timestamp, favorite.timestamp) &&
                Objects.equals(timezoneId, favorite.timezoneId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(favoriteId, product, user, timestamp, timezoneId);
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "favoriteId=" + favoriteId +
                ", product=" + product +
                ", user=" + user +
                ", timestamp='" + timestamp + '\'' +
                ", timezoneId=" + timezoneId +
                '}';
    }
}

