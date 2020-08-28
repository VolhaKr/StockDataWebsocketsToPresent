package org.examples.volha.stock.webpresentation;

public class InputProducts {

    private String productPair;
    private String bitfinexProductPair;
    private String productPairWithoutSlash;

    public InputProducts(String productPair, String bitfinexProductPair, String productPairWithoutSlash) {
        this.productPair = productPair;
        this.bitfinexProductPair = bitfinexProductPair;
        this.productPairWithoutSlash = productPairWithoutSlash;
    }

    public void setBitfinexProductPair(String bitfinexProductPair) {
        this.bitfinexProductPair = bitfinexProductPair;
    }

    public void setProductPairWithoutSlash(String productPairWithoutSlash) {
        this.productPairWithoutSlash = productPairWithoutSlash;
    }

    public InputProducts(String productPair, String bitfinexProductPair) {
        this.productPair = productPair;
        this.bitfinexProductPair = bitfinexProductPair;
    }

    public InputProducts(String productPair) {
        this.productPair = productPair;
    }

    public InputProducts() {
    }

    public void setProductPair(String productPair) {
        this.productPair = productPair;
    }

    public String getBitfinexProductPair() {
        bitfinexProductPair = "t" + productPair.substring(0, 3) + productPair.substring(4);
        //        StringBuilder convertedName = new StringBuilder(productPair);
//        convertedName.deleteCharAt(3);
//        convertedName.insert(0, "t");
//        String bitfinexProductPair = convertedName.toString();
        return bitfinexProductPair;
    }


    public String getProductPair() {

        return productPair;
    }

    public String getProductPairWithoutSlash() {
        return productPair.substring(0, 3) + productPair.substring(4);
    }


//    public void setBitfinexProductPair(String productPair) {
//
//        StringBuilder convertedName = new StringBuilder(productPair);
//        convertedName.deleteCharAt(3);
//        convertedName.insert(0, "t");
//        String bitfinexProductPair = convertedName.toString();
//    }

}