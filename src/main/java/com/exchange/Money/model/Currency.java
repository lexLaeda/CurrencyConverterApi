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
}
