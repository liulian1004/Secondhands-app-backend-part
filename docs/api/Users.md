# User Service Detailed Explanations

## `GET` /users

### Introduction

[Go back to README.md :smirk:](../../README.md#user-service)

Retrieve all the users' information stored in the database, this is only accessible for registered users with ID_TOKEN 
(currently using userUID).

### Endpoint URL

<http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users>

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

HTTP GET: <http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users?id_token=user1>

Using `cURL`:

```
curl http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users?id_token=user1
```

### Example Response

```json
"status": "OK",
    "summary": "100 users rendered.",
    "users": [
        {
            "userUID": "user1",
            "name": "N/A",
            "email": "user1@gmail.com",
            "photoUrl": "Pictures of this awesome girl.",
            "rating": "4.37"
        },
        {
            "userUID": "user2",
            "name": "helloimuser2",
            "email": "user2@gmail.com",
            "photoUrl": "Pictures of this awesome dude.",
            "rating": "4.92"
        },
        {
            "userUID": "user3",
            "name": "N/A",
            "email": null,
            "photoUrl": "Pictures of this awesome dude.",
            "rating": "1.87"
        },
        {
            "userUID": "user4",
            "name": "helloimuser4",
            "email": "user4@gmail.com",
            "photoUrl": "This shady guy doesn't have a face.",
            "rating": "N/A"
        },
        {
            "userUID": "user5",
            "name": "helloimuser5",
            "email": "user5@gmail.com",
            "photoUrl": "This shady guy doesn't have a face.",
            "rating": "N/A"
        },
        {
            "userUID": "user6",
            "name": "helloimuser6",
            "email": "user6@gmail.com",
            "photoUrl": "This shady guy doesn't have a face.",
            "rating": "N/A"
        },
        {
            "userUID": "user7",
            "name": "helloimuser7",
            "email": "user7@gmail.com",
            "photoUrl": "Pictures of this awesome girl.",
            "rating": "3.84"
        },
        {
            "userUID": "user8",
            "name": "N/A",
            "email": "user8@gmail.com",
            "photoUrl": "This shady guy doesn't have a face.",
            "rating": "3.60"
        },
        {
            "userUID": "user9",
            "name": "N/A",
            "email": "user9@gmail.com",
            "photoUrl": "This shady guy doesn't have a face.",
            "rating": "3.57"
        },
        {
            "userUID": "user10",
            "name": "N/A",
            "email": "user10@gmail.com",
            "photoUrl": "This shady guy doesn't have a face.",
            "rating": "N/A"
        },
        {
            "userUID": "user11",
            "name": "helloimuser11",
            "email": "user11@gmail.com",
            "photoUrl": "Pictures of this awesome girl.",
            "rating": "N/A"
        },
        {
            "userUID": "user12",
            "name": "N/A",
            "email": "user12@gmail.com",
            "photoUrl": "Pictures of this awesome girl.",
            "rating": "3.05"
        },
        {
            "userUID": "user13",
            "name": "N/A",
            "email": "user13@gmail.com",
            "photoUrl": "Pictures of this awesome dude.",
            "rating": "1.44"
        },
        {
            "userUID": "user14",
            "name": "N/A",
            "email": null,
            "photoUrl": "Pictures of this awesome girl.",
            "rating": "N/A"
        },
        {
            "userUID": "user15",
            "name": "N/A",
            "email": null,
            "photoUrl": "This shady guy doesn't have a face.",
            "rating": "N/A"
        },
        {
            "userUID": "user16",
            "name": "N/A",
            "email": null,
            "photoUrl": "Pictures of this awesome dude.",
            "rating": "N/A"
        },
        ...
          ]
}
```

### Response Fields

| Name                 | Type   | Description                                                                                                                                                                                         |
|----------------------|--------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| status               | String | status of the response, default "OK" upon success                                                                                                                                                   |
| summary              | String | total number of users retrieved                                                                                               |
| users | Array | all users in the database |

[Reference here if you want to know what's inside the user object :smirk:](#get-usersuseruseruid)

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

## `GET` /users/user

### Introduction

[Go back to README.md :smirk:](../../README.md#user-service)

Retrieve the user item with given `id_token`, accessible for registered users only.

### Endpoint URL

<http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/user>

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

HTTP GET: <http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/user?id_token={id_token}>

Using `cURL`:

```
curl http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/user?id_token={id_token}
```

### Example Response

```json
{
    "status": "OK",
    "user": {
        "userUID": "user5",
        "name": "helloimuser5",
        "email": "user5@gmail.com",
        "photoUrl": "This shady guy doesn't have a face.",
        "rating": "N/A"
    }
}
```

### Response Fields

| Name         | Type    | Description                                                                                                                                                                                                                                |
|--------------|---------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| status       | String  | status of the response, default "OK" upon success                                                                                                                                                                                          |
| user      | Object  | user object with given `userUID`                                                                                                                                                                                                      |
| userUID    | Long    | userUID corresponding to the user item inside the database                                                                                                                                                                          |
| name         | String  | name of the user, default "N/A"                                                                                                                                                                                                            |
| email        | String  | email of the user, default "N/A"                                                                                                                                                                                                           |
| photoUrl     | String  | url string representing the address of the cloud storage <br>for the user profile photo, default "N/A"                                                                                                                                     |
| rating       | String  | string containing the double value of the rating of the <br>seller, default "N/A"                                                                                                                                                          |

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

## `POST` /users

### Introduction

[Go back to README.md :smirk:](../../README.md#user-service)

Create user identity in the backend database with given `id_token` (user information fetched from Firebase).

### Endpoint URL

<http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users>

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

HTTP GET: <http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users?id_token=user10001>

Using `cURL`:

```
curl http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users?id_token=user10001
```

### Example Response

```json
{
    "status": "OK",
    "user": {
        "userUID": "user10001",
        "name": "N/A",
        "email": null,
        "photoUrl": "This shady guy doesn't have a face.",
        "rating": "N/A"
    }
}
```

### Response Fields

| Name                 | Type   | Description                                                                                                                                                                                         |
|----------------------|--------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| status               | String | status of the response, default "OK" upon success                                                                                                                                                   |
| user | Object | user object representing the newly created user entry |

[Reference here if you want to know what's inside the user object :smirk:](#get-usersuser)

### Errors

#### HttpStatus.CONFLICT

##### Introduction

Happens when user entry already exists in the database.

##### Example Response

```json
{
    "status": "CONFLICT",
    "description": "User entry already exists in the database, try using a new userUID."
}
```

## `GET` /users/user/products

[Go back to README.md :smirk:](../../README.md#user-service)

### Introduction

Retrieve products that belong the given user, registered user access only.


### Endpoint URL

<http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/user/products>

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

HTTP GET: <http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/user/products?id_token={id_token}>

Using `cURL`:

```
curl http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/user/products?id_token=user2
```

### Example Response

```json
{
    "status": "OK",
    "summary": "3 products rendered.",
    "products_of_user": [
        {
            "productId": 116,
            "user": {
                "userUID": "user3",
                "name": "helloimuser3",
                "email": "user3@gmail.com",
                "photoUrl": "Pictures of this awesome girl.",
                "rating": "N/A"
            },
            "productName": "Awesome product-1 of user user3.",
            "category": "All Categories",
            "price": "558.76",
            "favorite": false,
            "availability": false,
            "description": "This is a product, which is awesome.",
            "imageUrls": "Image 0 of this awesome product.*Image 1 of this awesome product.*Image 2 of this awesome product.",
            "timestamp": "10/6/20 6:38 PM",
            "timezoneId": "US/Pacific",
            "lat": 0.0,
            "lon": 0.0,
            "state": "MP",
            "city": "Brookline",
            "gpsenabled": false,
            "location": "Coordinates (Lat, Lon): N/A\nState: MP\nCity: Brookline"
        },
        {
            "productId": 117,
            "user": {
                "userUID": "user3",
                "name": "helloimuser3",
                "email": "user3@gmail.com",
                "photoUrl": "Pictures of this awesome girl.",
                "rating": "N/A"
            },
            "productName": "Awesome product-2 of user user3.",
            "category": "Furniture",
            "price": "167.15",
            "favorite": false,
            "availability": false,
            "description": "This is a Furniture product, which is awesome.",
            "imageUrls": "Image 0 of this awesome product.*Image 1 of this awesome product.",
            "timestamp": "10/6/20 6:38 PM",
            "timezoneId": "US/Pacific",
            "lat": 0.0,
            "lon": 0.0,
            "state": "MI",
            "city": "St. Louis",
            "gpsenabled": false,
            "location": "Coordinates (Lat, Lon): N/A\nState: MI\nCity: St. Louis"
        },
        {
            "productId": 118,
            "user": {
                "userUID": "user3",
                "name": "helloimuser3",
                "email": "user3@gmail.com",
                "photoUrl": "Pictures of this awesome girl.",
                "rating": "N/A"
            },
            "productName": "Awesome product-3 of user user3.",
            "category": "Baby & Kids",
            "price": "127.11",
            "favorite": false,
            "availability": false,
            "description": "This is a Baby & Kids product, which is awesome.",
            "imageUrls": "Image 0 of this awesome product.*Image 1 of this awesome product.*Image 2 of this awesome product.",
            "timestamp": "10/6/20 6:38 PM",
            "timezoneId": "US/Pacific",
            "lat": 0.0,
            "lon": 0.0,
            "state": "HI",
            "city": "San Francisco",
            "gpsenabled": false,
            "location": "Coordinates (Lat, Lon): N/A\nState: HI\nCity: San Francisco"
        }
    ]
}
```

### Response Fields

| Name                 | Type   | Description                                                                                                                                                                                         |
|----------------------|--------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| status               | String | status of the response, default "OK" upon success                                                                                                                                                   |
| summary              | String | summary of how many products rendered
| products_of_user | Array | array of product objects belong to the user |

[Reference here if you want to know what's inside the product object :smirk:](Products.md#get-productsproductproductid)
