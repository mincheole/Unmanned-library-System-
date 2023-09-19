package com.example.libraryapp;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerConnector {
    public static class ConnectionParams{   // 각 멤버변수(파라미터)를 가진 클래스 생성
        int jspOption;
        static String id;
        String pw;
        String rfid;
        String title;
        String mode;

        public ConnectionParams setOption(int jspOption){    // 파라미터 객체 + 빌더 패턴
            this.jspOption = jspOption;     // jsp에 접속할 Option 변수
            return this;
        }

        public ConnectionParams setId(String id){    // 파라미터 객체 + 빌더 패턴
            this.id = id;       // 사용자 id
            return this;
        }

        public ConnectionParams setPw(String pw){
            this.pw = pw;       // 사용자 pw
            return this;
        }

        public ConnectionParams setRfid(String rfid){
            this.rfid = rfid;       // 도서RFID
            return this;
        }

        public ConnectionParams setTitle(String title, String mode){
            this.title = title;     // 책제목
            this.mode = mode;       // 검색 옵션(저자, 제목)
            return this;
        }

    }

    public static String connect(ConnectionParams params) {     // JSP와 통신 메소드, 파라미터를 담은 클래스 타입의 객체를 매개변수로 넘겨받음.
        StringBuffer buf = new StringBuffer();  // JSP 화면에 뜨는 정보들 저장할 변수
        String urlPath = null;

        switch (params.jspOption) {     // 로그인 화면이랑 대여정보 화면에서 connect() 메소드를 사용하기 위함(1일때 로그인, 2일때 대여정보)
            case 1:     // 로그인
                urlPath = "http://whereisthebook.kro.kr:8080/DbConn1/f_login.jsp?userid=" + params.id + "&userpw=" + params.pw;
                break;
            case 2:     // 대여정보
                urlPath = "http://whereisthebook.kro.kr:8080/DbConn1/f_userinfo.jsp?userid="+params.id;
                break;
            case 3:     // 대출반납RFID 및 사용자id 전송
                urlPath = "http://whereisthebook.kro.kr:8080/DbConn1/f_rfidkey.jsp?rfid="+params.rfid + "&userid=" + params.id;
                break;
            case 4:     // 책검색
                urlPath = "http://whereisthebook.kro.kr:8080/DbConn1/f_bookdb.jsp?keyword=" + params.title + "&mode=" + params.mode;   // 제목,저자 입력받아야함(한글 X)
                break;
        }

        try {
            URL url = new URL(urlPath);//Jsp 경로를 변수에 저장
            HttpURLConnection con = (HttpURLConnection) url.openConnection();//안드로이드 Http 통신으로 연결
            if(con != null){
                con.setRequestMethod("POST");    // Http를 GET 또는 POST 방식으로 설정
                con.setDoInput(true);
                con.setDoOutput(true);
//                Log.v("try", "P1");
                InputStream input = con.getInputStream(); //Stream 형식으로 전환
//                Log.v("try", "P2");
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));//버퍼에 저장

                String str = reader.readLine();//버퍼의 데이터를 String 형식으로 전환
                while(str!=null){
//                    Log.v("try", "P3");
                    buf.append(str);//버퍼의 모든 데이터를 변수에 저장
                    str = reader.readLine();
                }

                reader.close();
            }
            con.disconnect();//Http 연결 해제
            Log.i("mytag", "buf "+ buf.toString());     // 로그에서 코드 실행결과(buf값) 확인코드
//            Log.v("try", "P4");
            return buf.toString();    // buf를 string 타입으로(json 값들을 받기 위해해)

        }catch (Exception e){
            Log.v("catch", "실패");
            if(e.getLocalizedMessage() != null)
                Log.i("mytag", e.getLocalizedMessage());    // 로그에서 코드 실행결과 확인코드
        }
        return "실패";
    }
}
