# Cryptopunks API

## 1. Punk information with offer data
#### Request: `GET /punks/{id}`
#### Response:
**200 OK**
```json
{
  "id": 1,
  "gender": "Female",
  "accessories": ["Wild Hair"],
  "activeOffer": {
    "seller": "0x45e4a2b60cf48e8bak7d777e175a5b1e4d0c2d8f",
    "onlySellTo": "0x0000000000000000000000000000000000000000",
    "minValueInWei": 15000000000000000000,
    "highestBid": {
      "bidder": "0x21e4a2b900cf48u7bak7d666e175a5b1e4d0c2d8a",
      "value": 25000000000000000000
    } 
  }
}
```
**404 NOT FOUND** - No punk found for provided id

## 2. List of offered punks
#### Request: `GET /punks`
#### Request parameters:
`fields`  
`activeOffer`
#### Response:
**200 OK**
```json
[
  {"id": 1}, 
  {"id": 2},
  /* more data */ 
]
```
#### Limitations:
`GET /punks` currently supports only `fields=id` and `activeOffer=true`
