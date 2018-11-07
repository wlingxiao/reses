package com.github.wlingixao.reses;

import java.net.URI;
import java.net.URISyntaxException;

public class Reses {

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
}
