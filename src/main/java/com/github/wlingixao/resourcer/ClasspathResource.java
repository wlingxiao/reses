package com.github.wlingixao.resourcer;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ClasspathResource extends Resource {

    private String name;

    public ClasspathResource(String name) {
        this.name = name;
    }

    @Override
    public URL toUrl() {
        return getURLFromClassLoaders();
    }

    @Override
    public URI toUri() {
        try {
            return toUrl().toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File toFile() {
        return toPath().toFile();
    }

    @Override
    public Path toPath() {
        return Paths.get(toUri());
    }

    @Override
    public InputStream toInputStream() {
        URL url = getURLFromClassLoaders();
        try {
            return url != null ? url.openStream() : null;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private ClassLoader[] getClassLoaders() {
        return new ClassLoader[]{
                Thread.currentThread().getContextClassLoader(),
                getClass().getClassLoader()};
    }

    private URL getURLFromClassLoaders() {
        return getURLFromClassLoaders(name, getClassLoaders());
    }

    private URL getURLFromClassLoaders(String name, ClassLoader[] classLoaders) {
        URL url;
        for (ClassLoader cl : classLoaders) {
            if (cl != null) {
                url = cl.getResource(name);
                if (url == null) {
                    url = cl.getResource("/" + name);
                }
                if (url != null) {
                    return url;
                }
            }
        }
        return null;
    }
}
