/*
 * Copyright (c) 2020. All rights reserved.
 * Author: Xin Li
 * Email: helloimlixin@gmail.com
 */

package secondHandMarket;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import secondHandMarket.model.*;
import secondHandMarket.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@RestController
public class SecondHandMarketApplication {

	private static final Logger log = LoggerFactory.getLogger(SecondHandMarketApplication.class);
	private int numOfCategories = 7;
	private int numOfDummyUsers = 10;
	private int dummyPostLimit = 10;

	@GetMapping("/")
	public String index() {
		return "Spring is here!";
	}

	public static void main(String[] args) {
		SpringApplication.run(SecondHandMarketApplication.class, args);
	}

	@Bean
	CommandLineRunner initializeDatabase(CategoryService categoryService,
										 UserService userService,
										 ProductService productService,
										 ProductStatusService productStatusService,
										 FavoriteService favoriteService,
										 ProductOrderService productOrderService) {
		return args -> {
			for (int i = 0; i < numOfCategories; i++) {
				categoryService.save(new Category(Category.getCategoryNameByIdx(i + 1)));
			}

			productStatusService.save(true);
			productStatusService.save(false);

			for (int i = 1; i <= numOfDummyUsers; i++) {
				String userId = "user" + i;
				User user = new User(userId);
				if ((int) (Math.random() + 0.7) == 1) {
					user.setEmail(String.format("%s@gmail.com", userId));
				}
				if ((int) (Math.random() + 0.5) == 1) {
					user.setName(String.format("helloim%s", userId));
				}
				if ((int) (Math.random() + 0.5) == 1) {
					user.setRating(String.format("%.2f", 5 * Math.random()));
				}
				if ((int) (Math.random() + 0.2) == 1) {
					user.setPhotoUrl("Pictures of this awesome dude.");
				}
				if ((int) (Math.random() + 0.2) == 1) {
					user.setPhotoUrl("Pictures of this awesome girl.");
				}

				userService.save(user);
			}

			for (int i = 1; i <= numOfDummyUsers; i++) {
				String userUID = "user" + i;
				User user = userService.getUserById(userUID);
				if ((int) (Math.random() + 0.7) == 1) {
					createDummyPosts(user, categoryService, productService);
				}
			}

			List<Product> products = productService.getAllProducts();
			List<User> users = userService.getAllUsers();
			Random random = new Random();
			for (Product product : products) {
				if ((int) (Math.random() + 0.9) == 1) {
					Favorite favorite = new Favorite(product, users.get(random.nextInt(numOfDummyUsers)));
					favoriteService.save(favorite);
				}
			}

			// Create Dummy Orders
			for (Product product : products){
				if((int) (Math.random() + 0.3) == 1) {
					User user = users.get(random.nextInt(numOfDummyUsers));
					if(!product.getUser().getUserUID().equals(user.getUserUID())){
						ProductOrder productOrder = new ProductOrder(product, user);
						productOrder.setConfirmed(true);
						productOrderService.save(productOrder);
					}
				}
			}

			log.info("Categories are preloaded.");
			log.info("Dummy users loaded.");
			log.info("Dummy posts loaded.");
			log.info("Product statuses updated.");
			log.info("Dummy favorite items loaded.");
			log.info("Dummy orders loaded.");
		};
	}

	private int getRandomCategoryCnt() {
		Random random = new Random();

		return random.nextInt(numOfCategories) + 1;
	}

	private void createDummyPosts(User user, CategoryService categoryService,
								  ProductService productService) {
		// Create posts.
		String[] states = new String[]{
				"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "LA",
				"KY", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND",
				"OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY", "AS",
				"DC", "FM", "GU", "MH", "MP", "PW", "PR", "VI"
		};
		String[] cities = new String[] {
				"Tucson", "Boston", "Los Angeles", "San Francisco", "Washington D.C.", "Seattle", "Brookline",
				"New York", "New Brunswick", "Cambridge", "St. Louis", "Orlando", "Miami", "Austin", "Houston"
		};
		int numOfStates = states.length;
		int numOfCities = cities.length;
		String descriptionTemplate = "This is a%s product, which is awesome.";

		Random random = new Random();

		for (int i = 0; i < random.nextInt(dummyPostLimit -  1) + 1; i++) {
			int stateIdx = new RandomDataGenerator().nextInt(0, numOfStates - 1);
			int cityIdx = new RandomDataGenerator().nextInt(0, numOfCities - 1);
			Product product = new Product(user,
					String.format("Awesome product-%d of user %s.", i + 1, user.getUserUID()),
					states[stateIdx]);
			StringBuffer sb = new StringBuffer();
			if ((int) (Math.random() + 0.7) == 1) {
				Category category = categoryService.getCategoryById(getRandomCategoryCnt());
				product.setCategory(category.getCategoryName());
				sb.append(" ").append(category.getCategoryName());
			}
			product.setPrice(String.valueOf(
					new RandomDataGenerator().nextUniform(0.0, 1000.0)));
			product.setDescription(String.format(descriptionTemplate, sb.toString()));
			if ((int) (Math.random() + 0.5) == 1) {
				product.setCity(cities[cityIdx]);
			} else {
				double lat = new RandomDataGenerator().nextUniform(37.13, 37.63);
				double lon = new RandomDataGenerator().nextUniform(-122.33, -121.83);
				product.setLocation(lat, lon, "San Francisco");
				product.setState("CA");
			}

			if ((int) (Math.random() + 0.8) == 1) {
				product.setAvailability(true);
			} else {
				product.setAvailability(false);
			}

			if ((int) (Math.random() + 0.7) == 1) {
				List<String> imageUrls = new ArrayList<>();
				for (int j = 0; j < random.nextInt(10); j++) {
					imageUrls.add(String.format("Image %d of this awesome product.", j));
				}
				product.setImageUrls(String.join("*", imageUrls));
			}

			productService.save(product);
		}
	}
}