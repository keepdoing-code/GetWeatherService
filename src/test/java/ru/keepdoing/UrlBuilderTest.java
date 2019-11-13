package ru.keepdoing;

import org.junit.Assert;
import org.junit.Test;
import ru.keepdoing.exception.InvalidParamsException;
import ru.keepdoing.utils.UrlBuilder;

public class UrlBuilderTest {

    private static String testUrl = "http://dataservice.accuweather.com/";
    private static String combinedParam = "units=metric&lang=ru";
    private static String[] combinedParamError = {"units=metric&lang==ru", "units=metric&lang=ru=", "units=metric&lang=ru&"};
    private static String paramKey1 = "units";
    private static String paramValue1 = "metric";
    private static String paramKey2 = "lang";
    private static String paramValue2 = "ru";

    @Test
    public void combinedParamsTest() {
        UrlBuilder builder = new UrlBuilder(testUrl);
        try {
            builder.addParam(combinedParam);
        } catch (InvalidParamsException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(testUrl + "?" + combinedParam, builder.build());
    }

    @Test(expected = InvalidParamsException.class)
    public void invalidParamExceptionTest() throws InvalidParamsException {
            new UrlBuilder(testUrl).addParam(combinedParamError[0]);
    }

    @Test
    public void invalidParamsExceptionTest() {
        for (String param: combinedParamError) {
            try {
                new UrlBuilder(testUrl).addParam(param);
                Assert.fail("Expected InvalidParamsException");
            } catch (InvalidParamsException ignored) {
            }
        }
    }

    @Test
    public void splitParamsTest() {
        UrlBuilder builder = new UrlBuilder(testUrl);
        builder.addKeyValue(paramKey1, paramValue1);
        builder.addKeyValue(paramKey2, paramValue2);
        Assert.assertEquals(testUrl + "?" + combinedParam, builder.build());
    }


    @Test
    public void replacePlaceholderTest(){
        System.out.println(UrlBuilder.replacePlaceholder(
                "http://dataservice.accuweather.com/locations/v1/search?q={1}&{0}", "lang=ru","moscow"));
    }
}
