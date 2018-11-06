package com.github.wlingixao.resourcer;

import org.junit.Test;

public class ClassPathResourceTests extends ResourceTests {

    @Override
    Resource getResource() {
        return new ClasspathResource("test.file");
    }
}
