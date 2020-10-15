/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Xin Li
 * Email: helloimlixin@gmail.com
 */

package secondHandMarket.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import secondHandMarket.exception.ResourceNotFoundException;
import secondHandMarket.model.Favorite;
import secondHandMarket.repository.FavoriteRepository;

import java.util.List;

@Service
@Transactional
public class FavoriteServiceImpl implements FavoriteService {

    FavoriteRepository favoriteRepository;

    public FavoriteServiceImpl(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    /**
     * Get a list of favorite items sorted by timestamp in DESC order.
     * @see <a href="https://www.baeldung.com/spring-data-sorting">Sorting Query Results with Spring Data on Baeldung</a>
     * @return a list of favorite items from the most recent to the oldest
     */
    @Override
    public List<Favorite> getFavorites() {
        return favoriteRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
    }

    @Override
    public Favorite getFavoriteById(long id) {
        return favoriteRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Favorite item not found:(")
        );
    }

    @Transactional
    @Override
    public Favorite save(Favorite favorite) {
        List<Favorite> existingFavorites = favoriteRepository.findAll();
        for (Favorite existingFavorite: existingFavorites) {
            if (existingFavorite.getUser().getUserUID().equals(favorite.getUser().getUserUID())
                    && existingFavorite.getProduct().getProductName().equals(favorite.getProduct().getProductName())) {
                System.out.println("Favorite item already exists.");
                return null;
            }
        }
        return favoriteRepository.save(favorite);
    }

    @Override
    public void delete(Favorite favorite) {
        favoriteRepository.delete(favorite);
    }
}
