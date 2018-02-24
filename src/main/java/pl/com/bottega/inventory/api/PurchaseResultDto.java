package pl.com.bottega.inventory.api;

import pl.com.bottega.inventory.domain.commands.PurchaseProductCommand;

import java.util.Map;

public class PurchaseResultDto extends PurchaseResult {

    private boolean success;

    private Map<String, Integer> purchasedProducts;

    public PurchaseResultDto(Boolean succes, PurchaseProductCommand cmd) {
        this.success = true;
        this.purchasedProducts = cmd.getProducts();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, Integer> getPurchasedProducts() {
        return purchasedProducts;
    }

    public void setPurchasedProducts(Map<String, Integer> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }
}
