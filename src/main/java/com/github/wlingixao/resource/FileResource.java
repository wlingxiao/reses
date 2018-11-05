package com.github.wlingixao.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileResource extends Resource {

    private Path path;

    public FileResource(URI location) {
        path = Paths.get(location);
    }

    public FileResource(Path path) {
        this.path = path;
    }

    public static FileResource get(String first, String... more) {
        return new FileResource(first, more);
    }

    public FileResource(String first, String... more) {
        path = Paths.get(first, more);
    }

    @Override
    public URL toUrl() {
        try {
            return toUri().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public URI toUri() {
        return path.toUri();
    }

    @Override
    public File toFile() {
        return path.toFile();
    }

    @Override
    public Path toPath() {
        return path;
    }

    @Override
    public InputStream toInputStream() {
        try {
            return Files.newInputStream(toPath());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
