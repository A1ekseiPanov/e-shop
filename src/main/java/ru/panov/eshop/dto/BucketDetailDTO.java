package ru.panov.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.panov.eshop.model.Product;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BucketDetailDTO {
    private Long productId;
    private String title;
    private BigDecimal amount;
    private BigDecimal price;
    private Double sum;

    public BucketDetailDTO(Product product) {
        this.productId = product.getId();
        this.title = product.getTitle();
        this.amount = new BigDecimal(1);
        this.price = product.getPrice();
        this.sum = Double.valueOf(product.getPrice().toString());
    }
}
