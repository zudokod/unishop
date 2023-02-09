# unishop


The project's naive custom rule engine is build on java. Better options are there to go for
rule engines or DSL etc. If python is used, we could have used other engines like PyKE, rule-engine, sickit etc

This is a naive implementation where the rule are defined in
[com.unishop.marketplace.servic.RuleDefinitions] where the RuleConfig is set. 
This can be further enhanced to support different configs and rules through REST API

On order complete ie. checkout -> if the rule is satsfied an action is taken, in this case associate a discount coupon to user
The discount coupon is manually set to cart on next shopping which will be tagged for that checkout.
On order complete, the discount coupon is used up.

Test cases are added under /src/test/java/com/unishop/marketplace

This uses Java 17, Spring Boot

For the use of
1. Generate a discount code if the condition above is satisfied.
     - Use the discounts API, once N times the checkout is done (refer test suite), the discount is added
2. Lists count of items purchased, total purchase amount, list of discount codes and total discount amount.
    -  Use the order history API. Otherwise, the checkout will return order details as well

#### Assumptions made
- Uses predefined product catalog
- Doesn't handle inventory changes, change in quantity etc.
- The coupon definition is predefined
    - But the capability is there to add custom rules and associate discount
    - The API for discount definition and rule definition is not exposed but service implementation is there
    - Custom rule chaining engine
- No error responses for now, which will be updated later.


The sample postman collection can be found in [api](/apis/Unishop.postman_collection.json)


# API Definitions


### End-point: Get Products
#### Method: GET
>```
>http://{{host}}/products
>```

### Response

```json
[
    {
        "id": "1",
        "name": "Surgical Mask",
        "unitPrice": 10.0,
        "availableStock": {
            "quantity": 128
        }
    },
    {
        "id": "2",
        "name": "Surgical Gloves",
        "unitPrice": 50.0,
        "availableStock": {
            "quantity": 41
        }
    },
    {
        "id": "3",
        "name": "Unisex Hospital Gown",
        "unitPrice": 100.0,
        "availableStock": {
            "quantity": 68
        }
    },
    {
        "id": "4",
        "name": "Insulin Syringe",
        "unitPrice": 40.0,
        "availableStock": {
            "quantity": 117
        }
    }
]
```

⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

### End-point: Get Product by Id
##### Method: GET
>```
>http://{{host}}/product/1
>```

### Response


```json
{
    "id": "1",
    "name": "Surgical Mask",
    "unitPrice": 10.0,
    "availableStock": {
        "quantity": 128
    }
}
```
⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

### End-point: Get Cart
#### Method: GET
>```
>http://{{host}}/cart
>```
#### Headers

|Content-Type|Value|
|---|---|
|userId|{{userId}}|

### Response

```json
{
    "userId": "1",
    "items": [],
    "rewardIds": []
}

```

⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

### End-point: Add Cart item
#### Method: POST
>```
>http://{{host}}/cart/cart_item
>```
#### Headers

|Content-Type|Value|
|---|---|
|userid|{{userId}}|


#### Body (**raw**)

```json
{
    "productId" : "1",
    "quantity" : 11
}
```

### Response

```json
{
    "userId": "1",
    "items": [
        {
            "product": {
                "id": "1",
                "name": "Surgical Mask",
                "unitPrice": 10.0,
                "availableStock": {
                    "quantity": 128
                }
            },
            "quantity": 11
        }
    ],
    "rewardIds": []
}

```

⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

### End-point: Get Discounts
#### Method: GET
>```
>http://{{host}}/rewards
>```
#### Headers

|Content-Type|Value|
|---|---|
|userId|{{userId}}|

### Response

```json
{
    "userId": "1",
    "eligibleDiscounts": [
        {
            "discountCode": "DISCOUNT10",
            "discountType": "ORDER_AMOUNT_PERCENT",
            "percentage": 10
        }
    ]
}

```

⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

### End-point: Apply Coupon
#### Method: POST
>```
>http://{{host}}/cart/discount
>```
#### Headers

|Content-Type|Value|
|---|---|
|userId|{{userId}}|


#### Body (**raw**)

```json
{
    "couponCode" : "DISCOUNT10"
}
```

### Response

```json

{
    "userId": {
        "value": "1"
    },
    "items": {
        "1": {
            "product": {
                "id": "1",
                "name": "Surgical Mask",
                "unitPrice": 10.0,
                "availableStock": {
                    "quantity": 128
                }
            },
            "quantity": 11
        }
    },
    "rewardIds": [
        "DISCOUNT10"
    ]
}
```

⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

### End-point: Checkout
#### Method: POST
>```
>http://{{host}}/cart/checkout
>```
#### Headers

|Content-Type|Value|
|---|---|
|userId|{{userId}}|

### Response

```json
{
    "userId": "1",
    "orderItems": [
        {
            "productId": "1",
            "name": "Surgical Mask",
            "quantity": 11,
            "price": 10.0
        }
    ],
    "itemCount": 11,
    "totalAmount": 110.0,
    "discountAmount": 0.0,
    "payableAmount": 110.0,
    "appliedDiscountCoupon": null
}

```

⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

### End-point: Get OrderCount
#### Method: GET
>```
>http://{{host}}/orders/count
>```
#### Headers

|Content-Type|Value|
|---|---|
|userId|{{userId}}|


#### Response

```json
{
    "userid": "1",
    "orderCount": 1
}
```

⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃

### End-point: Get Order History
#### Method: GET
>```
>http://{{host}}/orders
>```
#### Headers

|Content-Type|Value|
|---|---|
|userId|{{userId}}|

#### Response

```json
[
    {
        "userId": {
            "value": "1"
        },
        "orderItems": [
            {
                "productId": "1",
                "name": "Surgical Mask",
                "quantity": 11,
                "price": 10.0
            }
        ],
        "itemCount": 11,
        "totalAmount": 110.0,
        "discountAmount": 0.0,
        "payableAmount": 110.0,
        "appliedDiscountCoupon": null
    }
]

```

⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃ ⁃
