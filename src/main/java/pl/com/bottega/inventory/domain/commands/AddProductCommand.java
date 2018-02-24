package pl.com.bottega.inventory.domain.commands;

public class AddProductCommand implements Command {

    String skuCode;

    Integer amount;

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setCount(Integer count) {
        this.amount = count;
    }

    public void validate(Validatable.ValidationErrors errors){
        validatePresence(errors, "skuCode", skuCode);
        validatePresence(errors, "amount", amount);
        validateAmount(errors, amount);
    }

    private void validateAmount(Validatable.ValidationErrors errors, Integer amount) {
        if(!(amount == null))
            if (amount <= 0 || amount > 999)
                errors.add("amount", "must be between 1 and 999");
    }
}
