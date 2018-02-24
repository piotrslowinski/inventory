package pl.com.bottega.inventory.domain;

import javax.persistence.*;
import java.awt.datatransfer.DataFlavor;
import java.util.Objects;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String skuCode;

    @Column
    private Integer amount;

    public Product() {
    }

    public Product(String code, Integer amount) {
        this.skuCode = code;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public void setCount(Integer count) {
        this.amount = count;
    }

    public void increaseCount(Integer amount) {
        this.amount = this.amount + amount;
    }

    public String getCode() {
        return skuCode;
    }

    public void decreaseCount(Integer amount){
        if(isEnough(amount))
        this.amount = this.amount - amount;
    }

    public Integer getCount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(skuCode, product.skuCode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(skuCode);
    }

    public boolean isEnough(Integer amount){
        if (this.amount >= amount)
            return true;
        else
            return false;
    }
}
