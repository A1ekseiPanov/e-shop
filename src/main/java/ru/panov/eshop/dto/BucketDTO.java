package ru.panov.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BucketDTO {
    private Integer amountProduct;
    private Double sum;
    private List<BucketDetailDTO> bucketDetail = new ArrayList<>();

    public void aggregate() {
        this.amountProduct = bucketDetail.size();
        this.sum = bucketDetail.stream()
                .map(BucketDetailDTO::getSum)
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}
