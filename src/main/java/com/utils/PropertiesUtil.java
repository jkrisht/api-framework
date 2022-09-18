package com.utils;

import com.commons.Logger;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.commons.TestConstants.FILE_SEPARATOR;
import static com.commons.TestConstants.PAGE_CONTENT_DIR;

public class PropertiesUtil {
    private final static Logger logger = Logger.getLogger(PropertiesUtil.class);

    synchronized public static ResourceBundle getBundle(BundleFile bundleFile) {
        if (bundleFile == null || bundleFile.getName().isBlank()) {
            throw new RuntimeException("Given bundle file name is empty or null");
        }

        String filePath = PAGE_CONTENT_DIR + FILE_SEPARATOR;
        ResourceBundle bundle;
        try {
            File file = new File(filePath);
            URL[] urls = {file.toURI().toURL()};
            ClassLoader loader = new URLClassLoader(urls);
            bundle = ResourceBundle.getBundle(bundleFile.getName(), Locale.getDefault(), loader);
        } catch (Exception e) {
            String message = String.format("Failed to read the %s properties file", bundleFile.getName());
            logger.trace(message, e);
            throw new RuntimeException(message);
        }
        logger.info("Following file has been read: " + bundleFile.getName());
        return bundle;
    }
}
