package com.example.infinitiumassignment.model;

public enum Currency {
    // We are using USD as main source of conversion
    IDR(15000D), USD(1D), MYR(5D);

    final Double conversionRate;

    Currency(Double conversionRate) {
        this.conversionRate = conversionRate;
    }

    public Double getConversionRate() {
        return this.conversionRate;
    }

    public Double convert(Double amount, Currency to) {
        if (to == USD) {
            return amount / this.conversionRate;
        } else {
            Double usdConversion = this.convert(amount, USD);
            return usdConversion * to.getConversionRate();
        }
    }
}
