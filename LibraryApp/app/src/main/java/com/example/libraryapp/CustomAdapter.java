package com.example.libraryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

//adapter는 리스트 형식을 보여주기 위해서 데이터와 리스트뷰 사이에 존재하는 객체임.

public class CustomAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {

    private Context context;
    private List list;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();// 각 리스트 아이템 클릭시 toast메세지로 확인차 띄워주는용
    }

    class ViewHolder { //각 리스트 아이템들의 정보 (책 이미지(thumb썸네일), 책 제목, 책 내용)
        public TextView tv_name;
        public TextView tv_summary;
        public ImageView iv_thumb;
    }

    public CustomAdapter(Context context, ArrayList list){ //초기화
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertView = layoutInflater.inflate(R.layout.row_item, parent, false);
        }

        viewHolder = new ViewHolder();
        //layout 파일에 row_item을 보면 각 view들의 id를 매칭한 것
        viewHolder.tv_name = (TextView) convertView.findViewById(R.id.textView_name);
        viewHolder.tv_summary = (TextView) convertView.findViewById(R.id.textView_summary);
        viewHolder.iv_thumb = (ImageView) convertView.findViewById(R.id.imageView_thumb);

        final Book book = (Book) list.get(position); //책정보 가져오기
        viewHolder.tv_name.setText(book.getName());
        viewHolder.tv_summary.setText(book.getSummary());
        Glide //이미지 로딩할때 사용하는 API, drawable 폴더에 잇는 이미지를 가져옴 나중엔 db에서 연결할 예정
                .with(context)
                .load(book.getThumb_url())
                .centerCrop()
                .apply(new RequestOptions().override(250, 350))
                .into(viewHolder.iv_thumb);
        viewHolder.tv_name.setTag(book.getName());

        return convertView;
    }
}
