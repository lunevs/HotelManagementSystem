package com.ichtus.hotelmanagementsystem.configuration;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;

import java.io.*;

public class CachedHttpServletRequest extends HttpServletRequestWrapper {

    private final byte[] cachedPayload;

    public CachedHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        InputStream requestInputStream = request.getInputStream();
        this.cachedPayload = StreamUtils.copyToByteArray(requestInputStream);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedPayload);
        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CachedServletInputStream(this.cachedPayload);
    }
}
