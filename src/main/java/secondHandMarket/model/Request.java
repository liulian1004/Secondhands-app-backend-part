package secondHandMarket.model;

import javax.persistence.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long requestId;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    private String status = "Pending";
    private String timestamp;
    private ZoneId timezoneId = ZoneId.of("US/Pacific"); // default pacific time

    /**
     * Empty constructor for Entity table creation
     */
    public Request() {}

    /**
     * Create request entry with given product and user information, the status is defaulted "Pending", and
     * timestamp is also created automatically.
     * @param product product of the order, which is of class <code>Product</code>
     * @param user customer of the order, which is of class <code>User</code>
     */
    public Request(User user, Product product) {
        this.user = user;
        this.product = product;
        this.status = "Pending";
        DateTimeFormatter formatter = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.SHORT)
                .withLocale(Locale.US)
                .withZone(timezoneId);
        this.timestamp = formatter.format(Instant.now());
    }

    public Long getRequestId() {
        return requestId;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public Product getProduct() {
        return product;
    }


    public void setProduct(Product product) {
        this.product = product;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
