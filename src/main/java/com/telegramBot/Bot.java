package com.telegramBot;

import lombok.*;
import io.github.cdimascio.dotenv.Dotenv;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import com.vdurmont.emoji.EmojiParser;

import java.util.List;
import java.util.ArrayList;


@AllArgsConstructor
@NoArgsConstructor
public class Bot extends TelegramLongPollingBot {

    @Setter
    @Getter
    String userName = System.getenv("BOT_NAME");
    @Setter
    @Getter
    String token = System.getenv("BOT_TOKEN");

    @Getter
    @Setter
    Database database = new Database();
    @Getter
    @Setter
    Settings settings = new Settings();
    @Getter
    @Setter
    Bank Privatbank = new Bank("Privat");
    @Getter
    @Setter
    Bank Monobank = new Bank("Mono");
    @Getter
    @Setter
    Bank NBU = new Bank("NBU");

    public NotificationTimer timer = new NotificationTimer();

    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public InlineKeyboardMarkup startMenu(){
        InlineKeyboardMarkup startInline = new InlineKeyboardMarkup();
        InlineKeyboardButton getInfo = new InlineKeyboardButton();
        getInfo.setText("Get information");
        getInfo.setCallbackData("info_callback");
        InlineKeyboardButton settings_button = new InlineKeyboardButton();
        settings_button.setText("Settings");
        settings_button.setCallbackData("settings_callback");
        List<InlineKeyboardButton> infoButton = new ArrayList<>();
        List<InlineKeyboardButton> settingsButton = new ArrayList<>();
        List<List<InlineKeyboardButton>> startButtonRows = new ArrayList<>();
        infoButton.add(getInfo);
        settingsButton.add(settings_button);
        startButtonRows.add(infoButton);
        startButtonRows.add(settingsButton);
        startInline.setKeyboard(startButtonRows);
        return startInline;
    }

    public InlineKeyboardMarkup settingsMenu(){
        InlineKeyboardMarkup settingsMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton acs = new InlineKeyboardButton();
        acs.setText("Number of symbols after comma");
        acs.setCallbackData("acs_callback");
        InlineKeyboardButton bank = new InlineKeyboardButton();
        bank.setText("Bank");
        bank.setCallbackData("bank_callback");
        InlineKeyboardButton currency = new InlineKeyboardButton();
        currency.setText("Currency");
        currency.setCallbackData("currency_callback");
        InlineKeyboardButton notifications = new InlineKeyboardButton();
        notifications.setText("Notifications");
        notifications.setCallbackData("notification_callback");
        InlineKeyboardButton back = new InlineKeyboardButton();
        back.setText("Back");
        back.setCallbackData("back_to_start_callback");
        List<InlineKeyboardButton> acsButton = new ArrayList<>();
        List<InlineKeyboardButton> bankButton = new ArrayList<>();
        List<InlineKeyboardButton> currencyButton = new ArrayList<>();
        List<InlineKeyboardButton> notificationButton = new ArrayList<>();
        List<InlineKeyboardButton> backButton = new ArrayList<>();
        List<List<InlineKeyboardButton>> settingsButtonRows = new ArrayList<>();
        acsButton.add(acs);
        bankButton.add(bank);
        currencyButton.add(currency);
        backButton.add(back);
        notificationButton.add(notifications);
        settingsButtonRows.add(acsButton);
        settingsButtonRows.add(bankButton);
        settingsButtonRows.add(currencyButton);
        settingsButtonRows.add(notificationButton);
        settingsButtonRows.add(backButton);
        settingsMarkup.setKeyboard(settingsButtonRows);
        return settingsMarkup;
    }

    public InlineKeyboardMarkup acsMenu(){

        InlineKeyboardMarkup acsMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton acs2 = new InlineKeyboardButton();
        if(settings.after_comma_symbol == 2)
        {
            acs2.setText(EmojiParser.parseToUnicode(":white_check_mark: "+ "2"));
        }
        else
        {
            acs2.setText("2");
        }
        acs2.setCallbackData("set_acs2_callback");
        InlineKeyboardButton acs3 = new InlineKeyboardButton();
        if(settings.after_comma_symbol == 3)
        {
            acs3.setText(EmojiParser.parseToUnicode(":white_check_mark: "+ "3"));
        }
        else
        {
            acs3.setText("3");
        }
        acs3.setCallbackData("set_acs3_callback");
        InlineKeyboardButton acs4 = new InlineKeyboardButton();
        if(settings.after_comma_symbol == 4)
        {
            acs4.setText(EmojiParser.parseToUnicode(":white_check_mark: "+ "4"));
        }
        else
        {
            acs4.setText("4");
        }
        acs4.setCallbackData("set_acs4_callback");
        InlineKeyboardButton back = new InlineKeyboardButton();
        back.setText("Back");
        back.setCallbackData("back_to_settings_callback");
        List<InlineKeyboardButton> acsButton2 = new ArrayList<>();
        List<InlineKeyboardButton> acsButton3= new ArrayList<>();
        List<InlineKeyboardButton> acsButton4 = new ArrayList<>();
        List<InlineKeyboardButton> backButton = new ArrayList<>();
        List<List<InlineKeyboardButton>> acsButtonRows = new ArrayList<>();
        acsButton2.add(acs2);
        acsButton3.add(acs3);
        acsButton4.add(acs4);
        backButton.add(back);
        acsButtonRows.add(acsButton2);
        acsButtonRows.add(acsButton3);
        acsButtonRows.add(acsButton4);
        acsButtonRows.add(backButton);
        acsMarkup.setKeyboard(acsButtonRows);
        return acsMarkup;
    }

    public InlineKeyboardMarkup bankMenu(){

        InlineKeyboardMarkup bankMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton bank1 = new InlineKeyboardButton();
        if(settings.bank.equals("NBU"))
        {
            bank1.setText(EmojiParser.parseToUnicode(":white_check_mark: "+ "National Bank of Ukraine"));
        }
        else
        {
            bank1.setText("National Bank of Ukraine");
        }
        bank1.setCallbackData("NBU_callback");
        InlineKeyboardButton bank2 = new InlineKeyboardButton();
        if(settings.bank.equals("Privat"))
        {
            bank2.setText(EmojiParser.parseToUnicode(":white_check_mark: "+ "Privatbank"));
        }
        else
        {
            bank2.setText("Privatbank");
        }
        bank2.setCallbackData("Privatbank_callback");
        InlineKeyboardButton bank3 = new InlineKeyboardButton();
        if(settings.bank.equals("Mono"))
        {
            bank3.setText(EmojiParser.parseToUnicode(":white_check_mark: "+ "Monobank"));
        }
        else
        {
            bank3.setText("Monobank");
        }
        bank3.setCallbackData("Monobank_callback");
        InlineKeyboardButton back = new InlineKeyboardButton();
        back.setText("Back");
        back.setCallbackData("back_to_settings_callback");
        List<InlineKeyboardButton> bankButton1 = new ArrayList<>();
        List<InlineKeyboardButton> bankButton2= new ArrayList<>();
        List<InlineKeyboardButton> bankButton3 = new ArrayList<>();
        List<InlineKeyboardButton> backButton = new ArrayList<>();
        List<List<InlineKeyboardButton>> bankButtonRows = new ArrayList<>();
        bankButton1.add(bank1);
        bankButton2.add(bank2);
        bankButton3.add(bank3);
        backButton.add(back);
        bankButtonRows.add(bankButton1);
        bankButtonRows.add(bankButton2);
        bankButtonRows.add(bankButton3);
        bankButtonRows.add(backButton);
        bankMarkup.setKeyboard(bankButtonRows);
        return bankMarkup;
    }

    public InlineKeyboardMarkup currencyMenu(){
        InlineKeyboardMarkup currencyMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton USD = new InlineKeyboardButton();
        if(settings.currency.contains("USD"))
        {
            USD.setText(EmojiParser.parseToUnicode(":white_check_mark: "+ "USD"));
        }
        else
        {
            USD.setText("USD");
        }
        USD.setCallbackData("USD_callback");
        InlineKeyboardButton EUR = new InlineKeyboardButton();
        if(settings.currency.contains("EUR"))
        {
            EUR.setText(EmojiParser.parseToUnicode(":white_check_mark: "+ "EUR"));
        }
        else
        {
            EUR.setText("EUR");
        }
        EUR.setCallbackData("EUR_callback");
        InlineKeyboardButton RUB = new InlineKeyboardButton();
        if(settings.currency.contains("RUB"))
        {
            RUB.setText(EmojiParser.parseToUnicode(":white_check_mark: "+ "RUB"));
        }
        else
        {
            RUB.setText("RUB");
        }
        RUB.setCallbackData("RUB_callback");
        InlineKeyboardButton back = new InlineKeyboardButton();
        back.setText("Back");
        back.setCallbackData("back_to_settings_callback");
        List<InlineKeyboardButton> USDButton = new ArrayList<>();
        List<InlineKeyboardButton> EURButton= new ArrayList<>();
        List<InlineKeyboardButton> RUBButton = new ArrayList<>();
        List<InlineKeyboardButton> backButton = new ArrayList<>();
        List<List<InlineKeyboardButton>> currencyButtonRows = new ArrayList<>();
        USDButton.add(USD);
        EURButton.add(EUR);
        RUBButton.add(RUB);
        backButton.add(back);
        currencyButtonRows.add(USDButton);
        currencyButtonRows.add(EURButton);
        currencyButtonRows.add(RUBButton);
        currencyButtonRows.add(backButton);
        currencyMarkup.setKeyboard(currencyButtonRows);
        return currencyMarkup;
    }

    public InlineKeyboardButton notificationButton(String time, String callback_name){
        InlineKeyboardButton hours = new InlineKeyboardButton();
        if(settings.notification.equals(time))
        {
            hours.setText(EmojiParser.parseToUnicode(":white_check_mark: "+ time));
        }
        else
        {
            hours.setText(time);
        }
        hours.setCallbackData(callback_name);
        return hours;
    }

    public InlineKeyboardMarkup notificationMenu(){
        InlineKeyboardMarkup notificationMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton hours1 = notificationButton("9:00", "hours1_callback");
        InlineKeyboardButton hours2 = notificationButton("10:00", "hours2_callback");
        InlineKeyboardButton hours3 = notificationButton("11:00", "hours3_callback");
        InlineKeyboardButton hours4 = notificationButton("12:00", "hours4_callback");
        InlineKeyboardButton hours5 = notificationButton("13:00", "hours5_callback");
        InlineKeyboardButton hours6 = notificationButton("14:00", "hours6_callback");
        InlineKeyboardButton hours7 = notificationButton("15:00", "hours7_callback");
        InlineKeyboardButton hours8 = notificationButton("16:00", "hours8_callback");
        InlineKeyboardButton hours9 = notificationButton("17:00", "hours9_callback");
        InlineKeyboardButton hours10 = notificationButton("18:00", "hours10_callback");
        InlineKeyboardButton hours11 = notificationButton("Turn off notifications", "hours11_callback");
        InlineKeyboardButton back = new InlineKeyboardButton();
        back.setText("Back");
        back.setCallbackData("back_to_settings_callback");
        ArrayList<InlineKeyboardButton> keyboardRow1 = new ArrayList<>();
        ArrayList<InlineKeyboardButton> keyboardRow2 = new ArrayList<>();
        ArrayList<InlineKeyboardButton> keyboardRow3 = new ArrayList<>();
        ArrayList<InlineKeyboardButton> keyboardRow4 = new ArrayList<>();
        ArrayList<InlineKeyboardButton> keyboardRow5 = new ArrayList<>();
        List<List<InlineKeyboardButton>> keyboardButtonRows = new ArrayList<>();
        keyboardRow1.add(hours1);
        keyboardRow1.add(hours2);
        keyboardRow1.add(hours3);
        keyboardRow2.add(hours4);
        keyboardRow2.add(hours5);
        keyboardRow2.add(hours6);
        keyboardRow3.add(hours7);
        keyboardRow3.add(hours8);
        keyboardRow3.add(hours9);
        keyboardRow4.add(hours10);
        keyboardRow4.add(hours11);
        keyboardRow5.add(back);
        keyboardButtonRows.add(keyboardRow1);
        keyboardButtonRows.add(keyboardRow2);
        keyboardButtonRows.add(keyboardRow3);
        keyboardButtonRows.add(keyboardRow4);
        keyboardButtonRows.add(keyboardRow5);
        notificationMarkup.setKeyboard(keyboardButtonRows);
        return notificationMarkup;
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.getText().equals("/start")) {
                SendMessage newMessage = new SendMessage();
                if(database.checkExisting(message.getChatId().toString()))
                {
                    settings = database.getSettings(message.getChatId().toString());
                }
                else
                {
                    settings = database.setSettings(message.getChatId().toString());
                }
                newMessage.setChatId(message.getChatId().toString());
                newMessage.setText("Welcome. This bot will help you to track current exchange rates");
                newMessage.setReplyMarkup(startMenu());
                try {
                    execute(newMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (update.hasCallbackQuery()) {
            String callback_data = update.getCallbackQuery().getData();
            String chat_id = update.getCallbackQuery().getMessage().getChatId().toString();
            if(database.checkExisting(update.getCallbackQuery().getMessage().getChatId().toString()))
            {
                settings = database.getSettings(update.getCallbackQuery().getMessage().getChatId().toString());
            }
            else
            {
                settings = database.setSettings(update.getCallbackQuery().getMessage().getChatId().toString());
            }
            switch (callback_data) {
                case "info_callback" -> {
                    String answer = "";
                    switch (settings.bank) {
                        case "Privat" -> answer = new InfoConfigurator(settings).configure_info(Privatbank, "Currency exchange rate in Privatbank: \n");
                        case "NBU" -> answer = new InfoConfigurator(settings).configure_info(NBU, "Currency exchange rate in NBU: \n");
                        case "Mono" -> answer = new InfoConfigurator(settings).configure_info(Monobank, "Currency exchange rate in Monobank: \n");
                    }
                    SendMessage new_message = new SendMessage();
                    new_message.setChatId(chat_id);
                    new_message.setText(answer);
                    new_message.setReplyMarkup(startMenu());
                    try {
                        execute(new_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case "settings_callback" -> {
                    String answer = "Settings";
                    SendMessage new_message = new SendMessage();
                    new_message.setChatId(chat_id);
                    new_message.setText(answer);
                    new_message.setReplyMarkup(settingsMenu());
                    try {
                        execute(new_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case "acs_callback" -> {
                    String answer = "Select the number of decimal places";
                    SendMessage new_message = new SendMessage();
                    new_message.setChatId(chat_id);
                    new_message.setText(answer);
                    new_message.setReplyMarkup(acsMenu());
                    try {
                        execute(new_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case "set_acs2_callback" -> {
                    if (settings.after_comma_symbol == 2) {
                        break;
                    } else {
                        database.updateIntSettings(update.getCallbackQuery().getMessage().getChatId().toString(), "after_comma_symbol", 2);
                        settings.after_comma_symbol = database.getSettings(update.getCallbackQuery().getMessage().getChatId().toString()).after_comma_symbol;
                        ;
                    }
                    EditMessageText edited_message = new EditMessageText();
                    edited_message.setChatId(chat_id);
                    edited_message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    edited_message.setText(update.getCallbackQuery().getMessage().getText());
                    edited_message.setReplyMarkup(acsMenu());
                    try {
                        execute(edited_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case "set_acs3_callback" -> {
                    if (settings.after_comma_symbol == 3) {
                        break;
                    } else {
                        database.updateIntSettings(update.getCallbackQuery().getMessage().getChatId().toString(), "after_comma_symbol", 3);
                        settings.after_comma_symbol = database.getSettings(update.getCallbackQuery().getMessage().getChatId().toString()).after_comma_symbol;
                        ;
                    }
                    EditMessageText edited_message = new EditMessageText();
                    edited_message.setChatId(chat_id);
                    edited_message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    edited_message.setText(update.getCallbackQuery().getMessage().getText());
                    edited_message.setReplyMarkup(acsMenu());
                    try {
                        execute(edited_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case "set_acs4_callback" -> {
                    if (settings.after_comma_symbol == 4) {
                        break;
                    } else {
                        database.updateIntSettings(update.getCallbackQuery().getMessage().getChatId().toString(), "after_comma_symbol", 4);
                        settings.after_comma_symbol = database.getSettings(update.getCallbackQuery().getMessage().getChatId().toString()).after_comma_symbol;
                        ;
                    }
                    EditMessageText edited_message = new EditMessageText();
                    edited_message.setChatId(chat_id);
                    edited_message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    edited_message.setText(update.getCallbackQuery().getMessage().getText());
                    edited_message.setReplyMarkup(acsMenu());
                    try {
                        execute(edited_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case "bank_callback" -> {
                    String answer = "Select bank";
                    SendMessage new_message = new SendMessage();
                    new_message.setChatId(chat_id);
                    new_message.setText(answer);
                    new_message.setReplyMarkup(bankMenu());
                    try {
                        execute(new_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case "NBU_callback" -> {
                    if (settings.bank.equals("NBU")) {
                        break;
                    } else {
                        database.updateSettings(update.getCallbackQuery().getMessage().getChatId().toString(), "bank", "NBU");
                        settings.bank = database.getSettings(update.getCallbackQuery().getMessage().getChatId().toString()).bank;
                    }
                    EditMessageText edited_message = new EditMessageText();
                    edited_message.setChatId(chat_id);
                    edited_message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    edited_message.setText(update.getCallbackQuery().getMessage().getText());
                    edited_message.setReplyMarkup(bankMenu());
                    try {
                        execute(edited_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case "Privatbank_callback" ->{
                    if (settings.bank.equals("Privat")) {
                        break;
                    } else {
                        database.updateSettings(update.getCallbackQuery().getMessage().getChatId().toString(), "bank", "Privat");
                        settings.bank = database.getSettings(update.getCallbackQuery().getMessage().getChatId().toString()).bank;
                    }
                    EditMessageText edited_message = new EditMessageText();
                    edited_message.setChatId(chat_id);
                    edited_message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    edited_message.setText(update.getCallbackQuery().getMessage().getText());
                    edited_message.setReplyMarkup(bankMenu());
                    try {
                        execute(edited_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case "Monobank_callback" -> {
                    if (settings.bank.equals("Mono")) {
                        break;
                    } else {
                        database.updateSettings(update.getCallbackQuery().getMessage().getChatId().toString(), "bank", "Mono");
                        settings.bank = database.getSettings(update.getCallbackQuery().getMessage().getChatId().toString()).bank;
                    }
                    EditMessageText edited_message = new EditMessageText();
                    edited_message.setChatId(chat_id);
                    edited_message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    edited_message.setText(update.getCallbackQuery().getMessage().getText());
                    edited_message.setReplyMarkup(bankMenu());
                    try {
                        execute(edited_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case "currency_callback" -> {
                    String answer = "Select currency";
                    SendMessage new_message = new SendMessage();
                    new_message.setChatId(chat_id);
                    new_message.setText(answer);
                    new_message.setReplyMarkup(currencyMenu());
                    try {
                        execute(new_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case "USD_callback" -> {
                    if (settings.currency.contains("USD") && settings.currency.size() == 1) {
                        break;
                    } else if (settings.currency.contains("USD") && settings.currency.size() != 1) {
                        List<String> currencies = settings.currency;
                        currencies.remove("USD");
                        database.updateCurrencySettings(update.getCallbackQuery().getMessage().getChatId().toString(), currencies);
                        settings.currency = database.getSettings(update.getCallbackQuery().getMessage().getChatId().toString()).currency;
                    } else {
                        List<String> currencies = settings.currency;
                        currencies.add("USD");
                        database.updateCurrencySettings(update.getCallbackQuery().getMessage().getChatId().toString(), currencies);
                        settings.currency = database.getSettings(update.getCallbackQuery().getMessage().getChatId().toString()).currency;
                    }
                    EditMessageText edited_message = new EditMessageText();
                    edited_message.setChatId(chat_id);
                    edited_message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    edited_message.setText(update.getCallbackQuery().getMessage().getText());
                    edited_message.setReplyMarkup(currencyMenu());
                    try {
                        execute(edited_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case "EUR_callback" -> {
                    if (settings.currency.contains("EUR") && settings.currency.size() == 1) {
                        break;
                    } else if (settings.currency.contains("EUR") && settings.currency.size() != 1) {
                        List<String> currencies = settings.currency;
                        currencies.remove("EUR");
                        database.updateCurrencySettings(update.getCallbackQuery().getMessage().getChatId().toString(), currencies);
                        settings.currency = database.getSettings(update.getCallbackQuery().getMessage().getChatId().toString()).currency;
                    } else {
                        List<String> currencies = settings.currency;
                        currencies.add("EUR");
                        database.updateCurrencySettings(update.getCallbackQuery().getMessage().getChatId().toString(), currencies);
                        settings.currency = database.getSettings(update.getCallbackQuery().getMessage().getChatId().toString()).currency;
                    }
                    EditMessageText edited_message = new EditMessageText();
                    edited_message.setChatId(chat_id);
                    edited_message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    edited_message.setText(update.getCallbackQuery().getMessage().getText());
                    edited_message.setReplyMarkup(currencyMenu());
                    try {
                        execute(edited_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case "RUB_callback" -> {
                    if (settings.currency.contains("RUB") && settings.currency.size() == 1) {
                        break;
                    } else if (settings.currency.contains("RUB") && settings.currency.size() != 1) {
                        List<String> currencies = settings.currency;
                        currencies.remove("RUB");
                        database.updateCurrencySettings(update.getCallbackQuery().getMessage().getChatId().toString(), currencies);
                        settings.currency = database.getSettings(update.getCallbackQuery().getMessage().getChatId().toString()).currency;
                    } else {
                        List<String> currencies = settings.currency;
                        currencies.add("RUB");
                        database.updateCurrencySettings(update.getCallbackQuery().getMessage().getChatId().toString(), currencies);
                        settings.currency = database.getSettings(update.getCallbackQuery().getMessage().getChatId().toString()).currency;
                    }
                    EditMessageText edited_message = new EditMessageText();
                    edited_message.setChatId(chat_id);
                    edited_message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    edited_message.setText(update.getCallbackQuery().getMessage().getText());
                    edited_message.setReplyMarkup(currencyMenu());
                    try {
                        execute(edited_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case "notification_callback" -> {
                    String answer = "Set notifications";
                    SendMessage new_message = new SendMessage();
                    new_message.setChatId(chat_id);
                    new_message.setText(answer);
                    new_message.setReplyMarkup(notificationMenu());
                    try {
                        execute(new_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case "back_to_start_callback" -> {
                    EditMessageText edited_message = new EditMessageText();
                    edited_message.setChatId(chat_id);
                    edited_message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    edited_message.setText("Information menu");
                    edited_message.setReplyMarkup(startMenu());
                    try {
                        execute(edited_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case "back_to_settings_callback" -> {
                    EditMessageText edited_message = new EditMessageText();
                    edited_message.setChatId(chat_id);
                    edited_message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    edited_message.setText("Settings");
                    edited_message.setReplyMarkup(settingsMenu());
                    try {
                        execute(edited_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case "hours1_callback" -> {
                    if (settings.notification.equals("9:00")) {
                        break;
                    } else {
                        database.updateSettings(update.getCallbackQuery().getMessage().getChatId().toString(), "notification", "9:00");
                        settings.notification = database.getSettings(update.getCallbackQuery().getMessage().getChatId().toString()).notification;
                        timer.setTimer(this, chat_id);
                    }
                    EditMessageText edited_message = new EditMessageText();
                    edited_message.setChatId(chat_id);
                    edited_message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    edited_message.setText(update.getCallbackQuery().getMessage().getText());
                    edited_message.setReplyMarkup(notificationMenu());
                    try {
                        execute(edited_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case "hours2_callback" -> {
                    if (settings.notification.equals("10:00")) {
                        break;
                    } else {
                        database.updateSettings(update.getCallbackQuery().getMessage().getChatId().toString(), "notification", "10:00");
                        settings.notification = database.getSettings(update.getCallbackQuery().getMessage().getChatId().toString()).notification;
                        timer.setTimer(this, chat_id);
                    }
                    EditMessageText edited_message = new EditMessageText();
                    edited_message.setChatId(chat_id);
                    edited_message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    edited_message.setText(update.getCallbackQuery().getMessage().getText());
                    edited_message.setReplyMarkup(notificationMenu());
                    try {
                        execute(edited_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case "hours3_callback" -> {
                    if (settings.notification.equals("11:00")) {
                        break;
                    } else {
                        database.updateSettings(update.getCallbackQuery().getMessage().getChatId().toString(), "notification", "11:00");
                        settings.notification = database.getSettings(update.getCallbackQuery().getMessage().getChatId().toString()).notification;
                        timer.setTimer(this, chat_id);
                    }
                    EditMessageText edited_message = new EditMessageText();
                    edited_message.setChatId(chat_id);
                    edited_message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    edited_message.setText(update.getCallbackQuery().getMessage().getText());
                    edited_message.setReplyMarkup(notificationMenu());
                    try {
                        execute(edited_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case "hours4_callback" -> {
                    if (settings.notification.equals("12:00")) {
                        break;
                    } else {
                        database.updateSettings(update.getCallbackQuery().getMessage().getChatId().toString(), "notification", "12:00");
                        settings.notification = database.getSettings(update.getCallbackQuery().getMessage().getChatId().toString()).notification;
                        timer.setTimer(this, chat_id);
                    }
                    EditMessageText edited_message = new EditMessageText();
                    edited_message.setChatId(chat_id);
                    edited_message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    edited_message.setText(update.getCallbackQuery().getMessage().getText());
                    edited_message.setReplyMarkup(notificationMenu());
                    try {
                        execute(edited_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case "hours5_callback" -> {
                    if (settings.notification.equals("13:00")) {
                        break;
                    } else {
                        database.updateSettings(update.getCallbackQuery().getMessage().getChatId().toString(), "notification", "13:00");
                        settings.notification = database.getSettings(update.getCallbackQuery().getMessage().getChatId().toString()).notification;
                        timer.setTimer(this, chat_id);
                    }
                    EditMessageText edited_message = new EditMessageText();
                    edited_message.setChatId(chat_id);
                    edited_message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    edited_message.setText(update.getCallbackQuery().getMessage().getText());
                    edited_message.setReplyMarkup(notificationMenu());
                    try {
                        execute(edited_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case "hours6_callback" -> {
                    if (settings.notification.equals("14:00")) {
                        break;
                    } else {
                        database.updateSettings(update.getCallbackQuery().getMessage().getChatId().toString(), "notification", "13:24");
                        settings.notification = database.getSettings(update.getCallbackQuery().getMessage().getChatId().toString()).notification;
                        timer.setTimer(this, chat_id);
                    }
                    EditMessageText edited_message = new EditMessageText();
                    edited_message.setChatId(chat_id);
                    edited_message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    edited_message.setText(update.getCallbackQuery().getMessage().getText());
                    edited_message.setReplyMarkup(notificationMenu());
                    try {
                        execute(edited_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case "hours7_callback" -> {
                    if (settings.notification.equals("15:00")) {
                        break;
                    } else {
                        database.updateSettings(update.getCallbackQuery().getMessage().getChatId().toString(), "notification", "15:00");
                        settings.notification = database.getSettings(update.getCallbackQuery().getMessage().getChatId().toString()).notification;
                        timer.setTimer(this, chat_id);
                    }
                    EditMessageText edited_message = new EditMessageText();
                    edited_message.setChatId(chat_id);
                    edited_message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    edited_message.setText(update.getCallbackQuery().getMessage().getText());
                    edited_message.setReplyMarkup(notificationMenu());
                    try {
                        execute(edited_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case "hours8_callback" -> {
                    if (settings.notification.equals("16:00")) {
                        break;
                    } else {
                        database.updateSettings(update.getCallbackQuery().getMessage().getChatId().toString(), "notification", "16:00");
                        settings.notification = database.getSettings(update.getCallbackQuery().getMessage().getChatId().toString()).notification;
                        timer.setTimer(this, chat_id);
                    }
                    EditMessageText edited_message = new EditMessageText();
                    edited_message.setChatId(chat_id);
                    edited_message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    edited_message.setText(update.getCallbackQuery().getMessage().getText());
                    edited_message.setReplyMarkup(notificationMenu());
                    try {
                        execute(edited_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case "hours9_callback" -> {
                    if (settings.notification.equals("17:00")) {
                        break;
                    } else {
                        database.updateSettings(update.getCallbackQuery().getMessage().getChatId().toString(), "notification", "17:00");
                        settings.notification = database.getSettings(update.getCallbackQuery().getMessage().getChatId().toString()).notification;
                        timer.setTimer(this, chat_id);
                    }
                    EditMessageText edited_message = new EditMessageText();
                    edited_message.setChatId(chat_id);
                    edited_message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    edited_message.setText(update.getCallbackQuery().getMessage().getText());
                    edited_message.setReplyMarkup(notificationMenu());
                    try {
                        execute(edited_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case "hours10_callback" -> {
                    if (settings.notification.equals("18:00")) {
                        break;
                    } else {
                        database.updateSettings(update.getCallbackQuery().getMessage().getChatId().toString(), "notification", "18:00");
                        settings.notification = database.getSettings(update.getCallbackQuery().getMessage().getChatId().toString()).notification;
                        timer.setTimer(this, chat_id);
                    }
                    EditMessageText edited_message = new EditMessageText();
                    edited_message.setChatId(chat_id);
                    edited_message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    edited_message.setText(update.getCallbackQuery().getMessage().getText());
                    edited_message.setReplyMarkup(notificationMenu());
                    try {
                        execute(edited_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                case "hours11_callback" -> {
                    if (settings.notification.equals("Turn off notifications")) {
                        break;
                    } else {
                        database.updateSettings(update.getCallbackQuery().getMessage().getChatId().toString(), "notification", "Turn off notifications");
                        settings.notification = database.getSettings(update.getCallbackQuery().getMessage().getChatId().toString()).notification;
                        timer.setTimer(this, chat_id);
                        timer = new NotificationTimer();
                    }
                    settings.notification = "Turn off notifications";
                    EditMessageText edited_message = new EditMessageText();
                    edited_message.setChatId(chat_id);
                    edited_message.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                    edited_message.setText(update.getCallbackQuery().getMessage().getText());
                    edited_message.setReplyMarkup(notificationMenu());
                    try {
                        execute(edited_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @SneakyThrows
    public void botConnect() {
        TelegramBotsApi telegramBotsApi  = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(this);
            timer.setBankTimer(this);
        } catch (TelegramApiRequestException e) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                return;
            }
            botConnect();
        }
    }
}