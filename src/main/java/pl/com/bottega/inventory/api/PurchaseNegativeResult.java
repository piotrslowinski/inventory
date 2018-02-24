package pl.com.bottega.inventory.api;

import pl.com.bottega.inventory.domain.commands.PurchaseProductCommand;

import java.util.Map;

public class PurchaseNegativeResult extends PurchaseResult {


    private boolean success;

    private Map<String , Integer> missingProducts;


    public PurchaseNegativeResult(boolean success, PurchaseProductCommand cmd) {
        this.success = success;
        this.missingProducts = missingProducts;
    }
}
