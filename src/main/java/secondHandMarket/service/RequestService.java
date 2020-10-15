/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Shuyu Zhou, Yi Ji
 * Email: szhou12@u.rochester.edu, yuki.yi.ji@gmail.com
 */

package secondHandMarket.service;

import secondHandMarket.model.Request;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface RequestService {
    List<Request> getAllRequests();
    Request getRequestById(Long id);
    Request save(Request request);
    void delete(Request request);
}
