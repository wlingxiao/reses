package com.github.wlingixao.resource;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.*;

public abstract class ResourceTests {

    private Resource resource;

    abstract Resource getResource();

    @Before
    public void setUp() {
        this.resource = getResource();
    }

    @Test
    public void testToUri() {
        URI url = resource.toUri();
        assertThat(url).isNotNull();
    }

    @Test
    public void testToUrl() {
        URL url = resource.toUrl();
        assertThat(url).isNotNull();
    }

    @Test
    public void testToFile() {
        File url = resource.toFile();
        assertThat(url).isNotNull();
    }

    @Test
    public void testToPath() {
        Path url = resource.toPath();
        assertThat(url).isNotNull();
    }

    @Test
    public void testToInputStream() {
        InputStream url = resource.toInputStream();
        assertThat(url).isNotNull();
    }

    @Test
    public void testToReader() {
        Reader reader = resource.toReader();
        assertThat(reader).isNotNull();
    }

    @Test
    public void testToBytes() {
        byte[] bytes = resource.toBytes();
        assertThat(new String(bytes)).isEqualTo("abc");
    }

    @Test
    public void testGetResourceFromUri() throws IOException {
        Resource resource = Resource.get("classpath:test.file");
        assertThat(resource).isNotNull();

        Path path = Files.createTempFile("file", "test");
        Resource fileRes = Resource.get(path.toUri().toString());
        assertThat(fileRes).isNotNull();

    }

}
