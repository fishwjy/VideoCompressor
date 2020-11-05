package com.vincent.videocompressor;

import android.media.MediaExtractor;
import android.media.MediaMetadataRetriever;
import android.os.Build;

import androidx.annotation.RequiresApi;

public interface SourceVideo {

    boolean isReadable();

    void asDataSource(MediaMetadataRetriever retriever);

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    void asDataSource(MediaExtractor extractor);
}
