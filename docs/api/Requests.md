# Request Service Detailed Explanations

## `GET` /users/customer/requests

[Go back to README.md :smirk:](../../README.md#request-service)

### Introduction

Retrieve requests made by the customer corresponds to specified `id_token`, registered user access only.


### Endpoint URL

<http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/customer/requests>

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

HTTP GET: <http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/customer/requests?id_token={id_token}>

Using `cURL`:

```
curl http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/customer/requests?id_token=user1
```

### Example Response

```json

    "status": "OK",
    "summary": "6 requests found.",
    "requests": [
        {
            "requestId": 487,
            "user": {
                "userUID": "user1",
                "name": "N/A",
                "email": null,
                "photoUrl": "Pictures of this awesome girl.",
                "rating": "N/A"
            },
            "product": {
                "productId": 180,
                "user": {
                    "userUID": "user25",
                    "name": "helloimuser25",
                    "email": "user25@gmail.com",
                    "photoUrl": "Pictures of this awesome dude.",
                    "rating": "N/A"
                },
                "productName": "Awesome product-7 of user user25.",
                "category": "Household",
                "price": "282.35",
                "favorite": false,
                "availability": true,
                "description": "This is a Household product, which is awesome.",
                "imageUrls": "Image 0 of this awesome product.*Image 1 of this awesome product.",
                "timestamp": "10/6/20 11:17 PM",
                "timezoneId": "US/Pacific",
                "lat": 37.3053585034095,
                "lon": -122.0918974642433,
                "state": "CA",
                "city": "San Francisco",
                "gpsenabled": true,
                "location": "Coordinates (Lat, Lon): (37.3053585034095,-122.0918974642433)\nState: CA\nCity: San Francisco"
            },
            "status": "Pending",
            "timestamp": "10/6/20 11:18 PM",
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
| summary              | String | summary of how many requests rendered
| requests | Array | array of request objects made by the specified customer |
| requestId | Long | primary key of request item in the database |
| user | Object | user object representing of the customer who makes the request |
| product | Object | product object the customer requesting |
| status | String | status string representing the request status, default "pending" |
| timestamp | String | timestamp string representing request creation time with given time zone |
| timezone | String | string representing the timezoneId, default "US/Pacific" |

[Reference here if you want to know what's inside the user object :smirk:](Users.md#get-usersuser)
[Reference here if you want to know what's inside the product object :smirk:](Products.md#get-productsproductproductid)

## `POST` /users/customer/requests

[Go back to README.md :smirk:](../../README.md#request-service)

### Introduction

Create request to buy the product as a customer, registered user access only.


### Endpoint URL

<http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/customer/requests>

### Authentication and Rate Limits

| Name | Description |
|-----------------------|------|
| Authentication Method | Firebase Auth (Currently faked by <br> simple db operations.) |
| Rate Limit            | None |

### Query Parameters

| Name | Type | Description |
|------|------|-------------|
| id_token | String | ID_TOKEN for Firebase authentication, <br> currently just userUID |
| product_id | Long | Long type integer representing primary key of the product item in the backend database |

### Example Requests

HTTP POST: <http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/customer/requests?id_token={id_token}>

Using `cURL`:

```
curl -X POST http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/customer/requests?id_token=user1&product_id=168
```

### Example Response

```json
{
    "status": "OK",
    "saved_request": {
        "requestId": 495,
        "user": {
            "userUID": "user1",
            "name": "N/A",
            "email": null,
            "photoUrl": "Pictures of this awesome girl.",
            "rating": "N/A"
        },
        "product": {
            "productId": 168,
            "user": {
                "userUID": "user23",
                "name": "N/A",
                "email": null,
                "photoUrl": "This shady guy doesn't have a face.",
                "rating": "4.71"
            },
            "productName": "Awesome product-6 of user user23.",
            "category": "Baby & Kids",
            "price": "990.43",
            "favorite": false,
            "availability": true,
            "description": "This is a Baby & Kids product, which is awesome.",
            "imageUrls": "",
            "timestamp": "10/6/20 11:17 PM",
            "timezoneId": "US/Pacific",
            "lat": 0.0,
            "lon": 0.0,
            "state": "IN",
            "city": "San Francisco",
            "gpsenabled": false,
            "location": "Coordinates (Lat, Lon): N/A\nState: IN\nCity: San Francisco"
        },
        "status": "Pending",
        "timestamp": "10/6/20 11:54 PM",
        "timezoneId": "US/Pacific"
    }
}
```

### Response Fields

| Name                 | Type   | Description                                                                                                                                                                                         |
|----------------------|--------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| status               | String | status of the response, default "OK" upon success                                                                                                                                                   |
| saved_request | Object | request object made by the specified customer |
| requestId | Long | primary key of request item in the database |
| user | Object | user object representing of the customer who makes the request |
| product | Object | product object the customer requesting |
| status | String | status string representing the request status, default "pending" |
| timestamp | String | timestamp string representing request creation time with given time zone |
| timezone | String | string representing the timezoneId, default "US/Pacific" |

[Reference here if you want to know what's inside the user object :smirk:](Users.md#get-usersuser)

[Reference here if you want to know what's inside the product object :smirk:](Products.md#get-productsproductproductid)

## `DELETE` /users/customer/requests/request/{requestId}

[Go back to README.md :smirk:](../../README.md#request-service)

### Introduction

Cancel product request for the customer, registered user access only.


### Endpoint URL

<http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/customer/requests/request/{requestId}>

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

HTTP DELETE: <http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/customer/requests/request/{requestId}?id_token={id_token}>

Using `cURL`:

```
curl -X DELETE http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/customer/requests/request/495?id_token=user1
```

### Example Response

```json
{
    "status": "OK",
    "message": "Request canceled by the customer."
}
```

### Response Fields

| Name                 | Type   | Description                                                                                                                                                                                         |
|----------------------|--------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| status               | String | status of the response, default "OK" upon success                                                                                                                                                   |
| message              | String | message indicating request canceled successfully

## `GET` /users/seller/requests

[Go back to README.md :smirk:](../../README.md#request-service)

### Introduction

Retrieve requests of the products owned by the seller, registered user access only.


### Endpoint URL

<http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/seller/requests>

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

HTTP GET: <http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/seller/requests?id_token={id_token}>

Using `cURL`:

```
curl http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/seller/requests?id_token=user27
```

### Example Response

```json
{
    "status": "OK",
    "summary": "4 requests found.",
    "requests": [
        {
            "requestId": 489,
            "user": {
                "userUID": "user1",
                "name": "N/A",
                "email": null,
                "photoUrl": "Pictures of this awesome girl.",
                "rating": "N/A"
            },
            "product": {
                "productId": 182,
                "user": {
                    "userUID": "user27",
                    "name": "helloimuser27",
                    "email": null,
                    "photoUrl": "This shady guy doesn't have a face.",
                    "rating": "N/A"
                },
                "productName": "Awesome product-2 of user user27.",
                "category": "Electronics",
                "price": "175.94",
                "favorite": false,
                "availability": true,
                "description": "This is a Electronics product, which is awesome.",
                "imageUrls": null,
                "timestamp": "10/6/20 11:17 PM",
                "timezoneId": "US/Pacific",
                "lat": 37.428893618528235,
                "lon": -122.20151696911324,
                "state": "CA",
                "city": "San Francisco",
                "gpsenabled": true,
                "location": "Coordinates (Lat, Lon): (37.428893618528235,-122.20151696911324)\nState: CA\nCity: San Francisco"
            },
            "status": "Pending",
            "timestamp": "10/6/20 11:18 PM",
            "timezoneId": "US/Pacific"
        },
        {
            "requestId": 490,
            "user": {
                "userUID": "user1",
                "name": "N/A",
                "email": null,
                "photoUrl": "Pictures of this awesome girl.",
                "rating": "N/A"
            },
            "product": {
                "productId": 183,
                "user": {
                    "userUID": "user27",
                    "name": "helloimuser27",
                    "email": null,
                    "photoUrl": "This shady guy doesn't have a face.",
                    "rating": "N/A"
                },
                "productName": "Awesome product-3 of user user27.",
                "category": "Household",
                "price": "957.64",
                "favorite": false,
                "availability": true,
                "description": "This is a Household product, which is awesome.",
                "imageUrls": null,
                "timestamp": "10/6/20 11:17 PM",
                "timezoneId": "US/Pacific",
                "lat": 37.17576161354143,
                "lon": -121.89215982070957,
                "state": "CA",
                "city": "San Francisco",
                "gpsenabled": true,
                "location": "Coordinates (Lat, Lon): (37.17576161354143,-121.89215982070957)\nState: CA\nCity: San Francisco"
            },
            "status": "Pending",
            "timestamp": "10/6/20 11:18 PM",
            "timezoneId": "US/Pacific"
        },
        {
            "requestId": 491,
            "user": {
                "userUID": "user1",
                "name": "N/A",
                "email": null,
                "photoUrl": "Pictures of this awesome girl.",
                "rating": "N/A"
            },
            "product": {
                "productId": 184,
                "user": {
                    "userUID": "user27",
                    "name": "helloimuser27",
                    "email": null,
                    "photoUrl": "This shady guy doesn't have a face.",
                    "rating": "N/A"
                },
                "productName": "Awesome product-4 of user user27.",
                "category": "All Categories",
                "price": "216.00",
                "favorite": false,
                "availability": true,
                "description": "This is a product, which is awesome.",
                "imageUrls": "Image 0 of this awesome product.",
                "timestamp": "10/6/20 11:17 PM",
                "timezoneId": "US/Pacific",
                "lat": 37.39132633955441,
                "lon": -122.17051453865596,
                "state": "CA",
                "city": "San Francisco",
                "gpsenabled": true,
                "location": "Coordinates (Lat, Lon): (37.39132633955441,-122.17051453865596)\nState: CA\nCity: San Francisco"
            },
            "status": "Pending",
            "timestamp": "10/6/20 11:18 PM",
            "timezoneId": "US/Pacific"
        },
        {
            "requestId": 492,
            "user": {
                "userUID": "user1",
                "name": "N/A",
                "email": null,
                "photoUrl": "Pictures of this awesome girl.",
                "rating": "N/A"
            },
            "product": {
                "productId": 185,
                "user": {
                    "userUID": "user27",
                    "name": "helloimuser27",
                    "email": null,
                    "photoUrl": "This shady guy doesn't have a face.",
                    "rating": "N/A"
                },
                "productName": "Awesome product-5 of user user27.",
                "category": "Electronics",
                "price": "326.79",
                "favorite": false,
                "availability": true,
                "description": "This is a Electronics product, which is awesome.",
                "imageUrls": null,
                "timestamp": "10/6/20 11:17 PM",
                "timezoneId": "US/Pacific",
                "lat": 0.0,
                "lon": 0.0,
                "state": "DE",
                "city": "New Brunswick",
                "gpsenabled": false,
                "location": "Coordinates (Lat, Lon): N/A\nState: DE\nCity: New Brunswick"
            },
            "status": "Pending",
            "timestamp": "10/6/20 11:18 PM",
            "timezoneId": "US/Pacific"
        }
    ]
}
```

### Response Fields

| Name                 | Type   | Description                                                                                                                                                                                         |
|----------------------|--------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| status               | String | status of the response, default "OK" upon success                                                                                                                                                   |
| summary              | String | summary of how many requests rendered
| requests | Array | array of request objects of products owned by the seller given |
| requestId | Long | primary key of request item in the database |
| user | Object | user object representing of the customer who makes the request |
| product | Object | product object the customer requesting |
| status | String | status string representing the request status, default "pending" |
| timestamp | String | timestamp string representing request creation time with given time zone |
| timezone | String | string representing the timezoneId, default "US/Pacific" |

[Reference here if you want to know what's inside the user object :smirk:](Users.md#get-usersuser)
[Reference here if you want to know what's inside the product object :smirk:](Products.md#get-productsproductproductid)


## `PUT` /users/seller/requests/request/{requestId}

[Go back to README.md :smirk:](../../README.md#request-service)

### Introduction

Confirm the customer request and create a confirmed order item, operated by sellers only, registered user access only.


### Endpoint URL

<http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/seller/requests/request/{requestId}>

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

HTTP PUT: <http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/seller/requests/request/{requestId}?id_token={id_token}>

Using `cURL`:

```
curl -X PUT http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/seller/requests/request/489?id_token=user27
```

### Example Response

```json
{
    "status": "OK",
    "message": "request confirmed",
    "created_order": {
        "productOrderId": 497,
        "product": {
            "productId": 182,
            "user": {
                "userUID": "user27",
                "name": "helloimuser27",
                "email": null,
                "photoUrl": "This shady guy doesn't have a face.",
                "rating": "N/A"
            },
            "productName": "Awesome product-2 of user user27.",
            "category": "Electronics",
            "price": "175.94",
            "favorite": false,
            "availability": true,
            "description": "This is a Electronics product, which is awesome.",
            "imageUrls": null,
            "timestamp": "10/6/20 11:17 PM",
            "timezoneId": "US/Pacific",
            "lat": 37.428893618528235,
            "lon": -122.20151696911324,
            "state": "CA",
            "city": "San Francisco",
            "gpsenabled": true,
            "location": "Coordinates (Lat, Lon): (37.428893618528235,-122.20151696911324)\nState: CA\nCity: San Francisco"
        },
        "customer": {
            "userUID": "user1",
            "name": "N/A",
            "email": null,
            "photoUrl": "Pictures of this awesome girl.",
            "rating": "N/A"
        },
        "rating": 0.0,
        "confirmed": true,
        "timestamp": "10/7/20 12:12 AM",
        "timezoneId": "US/Pacific"
    }
}
```

### Response Fields

| Name                 | Type   | Description                                                                                                                                                                                         |
|----------------------|--------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| status               | String | status of the response, default "OK" upon success                                                                                                                                                   |
| message | String | message indicating request confirmation success
| created_order | Object | confirmed order object automatically created with the request information |

[Reference here if you want to know what's inside the order object :smirk:](Orders.md#get-orderscustomer)

## `DELETE` /users/seller/requests/request/{requestId}

[Go back to README.md :smirk:](../../README.md#request-service)

### Introduction

Decline a request by the seller, registered user access only.

### Endpoint URL

<http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/seller/requests/request/{requestId}>

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

HTTP DELETE: <http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/seller/requests/request/{requestId}?id_token={id_token}>

Using `cURL`:

```
curl -X DELETE http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/users/seller/requests/request/490?id_token=user27
```

### Example Response

```json
{
    "status": "OK",
    "message": "Request declined by the seller."
}
```

### Response Fields

| Name                 | Type   | Description                                                                                                                                                                                         |
|----------------------|--------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| status               | String | status of the response, default "OK" upon success                                                                                                                                                   |
| message              | String | message indicating the request is declined by the seller |

