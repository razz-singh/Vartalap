package com.brainzerstech.vartalap.Adapters;

import android.support.v4.app.FragmentActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.brainzerstech.vartalap.R;
import com.brainzerstech.vartalap.fragments.ChatFragment;
import com.brainzerstech.vartalap.utilities.CircleTransform;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FriendListAdapter extends BaseAdapter {


    private FragmentActivity context;
    private ArrayList<String> friendList;
    private LayoutInflater inflater;
    private ViewHolder viewHolder;

    public FriendListAdapter(FragmentActivity activity, ArrayList<String> friendsList) {
        this.context = activity;
        this.friendList = friendsList;
        this.inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return friendList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.friend_list_cell,parent,false);
        if (view == null){
            viewHolder = new ViewHolder();

            viewHolder.tvName = view.findViewById(R.id.tv_flist_name);
            viewHolder.tvStatus = view.findViewById(R.id.tv_flist_status);
            viewHolder.ivDp = view.findViewById(R.id.iv_flist_dp);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvName.setText(friendList.get(position));
        //Picasso.with(context.getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/phoneauth-4fd5e.appspot.com/o/PROFILE_PICS%2FJPEG_20180406_011858_1818541435.jpg?alt=media&token=9d872fcd-a0f3-4c7b-a214-f8b58c73c7e4").transform(new CircleTransform()).into(viewHolder.ivDp);

        return view;
    }

    class ViewHolder{
        TextView tvName,tvStatus;
        ImageView ivDp;
    }

}
