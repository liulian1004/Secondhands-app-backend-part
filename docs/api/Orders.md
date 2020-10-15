# Order Service Detailed Explanations

## `GET` /orders/seller

### Example Request

`HTTP GET`: http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/orders/seller?&id_token=user1

### Example Response

```json
{
    "status": "OK",
    "summary": "5 orders found",
    "seller_order_history": [
        {
            "productOrderId": 843,
            "product": {
                "productId": 116,
                "user": {
                    "userUID": "user1",
                    "name": "N/A",
                    "email": "user1@gmail.com",
                    "photoUrl": "This shady guy doesn't have a face.",
                    "rating": "1.89"
                },
                "productName": "Awesome product-7 of user user1.",
                "category": "All Categories",
                "price": "946.91",
                "favorite": false,
                "availability": true,
                "description": "This is a product, which is awesome.",
                "imageUrls": null,
                "timestamp": "10/5/20 9:58 PM",
                "timezoneId": "US/Pacific",
                "lat": 0.0,
                "lon": 0.0,
                "state": "PW",
                "city": "Brookline",
                "gpsenabled": false,
                "location": "Coordinates (Lat, Lon): N/A\nState: PW\nCity: Brookline"
            },
            "customer": {
                "userUID": "user8",
                "name": "helloimuser8",
                "email": "user8@gmail.com",
                "photoUrl": "This shady guy doesn't have a face.",
                "rating": "N/A"
            },
            "rating": 0.0,
            "confirmed": true,
            "timestamp": "10/5/20 10:01 PM",
            "timezoneId": "US/Pacific"
        },
        {
            "productOrderId": 840,
            "product": {
                "productId": 111,
                "user": {
                    "userUID": "user1",
                    "name": "N/A",
                    "email": "user1@gmail.com",
                    "photoUrl": "This shady guy doesn't have a face.",
                    "rating": "1.89"
                },
                "productName": "Awesome product-2 of user user1.",
                "category": "All Categories",
                "price": "369.27",
                "favorite": false,
                "availability": false,
                "description": "This is a product, which is awesome.",
                "imageUrls": null,
                "timestamp": "10/5/20 9:58 PM",
                "timezoneId": "US/Pacific",
                "lat": 0.0,
                "lon": 0.0,
                "state": "GA",
                "city": "San Francisco",
                "gpsenabled": false,
                "location": "Coordinates (Lat, Lon): N/A\nState: GA\nCity: San Francisco"
            },
            "customer": {
                "userUID": "user8",
                "name": "helloimuser8",
                "email": "user8@gmail.com",
                "photoUrl": "This shady guy doesn't have a face.",
                "rating": "N/A"
            },
            "rating": 0.0,
            "confirmed": false,
            "timestamp": "10/5/20 10:01 PM",
            "timezoneId": "US/Pacific"
        },
        {
            "productOrderId": 841,
            "product": {
                "productId": 113,
                "user": {
                    "userUID": "user1",
                    "name": "N/A",
                    "email": "user1@gmail.com",
                    "photoUrl": "This shady guy doesn't have a face.",
                    "rating": "1.89"
                },
                "productName": "Awesome product-4 of user user1.",
                "category": "Car",
                "price": "227.72",
                "favorite": false,
                "availability": false,
                "description": "This is a Car product, which is awesome.",
                "imageUrls": "Image 0 of this awesome product.*Image 1 of this awesome product.*Image 2 of this awesome product.*Image 3 of this awesome product.*Image 4 of this awesome product.*Image 5 of this awesome product.",
                "timestamp": "10/5/20 9:58 PM",
                "timezoneId": "US/Pacific",
                "lat": 37.451109912396035,
                "lon": -122.22297052720319,
                "state": "CA",
                "city": "San Francisco",
                "gpsenabled": true,
                "location": "Coordinates (Lat, Lon): (37.451109912396035,-122.22297052720319)\nState: CA\nCity: San Francisco"
            },
            "customer": {
                "userUID": "user8",
                "name": "helloimuser8",
                "email": "user8@gmail.com",
                "photoUrl": "This shady guy doesn't have a face.",
                "rating": "N/A"
            },
            "rating": 0.0,
            "confirmed": false,
            "timestamp": "10/5/20 10:01 PM",
            "timezoneId": "US/Pacific"
        },
        {
            "productOrderId": 842,
            "product": {
                "productId": 115,
                "user": {
                    "userUID": "user1",
                    "name": "N/A",
                    "email": "user1@gmail.com",
                    "photoUrl": "This shady guy doesn't have a face.",
                    "rating": "1.89"
                },
                "productName": "Awesome product-6 of user user1.",
                "category": "Car",
                "price": "948.46",
                "favorite": false,
                "availability": false,
                "description": "This is a Car product, which is awesome.",
                "imageUrls": null,
                "timestamp": "10/5/20 9:58 PM",
                "timezoneId": "US/Pacific",
                "lat": 37.151549294947884,
                "lon": -121.93391178331639,
                "state": "CA",
                "city": "San Francisco",
                "gpsenabled": true,
                "location": "Coordinates (Lat, Lon): (37.151549294947884,-121.93391178331639)\nState: CA\nCity: San Francisco"
            },
            "customer": {
                "userUID": "user8",
                "name": "helloimuser8",
                "email": "user8@gmail.com",
                "photoUrl": "This shady guy doesn't have a face.",
                "rating": "N/A"
            },
            "rating": 0.0,
            "confirmed": false,
            "timestamp": "10/5/20 10:01 PM",
            "timezoneId": "US/Pacific"
        },
        {
            "productOrderId": 839,
            "product": {
                "productId": 110,
                "user": {
                    "userUID": "user1",
                    "name": "N/A",
                    "email": "user1@gmail.com",
                    "photoUrl": "This shady guy doesn't have a face.",
                    "rating": "1.89"
                },
                "productName": "Awesome product-1 of user user1.",
                "category": "Electronics",
                "price": "72.88",
                "favorite": false,
                "availability": false,
                "description": "This is a Electronics product, which is awesome.",
                "imageUrls": "Image 0 of this awesome product.*Image 1 of this awesome product.*Image 2 of this awesome product.",
                "timestamp": "10/5/20 9:58 PM",
                "timezoneId": "US/Pacific",
                "lat": 37.27068663345335,
                "lon": -121.89141642011633,
                "state": "CA",
                "city": "San Francisco",
                "gpsenabled": true,
                "location": "Coordinates (Lat, Lon): (37.27068663345335,-121.89141642011633)\nState: CA\nCity: San Francisco"
            },
            "customer": {
                "userUID": "user8",
                "name": "helloimuser8",
                "email": "user8@gmail.com",
                "photoUrl": "This shady guy doesn't have a face.",
                "rating": "N/A"
            },
            "rating": 0.0,
            "confirmed": false,
            "timestamp": "10/5/20 10:00 PM",
            "timezoneId": "US/Pacific"
        }
    ]
}
```

### Response Fields

| Name | Type | Description |
|------|------|-------------|
| status | String | default "OK" upon order creation success |
| summary | String | total number of orders found |
| seller_order_history | Array | an array of product order objects |
| productOrderId | Long | primary key for product order object in backend database |
| product | Object | `product` object |
| customer | Object | customer `user` object |
| rating | Double | default 0.0 |
| confirmed | Boolean | default false |
| timestamp | String | order creation timestamp |
| timezoneId | String | time zone setting |

[Reference here if you want to know what's inside the product object :smirk:](Products.md#get-productsproductproductid)

[Reference here if you want to know what's inside the `user` object :smirk:](Users.md#get-usersuseruseruid)

## `GET` /orders/customer

### Example Request

`HTTP GET`: http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/orders/customer?&id_token=user8

### Example Response

```json
{
    "status": "OK",
    "summary": "5 orders found",
    "customer_order_history": [
        {
            "productOrderId": 840,
            "product": {
                "productId": 111,
                "user": {
                    "userUID": "user1",
                    "name": "N/A",
                    "email": "user1@gmail.com",
                    "photoUrl": "This shady guy doesn't have a face.",
                    "rating": "1.89"
                },
                "productName": "Awesome product-2 of user user1.",
                "category": "All Categories",
                "price": "369.27",
                "favorite": false,
                "availability": false,
                "description": "This is a product, which is awesome.",
                "imageUrls": null,
                "timestamp": "10/5/20 9:58 PM",
                "timezoneId": "US/Pacific",
                "lat": 0.0,
                "lon": 0.0,
                "state": "GA",
                "city": "San Francisco",
                "gpsenabled": false,
                "location": "Coordinates (Lat, Lon): N/A\nState: GA\nCity: San Francisco"
            },
            "customer": {
                "userUID": "user8",
                "name": "helloimuser8",
                "email": "user8@gmail.com",
                "photoUrl": "This shady guy doesn't have a face.",
                "rating": "N/A"
            },
            "rating": 0.0,
            "confirmed": false,
            "timestamp": "10/5/20 10:01 PM",
            "timezoneId": "US/Pacific"
        },
        {
            "productOrderId": 841,
            "product": {
                "productId": 113,
                "user": {
                    "userUID": "user1",
                    "name": "N/A",
                    "email": "user1@gmail.com",
                    "photoUrl": "This shady guy doesn't have a face.",
                    "rating": "1.89"
                },
                "productName": "Awesome product-4 of user user1.",
                "category": "Car",
                "price": "227.72",
                "favorite": false,
                "availability": false,
                "description": "This is a Car product, which is awesome.",
                "imageUrls": "Image 0 of this awesome product.*Image 1 of this awesome product.*Image 2 of this awesome product.*Image 3 of this awesome product.*Image 4 of this awesome product.*Image 5 of this awesome product.",
                "timestamp": "10/5/20 9:58 PM",
                "timezoneId": "US/Pacific",
                "lat": 37.451109912396035,
                "lon": -122.22297052720319,
                "state": "CA",
                "city": "San Francisco",
                "gpsenabled": true,
                "location": "Coordinates (Lat, Lon): (37.451109912396035,-122.22297052720319)\nState: CA\nCity: San Francisco"
            },
            "customer": {
                "userUID": "user8",
                "name": "helloimuser8",
                "email": "user8@gmail.com",
                "photoUrl": "This shady guy doesn't have a face.",
                "rating": "N/A"
            },
            "rating": 0.0,
            "confirmed": false,
            "timestamp": "10/5/20 10:01 PM",
            "timezoneId": "US/Pacific"
        },
        {
            "productOrderId": 842,
            "product": {
                "productId": 115,
                "user": {
                    "userUID": "user1",
                    "name": "N/A",
                    "email": "user1@gmail.com",
                    "photoUrl": "This shady guy doesn't have a face.",
                    "rating": "1.89"
                },
                "productName": "Awesome product-6 of user user1.",
                "category": "Car",
                "price": "948.46",
                "favorite": false,
                "availability": false,
                "description": "This is a Car product, which is awesome.",
                "imageUrls": null,
                "timestamp": "10/5/20 9:58 PM",
                "timezoneId": "US/Pacific",
                "lat": 37.151549294947884,
                "lon": -121.93391178331639,
                "state": "CA",
                "city": "San Francisco",
                "gpsenabled": true,
                "location": "Coordinates (Lat, Lon): (37.151549294947884,-121.93391178331639)\nState: CA\nCity: San Francisco"
            },
            "customer": {
                "userUID": "user8",
                "name": "helloimuser8",
                "email": "user8@gmail.com",
                "photoUrl": "This shady guy doesn't have a face.",
                "rating": "N/A"
            },
            "rating": 0.0,
            "confirmed": false,
            "timestamp": "10/5/20 10:01 PM",
            "timezoneId": "US/Pacific"
        },
        {
            "productOrderId": 839,
            "product": {
                "productId": 110,
                "user": {
                    "userUID": "user1",
                    "name": "N/A",
                    "email": "user1@gmail.com",
                    "photoUrl": "This shady guy doesn't have a face.",
                    "rating": "1.89"
                },
                "productName": "Awesome product-1 of user user1.",
                "category": "Electronics",
                "price": "72.88",
                "favorite": false,
                "availability": false,
                "description": "This is a Electronics product, which is awesome.",
                "imageUrls": "Image 0 of this awesome product.*Image 1 of this awesome product.*Image 2 of this awesome product.",
                "timestamp": "10/5/20 9:58 PM",
                "timezoneId": "US/Pacific",
                "lat": 37.27068663345335,
                "lon": -121.89141642011633,
                "state": "CA",
                "city": "San Francisco",
                "gpsenabled": true,
                "location": "Coordinates (Lat, Lon): (37.27068663345335,-121.89141642011633)\nState: CA\nCity: San Francisco"
            },
            "customer": {
                "userUID": "user8",
                "name": "helloimuser8",
                "email": "user8@gmail.com",
                "photoUrl": "This shady guy doesn't have a face.",
                "rating": "N/A"
            },
            "rating": 0.0,
            "confirmed": false,
            "timestamp": "10/5/20 10:00 PM",
            "timezoneId": "US/Pacific"
        },
        {
            "productOrderId": 843,
            "product": {
                "productId": 116,
                "user": {
                    "userUID": "user1",
                    "name": "N/A",
                    "email": "user1@gmail.com",
                    "photoUrl": "This shady guy doesn't have a face.",
                    "rating": "1.89"
                },
                "productName": "Awesome product-7 of user user1.",
                "category": "All Categories",
                "price": "946.91",
                "favorite": false,
                "availability": true,
                "description": "This is a product, which is awesome.",
                "imageUrls": null,
                "timestamp": "10/5/20 9:58 PM",
                "timezoneId": "US/Pacific",
                "lat": 0.0,
                "lon": 0.0,
                "state": "PW",
                "city": "Brookline",
                "gpsenabled": false,
                "location": "Coordinates (Lat, Lon): N/A\nState: PW\nCity: Brookline"
            },
            "customer": {
                "userUID": "user8",
                "name": "helloimuser8",
                "email": "user8@gmail.com",
                "photoUrl": "This shady guy doesn't have a face.",
                "rating": "N/A"
            },
            "rating": 0.0,
            "confirmed": true,
            "timestamp": "10/5/20 10:01 PM",
            "timezoneId": "US/Pacific"
        }
    ]
}
```

### Response Fields

| Name | Type | Description |
|------|------|-------------|
| status | String | default "OK" upon order creation success |
| summary | String | total number of orders found |
| customer_order_history | Array | an array of product order objects |
| productOrderId | Long | primary key for product order object in backend database |
| product | Object | `product` object |
| customer | Object | customer `user` object |
| rating | Double | default 0.0 |
| confirmed | Boolean | default false |
| timestamp | String | order creation timestamp |
| timezoneId | String | time zone setting |

[Reference here if you want to know what's inside the product object :smirk:](Products.md#get-productsproductproductid)

[Reference here if you want to know what's inside the `user` object :smirk:](Users.md#get-usersuseruseruid)

## `POST` /orders/seller

Create an order with given `product_id` and `customer_id`, registered user accessible only.

### Endpoint URL

<http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/orders/seller>

### Query Parameters

| Name | Type | Description |
|------|------|-------------|
| id_token | String | ID_TOKEN for Firebase Auth of Seller, currently faked by userUID |
| customer_id | String | ID_TOKEN for Firebase Auth of Customer, currently faked by userUID |
| product_id | Long | primary key for the `product` item in the database |

### Example Request

**NOTE**

Check the h2-console to see the dummy data: <http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/h2-console/>

`HTTP POST`: http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/orders/seller?id_token=user1&customer_id=user8&product_id=112

```shell script
curl -X POST http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/orders/seller?id_token=user1&customer_id=user8&product_id=112
```

### Example Response

```json
{
    "status": "OK",
    "product_order": {
        "productOrderId": 943,
        "product": {
            "productId": 110,
            "user": {
                "userUID": "user3",
                "name": "helloimuser3",
                "email": "user3@gmail.com",
                "photoUrl": "Pictures of this awesome girl.",
                "rating": "N/A"
            },
            "productName": "Awesome product-1 of user user3.",
            "category": "Household",
            "price": "13.34",
            "favorite": false,
            "availability": true,
            "description": "This is a Household product, which is awesome.",
            "imageUrls": "Image 0 of this awesome product.",
            "timestamp": "10/5/20 8:47 PM",
            "timezoneId": "US/Pacific",
            "lat": 0.0,
            "lon": 0.0,
            "state": "MS",
            "city": "Austin",
            "gpsenabled": false,
            "location": "Coordinates (Lat, Lon): N/A\nState: MS\nCity: Austin"
        },
        "customer": {
            "userUID": "user8",
            "name": "N/A",
            "email": "user8@gmail.com",
            "photoUrl": "Pictures of this awesome girl.",
            "rating": "4.54"
        },
        "rating": 0.0,
        "confirmed": false,
        "timestamp": "10/5/20 9:44 PM",
        "timezoneId": "US/Pacific"
    }
}
```

### Response Fields

| Name | Type | Description |
|------|------|-------------|
| status | String | default "OK" upon order creation success |
| product_order | Object | `productOrder` object of the order item, containing `product`, `customer`, `rating`, `timestamp`, and `timezoneId` |
| productOrderId | Long | primary key for product order object in backend database |
| product | Object | `product` object |
| customer | Object | customer `user` object |
| rating | Double | default 0.0 |
| confirmed | Boolean | default false |
| timestamp | String | order creation timestamp |
| timezoneId | String | time zone setting |

[Reference here if you want to know what's inside the product object :smirk:](Products.md#get-productsproductproductid)

[Reference here if you want to know what's inside the `user` object :smirk:](Users.md#get-usersuseruseruid)

## `PUT` /orders/seller/order/{order_id}

Update an order status to `confirmed`, seller accessible only.

### Endpoint URL

<http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/orders/seller>

### Query Parameters

| Name | Type | Description |
|------|------|-------------|
| id_token | String | ID_TOKEN for Firebase Auth of Seller, currently faked by userUID |
| customer_id | String | ID_TOKEN for Firebase Auth of Customer, currently faked by userUID |
| product_id | Long | primary key for the `product` item in the database |

### Example Request

**NOTE**

Check the h2-console to see the dummy data: <http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/h2-console/>

`HTTP PUT`: http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/orders/seller/order/841?id_token=user1

```shell script
curl -X PUT http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/orders/seller/order/841?id_token=user1
```

### Example Response

```json
{
    "status": "OK",
    "product_order": {
        "productOrderId": 943,
        "product": {
            "productId": 110,
            "user": {
                "userUID": "user3",
                "name": "helloimuser3",
                "email": "user3@gmail.com",
                "photoUrl": "Pictures of this awesome girl.",
                "rating": "N/A"
            },
            "productName": "Awesome product-1 of user user3.",
            "category": "Household",
            "price": "13.34",
            "favorite": false,
            "availability": true,
            "description": "This is a Household product, which is awesome.",
            "imageUrls": "Image 0 of this awesome product.",
            "timestamp": "10/5/20 8:47 PM",
            "timezoneId": "US/Pacific",
            "lat": 0.0,
            "lon": 0.0,
            "state": "MS",
            "city": "Austin",
            "gpsenabled": false,
            "location": "Coordinates (Lat, Lon): N/A\nState: MS\nCity: Austin"
        },
        "customer": {
            "userUID": "user8",
            "name": "N/A",
            "email": "user8@gmail.com",
            "photoUrl": "Pictures of this awesome girl.",
            "rating": "4.54"
        },
        "rating": 0.0,
        "confirmed": true,
        "timestamp": "10/5/20 9:44 PM",
        "timezoneId": "US/Pacific"
    }
}
```

### Response Fields

| Name | Type | Description |
|------|------|-------------|
| status | String | default "OK" upon order creation success |
| product_order | Object | `productOrder` object of the order item, containing `product`, `customer`, `rating`, `timestamp`, and `timezoneId` |
| productOrderId | Long | primary key for product order object in backend database |
| product | Object | `product` object |
| customer | Object | customer `user` object |
| rating | Double | default 0.0 |
| confirmed | Boolean | default false |
| timestamp | String | order creation timestamp |
| timezoneId | String | time zone setting |

[Reference here if you want to know what's inside the product object :smirk:](Products.md#get-productsproductproductid)

[Reference here if you want to know what's inside the `user` object :smirk:](Users.md#get-usersuseruseruid)

## `PUT` /orders/customer/order/{order_id}

Rate the order, customer accessible only, one-time access only.

### Authentication and Rate Limits

| Name | Description |
|-----------------------|------|
| Authentication Method | Firebase Auth (Currently faked by <br> simple db operations.) |
| Rate Limit            | 1 for each <order, customer> pair |

### Endpoint URL

<http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/orders/seller>

### Query Parameters

| Name | Type | Description |
|------|------|-------------|
| id_token | String | ID_TOKEN for Firebase Auth of Seller, currently faked by userUID |
| customer_id | String | ID_TOKEN for Firebase Auth of Customer, currently faked by userUID |
| product_id | Long | primary key for the `product` item in the database |

### Example Request

**NOTE**

Check the h2-console to see the dummy data: <http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/h2-console/>

`HTTP PUT`: http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/orders/customer/order/841?id_token=user1&rating=4.5

```shell script
curl -X PUT http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/orders/customer/order/841?id_token=user1&rating=4.5
```

### Example Response

```json
{
    "status": "OK",
    "product_order": {
        "productOrderId": 943,
        "product": {
            "productId": 110,
            "user": {
                "userUID": "user3",
                "name": "helloimuser3",
                "email": "user3@gmail.com",
                "photoUrl": "Pictures of this awesome girl.",
                "rating": "N/A"
            },
            "productName": "Awesome product-1 of user user3.",
            "category": "Household",
            "price": "13.34",
            "favorite": false,
            "availability": true,
            "description": "This is a Household product, which is awesome.",
            "imageUrls": "Image 0 of this awesome product.",
            "timestamp": "10/5/20 8:47 PM",
            "timezoneId": "US/Pacific",
            "lat": 0.0,
            "lon": 0.0,
            "state": "MS",
            "city": "Austin",
            "gpsenabled": false,
            "location": "Coordinates (Lat, Lon): N/A\nState: MS\nCity: Austin"
        },
        "customer": {
            "userUID": "user8",
            "name": "N/A",
            "email": "user8@gmail.com",
            "photoUrl": "Pictures of this awesome girl.",
            "rating": "4.54"
        },
        "rating": 4.5,
        "confirmed": true,
        "timestamp": "10/5/20 9:44 PM",
        "timezoneId": "US/Pacific"
    }
}
```

### Response Fields

| Name | Type | Description |
|------|------|-------------|
| status | String | default "OK" upon order creation success |
| product_order | Object | `productOrder` object of the order item, containing `product`, `customer`, `rating`, `timestamp`, and `timezoneId` |
| productOrderId | Long | primary key for product order object in backend database |
| product | Object | `product` object |
| customer | Object | customer `user` object |
| rating | Double | default 0.0 |
| confirmed | Boolean | default false |
| timestamp | String | order creation timestamp |
| timezoneId | String | time zone setting |

[Reference here if you want to know what's inside the product object :smirk:](Products.md#get-productsproductproductid)

[Reference here if you want to know what's inside the `user` object :smirk:](Users.md#get-usersuseruseruid)


## `DELETE` /orders/seller/order/{order_id}

Delete an order item with `order_id`, seller accessible only

### Endpoint URL

<http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/orders/seller/order/{order_id}>

### Query Parameters

| Name | Type | Description |
|------|------|-------------|
| id_token | String | ID_TOKEN for Firebase Auth of Seller, currently faked by userUID |

### Example Request

**NOTE**

Check the h2-console to see the dummy data: <http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/h2-console/>

`HTTP DELETE`: http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/orders/seller/order/841?id_token=user1

```shell script
curl -X DELETE http://zarathos-env.eba-ccwytkkn.us-east-2.elasticbeanstalk.com/orders/seller/order/841?id_token=user1
```

### Example Response

```json
{
    "status": "OK",
    "deleted order": true
}
```

### Response Fields

| Name | Type | Description |
|------|------|-------------|
| status | String | default "OK" upon order creation success |
| deleted_order | Boolean | default `true` upon deletion success |


[Reference here if you want to know what's inside the product object :smirk:](Products.md#get-productsproductproductid)

[Reference here if you want to know what's inside the `user` object :smirk:](Users.md#get-usersuseruseruid)
