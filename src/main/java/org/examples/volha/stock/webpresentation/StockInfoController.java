package org.examples.volha.stock.webpresentation;

import com.google.gson.Gson;
import org.examples.volha.stock.datacapture.BitfinexSubscription;
import org.examples.volha.stock.datacapture.CoinbaseSubscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import static org.examples.volha.stock.datacapture.BitfinexClient.bitfinexConnected;
import static org.examples.volha.stock.datacapture.CoinbaseClient.coinbaseConnected;
import static org.examples.volha.stock.webpresentation.WebPresentationApplication.bitfinexClient;
import static org.examples.volha.stock.webpresentation.WebPresentationApplication.coinbaseClient;


@Controller
public class StockInfoController {
    private final BitfinexSubscription bitfinexSubscription;
    private final CoinbaseSubscription coinbaseSubscription;
    public static String[] productIDsToSubscribe;
    public static boolean bitfinexSubscribed;
    public static boolean coinbaseSubcribed;

    // private final BitfinexDataProcessor bitfinexDataProcessor;
    // private final InputProducts inputProducts;
    @Autowired
    //public StockInfoController(BitfinexDataProcessor bitfinexDataProcessor, BitfinexSubscription bitfinexSubscription)
    public StockInfoController(BitfinexSubscription bitfinexSubscription, CoinbaseSubscription coinbaseSubscription) {
        this.bitfinexSubscription = bitfinexSubscription;
        this.coinbaseSubscription = coinbaseSubscription;
        //this.bitfinexDataProcessor = bitfinexDataProcessor;
        // this.inputProducts = inputProducts;
    }
////////////////////////////m,m,
    /*

    @MessageMapping("/productsid")
    @SendTo("/products2/subscribedproducts2")
    public StockInfo stockInfo(InputProducts products) throws Exception {
        //???? return???
        //bitfinexDataProcessor.init();
        ///removed
//        Thread.sleep(1000); // simulated delay
//        productPairStatic = products.getProductPair();
//        System.out.println(" " + products.getProductPair());
//        if (bitfinexConnected) {
//            bitfinexClient.send(bitfinexSubscription.makeSubscrString(products.getProductPair()));
//            System.out.println("Bitfinex connected and subsription sent " + products.getBitfinexProductPair());
//        }
        //removed
        try {
            // return new StockInfo( coinbaseClient.coinbaseHashtable.get(products.getProductPair()).getPrice());
            // return new StockInfo("Hello, " + HtmlUtils.htmlEscape(products.getProductPair()+ "!"));
            // return new StockInfo("Hello, " + "bitfinexDataProcessor works " +products.getBitfinexProductPair() + "!");
            System.out.println("   ++++++This should be returned  " + products.getProductPairWithoutSlash());
            String temp_productID;
            System.out.println("VERYIMPORTANT!!!!   !!!++++++This should be returned  ");
            temp_productID = products.getProductPairWithoutSlash();
            //+ bitfinexDataProcessor.bitfinexProductIDDataObject.get(products.getProductPairWithoutSlash()).getPrice());
            bitfinexClient.bitfinexDataProcessor.printTest(temp_productID);
            System.out.println("   !!!++++++This should be returned  " + bitfinexClient.bitfinexDataProcessor.getPrice(temp_productID));
            return new StockInfo("Hello, " + bitfinexClient.bitfinexDataProcessor.getPrice(products.getProductPairWithoutSlash()) + "!");
            // return new StockInfo("Hello, " + products.getBitfinexProductPair() + "!");
        }
        /*    if ((bitfinexClient.bitfinexHashtable.get(BitfinexClient.productPairStatic)!=null)&(bitfinexClient.bitfinexHashtable.get(BitfinexClient.productPairStatic).getPrice()!=0)) {
                return new StockInfo("|       " + products.getProductPair() + "      |      " + products.getBitfinexProductPair() + "      |      " +
                        coinbaseClient.coinbaseHashtable.get(products.getProductPair()).getPrice() + "      |      " +
                        bitfinexClient.bitfinexHashtable.get(products.getBitfinexProductPair()).getPrice() + "      |      ");
            }
            else return  new StockInfo("|Wait a moment please");
        }*/ /*

        catch (Exception e) {
            return new StockInfo("Some problem occuried. Incorrect data " + e);

        }

    }
*/
///////////////////l;

    @MessageMapping("/products_to_subscribe")
    @SendTo("/products/subscribedproducts")
    public InputProducts inputProducts(CheckedProductPairs checkedProductPairs) throws Exception {
        Thread.sleep(1000); // simulated delay
        System.out.println("error?");
//        coinbaseClient.testPrint();
        System.out.println("error? NOt");
        productIDsToSubscribe = checkedProductPairs.getProduct_id();
        System.out.println("These are products to subscribe" + productIDsToSubscribe);
        System.out.println("???Bitfinex opened connection" + productIDsToSubscribe);
//              productPairStatic = products.getProductPair();
//        System.out.println(" " + products.getProductPair());
        if (bitfinexConnected) {
            for (String productID : productIDsToSubscribe) {
                bitfinexClient.send(bitfinexSubscription.makeSubscrString(productID));
                System.out.println("Bitfinex connected and subsription sent " + productID);
                bitfinexSubscribed = true;
            }
        }
        if (coinbaseConnected) {
            System.out.println("************coinbaseConnected************Coinbase opened connection");
            coinbaseClient.testPrint();
            coinbaseClient.send("{\n" +
                    "    \"type\": \"subscribe\",\n" +
                    "    \"product_ids\": [\n" +
                    "        \"ETH-USD\",\n" +
                    "        \"ETH-EUR\"\n" +
                    "    ],\n" +
                    "    \"channels\": [\n" +
                    "        \"level2\",\n" +
                    "        \"heartbeat\",\n" +
                    "        {\n" +
                    "            \"name\": \"ticker\",\n" +
                    "            \"product_ids\": [\n" +
                    "                \"ETH-BTC\",\n" +
                    "                \"ETH-USD\"\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}");
            System.out.println("************************Coinbase subscription sent op");
            coinbaseClient.send("{\"type\":\"subscribe\",\"channel\":\"ticker\",\"product_ids\":[\"BTC-USD\",\"BTC-EUR\",\"BTC-IOTA\"]}");
            System.out.println("************************Coinbase subscription sent opened connection" + "???????????????");

            coinbaseClient.send(coinbaseSubscription.makeSubscriptionString(productIDsToSubscribe));
            System.out.println("************************Coinbase subscription sent opened connection" + coinbaseSubscription.makeSubscriptionString(productIDsToSubscribe));
            coinbaseSubcribed = true;
        }

        return new InputProducts(productIDsToSubscribe[0]);
    }


    @MessageMapping("/products_to_display")
    @SendTo("/stockinfo/displayedprices")
    public StockInfo stockInfo(InputProducts inputProductToDisplay) throws Exception {
        Thread.sleep(1000); // simulated delay
        String productPairToDisplay = inputProductToDisplay.getProductPairWithoutSlash();
        System.out.println("???????????????????????? " + productPairToDisplay);
        try {
            // return new StockInfo( coinbaseClient.coinbaseHashtable.get(products.getProductPair()).getPrice());
            // return new StockInfo("Hello, " + HtmlUtils.htmlEscape(products.getProductPair()+ "!"));
            // return new StockInfo("Hello, " + "bitfinexDataProcessor works " +products.getBitfinexProductPair() + "!");
            System.out.println("   ++++++This should be returned  " + inputProductToDisplay.getBitfinexProductPair());
            String temp_productID;
            System.out.println("VERYIMPORTANT!!!!   !!!++++++This should be returned  ");
            temp_productID = inputProductToDisplay.getProductPairWithoutSlash();
            //+ bitfinexDataProcessor.bitfinexProductIDDataObject.get(products.getProductPairWithoutSlash()).getPrice());
            bitfinexClient.bitfinexDataProcessor.printTest(temp_productID);
            System.out.println("   !!!++++++This should be returned  " + bitfinexClient.bitfinexDataProcessor.getPrice(temp_productID));
            return new StockInfo(temp_productID + " | " + bitfinexClient.bitfinexDataProcessor.getPrice(temp_productID) + "!");
            // return new StockInfo("Hello, " + products.getBitfinexProductPair() + "!");
        }
        /*    if ((bitfinexClient.bitfinexHashtable.get(BitfinexClient.productPairStatic)!=null)&(bitfinexClient.bitfinexHashtable.get(BitfinexClient.productPairStatic).getPrice()!=0)) {
                return new StockInfo("|       " + products.getProductPair() + "      |      " + products.getBitfinexProductPair() + "      |      " +
                        coinbaseClient.coinbaseHashtable.get(products.getProductPair()).getPrice() + "      |      " +
                        bitfinexClient.bitfinexHashtable.get(products.getBitfinexProductPair()).getPrice() + "      |      ");
            }
            else return  new StockInfo("|Wait a moment please");
        }*/ catch (Exception e) {
            return new StockInfo("Some problem occuried. Incorrect data " + e);

        }

    }


}


//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
//package webrepresentation;
//
//package webrepresentation;
//
//
//
//import mysock.BitfinexClient;
//import mysock.BitfinexSubscription;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//
//import static webrepresentation.Application.bifinexClient;
//
//
//
//    @Controller
//    public class GetWebPageDataController {
//
//        static String test = null;
//
//
//        @MessageMapping("/products")
//        @SendTo("/topic2/products_subscribe")
//        public Products products(CheckedProductPairs message) throws Exception {
//            Thread.sleep(1000); // simulated delay
//
//
//            String[] productIDs = message.getProduct_id();
//            System.out.println("These are products to subscribe" + productIDs);
//            // bifinexClient.test3_allowed = "1";
//            bifinexClient.send("{\n" +
//                    "   \"event\":\"ping\",\n" +
//                    "   \"cid\": 1234\n" +
//                    "}");
//            if (bifinexClient.t == 1) {
//                for (String product : productIDs) {
//                    BitfinexClient.products = productIDs;
//
//
//                    StringBuilder convertedName = new StringBuilder(product);
//                    convertedName.deleteCharAt(3);
//                    convertedName.insert(0, "t");
//                    String bitfinexProductID = convertedName.toString();
//
//                    mysock.BitfinexSubscription bitfinexSubscription = new BitfinexSubscription(bitfinexProductID);
//                    System.out.println("Bitfinex opened connection" + bitfinexProductID);
//                    bifinexClient.send(String.valueOf(bitfinexSubscription.setSubscriptionString(bitfinexProductID)));
//                }
//
//                return new Products(productIDs);
//            }
//            else {
//                System.out.println("No Bitfinex connection");
//                return new Products(productIDs);
//
//            }
//        }
//
//        @MessageMapping("/hello")
//        @SendTo("/topic/greetings")
//        public HashTablePrint hashTablePrint(CheckedProductPairs message) throws Exception {
//            Thread.sleep(1000); // simulated delay
//            String coinbaseProductID = message.getName();
//            StringBuilder convertedName = new StringBuilder(coinbaseProductID);
//            convertedName.deleteCharAt(3);
//            String bitfinexProductID = convertedName.toString();
//            try{
//                if ((bifinexClient.bitfinexHashtable.get(bitfinexProductID)!=null)&(bifinexClient.bitfinexHashtable.get(bitfinexProductID).getPrice()!=0)) {
//                    return new HashTablePrint("|       " + coinbaseProductID + "      |      " + bitfinexProductID + "      |      " +
//                            Application.coinbaseClient.coinbaseHashtable.get(coinbaseProductID).getPrice() + "      |      " +
//                            bifinexClient.bitfinexHashtable.get(bitfinexProductID).getPrice() + "      |      ");
//                }
//                else return  new HashTablePrint("|Wait a moment please");
//            }
//            catch (Exception e){
//                return  new HashTablePrint("Some problem occuried. Incorrect data");
//
//            }}
//
//
//
//
//
//
//
//
//        @MessageMapping("/hello3")
//        @SendTo("/topic3/greetings3")
//        public HashtablePrint3 hashTablePrint3(CheckedProductPairs message) throws Exception {
//            Thread.sleep(1000); // simulated delay
//
//            String products_sent = message.getProducts_sent();
//
//            System.out.println("Subscription is allowed " + products_sent);
//            return new HashtablePrint3 (products_sent);
//        }
//    }






/*
        Thread.sleep(1000); // simulated delay
        if (BitfinexClient.productsReceived == false) {
            System.out.println("BitfinexClient.productsReceived");
             BitfinexClient.productPairStatic = products.getBitfinexProductPair();
            System.out.println(BitfinexClient.productPairStatic+"test");
            }
            return new StockInfo("Hello, " + HtmlUtils.htmlEscape(products.getProductPair()) + "!");
        }
*/


