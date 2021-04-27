package com.vincent.videocompress;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Environment;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vincent.videocompressor.VideoCompress;

import java.io.File;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_FOR_VIDEO_FILE = 1000;
    private TextView tv_input, tv_output, tv_indicator, tv_progress;
    private String outputDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();

    private String inputPath;
    private String outputPath;

    private ProgressBar pb_compress;

    private long startTime, endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        Button btn_select = (Button) findViewById(R.id.btn_select);
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
                //intent.setType("video/*;image/*");
                //intent.setType("audio/*"); //选择音频
                intent.setType("video/*"); //选择视频 （mp4 3gp 是android支持的视频格式）
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_FOR_VIDEO_FILE);
            }
        });

        Button btn_compress = (Button) findViewById(R.id.btn_compress);
        btn_compress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String destPath = tv_output.getText().toString() + File.separator + "VID_" + new SimpleDateFormat("yyyyMMdd_HHmmss", getLocale()).format(new Date()) + ".mp4";
                VideoCompress.compressVideoLow(tv_input.getText().toString(), destPath, new VideoCompress.CompressListener() {
                    @Override
                    public void onStart() {
                        tv_indicator.setText("Compressing..." + "\n"
                                + "Start at: " + new SimpleDateFormat("HH:mm:ss", getLocale()).format(new Date()));
                        pb_compress.setVisibility(View.VISIBLE);
                        startTime = System.currentTimeMillis();
                        Util.writeFile(MainActivity.this, "Start at: " + new SimpleDateFormat("HH:mm:ss", getLocale()).format(new Date()) + "\n");
                    }

                    @Override
                    public void onSuccess() {
                        String previous = tv_indicator.getText().toString();
                        tv_indicator.setText(previous + "\n"
                                + "Compress Success!" + "\n"
                                + "End at: " + new SimpleDateFormat("HH:mm:ss", getLocale()).format(new Date()));
                        pb_compress.setVisibility(View.INVISIBLE);
                        endTime = System.currentTimeMillis();
                        Util.writeFile(MainActivity.this, "End at: " + new SimpleDateFormat("HH:mm:ss", getLocale()).format(new Date()) + "\n");
                        Util.writeFile(MainActivity.this, "Total: " + ((endTime - startTime)/1000) + "s" + "\n");
                        Util.writeFile(MainActivity.this);
                    }

                    @Override
                    public void onFail() {
                        tv_indicator.setText("Compress Failed!");
                        pb_compress.setVisibility(View.INVISIBLE);
                        endTime = System.currentTimeMillis();
                        Util.writeFile(MainActivity.this, "Failed Compress!!!" + new SimpleDateFormat("HH:mm:ss", getLocale()).format(new Date()));
                    }

                    @Override
                    public void onProgress(float percent) {
                        tv_progress.setText(String.valueOf(percent) + "%");
                    }
                });
            }
        });

        tv_input = (TextView) findViewById(R.id.tv_input);
        tv_output = (TextView) findViewById(R.id.tv_output);
        tv_output.setText(outputDir);
        tv_indicator = (TextView) findViewById(R.id.tv_indicator);
        tv_progress = (TextView) findViewById(R.id.tv_progress);

        pb_compress = (ProgressBar) findViewById(R.id.pb_compress);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FOR_VIDEO_FILE && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
//                inputPath = data.getData().getPath();
//                tv_input.setText(inputPath);

                try {
                    inputPath = Util.getFilePath(this, data.getData());
                    tv_input.setText(inputPath);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

//                inputPath = "/storage/emulated/0/DCIM/Camera/VID_20170522_172417.mp4"; // 图片文件路径
//                tv_input.setText(inputPath);// /storage/emulated/0/DCIM/Camera/VID_20170522_172417.mp4
            }
        }
    }

    private Locale getLocale() {
        Configuration config = getResources().getConfiguration();
        Locale sysLocale = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = getSystemLocale(config);
        } else {
            sysLocale = getSystemLocaleLegacy(config);
        }

        return sysLocale;
    }

    @SuppressWarnings("deprecation")
    public static Locale getSystemLocaleLegacy(Configuration config){
        return config.locale;
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static Locale getSystemLocale(Configuration config){
        return config.getLocales().get(0);
    }
}
