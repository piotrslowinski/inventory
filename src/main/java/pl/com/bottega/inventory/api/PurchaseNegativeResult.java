package pl.com.bottega.inventory.api;

import pl.com.bottega.inventory.domain.commands.PurchaseProductCommand;

import java.util.Map;

public class PurchaseNegativeResult extends PurchaseResult {


    private boolean success;

    private Map<String , Integer> missingProducts;


    public PurchaseNegativeResult(boolean success,  Map<String, Integer> products) {
        this.success = success;
        this.missingProducts = products;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, Integer> getMissingProducts() {
        return missingProducts;
    }

    public void setMissingProducts(Map<String, Integer> missingProducts) {
        this.missingProducts = missingProducts;
    }
}
