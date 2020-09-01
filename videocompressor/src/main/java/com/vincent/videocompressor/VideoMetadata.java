package com.vincent.videocompressor;

public class VideoMetadata {
    public VideoMetadata(String sourcePath, String destinationPath) {
        this.sourcePath = sourcePath;
        this.destinationPath = destinationPath;
    }

    private String sourcePath;
    private String destinationPath;
    private String width = "";
    private String height = "";
    private String rotation = "";
    private Long duration = 0L;

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getDestinationPath() {
        return destinationPath;
    }

    public void setDestinationPath(String destinationPath) {
        this.destinationPath = destinationPath;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getRotation() {
        return rotation;
    }

    public void setRotation(String rotation) {
        this.rotation = rotation;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
