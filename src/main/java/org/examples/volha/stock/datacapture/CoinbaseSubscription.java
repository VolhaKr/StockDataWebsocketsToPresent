package org.examples.volha.stock.datacapture;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;

import static org.examples.volha.stock.webpresentation.StockInfoController.bitfinexSubscribed;
import static org.examples.volha.stock.webpresentation.StockInfoController.productIDsToSubscribe;
import static org.examples.volha.stock.webpresentation.WebPresentationApplication.bitfinexClient;


@Component
public class CoinbaseSubscription {
    // Creates subsrciption string in the format:
//{
//        "type": "subscribe",
//        "product_ids": [
//        "ETH-USD",
//                "ETH-EUR"
//    ],
//        "channels": [
//        "level2",
//                "heartbeat",
//                {
//                        "name": "ticker",
//        "product_ids": [
//        "ETH-BTC",
//                "ETH-USD"
//            ]
//        }
//    ]
//}
    String type = "subscribe";
    String[] product_ids = {"ETH-USD", "ETH-EUR", "ETH-BTC", "BTC-USD"};
    String channels[] = {"ticker"};


    private String subscrPair;

    // private JsonObject subscriptionString = new JsonObject();
    public CoinbaseSubscription() {

    }

    public CoinbaseSubscription(String type, String[] product_ids, String[] channels, String subscrPair) {
        this.type = type;
        this.product_ids = product_ids;
        this.channels = channels;
        this.subscrPair = subscrPair;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getProduct_ids() {
        return product_ids;
    }

    public void setProduct_ids(String[] product_ids) {
        this.product_ids = product_ids;
    }

    public String[] getChannels() {
        return channels;
    }

    public void setChannels(String[] channels) {
        this.channels = channels;
    }

    public String getSubscrPair() {
        return subscrPair;
    }

    public void setSubscrPair(String subscrPair) {
        this.subscrPair = subscrPair;
    }

    public String makeSubscriptionString(String[] checkedProductPairs) {
        JsonArray arrayOfSymbolPairs = new JsonArray();
        for (String productID : productIDsToSubscribe) {

            arrayOfSymbolPairs.add(productID);
        }
        JsonObject subscriptionString = new JsonObject();
        subscriptionString.addProperty("type", "subscribe");
        subscriptionString.addProperty("channel", "ticker");
        subscriptionString.add("product_ids", arrayOfSymbolPairs);
        String subscriptionStringToString = String.valueOf(subscriptionString);
        System.out.println("1111111111111111111111111SUBSCRIPTION STRING COINBASE**************************" + subscriptionStringToString);
        return subscriptionStringToString;

    }
}
