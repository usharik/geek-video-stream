package ru.geektube.service;

import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

public class StreamBytesInfo {

    private final StreamingResponseBody responseBody;

    private final long fileSize;

    private final long rangeStart;

    private final long rangeEnd;

    public StreamBytesInfo(StreamingResponseBody responseBody, long fileSize, long rangeStart, long rangeStop) {
        this.responseBody = responseBody;
        this.fileSize = fileSize;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeStop;
    }

    public StreamingResponseBody getResponseBody() {
        return responseBody;
    }

    public long getFileSize() {
        return fileSize;
    }

    public long getRangeStart() {
        return rangeStart;
    }

    public long getRangeStop() {
        return rangeEnd;
    }
}
