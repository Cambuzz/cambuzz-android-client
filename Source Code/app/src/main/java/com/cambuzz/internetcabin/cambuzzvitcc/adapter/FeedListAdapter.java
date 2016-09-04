package com.cambuzz.internetcabin.cambuzzvitcc.adapter;

import com.cambuzz.internetcabin.cambuzzvitcc.FeedImageView;
import com.cambuzz.internetcabin.cambuzzvitcc.R;
import com.cambuzz.internetcabin.cambuzzvitcc.app.AppController;
import com.cambuzz.internetcabin.cambuzzvitcc.data.FeedItem;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class FeedListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<FeedItem> feedItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public FeedListAdapter(Activity activity, List<FeedItem> feedItems) {
        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int location) {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.feed_item, null);
        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();


        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView timestamp = (TextView) convertView.findViewById(R.id.timestamp);
        TextView buzz_title = (TextView) convertView.findViewById(R.id.txtTitle);
        TextView buzz_content = (TextView) convertView.findViewById(R.id.txtContent);
        Button bt_start = (Button) convertView.findViewById(R.id.bt_start);
        Button bt_end = (Button) convertView.findViewById(R.id.bt_end);

        NetworkImageView profilePic = (NetworkImageView) convertView
                .findViewById(R.id.profilePic);
        FeedImageView feedImageView = (FeedImageView) convertView
                .findViewById(R.id.feedImage1);


        FeedItem item = feedItems.get(position);






        name.setText(item.getName());
        buzz_title.setText(item.getBuzz_title());
        buzz_content.setText(item.getBuzz_content());
        bt_start.setText(item.getS_time());
        bt_end.setText(item.getE_time());
        timestamp.setText(item.getTimeStamp());


        // user profile pic
        profilePic.setImageUrl(item.getProfilePic(), imageLoader);

        // Feed image
        if (item.getImge() != null)
        {
            feedImageView.setImageUrl(item.getImge(), imageLoader);
            feedImageView.setVisibility(View.VISIBLE);
            feedImageView
                    .setResponseObserver(new FeedImageView.ResponseObserver()
                    {
                        @Override
                        public void onError()
                        {
                        }

                        @Override
                        public void onSuccess()
                        {
                        }
                    });
        }
        else
        {
            feedImageView.setVisibility(View.GONE);
        }

        return convertView;
    }

}