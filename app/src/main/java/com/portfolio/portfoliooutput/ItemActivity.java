package com.portfolio.portfoliooutput;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class ItemActivity extends AppCompatActivity {

    private static String IP_ADDRESS = "34.84.90.157";
    private static String TAG = "DATABASE";

    private static final int MY_PERMISSION_STORAGE = 1111;

    private ArrayList<ADInsertData> mArrayList;
    private UsersAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String mJsonString;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navigation_item_home:
                        Toast.makeText(ItemActivity.this, menuItem.getTitle(), Toast.LENGTH_LONG).show();
                        break;
                }

                return true;
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ItemActivity.this));

        mRecyclerView.addOnItemTouchListener(new ItemActivity.RecyclerTouchListener(ItemActivity.this, mRecyclerView, new ItemActivity.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ADInsertData dict = mArrayList.get(position);
                // Toast.makeText(getApplicationContext(), dict.getMember_longex(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(ItemActivity.this, ApplyActivity.class);

                DataList.longex = dict.getMember_longex();
                DataList.caution = dict.getMember_caution();
                DataList.youtubeid = dict.getMember_youtubeid();
                DataList.email = dict.getMember_email();

                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

    }

    @Override
    public void onResume() {
        super.onResume();

        //데이터를 저장할 List 생성
        mArrayList = new ArrayList<>();
        //Adapter 생성
        mAdapter = new UsersAdapter(this, mArrayList);
        //데이터와 뷰 연결
        mRecyclerView.setAdapter(mAdapter);

        //다시 출력하기 위해서 리스트를 삭제
        mArrayList.clear();
        //데이터를 뷰에 반영
        mAdapter.notifyDataSetChanged();

        //데이터를 다시 가져와서 재출력
        ItemActivity.GetData task = new ItemActivity.GetData();
        task.execute("http://" + IP_ADDRESS + "/getjson.php", "");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ItemActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ItemActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }

    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ItemActivity.this,
                    "잠시만 기다려주세요.", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "response - " + result);

            if (result == null) {

            } else {
                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = params[1];

            while (true) {
                try {

                    URL url = new URL(serverURL);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                    httpURLConnection.setReadTimeout(5000);
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.connect();


                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    outputStream.write(postParameters.getBytes("UTF-8"));
                    outputStream.flush();
                    outputStream.close();


                    int responseStatusCode = httpURLConnection.getResponseCode();
                    Log.d(TAG, "response code - " + responseStatusCode);

                    InputStream inputStream;
                    if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                        inputStream = httpURLConnection.getInputStream();
                    } else {
                        inputStream = httpURLConnection.getErrorStream();
                    }


                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }

                    bufferedReader.close();

                    return sb.toString().trim();


                } catch (Exception e) {

                    Log.d(TAG, "GetData : Error ", e);
                    errorString = e.toString();

                    return null;
                }

            }

        }


        private void showResult() {

            String TAG_JSON = "pickme";
            String TAG_TITLE = "title";
            String TAG_ADEX = "adex";
            String TAG_YOUTUBEID = "youtubeid";
            String TAG_EMAIL = "email";
            String TAG_LONGEX = "longex";
            String TAG_CAUTION = "caution";
            String TAG_SDATE = "startdate";
            String TAG_EDATE = "enddate";


            try {
                //JSON 데이터를 받아서 파싱

                //문자열을 객체로 변환 -  { }
                JSONObject jsonObject = new JSONObject(mJsonString);
                //객체 안에서 TAG_JSON 에 해당하는 데이터를 배열로 변환 - [ ]
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

                //배열을 순회
                for (int i = 0; i < jsonArray.length(); i++) {

                    //배열의 데이터를 1개 가져오
                    JSONObject item = jsonArray.getJSONObject(i);
                    //가여온 데이터 읽
                    String title = item.getString(TAG_TITLE);
                    String adex = item.getString(TAG_ADEX);
                    String youtubeid = item.getString(TAG_YOUTUBEID);
                    String email = item.getString(TAG_EMAIL);
                    String longex = item.getString(TAG_LONGEX);
                    String caution = item.getString(TAG_CAUTION);
                    String sdate = item.getString(TAG_SDATE);
                    String edate = item.getString(TAG_EDATE);

                    ADInsertData adInsertData = new ADInsertData();

                    adInsertData.setMember_title(title);
                    adInsertData.setMember_adex(adex);
                    adInsertData.setMember_youtubeid(youtubeid);
                    adInsertData.setMember_email(email);
                    adInsertData.setMember_longex(longex);
                    adInsertData.setMember_caution(caution);
                    adInsertData.setMember_startdate(sdate);
                    adInsertData.setMember_enddate(edate);

                    mArrayList.add(adInsertData);
                    mAdapter.notifyDataSetChanged();


                }


            } catch (JSONException e) {

                Log.d(TAG, "showResult : ", e);
            }

        }

    }
}
