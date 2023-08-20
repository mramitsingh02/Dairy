package com.generic.khatabook.specification.model;

import lombok.Data;
import lombok.ToString;

import java.util.Collections;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Objects;

@Data
@ToString
public class ProductRatingViews {
    private final String productId;
    private final long totalNumberRating;
    private final double minRating;
    private final double maxRating;
    private final double avgRating;
    private final List<ProductRatingDTO> productRatings;

    public ProductRatingViews(final List<ProductRatingDTO> productRatings) {

        this(null, productRatings);

    }

    public ProductRatingViews(String productId, final List<ProductRatingDTO> productRatings) {
        this.productRatings = productRatings;
        this.productId = Objects.nonNull(productId) ? productId : productRatings.get(0).productId();
        DoubleSummaryStatistics statistics = productRatings.stream().map(ProductRatingDTO::rating).mapToDouble(Float::doubleValue).summaryStatistics();
        this.totalNumberRating = statistics.getCount();
        this.minRating = statistics.getMin();
        this.maxRating = statistics.getMax();
        this.avgRating = Math.round(statistics.getAverage());
    }

    public ProductRatingViews(final String productId) {

        this(productId, Collections.emptyList());

    }


}
