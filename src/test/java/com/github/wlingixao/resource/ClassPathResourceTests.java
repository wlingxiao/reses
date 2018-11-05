package com.github.wlingixao.resource;

import org.junit.Test;

public class ClassPathResourceTests extends ResourceTests {

    @Override
    Resource getResource() {
        return new ClasspathResource("test.file");
    }
}
