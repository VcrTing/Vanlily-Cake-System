
// 能搜到的清单列表
// 需要用到订单 ID
const example = [
    {
        "index": 1,
        "order_id": 1750366738293669889,
        "product_id": 1747893601102647298,
        "is_out_stock": null,
        "is_complete": null,
        "published": "2024-01-25T04:03:35.464+00:00",
        "complete_time": null,
        "content": "公仔: 雪域熊雕花二"
    }
]

// 修改 出貨檢查，完成檢查
export const check_one = {
    "index": 1,
    "order_id": 1750366738293669889,
    "product_id": 1747893601102647298,
    "is_out_stock": 1,
    "is_complete": 1,
    "published": "2024-01-25T04:03:35.464+00:00",
    "complete_time": null,
    "content": "公仔: 雪域熊雕花二"
}

// 打包訂單
