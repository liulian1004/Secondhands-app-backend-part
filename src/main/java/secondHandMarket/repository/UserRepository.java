/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Xin Li
 * Email: helloimlixin@gmail.com
 */

package secondHandMarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import secondHandMarket.model.User;

/**
 * This interface extends Spring Data JPA's <code>JpaRepository</code>,
 * which specifies the domain type as <code>Product</code> and the id type as
 * <code>Long</code> and supports a bunch of functionalities including:
 * 1. Creation of new instances.
 * 2. Updating existing data store.
 * 3. Deletion.
 * 4. Searching (one, all, by various kinds of property specifications)
 * This repository solution makes it possible to avoid getting stuck into
 * the db operations and helps people to focus more on the domain-specific
 * issues.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
