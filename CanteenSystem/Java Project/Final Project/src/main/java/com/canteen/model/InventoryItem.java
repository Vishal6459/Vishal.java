package com.canteen.model;

import java.math.BigDecimal;

public class InventoryItem {
    private final int itemId;
    private final String itemName;
    private final BigDecimal unitPrice;
    private final int stockCount;

    public InventoryItem(int itemId, String itemName, BigDecimal unitPrice, int stockCount) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.unitPrice = unitPrice;
        this.stockCount = stockCount;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public int getStockCount() {
        return stockCount;
    }
}

