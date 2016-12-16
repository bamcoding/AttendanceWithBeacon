package net.gondor.application.listView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import gondor.net.attendance.R;

/**
 * Created by 206-017 on 2016-12-12.
 */

public class LectureItemAdapter extends BaseAdapter {
    private List<LectureItem> lectureItemList;
    private Context context;

    public LectureItemAdapter(List<LectureItem> lectureItemList, Context context) {
        this.lectureItemList = lectureItemList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lectureItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_lecture, parent, false);
            holder = new Holder();
            holder.lectureTitle = (TextView) convertView.findViewById(R.id.lectureTitle);
            holder.lectureDesc = (TextView) convertView.findViewById(R.id.lectureDesc);
            holder.lectureTeacher = (TextView) convertView.findViewById(R.id.lectureTeacher);
            holder.enterTime = (TextView) convertView.findViewById(R.id.enterTime);
            holder.exitTime = (TextView) convertView.findViewById(R.id.exitTime);

            convertView.setTag(holder);
        }
        else {
            holder = (Holder) convertView.getTag();
        }

        // 레이아웃에 있는 컴포넌트를 참조
        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        LectureItem listViewItem = lectureItemList.get(position);

        holder.lectureTitle.setText(listViewItem.getLectureTitle());
        holder.lectureDesc.setText(listViewItem.getLectureDesc());
        holder.lectureTeacher.setText(listViewItem.getLectureTeacher());
        holder.enterTime.setText(listViewItem.getEnterTime());
        holder.exitTime.setText(listViewItem.getExitTime());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return lectureItemList.get(position);
    }

    public class Holder {
        public TextView lectureTitle;
        public TextView lectureDesc;
        public TextView lectureTeacher;
        public TextView enterTime;
        public TextView exitTime;
    }

}
