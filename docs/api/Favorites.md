# Favorite Service Detailed Explanations

## `GET` /users/user/favorites

[Go back to README.md :smirk:](../../README.md#favorites)

Retrieve all the favorite items specified by the `userUID`, note that here the `userUID` **must** 
match with the `id_token` for better security.

### Endpoint URL

<http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/user/favorites>

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

HTTP GET: <http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/user/favorites?id_token={id_token}>

Using `cURL`:

```
curl http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/user/favorites?id_token={id_token}
```

### Example Response

```json
{
    "status": "OK",
    "num_of_favorites": 6,
    "user_favorites": [
        {
            "product": {
                "productId": 176,
                "user": {
                    "userUID": "user13",
                    "name": "helloimuser13",
                    "email": "user13@gmail.com",
                    "photoUrl": "Pictures of this awesome dude.",
                    "rating": "0.39"
                },
                "productName": "Awesome product-2 of user user13.",
                "category": "Clothing & Shoes",
                "price": "536.32",
                "favorite": true,
                "availability": false,
                "description": "This is a Clothing & Shoes product, which is awesome.",
                "imageUrls": "Image 0 of this awesome product.",
                "timestamp": "10/3/20 9:24 PM",
                "timezoneId": "US/Pacific",
                "lat": 37.6200823575358,
                "lon": -122.32085555376551,
                "state": "CA",
                "city": "San Francisco",
                "gpsenabled": true,
                "location": "Coordinates (Lat, Lon): (37.6200823575358,-122.32085555376551)\nState: CA\nCity: San Francisco"
            },
            "user": {
                "userUID": "user1",
                "name": "N/A",
                "email": "user1@gmail.com",
                "photoUrl": "This shady guy doesn't have a face.",
                "rating": "N/A"
            },
            "timestamp": "10/3/20 9:24 PM",
            "timezoneId": "US/Pacific"
        },
        ...
    ]
}
```

### Response Fields

| Name                 | Type   | Description                                                                                                                                                                                         |
|----------------------|--------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| status               | String | status of the response, default "OK" upon success                                                                                                                                                   |
| num_of_favorites     | Integer | total number of favorite items retrieved                                                                                               |
| user_favorites | Array |  an array of favorite objects matched the given userUID |

[Reference here if you want to know what's inside the product object :smirk:](Products.md#get-productsproductproductid)

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

## `POST` /users/user/favorites

### Introduction

[Go back to README.md :smirk:](../../README.md#favorites)

Create favorite identity in the backend database with given `userUID` and `productId`.

### Endpoint URL

<http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/user/favorites>

### Authentication and Rate Limits

| Name | Description |
|-----------------------|------|
| Authentication Method | Firebase Auth (Currently faked by <br> simple db operations.) |
| Rate Limit            | None |

### Query Parameters

| Name | Type | Description |
|------|------|-------------|
| id_token | String | ID_TOKEN for Firebase authentication, <br> currently just userUID |
| productId | String | primary key for product item stored in the database |

### Example Requests

HTTP POST: <http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/user/favorites?id_token={id_token}&productId={productId}>

Using `cURL`:

```
curl -X POST http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/user/favorites?id_token=user2&productId=289
```

### Example Response

```json
{
    "status": "OK",
    "saved_favorite_item": {
        "product": {
            "productId": 289,
            "user": {
                "userUID": "user46",
                "name": "helloimuser46",
                "email": "user46@gmail.com",
                "photoUrl": "This shady guy doesn't have a face.",
                "rating": "N/A"
            },
            "productName": "Awesome product-5 of user user46.",
            "category": "Electronics",
            "price": "994.84",
            "favorite": false,
            "availability": false,
            "description": "This is a Electronics product, which is awesome.",
            "imageUrls": null,
            "timestamp": "10/4/20 3:03 PM",
            "timezoneId": "US/Pacific",
            "lat": 37.52952488866064,
            "lon": -121.91121853464115,
            "state": "CA",
            "city": "San Francisco",
            "gpsenabled": true,
            "location": "Coordinates (Lat, Lon): (37.52952488866064,-121.91121853464115)\nState: CA\nCity: San Francisco"
        },
        "user": {
            "userUID": "user2",
            "name": "helloimuser2",
            "email": null,
            "photoUrl": "Pictures of this awesome dude.",
            "rating": "N/A"
        },
        "timestamp": "10/4/20 3:13 PM",
        "timezoneId": "US/Pacific"
    }
}
```

### Response Fields

| Name                 | Type   | Description                                                                                                                                                                                         |
|----------------------|--------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| status               | String | status of the response, default "OK" upon success                                                                                                                                                   |
| saved_favorite_item | Object | favorite object representing the newly created favorite entry |
| product | Object | product liked by user |
| user | Object | user who favorites the product |
| timestamp | String | automatically generated timestamp |
| timezoneId | String | timezone used, default "US/Pacific" |

[Reference here if you want to know what's inside the `product` object :smirk:](Products.md#get-productsproductproductid)

[Reference here if you want to know what's inside the `user` object :smirk:](Users.md#get-usersuseruseruid)

## `DELETE` /users/user/favorites

### Introduction

[Go back to README.md :smirk:](../../README.md#favorites)

Delete favorite identity in the backend database with given `userUID` and `productId`.

### Endpoint URL

<http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/user/favorites>

### Authentication and Rate Limits

| Name | Description |
|-----------------------|------|
| Authentication Method | Firebase Auth (Currently faked by <br> simple db operations.) |
| Rate Limit            | None |

### Query Parameters

| Name | Type | Description |
|------|------|-------------|
| id_token | String | ID_TOKEN for Firebase authentication, <br> currently just userUID |
| productId | String | primary key for product item stored in the database |

### Example Requests

HTTP DELETE: <http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/user/favorites?id_token={id_token}&productId={productId}>

Using `cURL`:

```
curl -X DELETE http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/user/favorites?id_token=user2&productId=126
```

### Example Response

```json
{
    "status": "OK"
}
```

### Response Fields

| Name                 | Type   | Description                                                                                                                                                                                         |
|----------------------|--------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| status               | String | status of the response, default "OK" upon success                                                                                                                                                   |