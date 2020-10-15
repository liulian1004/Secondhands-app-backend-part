/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Xin Li
 * Email: helloimlixin@gmail.com
 */

package secondHandMarket.service;

import org.springframework.validation.annotation.Validated;
import secondHandMarket.model.Favorite;
import secondHandMarket.model.User;

import java.util.List;

@Validated
public interface FavoriteService {
    List<Favorite> getFavorites();
    Favorite getFavoriteById(long id);
    Favorite save(Favorite favorite);
    void delete(Favorite favorite);
}
