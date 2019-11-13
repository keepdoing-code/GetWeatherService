package ru.keepdoing;

import org.junit.Assert;
import org.junit.Test;
import ru.keepdoing.utils.PropertiesLoader;

public class PropertiesLoaderTest {

    @Test
    public void getNonexistentProperty() {
        Assert.assertNull(PropertiesLoader.get("null"));
    }
}
