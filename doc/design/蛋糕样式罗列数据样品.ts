
// 罗列数据样品
export const example = [
    {
        "items": [
            { "text": "一磅", "article_number": "", "index": 1 },
            { "text": "二磅", "article_number": "" }
        ],
        "text": "尺寸", "code": "size", "live": 1, "index": 1, "published": "2023-05-05"
    },
    {
        "items": [
            { "text": "粉红色小熊公仔", "article_number": "b201", "index": 1 },
            { "text": "紫色小熊公仔", "article_number": "b202" }
        ],
        "text": "公仔", "code": "doll"
    },
    {
        "items": [
            { "text": "纯白天鹅", "article_number": "", "index": 1 },
            { "text": "黑白天鹅", "article_number": "" }
        ],
        "text": "奶油图案", "code": "image"
    }
]

// 数据选择之后
export const choice = [
    {
        "items": [
            { "text": "一磅", "article_number": "", "index": 1 },
        ],
        "text": "尺寸", "code": "size"
    }
    // 未传过来的数据统一用默认值
    // index = 1 表示默认值
]