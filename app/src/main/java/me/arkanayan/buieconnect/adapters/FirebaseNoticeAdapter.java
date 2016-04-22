package me.arkanayan.buieconnect.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;

import in.uncod.android.bypass.Bypass;
import me.arkanayan.buieconnect.R;
import me.arkanayan.buieconnect.activities.NoticesFragment;
import me.arkanayan.buieconnect.models.Notice;
import me.arkanayan.buieconnect.utils.App;

/**
 * Created by arka on 4/20/16.
 */
public class FirebaseNoticeAdapter extends FirebaseRecyclerAdapter<Notice, FirebaseNoticeAdapter.ViewHolder>  {

    NoticesFragment.OnListFragmentInteractionListener mListener;
    private Bypass mBypass;

    public FirebaseNoticeAdapter(Class<Notice> modelClass, int modelLayout, Class<ViewHolder> viewHolderClass, Query ref
                            , NoticesFragment.OnListFragmentInteractionListener listener) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mListener = listener;
        mBypass = new Bypass(App.getContext());

    }
    

    @Override
    protected void populateViewHolder(ViewHolder viewHolder, final Notice notice, int i) {


        String markdownString = notice.getTitle();
        CharSequence string = mBypass.markdownToSpannable(markdownString);
        viewHolder.mTitleView.setText(string);

        markdownString = notice.getMessage();
        string = mBypass.markdownToSpannable(markdownString);
        viewHolder.mMessageView.setText(string);
        // Log.d("NoticeFragment", "Title: " + notice.getTitle() + " Message: " + notice.getMessage());

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onNoticeSelected(notice);
            }
        });

    }

/*    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(mModelLayout, parent, false);
        mBypass = new Bypass(parent.getContext());

        return new ViewHolder(view);
    }*/

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
