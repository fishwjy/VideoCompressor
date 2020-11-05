package com.vincent.videocompressor;

import android.os.AsyncTask;

/**
 * Created by Vincent Woo
 * Date: 2017/8/16
 * Time: 15:15
 */

public class VideoCompress {

    public static VideoCompressTask compressVideoHigh(String srcPath, String destPath, CompressListener listener) {
        VideoCompressTask task = new VideoCompressTask(listener, CompressQuality.HIGH);
        task.execute(srcPath, destPath);
        return task;
    }

    public static VideoCompressTask compressVideoMedium(String srcPath, String destPath, CompressListener listener) {
        VideoCompressTask task = new VideoCompressTask(listener, CompressQuality.MEDIUM);
        task.execute(srcPath, destPath);
        return task;
    }

    public static VideoCompressTask compressVideoLow(String srcPath, String destPath, CompressListener listener) {
        VideoCompressTask task = new VideoCompressTask(listener, CompressQuality.LOW);
        task.execute(srcPath, destPath);
        return task;
    }

    private static class VideoCompressTask extends AsyncTask<String, Float, Boolean> {

        private final CompressListener compressListener;
        private final CompressQuality compressQuality;

        public VideoCompressTask(CompressListener listener, CompressQuality compressQuality) {
            compressListener = listener;
            this.compressQuality = compressQuality;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            compressListener.onStart();
        }

        @Override
        protected Boolean doInBackground(String... paths) {

            CompressVideoRequest compressVideoRequest = new CompressVideoRequest.Builder()
                    .setSourceVideo(new AbsoluteFilePathSourceVideo(paths[0]))
                    .setCompressedVideoAbsoluteFilePath(paths[1])
                    .setCompressQuality(compressQuality)
                    .setCompressListener(new CompressListener.DoNothingCompressListener() {
                        @Override
                        public void onProgress(float percent) {
                            super.onProgress(percent);
                            compressListener.onProgress(percent);
                        }
                    })
                    .build();

            return VideoController.getInstance().convertVideo(compressVideoRequest);
        }

        @Override
        protected void onProgressUpdate(Float... percent) {
            super.onProgressUpdate(percent);
            compressListener.onProgress(percent[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                compressListener.onSuccess();
            } else {
                compressListener.onFail();
            }
        }
    }

}
