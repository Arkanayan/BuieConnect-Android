package me.arkanayan.buieconnect.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;

import me.arkanayan.buieconnect.R;
import me.arkanayan.buieconnect.activities.NoticesFragment;
import me.arkanayan.buieconnect.models.Notice;

/**
 * Created by arka on 4/20/16.
 */
public class FirebaseNoticeAdapter extends FirebaseRecyclerAdapter<Notice, FirebaseNoticeAdapter.ViewHolder>  {

    NoticesFragment.OnListFragmentInteractionListener mListener;

    public FirebaseNoticeAdapter(Class<Notice> modelClass, int modelLayout, Class<ViewHolder> viewHolderClass, Query ref
                            , NoticesFragment.OnListFragmentInteractionListener listener) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mListener = listener;
    }
    

    @Override
    protected void populateViewHolder(ViewHolder viewHolder, final Notice notice, int i) {
        viewHolder.mTitleView.setText(notice.getTitle());
        viewHolder.mMessageView.setText(notice.getMessage());
        // Log.d("NoticeFragment", "Title: " + notice.getTitle() + " Message: " + notice.getMessage());

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onNoticeSelected(notice);
            }
        });

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mMessageView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.text_view_card_title);
            mMessageView = (TextView) view.findViewById(R.id.text_view_card_message);
        }
    }


}
