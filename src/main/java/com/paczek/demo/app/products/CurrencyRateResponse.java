package com.paczek.demo.app.products;

import java.util.List;
import java.util.Map;

public record CurrencyRateResponse( String table,
         String currency,
         String code,
         List<Rate>rates){
}
