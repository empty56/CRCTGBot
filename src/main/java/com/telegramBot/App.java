package com.telegramBot;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.TimerTask;
import java.util.logging.Level;

public class App {

    public static void main(String[] args) {
        NotificationTimer timer = new NotificationTimer();
        Bot bot = new Bot();
        bot.botConnect();
    }
}