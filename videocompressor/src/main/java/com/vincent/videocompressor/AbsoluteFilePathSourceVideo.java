package com.vincent.videocompressor;

import android.media.MediaExtractor;
import android.media.MediaMetadataRetriever;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;

public class AbsoluteFilePathSourceVideo implements SourceVideo {

    private final String absoluteFilePath;

    public AbsoluteFilePathSourceVideo(String absoluteFilePath) {
        this.absoluteFilePath = absoluteFilePath;
    }

    @Override
    public void asDataSource(MediaMetadataRetriever retriever) {
        retriever.setDataSource(absoluteFilePath);
    }

    @Override
    public boolean isReadable() {
        return new File(absoluteFilePath).canRead();
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void asDataSource(MediaExtractor extractor) {
        try {
            extractor.setDataSource(absoluteFilePath);
        } catch (IOException e) {
            throw new RuntimeException("MediaExtractor.setDataSource failed for file path=" + absoluteFilePath, e);
        }
    }
}
