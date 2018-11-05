package com.github.wlingixao.resource;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileResourceTests extends ResourceTests {

    @Override
    Resource getResource() {
        try {
            Path path = Files.createTempFile("file", "test");
            String content = "abc";
            Files.write(path, content.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
            return new FileResource(path);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
