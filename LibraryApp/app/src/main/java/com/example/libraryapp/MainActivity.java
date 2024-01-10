package com.example.libraryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.nfc.tech.TagTechnology;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private BottomNavigationView bottomNavigationView; //하단 바
    private FragmentManager fm;
    private FragmentTransaction ft;
    private int SearchCount = 0; //검색 객체 구분 변수
    private Frag_home f_home; //홈 화면 객체
    private Frag_map f_map; //도서관 맵 화면 객체
    private Frag_Rentalinfo f_rentalInfo; //대여정보 화면 객체
    private Frag_search f_search; //검색화면 객체
    private Frag_search f_search2;//교체용 검색화면 객체
    private Frag_loan f_loan;
    private Frag_AllRentalInfo f_allRentalInfo;
    private Button btn_search;//검색창 옆 검색 버튼
    private String jdata;
    private JSONArray jsonArray;
    private String imageUrl;
    private String bookTitle;
    private String[] items = {"제목","저자"};
    private String title,mode;
    private ArrayList<BookData> bookDataArrayList;

    private EditText searchView;
    public static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) { //기본 Main 함수
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        searchView = findViewById(R.id.searchView);
        Spinner spinner = findViewById(R.id.Search_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (items[i].equals("제목")){
                    mode = "1";
                }
                else{
                    mode = "2";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mode = "1";
            }
        });

        btn_search = (Button) findViewById(R.id.search_button);// 검색버튼 id와 일치하는 버튼 호출
        btn_search.setOnClickListener(new View.OnClickListener() { //클릭 리스너
            @Override
            public void onClick(View view) {
                title = searchView.getText().toString();
                bookDataArrayList = new ArrayList<>();
                new t1().start();
            }
        });


        bottomNavigationView = findViewById(R.id.bottomNavi); //하단 바 (하단 네비게이션 바)
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) { //하단바 각 버튼에 프래그먼트 매칭
                switch (item.getItemId()){
                    case R.id.action_home: //첫 번째 선택 시 홈 프래그먼트
                        setFrag(0);
                        searchView.setVisibility(View.VISIBLE);     // 검색창 표시 (메인 레이아웃에서 레이아웃으로 검색창,버튼 묶어서 처리하기)
                        btn_search.setVisibility(View.VISIBLE);
                        spinner.setVisibility(View.VISIBLE);// 검색버튼 표시
                        break;
                    case R.id.action_map: //두번째 선택 시 도서관 맵 프래그먼트
                        setFrag(1);
                        searchView.setVisibility(View.GONE);    // 검색창 삭제
                        btn_search.setVisibility(View.GONE);
                        spinner.setVisibility(View.GONE);// 검색버튼 삭제
                        break;
                    case R.id.action_bookinfo: //세번째 선택 시 대여정보 프래그먼트
                        setFrag(2);
                        searchView.setVisibility(View.GONE);    // 상동
                        btn_search.setVisibility(View.GONE);
                        spinner.setVisibility(View.GONE);// 상동
                        break;
                    case R.id.action_scan: //네번째 선택 시 대출반납 프래그먼트
                        setFrag(4);
                        searchView.setVisibility(View.GONE);    // 상동
                        btn_search.setVisibility(View.GONE);
                        spinner.setVisibility(View.GONE);// 상동
                        break;
                }
                return true;
            }
        });
        f_home = new Frag_home();
        f_map = new Frag_map();
        f_rentalInfo = new Frag_Rentalinfo();
        f_search = new Frag_search();
        f_search2 = new Frag_search();
        f_loan = new Frag_loan();
        setFrag(0); //시작 fragment 화면 지정
    }

    private final String[][] techList = new String[][]{     // NFC 기술이 감지할 수 있는 TAG 종류
            new String[]{
                    NfcA.class.getName(),
                    NfcB.class.getName(),
                    NfcF.class.getName(),
                    NfcV.class.getName(),
                    NdefFormatable.class.getName(),
                    TagTechnology.class.getName(),
                    IsoDep.class.getName(),
                    MifareClassic.class.getName(),
                    MifareUltralight.class.getName(),
                    Ndef.class.getName()
            }
    };

    public void frag_onResume(){    // 프래그먼트에서 대출버튼 클릭시 호출
        Log.v("onResume", "onResume");
        /* PendingIntent : NFC가 감지되면 Intent를 생성(특정시점(나중에) Intent 생성)
        FLAG_ACTIVITY_SINGLE_TOP : 중복 호출 방지(스택에 기존의 같은게 있다면 새로 호출X) */
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter filter = new IntentFilter();   // IntenFilter 생성
        filter.addAction(NfcAdapter.ACTION_TAG_DISCOVERED); // Intent필터 지정(NFC 스캔 됐을 때로)
        filter.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_TECH_DISCOVERED);
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, new IntentFilter[]{filter}, this.techList);    // onResume()에서 호출하는게 좋음
    }

    @Override
    protected void onNewIntent(Intent intent) {     // NFC 구현시 onResume()과 같은 곳에 구현하는게 좋음
        super.onNewIntent(intent);
        f_loan = (Frag_loan) getSupportFragmentManager().findFragmentByTag("tag_loan"); // getSupportFragmentManager(): 프래그먼트 매니저에 접근
        if (f_loan != null) {
            f_loan.handleIntent(intent);
        }
    }

    //fragment 교체 함수
    //fragment 사용시 필수인듯 함...암튼 써야댐
    public void setFrag(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n){
            case 0:     // 홈 화면
                ft.replace(R.id.main_frame, f_home);
                ft.commit();
                break;
            case 1:     // 맵 화면
                ft.replace(R.id.main_frame, f_map);
                ft.commit();
                break;
            case 2:     // 대여정보 화면
                ft.replace(R.id.main_frame, f_rentalInfo);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case 3:     // 검색 화면
                if(SearchCount == 1){
                    ft.replace(R.id.main_frame, f_search2);
                    SearchCount--;
                }
                else{
                    ft.replace(R.id.main_frame, f_search);
                    SearchCount++;
                }
                ft.commit();
                break;
            case 4:     // 대출 화면
                ft.replace(R.id.main_frame, f_loan, "tag_loan");
                ft.commit();
                break;

            case 5:     // 모든 대여 기록 화면
                f_allRentalInfo = new Frag_AllRentalInfo();
                ft.replace(R.id.main_frame, f_allRentalInfo);
                ft.addToBackStack(null);
                ft.commit();
        }
    }

    class t1 extends Thread{
        public void run() {
            ServerConnector.ConnectionParams params = new ServerConnector.ConnectionParams();
            params.setOption(4).setTitle(title, mode);
            jdata = ServerConnector.connect(params);  //Jsp로 부터 값을 전달 받음
            if(jdata.equals("실패")){ //Jsp 연결 실패 시 Toast 메세지로 띄워줌
                Log.v("FailedSearch", "Fail");
            }else{//성공 시
                try {
                    jsonArray = new JSONArray(jdata);//현 Jsp서버는 JsonArray형태 이므로 넘겨받은 String을 Array형식으로 저장
                    for(int i=0; i<jsonArray.length(); i++){//넘겨받은 Array를 모두 추출
                        JSONObject jsonObject = jsonArray.getJSONObject(i);//Array안에 Key:Values형태의 Object로 추출
                        imageUrl = jsonObject.getString("도서이미지");   // 책 이미지 URL 저장
                        bookTitle = jsonObject.getString("제목");           // 책 제목 저장
                        String author = jsonObject.getString("저자");     // 책 저자 저장
                        String publisher = jsonObject.getString("출판사");     // 출판사 저장
                        String isbn = jsonObject.getString("ISBN");     // ISBN 저장
                        String summary = jsonObject.getString("도서소개");  // 책 소개 저장
                        bookDataArrayList.add(new BookData(imageUrl, bookTitle,author,publisher,isbn,summary));//값들을 List에 저장
                    }
                    f_search.setData(bookDataArrayList);//검색화면으로 데이터 List를 전달
                    f_search2.setData(bookDataArrayList);
                    setFrag(3);//App Ui 화면 전환

                } catch (JSONException | NullPointerException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}