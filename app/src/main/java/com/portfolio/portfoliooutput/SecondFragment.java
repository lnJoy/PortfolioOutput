package com.portfolio.portfoliooutput;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SecondFragment extends Fragment {
    // Store instance variables
    private String title;
    private int page;

    private LinearLayout Container;
    private Button mScreenBtn;

    // newInstance constructor for creating fragment with arguments
    public static SecondFragment newInstance(int page, String title) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragment.setArguments(args);
        return fragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        WebView mWebView;
        WebSettings mWebSettings;
        Button mScreenBtn;

        setHasOptionsMenu(true);

        Toolbar myToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);


        final String youtubeid = DataList.youtubeid;

        mWebView = (WebView) view.findViewById(R.id.webView);
        mWebView.setWebViewClient(new WebViewClient());
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);

        mScreenBtn = (Button) view.findViewById(R.id.screenShot);

        Container = (LinearLayout) view.findViewById(R.id.linear);

        String youtubeurl = "https://www.youtube.com/user/" + youtubeid;

        mWebView.loadUrl(youtubeurl);

        mScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String folder = "hyangSu_ScreenShot";

                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                    Date currentTime_1 = new Date();
                    String dateString = formatter.format(currentTime_1);
                    File sdCardPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    File dirs = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), folder);

                    if (!dirs.exists()) {
                        dirs.mkdirs();
                        Log.d("CAMERA_TEST", "Directory Created");
                    }
                    Container.buildDrawingCache();
                    Bitmap captureView = Container.getDrawingCache();
                    FileOutputStream fos;
                    String save;

                    try {
                        save = sdCardPath.getPath() + "/" + folder + "/" + dateString + "_" + youtubeid + ".jpg";
                        fos = new FileOutputStream(save);
                        captureView.compress(Bitmap.CompressFormat.JPEG, 100, fos); // capture

                        DataList.picture = dateString + "_" + youtubeid + ".jpg";
                        // DataList.picture = save;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            final Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            final Uri contentUri = Uri.fromFile(dirs);
                            scanIntent.setData(contentUri);
                            getActivity().sendBroadcast(scanIntent);
                        } else {
                            final Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)));
                            getActivity().sendBroadcast(intent);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getContext(), dateString + "_" + youtubeid + ".jpg 저장", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Log.e("Screen", "" + e.toString());
                }
            }

        });

        return view;
    }

}