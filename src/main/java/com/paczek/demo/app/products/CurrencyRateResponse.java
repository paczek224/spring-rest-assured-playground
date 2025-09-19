package com.paczek.demo.app.products;

import java.util.List;

public record CurrencyRateResponse( String table,
         String currency,
         String code,
         List<Rate>rates){
}
