package org.examples.volha.stock.datacapture;

import com.google.gson.Gson;
import org.examples.volha.stock.store.CoinbaseDataProcessor;
import org.examples.volha.stock.store.CoinbaseMessage;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;


import static org.examples.volha.stock.webpresentation.StockInfoController.coinbaseSubcribed;
import static org.examples.volha.stock.webpresentation.StockInfoController.productIDsToSubscribe;


public class CoinbaseClient extends StockClient {
    public static boolean coinbaseConnected = false;

    public CoinbaseClient(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public CoinbaseClient(URI serverURI) {
        super(serverURI);
    }

    public CoinbaseClient(URI serverUri, Map<String, String> httpHeaders) {
        super(serverUri, httpHeaders);
    }


    String creceivedSymbol;
    String creceivedPrice;
    public Hashtable<String, HashtableMessage> coinbaseHashtable
            = new Hashtable<String, HashtableMessage>();
    public Set<String> coinbaseKeys = coinbaseHashtable.keySet();

    public CoinbaseDataProcessor coinbaseDataProcessor = new CoinbaseDataProcessor();

    public void testPrint() {
        System.out.println("121333333333312222222222222222222242222222222222222222222222222222222222");
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("123***********************from CoinbaseClient : Coinbase opened connection");
        coinbaseConnected = true;
        coinbaseDataProcessor.init();

        if ((productIDsToSubscribe != null) & (!coinbaseSubcribed)) {
            CoinbaseSubscription coinbaseSubscription = new CoinbaseSubscription();
            send(coinbaseSubscription.makeSubscriptionString(productIDsToSubscribe));
//                Gson gson = new Gson();
//                String coinbaseSubscriptionString = gson.toJson(coinbaseSubscription.makeSubscriptionString(productIDsToSubscribe));
//                send ({
//                        "type": "subscribe",
//                        "product_ids": [
//                "ETH-USD",
//                        "ETH-EUR"
//    ],
//                "channels": [
//                "level2",
//                        "heartbeat",
//                        {
//                                "name": "ticker",
//                        "product_ids": [
//                "ETH-BTC",
//                        "ETH-USD"
//            ]
//        }
//    ]
//});
            System.out.println("***********************************Coinbase connected and subsription sent " + coinbaseSubscription.makeSubscriptionString(productIDsToSubscribe));
            coinbaseSubcribed = true;
        } else {
            System.out.println("Coinbase Waiting for input products");
        }


    }

    @Override
    public void onMessage(String message) {
        System.out.println("from CoinbaseClient : Bitfinex message received: " + message);

        // BitfinexDataProcessor bitfinexDataProcessor = new BitfinexDataProcessor();
        // System.out.println("from BitfinexClient : BitfinexProcessor  object created");
        coinbaseDataProcessor.put(message);
        System.out.println("_____________________________________from CoinbaseClient : BitfinexProcessor object created,  put" + message);







        System.out.println("Coinbase Subscription message received");
        //   System.out.println( "Coinbase received JSON: " + message );
        Gson g = new Gson();
        // Deserialization
        //if message cointains "ticker", this is a message with prices and data from the ticker channel  - see CoinbaseMessage
        if (message.contains("ticker")) {
            CoinbaseMessage coinbaseMessage = g.fromJson(message, CoinbaseMessage.class);
            creceivedSymbol = coinbaseMessage.getProduct_id();
            creceivedPrice = coinbaseMessage.getPrice();
            //   System.out.println("Coinbase received symbol and price: " + "symbol: " + creceivedSymbol + " price " + creceivedPrice);

            // if the message from ticker channel contains not null values, Coinbase hashtable is updated with the data
            //<<<probably to add comparison to null for other CoinbaseMessage fields
            if ((creceivedSymbol != null) & (creceivedPrice != null)) {
                // object with product info is created to be put into hashtable (unified for Bitfinex and Coinbase)
                HashtableMessage hashtableMessage = new HashtableMessage(creceivedSymbol, creceivedPrice,
                        coinbaseMessage.getVolume_24h(), coinbaseMessage.getBest_bid(), coinbaseMessage.getBest_ask(),
                        coinbaseMessage.getTime());
                coinbaseHashtable.put(creceivedSymbol, hashtableMessage);
                // System.out.println("price from hashtable: " + coinbaseHashtable.get(creceivedSymbol));
            }
        }
    }


    @Override
    public void onClose(int code, String reason, boolean remote) {
        // The codecodes are documented in class org.java_websocket.framing.CloseFrame
        System.out.println("Coinbase connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: " + reason);
        /*for (String i : keys){
    System.out.println( "Hashtable value " + i+ " : " + coinbaseTickers.get(i) );}*/
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
        // if the error is fatal then onClose will be called additionally
    }


}




