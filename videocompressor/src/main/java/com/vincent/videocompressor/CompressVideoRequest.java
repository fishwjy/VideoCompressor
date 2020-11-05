package com.vincent.videocompressor;

import androidx.annotation.Nullable;

public class CompressVideoRequest {

    private SourceVideo sourceVideo;
    private String compressedVideoAbsoluteFilePath;
    private CompressListener compressListener;
    private Integer targetWidth, targetHeight, targetBitRate;
    private CompressQuality compressQuality;

    private CompressVideoRequest() {
    }

    public CompressQuality getCompressQuality() {
        return compressQuality;
    }

    public SourceVideo getSourceVideo() {
        return sourceVideo;
    }

    public CompressListener getCompressListener() {
        return compressListener;
    }

    public String getCompressedVideoAbsoluteFilePath() {
        return compressedVideoAbsoluteFilePath;
    }

    public Integer getTargetWidth() {
        return targetWidth;
    }

    public Integer getTargetHeight() {
        return targetHeight;
    }

    public Integer getTargetBitRate() {
        return targetBitRate;
    }

    public static class Builder {

        private final CompressVideoRequest compressVideoRequest = new CompressVideoRequest();

        public Builder setSourceVideo(SourceVideo sourceVideo) {
            compressVideoRequest.sourceVideo = sourceVideo;
            return this;
        }

        public Builder setTargetWidth(@Nullable Integer targetWidth) {
            compressVideoRequest.targetWidth = targetWidth;
            return this;
        }

        public Builder setTargetHeight(@Nullable Integer targetHeight) {
            compressVideoRequest.targetHeight = targetHeight;
            return this;
        }

        public Builder setTargetBitRate(@Nullable Integer targetBitRate) {
            compressVideoRequest.targetBitRate = targetBitRate;
            return this;
        }

        public Builder setCompressedVideoAbsoluteFilePath(String compressedVideoAbsoluteFilePath) {
            compressVideoRequest.compressedVideoAbsoluteFilePath = compressedVideoAbsoluteFilePath;
            return this;
        }

        public Builder setCompressListener(CompressListener compressListener) {
            compressVideoRequest.compressListener = compressListener;
            return this;
        }

        public Builder setCompressQuality(@Nullable CompressQuality compressQuality) {
            compressVideoRequest.compressQuality = compressQuality;
            return this;
        }

        public CompressVideoRequest build() {

            checkNotNull(compressVideoRequest.sourceVideo, "source video");
            checkNotNull(compressVideoRequest.compressedVideoAbsoluteFilePath, "compressed video file path");
            checkNotNull(compressVideoRequest.compressListener, "compress listener");

            if (anyTargetMetadataIsMissing() && compressQualityIsMissing()) {
                throw new IllegalArgumentException("Must specify compress quality if any of the target meta data is missing");
            }
            return compressVideoRequest;
        }

        private boolean anyTargetMetadataIsMissing() {
            return compressVideoRequest.getTargetBitRate() == null ||
                    compressVideoRequest.getTargetHeight() == null ||
                    compressVideoRequest.getTargetWidth() == null;
        }

        private boolean compressQualityIsMissing() {
            return compressVideoRequest.compressQuality == null;
        }

        private void checkNotNull(Object o, String fieldName) {
            if (o == null) {
                throw new NullPointerException("Must provide " + fieldName);
            }
        }
    }


}
