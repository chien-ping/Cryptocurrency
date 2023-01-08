# Cryptocurrency

> 提供加密貨幣兌換匯查詢與幣別對應維護。

## Domain

```
 http://localhost:8080/cryptocurrency
```
## EndPoints
| HTTP Method                                    | Description |
|:-----------------------------------------------|:------------|
| GET [ /v1/crypto/{crypto_name} ](#加密貨幣匯率查詢)    | 加密貨幣匯率查詢    |
| GET [ /v1/currency/{currency_code} ](#幣別對應查詢)  | 幣別對應查詢      |
| POST [ /v1/currency ](#新增幣別)                   | 新增幣別        |
| PATCH [ /v1/currency/{currency_code} ](#更新幣別)  | 更新幣別        |
| DELETE [ /v1/currency/{currency_code} ](#刪除幣別) | 刪除幣別        |

### 加密貨幣匯率查詢
#### 傳入參數說明
#####  request url
| Attribute   | Necessary | Type | Description         |
|:------------|:----------|:----|:--------------------|
| crypto_name | Y         | String | 加密貨幣名稱:<br/>bitcoin |

```
 http://localhost:8080/cryptocurrency/v1/crypto/bitcoin
```

#### 回傳參數說明

| Attribute     | Sub-attribute | Type | Description |
|:--------------|:--------------|:----|:------------|
| cryptoName    |               | String      | 加密貨幣名稱      |
| updatedTime   |               | String      | 更新時間(台灣時間)  |
| exchangeRates |               | json object | 匯率          |
|               | code          | String | 貨幣代碼        |
|               | nameZh        | String | 幣別中文名稱      |
|               | rateFormat    | String | 匯率          |
|               | rate          | float | 匯率          |

```
{
    "cryptoName": "Bitcoin",
    "updatedTime": "1111/11/11 11:11:11",
    "exchangeRates": [
        {
            "code": "USD",
            "nameZh": "美元",
            "rateFormat": "16,914.9997",
            "rate": 16915.0
        },
        {
            "code": "GBP",
            "nameZh": "英鎊",
            "rateFormat": "14,134.0384",
            "rate": 14134.038
        },
        {
            "code": "EUR",
            "nameZh": "歐元",
            "rateFormat": "16,477.6792",
            "rate": 16477.68
        }
    ]
}

```

### 幣別對應查詢
#### 傳入參數說明
#####  request url
| Attribute     | Necessary | Type | Description   |
|:--------------|:----------|:----|:--------------|
| currency_code | N         | String | 幣別 |

```
 http://localhost:8080/cryptocurrency/v1/currency

```

#### 回傳參數說明

| Attribute | Type | Description     |
|:----------|:----|:----------------|
| code      |  String      | 幣別代碼 |
| nameZh    |    String | 幣別中文名稱 |

```
[
    {
        "code": "EUR",
        "nameZh": "歐元"
    },
    {
        "code": "GBP",
        "nameZh": "英鎊"
    },...
]

```
### 新增幣別
#### 傳入參數說明
##### request body
| Attribute | Necessary | Type | Description |
|:----------|:----------|:----|:------------|
| code      | Y         | String | 幣別代碼        |
| nameZh    | Y         | String | 幣別中文名稱      |

```
 
{
        "code": "GBP",
        "nameZh": "英鎊"
}

```

#### 回傳參數說明

| Attribute | Type         | Description |
|:----------|:-------------|:------------|
| message         | String       | 執行結果        |
| errors    | String array | 錯誤訊息        |

```
{
    "message": "執行成功"
}

```
### 更新幣別
#### 傳入參數說明
#####  request url

| Attribute     | Necessary | Type | Description |
|:--------------|:----------|:----|:------------|
| currency_code | Y         | String | 幣別代碼        |

```
http://localhost:8080/cryptocurrency/v1/currency/GBP
```
##### request body
| Attribute     | Necessary | Type | Description |
|:--------------|:----------|:----|:------------|
| code      | Y         | String | 幣別代碼        |
| nameZh    | Y         | String | 幣別中文名稱      |

```
 
{
        "code": "GBP",
        "nameZh": "英鎊"
}

```

#### 回傳參數說明


| Attribute | Type         | Description |
|:----------|:-------------|:------------|
| message         | String       | 執行結果        |
| errors    | String array | 錯誤訊息        |


```
{
    "message": "執行成功"
}

```

### 刪除幣別
#### 傳入參數說明
#####  request url
| Attribute     | Necessary | Type | Description |
|:--------------|:----------|:----|:------------|
| currency_code | Y         | String | 幣別代碼        |

```
http://localhost:8080/cryptocurrency/v1/currency/GBP

```

#### 回傳參數說明


| Attribute | Type         | Description |
|:----------|:-------------|:------------|
| message         | String       | 執行結果        |
| errors    | String array | 錯誤訊息        |

```
{
    "message": "執行成功"
}

```


## Table Schema

[schema](/src/main/resources/schema.sql)
