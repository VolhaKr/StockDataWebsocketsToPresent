package org.examples.volha.stock.store;

public class CoinbaseMessageProductData {
//    {
//        "type": "ticker",
//            "trade_id": 20153558,
//            "sequence": 3262786978,
//            "time": "2017-09-02T17:05:49.250000Z",
//            "product_id": "BTC-USD",
//            "price": "4388.01000000",
//            "side": "buy", // Taker side
//            "last_size": "0.03000000",
//            "best_bid": "4388",
//            "best_ask": "4388.01"
//    }
    private String symbol; //symbol: 'BTC-USD'
    private String price;
//     private Double volume_24h;
//     private Double best_bid;
//     private  Double best_ask;
//                        coinbaseMessage.getTime())

    public CoinbaseMessageProductData() {
    }

    public CoinbaseMessageProductData(String symbol, String price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
