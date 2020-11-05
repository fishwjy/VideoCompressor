package com.vincent.videocompressor;

import android.content.Context;
import android.media.MediaExtractor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;

public class UriSourceVideo implements SourceVideo {

    private final Uri uri;
    private final Context context;

    public UriSourceVideo(Uri uri, Context context) {
        this.uri = uri;
        this.context = context;
    }

    @Override
    public boolean isReadable() {
        ParcelFileDescriptor descriptor = null;
        //noinspection TryFinallyCanBeTryWithResources
        try {
            descriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            return true;
        } catch (Throwable t) {
            Log.e("UriSourceVideo", "Could not open uri for read mode. uri=" + uri, t);
            return false;
        } finally {
            if (descriptor != null) {
                try {
                    descriptor.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    @Override
    public void asDataSource(MediaMetadataRetriever retriever) {
        retriever.setDataSource(context, uri);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void asDataSource(MediaExtractor extractor) {
        try {
            extractor.setDataSource(context, uri, null);
        } catch (IOException e) {
            throw new RuntimeException("MediaExtractor.setDataSource failed for uri=" + uri, e);
        }
    }
}
