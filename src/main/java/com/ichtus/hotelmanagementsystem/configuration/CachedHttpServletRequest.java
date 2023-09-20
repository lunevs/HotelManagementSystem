package com.ichtus.hotelmanagementsystem.configuration;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.Generated;
import org.springframework.util.StreamUtils;

import java.io.*;

/**
 * Additional class for reading Http requests and logging them
 *
 */
@Generated
public class CachedHttpServletRequest extends HttpServletRequestWrapper {

    private final byte[] cachedPayload;

    /**
     * Parse request to Byte Array
     * @param request jakarta HttpServletRequest
     * @throws IOException if can't get Input Stream
     */
    public CachedHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        InputStream requestInputStream = request.getInputStream();
        this.cachedPayload = StreamUtils.copyToByteArray(requestInputStream);
    }


    /**
     * Get reader for input stream of bytes array
     * @return buffered reader for bytes array
     */
    @Override
    public BufferedReader getReader() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedPayload);
        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    }

    /**
     * Convert byte array to InputStream
     * @return CachedServletInputStream
     */
    @Override
    public ServletInputStream getInputStream() {
        return new CachedServletInputStream(this.cachedPayload);
    }
}
