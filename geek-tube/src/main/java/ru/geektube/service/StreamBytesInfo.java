package ru.geektube.service;

public class StreamBytesInfo {

    private final byte[] bytes;

    private final long fileSize;

    private final long rangeStart;

    private final long rangeEnd;

    public StreamBytesInfo(byte[] bytes, long fileSize, long rangeStart, long rangeStop) {
        this.bytes = bytes;
        this.fileSize = fileSize;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeStop;
    }

    public byte[] getBytes() {
        return bytes;
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
