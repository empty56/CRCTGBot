package com.telegramBot;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@NoArgsConstructor
public class Bank {

    @Getter
    @Setter
    String name;
    @Getter
    @Setter
    double buyUSD = 0;
    @Getter
    @Setter
    public double saleUSD = 0;
    @Getter
    @Setter
    public double buyEUR = 0;
    @Getter
    @Setter
    public double saleEUR = 0;
    @Getter
    @Setter
    public double buyRUB = 0;
    @Getter
    @Setter
    public double saleRUB = 0;
    @Getter
    @Setter
    public double rateUSD = 0;
    @Getter
    @Setter
    public double rateEUR = 0;
    @Getter
    @Setter
    public double rateRUB = 0;

    @SneakyThrows
    private static String readAll(BufferedReader rd)  {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    @SneakyThrows
    public static JSONArray readJsonFromUrl(String url)  {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            return new JSONArray(jsonText);
        }
    }

    public void updateInfo(){
        JSONArray currencies;
        switch (name) {
            case "Privat" -> {
                currencies = readJsonFromUrl("https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11");
                for (int i = 0; i < currencies.length(); i++) {
                    if (currencies.getJSONObject(i).get("ccy").equals("USD")) {
                        buyUSD = Double.parseDouble(currencies.getJSONObject(i).get("buy").toString());
                        saleUSD = Double.parseDouble(currencies.getJSONObject(i).get("sale").toString());
                    } else if (currencies.getJSONObject(i).get("ccy").equals("EUR")) {
                        buyEUR = Double.parseDouble(currencies.getJSONObject(i).get("buy").toString());
                        saleEUR = Double.parseDouble(currencies.getJSONObject(i).get("sale").toString());
                    } else if (currencies.getJSONObject(i).get("ccy").equals("RUR")) {
                        buyRUB = Double.parseDouble(currencies.getJSONObject(i).get("buy").toString());
                        saleRUB = Double.parseDouble(currencies.getJSONObject(i).get("sale").toString());
                    }
                }
            }
            case "NBU" -> {
                currencies = readJsonFromUrl("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?date=20200302&json");
                for (int i = 0; i < currencies.length(); i++) {
                    if (currencies.getJSONObject(i).get("r030").toString().equals("840")) {
                        rateUSD = Double.parseDouble(currencies.getJSONObject(i).get("rate").toString());
                    } else if (currencies.getJSONObject(i).get("r030").toString().equals("978")) {
                        rateEUR = Double.parseDouble(currencies.getJSONObject(i).get("rate").toString());
                    } else if (currencies.getJSONObject(i).get("r030").toString().equals("643")) {
                        rateRUB = Double.parseDouble(currencies.getJSONObject(i).get("rate").toString());
                    }
                }
            }
            case "Mono" -> {
                currencies = readJsonFromUrl("https://api.monobank.ua/bank/currency");
                for (int i = 0; i < currencies.length(); i++) {
                    if (currencies.getJSONObject(i).get("currencyCodeA").toString().equals("840") && currencies.getJSONObject(i).get("currencyCodeB").toString().equals("980")) {
                        buyUSD = Double.parseDouble(currencies.getJSONObject(i).get("rateBuy").toString());
                        saleUSD = Double.parseDouble(currencies.getJSONObject(i).get("rateSell").toString());
                    } else if (currencies.getJSONObject(i).get("currencyCodeA").toString().equals("978") && currencies.getJSONObject(i).get("currencyCodeB").toString().equals("980")) {
                        buyEUR = Double.parseDouble(currencies.getJSONObject(i).get("rateBuy").toString());
                        saleEUR = Double.parseDouble(currencies.getJSONObject(i).get("rateSell").toString());
                    } else if (currencies.getJSONObject(i).get("currencyCodeA").toString().equals("643") && currencies.getJSONObject(i).get("currencyCodeB").toString().equals("980")) {
                        buyRUB = Double.parseDouble(currencies.getJSONObject(i).get("rateBuy").toString());
                        saleRUB = Double.parseDouble(currencies.getJSONObject(i).get("rateSell").toString());
                    }
                }
            }
        }
    }

    @SneakyThrows
    Bank(String bank)  {
        JSONArray currencies;
        name = bank;
        switch (bank) {
            case "Privat" -> {
                currencies = readJsonFromUrl("https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11");
                for (int i = 0; i < currencies.length(); i++) {
                    if (currencies.getJSONObject(i).get("ccy").equals("USD")) {
                        buyUSD = Double.parseDouble(currencies.getJSONObject(i).get("buy").toString());
                        saleUSD = Double.parseDouble(currencies.getJSONObject(i).get("sale").toString());
                    } else if (currencies.getJSONObject(i).get("ccy").equals("EUR")) {
                        buyEUR = Double.parseDouble(currencies.getJSONObject(i).get("buy").toString());
                        saleEUR = Double.parseDouble(currencies.getJSONObject(i).get("sale").toString());
                    } else if (currencies.getJSONObject(i).get("ccy").equals("RUR")) {
                        buyRUB = Double.parseDouble(currencies.getJSONObject(i).get("buy").toString());
                        saleRUB = Double.parseDouble(currencies.getJSONObject(i).get("sale").toString());
                    }
                }
            }
            case "NBU" -> {
                currencies = readJsonFromUrl("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?date=20200302&json");
                for (int i = 0; i < currencies.length(); i++) {
                    if (currencies.getJSONObject(i).get("r030").toString().equals("840")) {
                        rateUSD = Double.parseDouble(currencies.getJSONObject(i).get("rate").toString());
                    } else if (currencies.getJSONObject(i).get("r030").toString().equals("978")) {
                        rateEUR = Double.parseDouble(currencies.getJSONObject(i).get("rate").toString());
                    } else if (currencies.getJSONObject(i).get("r030").toString().equals("643")) {
                        rateRUB = Double.parseDouble(currencies.getJSONObject(i).get("rate").toString());
                    }
                }
            }
            case "Mono" -> {
                currencies = readJsonFromUrl("https://api.monobank.ua/bank/currency");
                for (int i = 0; i < currencies.length(); i++) {
                    if (currencies.getJSONObject(i).get("currencyCodeA").toString().equals("840") && currencies.getJSONObject(i).get("currencyCodeB").toString().equals("980")) {
                        buyUSD = Double.parseDouble(currencies.getJSONObject(i).get("rateBuy").toString());
                        saleUSD = Double.parseDouble(currencies.getJSONObject(i).get("rateSell").toString());
                        ;
                    } else if (currencies.getJSONObject(i).get("currencyCodeA").toString().equals("978") && currencies.getJSONObject(i).get("currencyCodeB").toString().equals("980")) {
                        buyEUR = Double.parseDouble(currencies.getJSONObject(i).get("rateBuy").toString());
                        saleEUR = Double.parseDouble(currencies.getJSONObject(i).get("rateSell").toString());
                        ;
                    } else if (currencies.getJSONObject(i).get("currencyCodeA").toString().equals("643") && currencies.getJSONObject(i).get("currencyCodeB").toString().equals("980")) {
                        buyRUB = Double.parseDouble(currencies.getJSONObject(i).get("rateBuy").toString());
                        saleRUB = Double.parseDouble(currencies.getJSONObject(i).get("rateSell").toString());
                        ;
                    }
                }
            }
        }
    }
}
