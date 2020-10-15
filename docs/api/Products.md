# Product Service Detailed Explanations

## `GET` /index

[Go back to README.md :smirk:](../../README.md#products)

### Introduction

Retrieve a list of products ordered by the following seven categories, while each list can contain a maximum of
10 products:

1. Clothing & Shoes
2. Beauty & Health
3. Electronics
4. Household
5. Baby & Kids
6. Car
7. Furniture

### Endpoint URL

<http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/index>

### Authentication and Rate Limits

| Name | Description |
|-----------------------|------|
| Authentication Method | None |
| Rate Limit            | None |

### Query Parameters

| Name | Type | Description |
|------|------|-------------|
| None | None | None        |

### Example Requests

HTTP GET: <http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/index>

Using `cURL`:

```
curl http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/index
```

### Example Response

```json
{
    "status": "OK",
    "display_limit": "10 products per category",
    "products_by_category": {
        "clothing_and_shoes": [
            {
                "productId": 111,
                "user": {
                    "userUID": "user1",
                    "name": "N/A",
                    "email": "user1@gmail.com",
                    "photoUrl": "Pictures of this awesome dude.",
                    "rating": "4.07"
                },
                "productName": "Awesome product-2 of user user1.",
                "category": "Clothing & Shoes",
                "price": "256.01",
                "favorite": false,
                "availability": false,
                "description": "This is a Clothing & Shoes product, which is awesome.",
                "imageUrls": "Image 0 of this awesome product., Image 1 of this awesome product.",
                "timestamp": "10/3/20 4:26 PM",
                "timezoneId": "US/Pacific",
                "lat": 0.0,
                "lon": 0.0,
                "state": "KY",
                "city": "Boston",
                "gpsenabled": false,
                "location": "Coordinates (Lat, Lon): N/A\nState: KY\nCity: Boston"
            },
            {
                "productId": 120,
                "user": {
                    "userUID": "user3",
                    "name": "helloimuser3",
                    "email": "user3@gmail.com",
                    "photoUrl": "This shady guy doesn't have a face.",
                    "rating": "N/A"
                },
                "productName": "Awesome product-1 of user user3.",
                "category": "Clothing & Shoes",
                "price": "850.26",
                "favorite": false,
                "availability": false,
                "description": "This is a Clothing & Shoes product, which is awesome.",
                "imageUrls": null,
                "timestamp": "10/3/20 4:26 PM",
                "timezoneId": "US/Pacific",
                "lat": 37.182582420842344,
                "lon": -121.87407091694641,
                "state": "CA",
                "city": "San Francisco",
                "gpsenabled": true,
                "location": "Coordinates (Lat, Lon): (37.182582420842344,-121.87407091694641)\nState: CA\nCity: San Francisco"
            },
            ...
        ],
        "beauty_and_health": ...
        "electronics": ...
        "household": ...
        "baby_and_kids": ...
        "car": ...
        "furniture": ...
    }
}
```

### Response Fields

| Name                 | Type   | Description                                                                                                                                                                                         |
|----------------------|--------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| status               | String | status of the response, default "OK" upon success                                                                                                                                                   |
| display_limit        | String | short description indicating maximum number of products <br>per category, which is 10 by default here                                                                                               |
| products_by_category | Object | Contains a list of products ordered by 7 different<br>categories per specified, each category has an array<br>containing a limit number of products rendered defined<br>by the display limit above, |

[Reference here if you want to know what's inside the product object :smirk:](#get-productsproductproductid)

## `GET` /products

### Introduction

[Go back to README.md :smirk:](../../README.md#products)

Retrieve all the products stored in the database, this is only accessible for registered users with ID_TOKEN 
(currently using userUID).

### Endpoint URL

<http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/products>

### Authentication and Rate Limits

| Name | Description |
|-----------------------|------|
| Authentication Method | Firebase Auth (Currently faked by <br> simple db operations.) |
| Rate Limit            | None |

### Query Parameters

| Name | Type | Description |
|------|------|-------------|
| id_token | String | ID_TOKEN for Firebase authentication, <br> currently just userUID |
| page | Integer | page number for paginated products, default 1 |
| page_size | Integer | number of products for a single page to render, default 10 |

### Example Requests

HTTP GET: <http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/products?id_token={id_token}&page={page}&page_size={page_size}>

Using `cURL`:

```
curl http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/products?id_token={id_token}&page={page}&page_size={page_size}
```

### Example Response

```json
{
    "status": "OK",
        "summary": "459 products found",
        "page_size": 10,
        "page": 1,
        "products": [
            {
                "productId": 110,
                "user": {
                    "userUID": "user1",
                    "name": "N/A",
                    "email": "user1@gmail.com",
                    "photoUrl": "This shady guy doesn't have a face.",
                    "rating": "N/A"
                },
                "productName": "Awesome product-1 of user user1.",
                "category": "Beauty & Health",
                "price": "798.97",
                "favorite": false,
                "availability": false,
                "description": "This is a Beauty & Health product, which is awesome.",
                "imageUrls": "Image 0 of this awesome product., Image 1 of this awesome product., Image 2 of this awesome product., Image 3 of this awesome product.",
                "timestamp": "10/3/20 4:47 PM",
                "timezoneId": "US/Pacific",
                "lat": 0.0,
                "lon": 0.0,
                "state": "OR",
                "city": "New Brunswick",
                "location": "Coordinates (Lat, Lon): N/A\nState: OR\nCity: New Brunswick",
                "gpsenabled": false
            },
            {
                "productId": 111,
                "user": {
                    "userUID": "user1",
                    "name": "N/A",
                    "email": "user1@gmail.com",
                    "photoUrl": "This shady guy doesn't have a face.",
                    "rating": "N/A"
                },
                "productName": "Awesome product-2 of user user1.",
                "category": "Beauty & Health",
                "price": "157.38",
                "favorite": false,
                "availability": false,
                "description": "This is a Beauty & Health product, which is awesome.",
                "imageUrls": "",
                "timestamp": "10/3/20 4:47 PM",
                "timezoneId": "US/Pacific",
                "lat": 0.0,
                "lon": 0.0,
                "state": "CO",
                "city": "San Francisco",
                "location": "Coordinates (Lat, Lon): N/A\nState: CO\nCity: San Francisco",
                "gpsenabled": false
            },
            {
                "productId": 112,
                "user": {
                    "userUID": "user1",
                    "name": "N/A",
                    "email": "user1@gmail.com",
                    "photoUrl": "This shady guy doesn't have a face.",
                    "rating": "N/A"
                },
                "productName": "Awesome product-3 of user user1.",
                "category": "Clothing & Shoes",
                "price": "65.58",
                "favorite": false,
                "availability": false,
                "description": "This is a Clothing & Shoes product, which is awesome.",
                "imageUrls": null,
                "timestamp": "10/3/20 4:47 PM",
                "timezoneId": "US/Pacific",
                "lat": 0.0,
                "lon": 0.0,
                "state": "KS",
                "city": "New Brunswick",
                "location": "Coordinates (Lat, Lon): N/A\nState: KS\nCity: New Brunswick",
                "gpsenabled": false
            },
        ...
    ]
}
```

### Response Fields

| Name                 | Type   | Description                                                                                                                                                                                         |
|----------------------|--------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| status               | String | status of the response, default "OK" upon success                                                                                                                                                   |
| summary              | String | total number of products retrieved                                                                                               |
| page_size | Integer |  specified page size |
| page | Integer | page No. |
| products | Array | all products in the database |

[Reference here if you want to know what's inside the product object :smirk:](#get-productsproductproductid)

### Errors

#### HttpStatus.UNAUTHORIZED

##### Introduction

Happens when `id_token` is empty or doesn't match any existing user in the database.

##### Example Response

```json
{
    "status": "User not authorized",
    "description": "Please check if the userUID corresponds to the given id_token exists in the backend database."
}
```

## `GET` /products/nearby

### Introduction

[Go back to README.md :smirk:](../../README.md#products)

Retrieve at most top 50 products closest (+/- 0.25 in lat, lon respectively) to the user, ordered by distance 
(Manhattan Distance) to the given GPS coordinates, this is only accessible for registered users with ID_TOKEN
(currently using userUID) and when the products have GPS enabled.

### Endpoint URL

<http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/products/nearby>

### Authentication and Rate Limits

| Name | Description |
|-----------------------|------|
| Authentication Method | Firebase Auth (Currently faked by <br> simple db operations.) |
| Rate Limit            | None |

### Query Parameters

| Name | Type | Description |
|------|------|-------------|
| id_token | String | ID_TOKEN for Firebase authentication, <br> currently just userUID |
| lat | Double | target latitude, default 37.38 (Google Mountain View Office) |
| lon | Double | target longitutde, default -122.08 (Google Mountain View Office) |

### Example Requests

HTTP GET: <http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/products/nearby?id_token={id_token}&page={page}&page_size={page_size}>

Using `cURL`:

```
curl http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/products/nearby?id_token={id_token}&page={page}&page_size={page_size}
```

### Example Response

```json
{
    "status": "OK",
    "coordinates": "lat: 37.38, lon: -122.08",
    "available_products_nearby": [
        {
            "productId": 124,
            "user": {
                "userUID": "user10",
                "name": "helloimuser10",
                "email": "user10@gmail.com",
                "photoUrl": "This shady guy doesn't have a face.",
                "rating": "4.45"
            },
            "productName": "Awesome product-3 of user user10.",
            "category": "Car",
            "price": "22.10",
            "favorite": false,
            "availability": true,
            "description": "This is a Car product, which is awesome.",
            "imageUrls": "Image 0 of this awesome product.",
            "timestamp": "10/3/20 4:47 PM",
            "timezoneId": "US/Pacific",
            "lat": 37.6259150931637,
            "lon": -122.29232202521898,
            "state": "CA",
            "city": "San Francisco",
            "location": "Coordinates (Lat, Lon): (37.6259150931637,-122.29232202521898)\nState: CA\nCity: San Francisco",
            "gpsenabled": true
        },
        {
            "productId": 134,
            "user": {
                "userUID": "user11",
                "name": "N/A",
                "email": null,
                "photoUrl": "This shady guy doesn't have a face.",
                "rating": "N/A"
            },
            "productName": "Awesome product-6 of user user11.",
            "category": "Beauty & Health",
            "price": "346.90",
            "favorite": false,
            "availability": true,
            "description": "This is a Beauty & Health product, which is awesome.",
            "imageUrls": null,
            "timestamp": "10/3/20 4:47 PM",
            "timezoneId": "US/Pacific",
            "lat": 37.339335330677,
            "lon": -122.26592382578791,
            "state": "CA",
            "city": "San Francisco",
            "location": "Coordinates (Lat, Lon): (37.339335330677,-122.26592382578791)\nState: CA\nCity: San Francisco",
            "gpsenabled": true
        },
        {
            "productId": 148,
            "user": {
                "userUID": "user13",
                "name": "helloimuser13",
                "email": null,
                "photoUrl": "This shady guy doesn't have a face.",
                "rating": "3.63"
            },
            "productName": "Awesome product-7 of user user13.",
            "category": null,
            "price": "969.37",
            "favorite": false,
            "availability": true,
            "description": "This is a product, which is awesome.",
            "imageUrls": "Image 0 of this awesome product., Image 1 of this awesome product., Image 2 of this awesome product., Image 3 of this awesome product.",
            "timestamp": "10/3/20 4:47 PM",
            "timezoneId": "US/Pacific",
            "lat": 37.60127209690583,
            "lon": -122.20002541842096,
            "state": "CA",
            "city": "San Francisco",
            "location": "Coordinates (Lat, Lon): (37.60127209690583,-122.20002541842096)\nState: CA\nCity: San Francisco",
            "gpsenabled": true
        },
        {
            "productId": 153,
            "user": {
                "userUID": "user13",
                "name": "helloimuser13",
                "email": null,
                "photoUrl": "This shady guy doesn't have a face.",
                "rating": "3.63"
            },
            "productName": "Awesome product-12 of user user13.",
            "category": "Beauty & Health",
            "price": "307.25",
            "favorite": false,
            "availability": true,
            "description": "This is a Beauty & Health product, which is awesome.",
            "imageUrls": "Image 0 of this awesome product., Image 1 of this awesome product., Image 2 of this awesome product.",
            "timestamp": "10/3/20 4:47 PM",
            "timezoneId": "US/Pacific",
            "lat": 37.27215523094822,
            "lon": -121.89512006942044,
            "state": "CA",
            "city": "San Francisco",
            "location": "Coordinates (Lat, Lon): (37.27215523094822,-121.89512006942044)\nState: CA\nCity: San Francisco",
            "gpsenabled": true
        },
        ...
    ]
}
```

### Response Fields

| Name                 | Type   | Description                                                                                                                                                                                         |
|----------------------|--------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| status               | String | status of the response, default "OK" upon success                                                                                                                                                   |
| coordinates              | String | GPS coordinates in format "lat: %.2f, lon %.2f" |                                                                                               |
| available_products_nearby | Array | all available products ranked by the distance to the given coordinates |

[Reference here if you want to know what's inside the product object :smirk:](#get-productsproductproductid)

### Errors

#### HttpStatus.UNAUTHORIZED

##### Introduction

Happens when `id_token` is empty or doesn't match any existing user in the database.

##### Example Response

```json
{
    "status": "User not authorized",
    "description": "Please check if the userUID corresponds to the given id_token exists in the backend database."
}
```

## `GET` /products/product/{productId}

### Introduction

[Go back to README.md :smirk:](../../README.md#products)

Retrieve the product item with given `productId`, accessible for registered users only.

### Endpoint URL

<http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/products/product/{productId}>

### Authentication and Rate Limits

| Name | Description |
|-----------------------|------|
| Authentication Method | Firebase Auth (Currently faked by <br> simple db operations.) |
| Rate Limit            | None |

### Query Parameters

| Name | Type | Description |
|------|------|-------------|
| id_token | String | ID_TOKEN for Firebase authentication, <br> currently just userUID |

### Example Requests

HTTP GET: <http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/products/product/{productId}?id_token={id_token}>

Using `cURL`:

```
curl http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/products/product/{productId}?id_token={id_token}
```

### Example Response

```json
{
    "status": "OK",
    "product": {
        "productId": 110,
        "user": {
            "userUID": "user1",
            "name": "N/A",
            "email": "user1@gmail.com",
            "photoUrl": "This shady guy doesn't have a face.",
            "rating": "N/A"
        },
        "productName": "Awesome product-1 of user user1.",
        "category": "Beauty & Health",
        "price": "798.97",
        "favorite": true,
        "availability": false,
        "description": "This is a Beauty & Health product, which is awesome.",
        "imageUrls": "Image 0 of this awesome product., Image 1 of this awesome product., Image 2 of this awesome product., Image 3 of this awesome product.",
        "timestamp": "10/3/20 4:47 PM",
        "timezoneId": "US/Pacific",
        "lat": 0.0,
        "lon": 0.0,
        "state": "OR",
        "city": "New Brunswick",
        "location": "Coordinates (Lat, Lon): N/A\nState: OR\nCity: New Brunswick",
        "gpsenabled": false
    }
}
```

### Response Fields

| Name         | Type    | Description                                                                                                                                                                                                                                |
|--------------|---------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| status       | String  | status of the response, default "OK" upon success                                                                                                                                                                                          |
| product      | Object  | product object with given `productId`                                                                                                                                                                                                      |
| productId    | Long    | primary key corresponding to the product item inside the database                                                                                                                                                                          |
| user         | Object  | user object containing the seller information for the product                                                                                                                                                                              |
| userUID      | String  | userUID corresponding the one in firebase, here inside the product <br>object the user is the seller                                                                                                                                       |
| name         | String  | name of the user, default "N/A"                                                                                                                                                                                                            |
| email        | String  | email of the user, default "N/A"                                                                                                                                                                                                           |
| photoUrl     | String  | url string representing the address of the cloud storage <br>for the user profile photo, default "N/A"                                                                                                                                     |
| rating       | String  | string containing the double value of the rating of the <br>seller, default "N/A"                                                                                                                                                          |
| productName  | String  | unique produce name assigned to each product item                                                                                                                                                                                          |
| category     | String  | category name, default "All Categories".<br>There are currently 7 options of product categories, which are,<br><br>1. Clothing & Shoes<br>2. Beauty & Health<br>3. Electronics<br>4. Household<br>5. Baby & Kids<br>6. Car<br>7. Furniture |
| price        | String  | string containing the double value of the produce price, <br>default "0.0"                                                                                                                                                                 |
| favorite     | Boolean | boolean value indicating whether current product item is <br>favorited by the **viewer** (user corresponds to the given `id_token`)                                                                                                        |
| availability | Boolean | boolean value indicating the availability status of the product                                                                                                                                                                            |
| description  | String  | product description                                                                                                                                                                                                                        |
| imageUrls    | String  | concatenated string with product image urls                                                                                                                                                                                                |
| timestamp    | String  | string containing the product creation timestamp                                                                                                                                                                                           |
| timezoneId   | String  | string containing the zoneId specified for the product                                                                                                                                                                                     |
| lat          | Double  | latitude of the product location                                                                                                                                                                                                           |
| lon          | Double  | longitude of the product location                                                                                                                                                                                                          |
| state        | String  | state of the product location                                                                                                                                                                                                              |
| city         | String  | city of the product location                                                                                                                                                                                                               |
| location     | String  | string containing detailed information regarding the <br>product's location, which contains state, city, GPS <br>coordinates if given in format "Coordinates (Lat,Lon): <br>(%s,%s)\nState: %s\nCity: %s"                                  |
| gpsenabled   | Boolean | boolean field indicating whether the GPS coordinates <br>are provided for the product                                                                                                                                                      |

### Errors

#### HttpStatus.UNAUTHORIZED

##### Introduction

Happens when `id_token` is empty or doesn't match any existing user in the database.

##### Example Response

```json
{
    "status": "User not authorized",
    "description": "Please check if the userUID corresponds to the given id_token exists in the backend database."
}
```

## `GET` /products/search

### Introduction

[Go back to README.md :smirk:](../../README.md#products)

Retrieve products matching the search criteria: keywords, state, and category of 
the following:

1. Clothing & Shoes
2. Beauty & Health
3. Electronics
4. Household
5. Baby & Kids
6. Car
7. Furniture

### Endpoint URL

<http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/products/search>

### Authentication and Rate Limits

| Name | Description |
|-----------------------|------|
| Authentication Method | Firebase Auth (Currently faked by <br> simple db operations.) |
| Rate Limit            | None |

### Query Parameters

| Name | Type | Description |
|------|------|-------------|
| id_token | String | ID_TOKEN for Firebase authentication, <br> currently just userUID |
| keywords | String | comma separated keyword query |
| category | String | string representing the **exact** name of category |
| state | String | query state of the product location |
| page | Integer | page number for paginated products, default 1 |
| page_size | Integer | number of products for a single page to render, default 10 |

### Example Requests

> **NOTE**
> 
> Here for our API Endpoint a URL cannot accept special characters like `&` (ampersand) or ` ` (space).
Hence, we need to replace the special character with corresponding percent-styled encoded string, which can
be easily done in Java by simple one-liners: `categoryStr.replace(" ", "%20")`, `categoryStr.replace("&", "%26")`.
> 
> Reference: <https://stackoverflow.com/questions/16622504/escaping-ampersand-in-url>

HTTP GET: <http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/products/search?id_token={id_token}&page={page}&page_size={page_size}&keywords={keyword1,keyword2,keyword3}&category={category}&state={state}>

Using `cURL`:

```
curl http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/products/search?id_token={id_token}&page={page}&page_size={page_size}&keywords={keyword1,keyword2,keyword3}&category={category}&state={state}
```

### Example Response

```json
{
    "status": "OK",
    "summary": "18 products found.",
    "page": 1,
    "page_size": 10,
    "products": [
        {
            "productId": 113,
            "user": {
                "userUID": "user3",
                "name": "N/A",
                "email": null,
                "photoUrl": "This shady guy doesn't have a face.",
                "rating": "3.18"
            },
            "productName": "Awesome product-2 of user user3.",
            "category": "Clothing & Shoes",
            "price": "675.02",
            "favorite": false,
            "availability": false,
            "description": "This is a Clothing & Shoes product, which is awesome.",
            "imageUrls": null,
            "timestamp": "10/3/20 6:37 PM",
            "timezoneId": "US/Pacific",
            "lat": 37.42232795435642,
            "lon": -122.27009009030242,
            "state": "CA",
            "city": "San Francisco",
            "gpsenabled": true,
            "location": "Coordinates (Lat, Lon): (37.42232795435642,-122.27009009030242)\nState: CA\nCity: San Francisco"
        },
        {
            "productId": 138,
            "user": {
                "userUID": "user10",
                "name": "helloimuser10",
                "email": "user10@gmail.com",
                "photoUrl": "This shady guy doesn't have a face.",
                "rating": "N/A"
            },
            "productName": "Awesome product-2 of user user10.",
            "category": "Clothing & Shoes",
            "price": "649.60",
            "favorite": false,
            "availability": false,
            "description": "This is a Clothing & Shoes product, which is awesome.",
            "imageUrls": "Image 0 of this awesome product.",
            "timestamp": "10/3/20 6:38 PM",
            "timezoneId": "US/Pacific",
            "lat": 37.347144822176205,
            "lon": -121.9587992823311,
            "state": "CA",
            "city": "San Francisco",
            "gpsenabled": true,
            "location": "Coordinates (Lat, Lon): (37.347144822176205,-121.9587992823311)\nState: CA\nCity: San Francisco"
        },
        {
            "productId": 142,
            "user": {
                "userUID": "user13",
                "name": "N/A",
                "email": null,
                "photoUrl": "Pictures of this awesome girl.",
                "rating": "N/A"
            },
            "productName": "Awesome product-2 of user user13.",
            "category": "Clothing & Shoes",
            "price": "961.56",
            "favorite": false,
            "availability": false,
            "description": "This is a Clothing & Shoes product, which is awesome.",
            "imageUrls": "Image 0 of this awesome product., Image 1 of this awesome product., Image 2 of this awesome product., Image 3 of this awesome product.",
            "timestamp": "10/3/20 6:38 PM",
            "timezoneId": "US/Pacific",
            "lat": 37.42916773264181,
            "lon": -122.03742791679747,
            "state": "CA",
            "city": "San Francisco",
            "gpsenabled": true,
            "location": "Coordinates (Lat, Lon): (37.42916773264181,-122.03742791679747)\nState: CA\nCity: San Francisco"
        },
        ...
            ]
}
```

### Response Fields

| Name                 | Type   | Description                                                                                                                                                                                         |
|----------------------|--------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| status               | String | status of the response, default "OK" upon success                                                                                                                                                   |
| summary              | String | total number of products retrieved |
| page | Integer | page number for paginated products, default 1 |
| page_size | Integer | number of products for a single page to render, default 10 |
| products | Array | all products matched the search criteria |

[Reference here if you want to know what's inside the product object :smirk:](#get-productsproductproductid)

### Errors

#### HttpStatus.UNAUTHORIZED

##### Introduction

Happens when `id_token` is empty or doesn't match any existing user in the database.

##### Example Response

```json
{
    "status": "User not authorized",
    "description": "Please check if the userUID corresponds to the given id_token exists in the backend database."
}
```

## `POST` /products

### Introduction

[Go back to README.md :smirk:](../../README.md#products)

Create product identity in the backend database with given `id_token` (user information fetched from Firebase) and a
request body of product object containing at least a `user` object, `productName`, and `state`.

### Endpoint URL

<http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/products>

### Authentication and Rate Limits

| Name | Description |
|-----------------------|------|
| Authentication Method | Firebase Auth (Currently faked by <br> simple db operations.) |
| Rate Limit            | None |

### Query Parameters

| Name | Type | Description |
|------|------|-------------|
| id_token | String | ID_TOKEN for Firebase authentication, <br> currently just userUID |

### JSON Body Parameters

| Name         | Type    | Description                                                                                                                                                                                                                                |
|--------------|---------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| user (**required**)        | Object  | user object containing the seller information for the product                                                                                                                                                                |
| userUID      | String  | userUID corresponding the one in firebase, here inside the product <br>object the user is the seller and **must** match the user specified by `id_token`                                                                                   |
| name         | String  | name of the user, default "N/A"                                                                                                                                                                                                            |
| email        | String  | email of the user, default "N/A"                                                                                                                                                                                                           |
| photoUrl     | String  | url string representing the address of the cloud storage <br>for the user profile photo, default "N/A"                                                                                                                                     |
| rating       | String  | string containing the double value of the rating of the <br>seller, default "N/A"                                                                                                                                                          |
| productName (**required**) | String  | **unique** produce name assigned to each product item                                                                                                                                                                            |
| category     | String  | category name, default "All Categories".<br>There are currently 7 options of product categories, which are,<br><br>1. Clothing & Shoes<br>2. Beauty & Health<br>3. Electronics<br>4. Household<br>5. Baby & Kids<br>6. Car<br>7. Furniture |
| price        | String  | string containing the double value of the produce price, <br>default "0.0"                                                                                                                                                                 |
| favorite     | Boolean | boolean value indicating whether current product item is <br>favorited by the **viewer** (user corresponds to the given `id_token`)                                                                                                        |
| availability | Boolean | boolean value indicating the availability status of the product                                                                                                                                                                            |
| description  | String  | product description                                                                                                                                                                                                                        |
| imageUrls    | String  | concatenated string with product image urls                                                                                                                                                                                                |
| timestamp    | String  | string containing the product creation timestamp                                                                                                                                                                                           |
| timezoneId   | String  | string containing the zoneId specified for the product                                                                                                                                                                                     |
| lat          | Double  | latitude of the product location                                                                                                                                                                                                           |
| lon          | Double  | longitude of the product location                                                                                                                                                                                                          |
| state (**required**)       | String  | state of the product location                                                                                                                                                                                                |
| city         | String  | city of the product location                                                                                                                                                                                                               |
| location     | String  | string containing detailed information regarding the <br>product's location, which contains state, city, GPS <br>coordinates if given in format "Coordinates (Lat,Lon): <br>(%s,%s)\nState: %s\nCity: %s"                                  |
| gpsenabled   | Boolean | boolean field indicating whether the GPS coordinates <br>are provided for the product                                                                                                                                                      |

### Example Requests

Using `cURL`:

**With minimal information**

```shell script
curl http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/products?id_token=user5 \
-H "Content-type: application/json" -d \
'{
     "user": {
         "userUID": "user5",
         "name": "N/A",
         "email": "user2@gmail.com",
         "photoUrl": "This shady guy doesn't have a face.",
         "rating": "N/A"
     },
     "productName": "Awesome fancy new product of user user2.",
     "state": "CA"
 }'
```

**With a much more detailed `product` object**
```shell script
curl http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/products?id_token=user1 \
-H "Content-type: application/json" -d \
'{
     "user": {
         "userUID": "user1",
         "name": "N/A",
         "email": "user2@gmail.com",
         "photoUrl": "This shady guy doesn't have a face.",
         "rating": "N/A"
     },
     "productName": "Awesome fancy new product of user user2.",
     "category": "Car",
     "price": "928.69",
     "favorite": false,
     "availability": true,
     "description": "This is a Car product, which is awesome.",
     "imageUrls": "",
     "timestamp": "10/4/20 1:34 PM",
     "timezoneId": "US/Pacific",
     "lat": 0.0,
     "lon": 0.0,
     "state": "CA",
     "city": "New York",
     "gpsenabled": false,
     "location": "Coordinates (Lat, Lon): N/A\nState: CA\nCity: New York"
 }'
```

### Example Response

```json
{
    "status": "OK",
    "product": {
        "productId": 864,
        "user": {
            "userUID": "user2",
            "name": "helloimuser2",
            "email": null,
            "photoUrl": "Pictures of this awesome girl.",
            "rating": "4.04"
        },
        "productName": "Awesome fancy new product of user user2.",
        "category": "Car",
        "price": "928.69",
        "favorite": false,
        "availability": true,
        "description": "This is a Car product, which is awesome.",
        "imageUrls": "",
        "timestamp": "10/4/20 2:36 PM",
        "timezoneId": "US/Pacific",
        "lat": 0.0,
        "lon": 0.0,
        "state": "CA",
        "city": "New York",
        "location": "Coordinates (Lat, Lon): N/A\nState: CA\nCity: New York",
        "gpsenabled": false
    }
}
```

### Response Fields

| Name                 | Type   | Description                                                                                                                                                                                         |
|----------------------|--------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| status               | String | status of the response, default "OK" upon success                                                                                                                                                   |
| product | Object | product object representing the newly created user entry |

[Reference here if you want to know what's inside the product object :smirk:](#get-productsproductproductid)

### Errors

#### HttpStatus.CONFLICT

##### Introduction

Happens when product entry with the same name already exists in the database.

##### Example Response

```json
{
    "status": "CONFLICT",
    "description": "User entry already exists in the database, try using a new userUID."
}
```