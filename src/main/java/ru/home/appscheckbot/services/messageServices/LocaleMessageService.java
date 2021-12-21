package ru.home.appscheckbot.services.messageServices;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocaleMessageService {
    private final Locale locale;
    private final MessageSource messageSource;

    public LocaleMessageService(@Value("ru-RU") String localeTag, MessageSource messageSource) {
        this.locale = Locale.forLanguageTag(localeTag);
        this.messageSource = messageSource;
    }

    // Возвращает сообщение из файла messages_***_***.properties
    public String getMessage(String message) {
        return messageSource.getMessage(message, null, locale);
    }
    // Возвращает сообщение из файла messages_***_***.properties (с передачей доп. аргументов)
    public String getMessage(String message, Object... args) {
        return messageSource.getMessage(message, args, locale);
    }
}