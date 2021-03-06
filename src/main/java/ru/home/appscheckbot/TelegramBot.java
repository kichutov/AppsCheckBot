package ru.home.appscheckbot;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Setter
@Getter
public class TelegramBot extends TelegramWebhookBot {

    private String botPath;
    private String botUsername;
    private String botToken;

    private TelegramFacade telegramFacade;

    public TelegramBot(TelegramFacade telegramFacade) {
        this.telegramFacade = telegramFacade;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return telegramFacade.handleUpdate(update);
    }
}