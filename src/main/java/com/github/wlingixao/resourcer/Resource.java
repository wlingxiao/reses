package com.github.wlingixao.resourcer;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Properties;

public abstract class Resource {

    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    private final static String CLASSPATH_URL_PREFIX = "classpath:";

    private final static String File_URL_PREFIX = "file:";

    /**
     * Returns a resource for the specified location.
     * Only loading resource from classpath and file system support.
     *
     * @param location the location of resource must be fully qualified URLs, e.g. "file:/test.file".
     */
    public static Resource get(String location) {
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            return new ClasspathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        } else if (location.startsWith(File_URL_PREFIX)) {
            try {
                return new FileResource(new URI(location));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        } else throw new IllegalArgumentException(String.format("Not found support resource for %s", location));
    }

    /**
     * Returns the URL of the resource
     */
    public abstract URL toUrl();

    public abstract URI toUri();

    /**
     * Returns the resource as File object
     */
    public abstract File toFile();

    /**
     * Returns the resource as Path object
     */
    public abstract Path toPath();

    /**
     * Returns the resource as InputStream object
     */
    public abstract InputStream toInputStream();

    /**
     * Returns the resource as Reader object using UTF-8 charset
     */
    public Reader toReader() {
        return toReader(StandardCharsets.UTF_8);
    }

    /**
     * Returns the resource as Reader object using specified charset
     */
    public Reader toReader(Charset charset) {
        return new BufferedReader(new InputStreamReader(toInputStream(), charset));
    }

    /**
     * Reads the resource as a array of byte
     */
    public byte[] toBytes() {
        return toByteArray();
    }

    /**
     * Returns the resource as Properties object using specified charset
     */
    public Properties toProperties(Charset charset) {
        try {
            Properties props = new Properties();
            try (Reader in = toReader(charset)) {
                props.load(in);
            }
            return props;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Returns the resource as Properties object using UTF-8 charset
     */
    public Properties toProperties() {
        return toProperties(StandardCharsets.UTF_8);
    }

    private byte[] toByteArray() {
        try (final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            copy(toInputStream(), output, new byte[DEFAULT_BUFFER_SIZE]);
            return output.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    protected void copy(final InputStream input, final OutputStream output, final byte[] buffer)
            throws IOException {
        int n;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
    }

}
