package com.ylzinfo.bib.core.config;


import com.ylzinfo.bib.core.utils.GetRequestJsonUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 重新分装request，每调用一次就创建一个流
 */
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final byte[] body;

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = GetRequestJsonUtils.getRequestPostBytes(request);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        final ByteArrayInputStream bais = new ByteArrayInputStream(body);

        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }
}