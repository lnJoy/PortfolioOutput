package com.portfolio.portfoliooutput;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;


public class ThirdFragment extends Fragment {
    // Store instance variables
    private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static ThirdFragment newInstance(int page, String title) {
        ThirdFragment fragment = new ThirdFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        setHasOptionsMenu(true);

        Toolbar myToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(myToolbar);


        Button mEmailSendBtn = (Button)view.findViewById(R.id.emailSendBtn);

        Log.d("emailTest", "이메일: " + DataList.email);

        mEmailSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailSend = new Intent(Intent.ACTION_SEND_MULTIPLE);
                emailSend.setType("plain/text");
                emailSend.setType("application/vnd.android.package-archive");
                // email setting 배열로 해놔서 복수 발송 가능
                String[] address = {DataList.email};
                emailSend.putExtra(Intent.EXTRA_EMAIL, address);
                emailSend.putExtra(Intent.EXTRA_SUBJECT,DataList.youtubeid + " 유튜브 구독 이벤트 응모합니다. [유튜브 닉네임을 입력해주세요]");
                emailSend.putExtra(Intent.EXTRA_TEXT,"구독한 스크린샷을 올려주세요. \n이 이메일은 [응모] 애플리케이션을 사용한 이메일입니다.");
                emailSend.setType("application/image");

                // Uri uri = Uri.parse("file:///" + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/hyangSu_ScreenShot/" + DataList.picture);
                Uri uri = Uri.parse("file:///sdcard/Pictures" + "/hyangSu_ScreenShot/" + DataList.picture);
                Log.d("pictureTest", "사진 : " + DataList.picture);
                emailSend.putExtra(Intent.EXTRA_STREAM, uri);
                // emailSend.putExtra(Intent.EXTRA_STREAM, Uri.parse(DataList.picture)); //파일 첨부

                startActivity(emailSend);
            }
        });

        return view;
    }

}