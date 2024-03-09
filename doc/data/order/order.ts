
const ID = 1750366738293669889;
const PRODUCT = 1747893601102647298;

// 新增预约订单
// 如果不知道产品数据，请使用 POST 的 CAKE LIST 进行获取
export const reserve = [
    {
        "customer": {
            "phone": "13576639986",
            "name": "曾先生"
        },
        "cashier_id": 1750844806372216834,
        "date_finally": "2023-05-18T18:13:50.000+00:00",
        "is_ahead": 1,
        "be_from": "web",
        "products": [
            {
                "id": PRODUCT,
                "g": 530,
                "quantity": 1,
                "variations_article_number": [ "b202" ]
            }
        ]
    }
]

// 预约订单之后，返回的结果如下
const reserve_result = {
    "live":1,
    "id": ID,
    "published":"2023-05-08T12:14:22.000+00:00",
    "version":null,
    "uuid":"VAN20230508201400",
    "article_number":null,
    "date_update":null,
    "date_over":"2023-05-18T12:14:22.000+00:00",
    "status_send":"silence",
    "status_make":"waiting",
    "status_life":"working",
    "is_fill":0,
    "is_paid":1,
    "is_ahead":1,
    "is_over":0,
    "price_discount":null,
    "price_generate":null,
    "price_paid":null,
    "price_actually_profit":null,
    "customer_id":null,
    "cashier_id":1,
    "type_take":null,
    "charge":null,
    "be_from":"web",
    "remarks":null,
    "products":"[{\"id\":1747893601102647298,\"name\":\"雪域牛乳芝士\",\"quantity\":1,\"is_main_cake\":1,\"hour_live\":72,\"hour_fresh\":48,\"size\":\"{\\\"remark\\\":\\\"2-4人食\\\",\\\"text\\\":\\\"1磅\\\",\\\"g\\\":530,\\\"diameter\\\":\\\"16x16\\\",\\\"price_newest_selling\\\":218,\\\"price_origin_selling\\\":242}\",\"variations\":\"[{\\\"items\\\":[{\\\"text\\\":\\\"雪域熊雕花二\\\",\\\"article_number\\\":\\\"b202\\\"}],\\\"text\\\":\\\"公仔\\\",\\\"code\\\":\\\"doll\\\",\\\"index\\\":1}]\",\"price_newest_selling\":218,\"price_origin_selling\":242}]",
    "payments":null,
    "discounts":null
}

// 完善订单信息
export const filling = {
    "id": 1750366738293669889,
    "charge": { "id": 1750844806372216834, "number": "AAA", "phone": "13576639986", "name": "李小紅" },
    "discounts": [
        { "index": 1 , "name": "暑期优惠", "type_text": "summer", "discount_text": "9.5折扣", "discount": 0.95, "discount_price": 12.3, "remark": "" }
    ],
    "remarks": [
        { "type": "normal", "type_text": "备注一", "content": "多拿一个蛋糕刀" }
    ],
    "delivery": {
        "type_take": "delivery",
        "receive_name": "曾毅丹",
        "receive_phone": "13576639986",
        "receive_content_1": "深圳宝安固戍华丰机器人产业园",
        "receive_content_2": "C栋201，走进里面来，在最里面",
        "receive_remark": "没人收就放桌子",
        "reserve_arrive_time": "",
        "reserve_delivery_time_start": "2024-01-24T12:14:22.000+00:00",
        "reserve_delivery_time_end": "2024-01-24T14:14:22.000+00:00"
    },
    "products": [
        {
            "id": 1747893601102647298,
            "g": 530,
            "variations_article_number": [ "b202" ],
            "requires": [
                { "typed": "birthday_text", "type_text": "生日牌字粒", "content": "小丹生日快樂" },
                { "typed": "hope_text", "type_text": "生日寄語", "content": "小丹，眨眼就24歲了，今天是你的生日，祝你生日快樂啊，在新的一年里，要加油哦～" }
            ]
        }
    ]
}

// 录入付款
export const insert_payment = {
    "id": 1750366738293669889,
    "charge_checkout": {
        "phone": "13576639986",
        "email": "vcrting@163.com",
        "name": "小红"
    },
    "payments": [
        {
            "way": "PayMe",
            "way_text": "PayMe 支付",
            "price": 205.70,
            "pay_time": "2024-01-24T12:14:22.000+00:00"
        }
    ]
}