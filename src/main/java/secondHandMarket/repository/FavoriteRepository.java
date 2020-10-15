/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Xin Li
 * Email: helloimlixin@gmail.com
 */

package secondHandMarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import secondHandMarket.model.Favorite;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
