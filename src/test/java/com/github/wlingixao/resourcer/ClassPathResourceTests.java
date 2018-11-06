package com.github.wlingixao.resourcer;

import org.junit.Test;

import java.nio.charset.Charset;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class ClassPathResourceTests extends ResourceTests {

    @Override
    Resource getResource() {
        return new ClasspathResource("test.file");
    }

    @Test
    public void testToProperties() {
        Properties props = new ClasspathResource("test.properties").toProperties();
        assertThat(props.getProperty("abc")).isEqualTo("test");
        assertThat(props.getProperty("你好")).isEqualTo("こんにちは");
    }

    @Test
    public void testReadGBKFileToProperties() {
        Properties props = new ClasspathResource("test-gbk.properties").toProperties(Charset.forName("gb2312"));
        assertThat(props.getProperty("abc")).isEqualTo("test");
        assertThat(props.getProperty("你好")).isEqualTo("こんにちは");
    }

}
