package org.examples.volha.stock.webpresentation;

public class CheckedProductPairs {
    String[] product_id;

    public CheckedProductPairs(String[] product_id) {
        this.product_id = product_id;
    }

    public CheckedProductPairs() {
    }

    public String[] getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String[] product_id) {
        this.product_id = product_id;
    }
}
