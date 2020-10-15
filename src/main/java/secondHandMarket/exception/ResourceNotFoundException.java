/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Xin Li
 * Email: helloimlixin@gmail.com
 */

package secondHandMarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception resolver class for <em>Resource Not Found</em> exceptions.
 *
 * Reference:
 * @see <a href="https://www.baeldung.com/exception-handling-for-rest-with-spring#3-responsestatusexceptionresolver">Exception Handling for REST with Spring, Section 3.3</a>
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Resource not found.")
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -6551196251440907407L;

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
