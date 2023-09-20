package com.ichtus.hotelmanagementsystem.configuration;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Logging configuration file. Defines Logger and override methods for ServletInputStream
 */
@Generated
public class CachedServletInputStream extends ServletInputStream {

    /**
     * Defines SLF4J Logger factory
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(CachedServletInputStream.class);
    private final InputStream cachedInputStream;

    public CachedServletInputStream(byte[] cachedBody) {
        this.cachedInputStream = new ByteArrayInputStream(cachedBody);
    }

    @Override
    public boolean isFinished() {
        try {
            return cachedInputStream.available() == 0;
        } catch (IOException exp) {
            LOGGER.error(exp.getMessage());
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int read() throws IOException {
        return cachedInputStream.read();
    }
}
