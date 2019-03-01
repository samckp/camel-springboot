package com.sam.rest.dao;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import java.math.BigDecimal;

@CsvRecord(separator = ",", skipFirstLine = true)
public class Item {

    @DataField(pos = 1)
    private String tnxType;

    @DataField(pos = 2)
    private String skuId;

    @DataField(pos = 3)
    private String itemName;

    @DataField(pos = 4)
    private BigDecimal price;

    public String getTnxType() {
        return tnxType;
    }

    public void setTnxType(String tnxType) {
        this.tnxType = tnxType;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "\nItem { " +
                "tnxType='" + tnxType + '\'' +
                ", skuId='" + skuId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", price=" + price +
                '}' ;
    }
}
