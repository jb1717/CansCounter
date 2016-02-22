package com.epitech.jibb.canscounter.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.epitech.jibb.canscounter.R;
import com.epitech.jibb.canscounter.models.User;

import java.util.List;

public class UsersAdapter extends ArrayAdapter<User> {

    public UsersAdapter(Context context, List<User> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_row, parent, false);
        }

        UserViewHolder viewHolder = (UserViewHolder) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new UserViewHolder();
            viewHolder.pseudo = (TextView) convertView.findViewById(R.id.user_name);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.user_image);
            viewHolder.nb_cans = (TextView) convertView.findViewById(R.id.nb_cans);
        }

        User user = getItem(position);
        viewHolder.pseudo.setText(user.getUsername());
        viewHolder.nb_cans.setText(String.valueOf(user.getCans()));
        viewHolder.avatar.setImageDrawable(new ColorDrawable(user.getImg()));

        return convertView;
    }

    private class UserViewHolder {
        public ImageView avatar;
        public TextView pseudo;
        public TextView nb_cans;
    }
}
