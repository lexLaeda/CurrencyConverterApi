package com.exchange.Money.model;

import com.exchange.Money.adapter.MoneySerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@JsonAutoDetect
public class ConversionForm {

    @JsonProperty("customer_currency")
    private Currency customerCurrency;

    @JsonProperty("cash")
    private BigDecimal customerAmount;

    @JsonProperty("target_currency")
    private Currency targetCurrency;

    @JsonProperty("converted_cash")
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal targetAmount;

    @JsonProperty("operation_date")
    private LocalDate convertTime;

    @JsonProperty("last_update_date")
    private LocalDate timeOfUpdate;


    public static class Builder {
        private ConversionForm newConversionForm;

        public Builder() {
            newConversionForm = new ConversionForm();
        }

        public Builder customerCurrency(Currency customerCurrency) {
            newConversionForm.customerCurrency = customerCurrency;
            return this;
        }

        public Builder customerAmount(BigDecimal customerAmount) {
            newConversionForm.customerAmount = customerAmount;
            return this;
        }

        public Builder targetCurrency(Currency targetCurrency) {
            newConversionForm.targetCurrency = targetCurrency;
            return this;
        }

        public Builder targetAmount(BigDecimal targetAmount) {
            newConversionForm.targetAmount = targetAmount;
            return this;
        }

        public Builder convertTime(LocalDate convertTime) {
            newConversionForm.convertTime = convertTime;
            return this;
        }

        public Builder timeOfUpdate(LocalDate timeOfUpdate) {
            newConversionForm.timeOfUpdate = timeOfUpdate;
            return this;
        }

        public ConversionForm build() {
            return newConversionForm;
        }

    }
}
