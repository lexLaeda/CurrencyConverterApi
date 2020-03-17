package com.exchange.Money.model;


import com.exchange.Money.adapter.CurrencyValueAdapter;
import com.exchange.Money.adapter.MoneySerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;


import javax.persistence.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "Valute")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonAutoDetect
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore()
    private Long id;

    @XmlAttribute(name = "ID")
    @JsonProperty("id")
    private String currencyId;


    @XmlElement(name = "NumCode")
    @JsonProperty("num_code")
    private Integer numCode;


    @XmlElement(name = "CharCode")
    @JsonProperty("char_code")
    private String charCode;

    @XmlElement(name = "Nominal")
    private Integer nominal;

    @XmlElement(name = "Value")
    @XmlJavaTypeAdapter(CurrencyValueAdapter.class)
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal value;

    @Transient
    @ManyToMany(mappedBy = "currencies")
    @JsonIgnore
    private ValCurs valCurs;

    public static class Builder {
        private Currency newCurrency;

        public Builder(){
            newCurrency = new Currency();
        }
        public Builder addId(String currencyId) {
            newCurrency.currencyId = currencyId;
            return this;
        }

        public Builder addNumCode(Integer numCode) {
            newCurrency.numCode = numCode;
            return this;
        }

        public Builder addCharCode(String charCode) {
            newCurrency.charCode = charCode;
            return this;
        }

        public Builder addNominal(Integer nominal) {
            newCurrency.nominal = nominal;
            return this;
        }

        public Builder addValue(BigDecimal value) {
            newCurrency.value = value;
            return this;
        }

        public Builder addValCurs(ValCurs valCurs) {
            newCurrency.valCurs = valCurs;
            return this;
        }

        public Currency build() {
            return newCurrency;
        }
    }
}
