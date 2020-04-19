package com.learning.eventadmin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ClientsAdapter extends ArrayAdapter<Clients> {

    private Activity context;
    private List<Clients> listC;

    public ClientsAdapter(Activity context,List<Clients> listC) {
        super(context,R.layout.single_client_info_layout,listC);
        this.context=context;
        this.listC=listC;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View listViewItem = layoutInflater.inflate(R.layout.single_client_info_layout,null,true);
        TextView NameC = listViewItem.findViewById(R.id.NameC);
        TextView displayCollegeNameC = listViewItem.findViewById(R.id.displayCollegeNameC);
        TextView displayCollegeStudentIDC = listViewItem.findViewById(R.id.displayCollegeStudentIDC);
        TextView displayEmailC = listViewItem.findViewById(R.id.displayEmailC);
        TextView displayPhoneC = listViewItem.findViewById(R.id.displayPhoneC);

        final Clients client=listC.get(position);

        NameC.setText(client.getClientName());
        displayCollegeNameC.setText(client.getClientCollege());
        displayCollegeStudentIDC.setText(client.getClientCollegeStudentID());
        displayEmailC.setText(client.getClientEmail());
        displayPhoneC.setText(client.getClientPhoneNumber());

        Animation aniFade = AnimationUtils.loadAnimation(context,R.anim.fade_in_listview);
        listViewItem.startAnimation(aniFade);

        return listViewItem;
    }
}
