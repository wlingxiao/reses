package com.github.wlingixao.resource;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ClasspathResource extends Resource {

    private String name;

    public ClasspathResource(String name) {
        this.name = name;
    }

    @Override
    public URL toUrl() {
        return getURL();
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
        URL url = getURL();
        try {
            return url != null ? url.openStream() : null;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Reader toReader() {
        return toReader(StandardCharsets.UTF_8);
    }

    @Override
    public Reader toReader(Charset charset) {
        return new BufferedReader(new InputStreamReader(toInputStream(), charset));
    }

    private ClassLoader[] getClassLoaders() {
        return new ClassLoader[]{
                Thread.currentThread().getContextClassLoader(),
                getClass().getClassLoader()};
    }

    private URL getURL() {
        return getURL(name, getClassLoaders());
    }

    private URL getURL(String name, ClassLoader[] classLoaders) {
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
