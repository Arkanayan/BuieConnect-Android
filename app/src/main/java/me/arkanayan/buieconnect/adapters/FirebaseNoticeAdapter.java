package me.arkanayan.buieconnect.adapters;

import android.view.View;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;

import in.uncod.android.bypass.Bypass;
import me.arkanayan.buieconnect.activities.NoticesFragment;
import me.arkanayan.buieconnect.models.Notice;
import me.arkanayan.buieconnect.viewholders.NoticeViewHolder;
import me.arkanayan.buieconnect.utils.App;

/**
 * Created by arka on 4/20/16.
 */
public class FirebaseNoticeAdapter extends FirebaseRecyclerAdapter<Notice, NoticeViewHolder>  {

    NoticesFragment.OnListFragmentInteractionListener mListener;
    private Bypass mBypass;

    public FirebaseNoticeAdapter(Class<Notice> modelClass, int modelLayout, Class<NoticeViewHolder> viewHolderClass, Query ref
                            , NoticesFragment.OnListFragmentInteractionListener listener) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mListener = listener;
        mBypass = new Bypass(App.getContext());

    }
    

    @Override
    protected void populateViewHolder(NoticeViewHolder noticeViewHolder, final Notice notice, int i) {


        String markdownString = notice.getTitle();
        CharSequence string = mBypass.markdownToSpannable(markdownString);
        noticeViewHolder.mTitleView.setText(string);

        markdownString = notice.getMessage();
        string = mBypass.markdownToSpannable(markdownString);
        noticeViewHolder.mMessageView.setText(string);
        // Log.d("NoticeFragment", "Title: " + notice.getTitle() + " Message: " + notice.getMessage());

        noticeViewHolder.mView.setOnClickListener(new View.OnClickListener() {
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


}
