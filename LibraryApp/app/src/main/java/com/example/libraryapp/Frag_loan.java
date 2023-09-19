package com.example.libraryapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag_loan extends Fragment { //대출반납 프래그먼트
    private View view;
    private String rid;
    protected Button btn_checkout, btn_return;
    protected progressDialog customProgressDialog;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_loan, container, false); //레이아웃 inflate로 객체화

        customProgressDialog = new progressDialog(getActivity());   // 대출, 반납 버튼 클릭시 로딩화면(progressBar)
        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));   // progressBar 동작시 배경(어둡게)설정
        customProgressDialog.setCancelable(false);      // 주변 화면 클릭해도 종료 불가능!

        btn_checkout = view.findViewById(R.id.btn_checkout);
        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customProgressDialog.show();    // 로딩 프로그래스 동작
                ((MainActivity)getActivity()).frag_onResume();  // 프래그먼트에서 메인액티비티 안 frag_onResume함수 호출


            }
        });

        btn_return = view.findViewById(R.id.btn_return);
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customProgressDialog.show();    // 로딩 프로그래스 동작
                ((MainActivity)getActivity()).frag_onResume();  // 프래그먼트에서 메인액티비티 안 frag_onResume함수 호출
            }
        });

        return view;
    }

    public void handleIntent(Intent intent) {   // NFC 스캔 함수
        Log.v("handle", "handle");
        if (intent.getAction().equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
            rid = ByteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID));
            Log.v("rid", rid);
            new rfidConnect().start();      // 메서드(스레드) 시작
            customProgressDialog.dismiss(); // 로딩 프로그래스 정지
        }
    }

    class rfidConnect extends Thread{   // RFID 서버로 전송 메서드
        public void run(){
            ServerConnector.ConnectionParams params = new ServerConnector.ConnectionParams();
            params.setOption(3).setRfid(rid);     // 파라미터 옵션 및 RFID 설정
            ServerConnector.connect(params);
        }
    }
    public String ByteArrayToHexString(byte[] array) {    // 스캔한 NFC 바이트 배열을 문자열로 변환 함수
        int i,j,in;
        String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        String out = "";

        for(j=0; j<array.length; ++j){
            in = array[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        return out;
    }
}
