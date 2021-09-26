package com.telegramBot;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

@NoArgsConstructor
class NotificationTimer {
    Timer mTimer = new Timer();
    Timer bankTimer = new Timer();
    @SneakyThrows
    public void setTimer(Bot bot, String chat_id) {
        if (bot.settings.notification.equals("Turn off notifications"))
        {
            mTimer.cancel();
            mTimer.purge();
            return;
        }
        mTimer.cancel();
        mTimer.purge();
        NotificationTask mMyTimerTask = new NotificationTask(bot, chat_id);
        long delay = 0;
        Calendar cal = Calendar.getInstance();
        cal.set(1970, Calendar.JANUARY, 1);
        DateFormat dateFormat = new SimpleDateFormat("k:mm", new Locale("en"));
        if (dateFormat.parse(bot.settings.notification).getTime() - 10800000 > cal.getTimeInMillis())
        {
            delay = dateFormat.parse(bot.settings.notification).getTime() - 10800000 - cal.getTimeInMillis();
        }
        else
        {
            delay = 86400000 + dateFormat.parse(bot.settings.notification).getTime() - 10800000 - cal.getTimeInMillis();
        }
        mTimer.schedule(mMyTimerTask, delay);
    }
    @SneakyThrows
    public void setBankTimer(Bot bot) {
        BankTask mMyTimerTask = new BankTask(bot, this);
        long delay = 300000;
        bankTimer.schedule(mMyTimerTask, delay);
    }
}


@AllArgsConstructor
class NotificationTask extends TimerTask {
    Bot bot;
    String chat_id;
    @Override
    @SneakyThrows
    public void run() {
        String answer = "";
        switch (bot.settings.bank) {
            case "Privat" -> answer = new InfoConfigurator(bot.settings).configure_info(bot.Privatbank, "Currency exchange rate in Privatbank: \n");
            case "NBU" -> answer = new InfoConfigurator(bot.settings).configure_info(bot.NBU, "Currency exchange rate in NBU: \n");
            case "Mono" -> answer = new InfoConfigurator(bot.settings).configure_info(bot.Monobank, "Currency exchange rate in Monobank: \n");
        }
        SendMessage new_message = new SendMessage();
        new_message.setChatId(chat_id);
        new_message.setText(answer);
        new_message.setReplyMarkup(bot.startMenu());
        bot.timer.setTimer(bot, chat_id);
        try {
            bot.execute(new_message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
@AllArgsConstructor
class BankTask extends TimerTask {
    Bot bot;
    NotificationTimer notificationTimer;
    @Override
    @SneakyThrows
    public void run() {
        bot.Privatbank.updateInfo();
        bot.Monobank.updateInfo();
        bot.NBU.updateInfo();
        notificationTimer.setBankTimer(bot);
    }
}
