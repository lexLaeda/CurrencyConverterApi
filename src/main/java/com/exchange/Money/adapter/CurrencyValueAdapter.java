package com.exchange.Money.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.math.BigDecimal;

public class CurrencyValueAdapter extends XmlAdapter<String, BigDecimal> {
    @Override
    public BigDecimal unmarshal(String s) throws Exception {
        Double value = Double.parseDouble(s.replaceAll(",","."));
        return new BigDecimal(value);
    }

    @Override
    public String marshal(BigDecimal bigDecimal) throws Exception {
        return bigDecimal.toString();
    }
}
