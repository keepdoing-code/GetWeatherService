package ru.keepdoing.utils;

import ru.keepdoing.exception.InvalidParamsException;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class UrlBuilder {
    private final String ampersand = "&";
    private final String question = "?";
    private final String equals = "=";
    private final String baseUrl;
    private String url;
    private final HashMap<String, String> urlParams = new HashMap<>();

    public UrlBuilder(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Метод добавляет в коллекцию пару ключ-значение
     *
     * @param paramKey - ключ параметра url
     * @param value    - значение параметра url
     * @return
     */
    public UrlBuilder addKeyValue(String paramKey, String value) {
        urlParams.put(paramKey, value);
        return this;
    }

    /**
     * Метод добавляет в коллекцию строку с параметрами URL запроса
     *
     * @param inputParams На вход принимает строку уже соединенную в виде 'ключ=значение&ключ2=значение2..'
     * @return возвращает текущий объект
     * @throws InvalidParamsException кидает сиключение если есть ошибки синтаксиса в параметрах строки запроса
     */
    public UrlBuilder addParam(String inputParams) throws InvalidParamsException {
        if (!validateParamsString(inputParams)) throw new InvalidParamsException();
        urlParams.put(inputParams, inputParams);
        return this;
    }

    /**
     * Собирает воедино адрес URL запроса и параметры
     *
     * @return
     */
    public String build() {
        return baseUrl + question + assembleParams(urlParams);
    }

    /**
     * Метод возвращает уже собранную строку запроса. В случае если строка пустая - собирает.
     *
     * @return
     */
    public String getUrl() {
        if (url == null) url = build();
        return url;
    }

    public static String replacePlaceholder(String template, String... target) {
        return MessageFormat.format(template, target);
    }

    /**
     * Метод разделяет строку с параметрами на коллекцию пар ключ-значение
     *
     * @param inputString строка с параметрами разделенными & и =
     * @return HashMap пар ключ-значени
     */
    private HashMap<String, String> parseParams(String inputString) {
        HashMap<String, String> paramsMap = new HashMap<>();
        String[] params = inputString.split("&");
        for (String str : params) {
            String[] keyValue = str.split("=");
            if (keyValue.length > 1) {
                String key = keyValue[0];
                String value = keyValue[1];
                paramsMap.put(key, value);
            }
        }
        return paramsMap;
    }

    /**
     * Метод проверяет строку параметров запроса на наличие ошибок синтаксиса
     *
     * @param inputParams строка с параметрами запроса
     * @return возвращает boolean валидна или нет
     */
    private boolean validateParamsString(String inputParams) {
        HashMap<String, String> params = parseParams(inputParams);
        return assembleParams(params).equals(inputParams);
    }

    /**
     * Метод собирает коллекцию параметров ключ-значение в строку запроса
     *
     * @param paramsMap коллекция параметров ключ-значение
     * @return
     */
    private String assembleParams(HashMap<String, String> paramsMap) {
        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            if (entry.getKey().equals(entry.getValue())) {
                builder.append(entry.getValue()).append(ampersand);
                continue;
            }
            builder
                    .append(entry.getKey())
                    .append(equals)
                    .append(entry.getValue())
                    .append(ampersand);
        }
        builder.deleteCharAt(builder.lastIndexOf(ampersand));
        return builder.toString();
    }
}
