package me.arkanayan.buieconnect.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import me.arkanayan.buieconnect.R;

/**
 * Created by arka on 4/23/16.
 */
public class NoticeViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public final TextView mTitleView;
    public final TextView mMessageView;

    public NoticeViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mMessageView = (TextView) itemView.findViewById(R.id.text_view_card_message);
        mTitleView = (TextView) itemView.findViewById(R.id.text_view_card_title);
    }
}
