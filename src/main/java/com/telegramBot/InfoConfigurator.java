package com.telegramBot;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.json.JSONArray;

import java.io.IOException;


@NoArgsConstructor
@AllArgsConstructor
public class InfoConfigurator {
    Settings settings;

    @SneakyThrows
    public String price_configurator(Bank bank){
        StringBuilder price = new StringBuilder();
        for(String currency : settings.currency)
        {
            if(!bank.name.equals("NBU"))
            {
                switch (currency) {
                    case "USD": {
                        String buys = String.format("%." + settings.after_comma_symbol  + "f", bank.buyUSD);
                        String sales = String.format("%." + settings.after_comma_symbol  + "f", bank.saleUSD);
                        price.append("\nUSD/UAH \nBuy: ").append(buys).append("\nSale: ").append(sales).append("\n");
                        break;
                    }
                    case "EUR": {
                        String buys = String.format("%." + settings.after_comma_symbol  + "f", bank.buyEUR);
                        String sales = String.format("%." + settings.after_comma_symbol  + "f", bank.saleEUR);
                        price.append("\nEUR/UAH \nBuy: ").append(buys).append("\nSale: ").append(sales).append("\n");
                        break;
                    }
                    case "RUB": {
                        String buys = String.format("%." + settings.after_comma_symbol  + "f", bank.buyRUB);
                        String sales = String.format("%." + settings.after_comma_symbol  + "f", bank.saleRUB);
                        price.append("\nRUB/UAH \nBuy: ").append(buys).append("\nSale: ").append(sales).append("\n");
                        break;
                    }
                }
            }
            else
            {
                switch (currency) {
                    case "USD": {
                        String rates = String.format("%." + settings.after_comma_symbol  + "f", bank.rateUSD);
                        price.append("\nUSD/UAH \nRate: ").append(rates).append("\n");
                        break;
                    }
                    case "EUR": {
                        String buys = String.format("%." + settings.after_comma_symbol  + "f", bank.rateEUR);
                        price.append("\nEUR/UAH \nRate: ").append(buys).append("\n");
                        break;
                    }
                    case "RUB": {
                        String buys = String.format("%." + settings.after_comma_symbol  + "f", bank.rateRUB);
                        price.append("\nRUB/UAH \nRate: ").append(buys).append("\n");
                        break;
                    }
                }
            }
        }
        return price.toString();
    }

    @SneakyThrows
    public String configure_info(Bank bank, String bankInfo)  {
        return bankInfo.concat(price_configurator(bank));
    }
}
