package com.telegramBot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class Settings {

    @Getter
    @Setter
    String chat_id;

    @Getter
    @Setter
    int after_comma_symbol;

    @Getter
    @Setter
    String bank;

    @Getter
    @Setter
    List<String> currency = new ArrayList<>();

    @Getter
    @Setter
    String notification;

    Settings()
    {
        this.after_comma_symbol = 2;
        this.bank = "Privat";
        this.currency.add("USD");
        this.currency.add("RUB");
        this.notification = "9:00";
    }
    Settings(String new_chat_id)
    {
        this.chat_id = new_chat_id;
        this.after_comma_symbol = 2;
        this.bank = "Privat";
        this.currency.add("USD");
        this.currency.add("RUB");
        this.notification = "9:00";
    }
}