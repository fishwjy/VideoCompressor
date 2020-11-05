package com.vincent.videocompressor;

public interface CompressListener extends CompressProgressListener {

    void onStart();

    void onSuccess();

    void onFail();

    class DoNothingCompressListener implements CompressListener {

        @Override
        public void onStart() {

        }

        @Override
        public void onSuccess() {

        }

        @Override
        public void onFail() {

        }

        @Override
        public void onProgress(float percent) {

        }
    }
}
