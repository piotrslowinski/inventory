package pl.com.bottega.inventory.api;

public class ProductDto {

    private Integer id;

    private String skuCode;

    private Integer amount;


    public ProductDto(Integer id, String skuCode, Integer amount) {
        this.id = id;
        this.skuCode = skuCode;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
