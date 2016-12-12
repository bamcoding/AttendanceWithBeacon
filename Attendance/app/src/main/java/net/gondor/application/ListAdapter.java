package net.gondor.application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gondor.net.attendance.R;

/**
 * Created by 206-017 on 2016-12-12.
 */

public class ListAdapter extends BaseAdapter {
    private List<ListViewLecture> lectureList = new ArrayList<ListViewLecture>();

    @Override
    public int getCount() {
        return lectureList.size();
    }

    @Override
    public Object getItem(int position) {
        return lectureList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_lecture, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView lectureTitle = (TextView) convertView.findViewById(R.id.lectureTitle);
        TextView lectureDesc = (TextView) convertView.findViewById(R.id.lectureDesc);
        TextView lectureTeacher = (TextView) convertView.findViewById(R.id.lectureTeacher);
        TextView enterTime = (TextView) convertView.findViewById(R.id.enterTime);
        TextView exitTime = (TextView) convertView.findViewById(R.id.exitTime);
        Button enterBtn = (Button) convertView.findViewById(R.id.enterBtn);
        Button exitBtn = (Button) convertView.findViewById(R.id.exitBtn);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewLecture listViewItem = lectureList.get(position);

        lectureTitle.setText(listViewItem.getLectureTitle());
        lectureDesc.setText(listViewItem.getLectureDesc());
        lectureTeacher.setText(listViewItem.getLectureTeacher());
        enterTime.setText(listViewItem.getEnterTime());
        exitTime.setText(listViewItem.getExitTime());

        return convertView;
    }

    public void addItem(String lectureTitle, String lectureDesc, String lectureTeacher, String enterTime, String exitTime) {
        ListViewLecture item = new ListViewLecture();

        item.setLectureTitle(lectureTitle);
        item.setLectureDesc(lectureDesc);
        item.setLectureTeacher(lectureTeacher);
        item.setEnterTime(enterTime);
        item.setExitTime(exitTime);

        lectureList.add(item);
    }
}
