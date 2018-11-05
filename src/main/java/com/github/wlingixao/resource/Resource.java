package com.github.wlingixao.resource;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;

public abstract class Resource {

    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    public static Resource get(String str) {
        return null;
    }

    public abstract URL toUrl();

    public abstract URI toUri();

    public abstract File toFile();

    public abstract Path toPath();

    public abstract InputStream toInputStream();

    public abstract Reader toReader();

    public abstract Reader toReader(Charset charset);

    public byte[] toBytes() {
        return toByteArray();
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
