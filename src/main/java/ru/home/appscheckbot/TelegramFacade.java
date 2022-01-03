package ru.home.appscheckbot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.home.appscheckbot.botApi.BotState;
import ru.home.appscheckbot.cache.BotStateCash;

@Slf4j
@Component
public class TelegramFacade {

    private final MessageHandler messageHandler;
    private final CallbackQueryHandler callbackQueryHandler;
    private final BotStateCash botStateCash;

    public TelegramFacade(MessageHandler messageHandler, CallbackQueryHandler callbackQueryHandler, BotStateCash botStateCash) {
        this.messageHandler = messageHandler;
        this.callbackQueryHandler = callbackQueryHandler;
        this.botStateCash = botStateCash;
    }

    public BotApiMethod<?> handleUpdate(Update update) {

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return callbackQueryHandler.processCallbackQuery(callbackQuery);
        } else {
            Message message = update.getMessage();
            if (message != null && message.hasText()) {
                return handleInputMessage(message);
            }
        }
        return null;
    }

    private BotApiMethod<?> handleInputMessage(Message message) {

        BotState botState;
        String inputMessage = message.getText();

        switch (inputMessage) {
            case "/start":
                botState = BotState.START;
                break;
            case "Главное меню":
            case "Назад в главное меню":
                botState = BotState.MAIN_MENU;
                break;
            case "Написать разработчику":
                botState = BotState.WRITE_TO_DEVELOPER;
                break;
            case "Мои приложения":
            case "Назад к списку приложений":
                botState = BotState.MY_APPS;
                break;
            case "Добавить приложение":
                botState = BotState.ADD_NEW_APP;
                break;
            default:
                botState = botStateCash.getBotStateMap().get(message.getFrom().getId()) == null?
                        BotState.START: botStateCash.getBotStateMap().get(message.getFrom().getId());
        }
        log.info(String.valueOf(botState));
        return messageHandler.handle(message, botState);

    }

}
