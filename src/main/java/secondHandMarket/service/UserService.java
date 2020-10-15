/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Xin Li
 * Email: helloimlixin@gmail.com
 */

package secondHandMarket.service;

import org.springframework.validation.annotation.Validated;
import secondHandMarket.model.User;

import java.util.List;

@Validated
public interface UserService {
    List<User> getAllUsers();
    User getUserById(String id);
    User save(User user);
    User update(User user);
    void delete(User user);
}
