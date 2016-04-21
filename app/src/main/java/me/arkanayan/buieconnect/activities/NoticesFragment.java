package me.arkanayan.buieconnect.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.Query;

import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;
import me.arkanayan.buieconnect.R;
import me.arkanayan.buieconnect.adapters.FirebaseNoticeAdapter;
import me.arkanayan.buieconnect.models.Notice;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class NoticesFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    private Firebase ref;
    private FirebaseNoticeAdapter mAdapter;
    private Query queryRef;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NoticesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static NoticesFragment newInstance(int columnCount) {
        NoticesFragment fragment = new NoticesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initialize firebase
        ref = new Firebase(getString(R.string.firebase_url)).child("notices");
        queryRef = ref.orderByPriority().limitToFirst(15);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_notices_list, container, false);

        // Set the adapter
        if (view instanceof FrameLayout) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_list);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            // Set animator
            recyclerView.setItemAnimator(new SlideInRightAnimator(new OvershootInterpolator(0.5f)));
            recyclerView.getItemAnimator().setAddDuration(400);

           // final RelativeLayout indicatorView = (RelativeLayout) view.findViewById(R.id.loadingIndicatorView);

            mAdapter = new FirebaseNoticeAdapter(Notice.class, R.layout.fragment_notices, FirebaseNoticeAdapter.ViewHolder.class
                    , queryRef, mListener);

            // final NoticesAdapter noticesAdapter = new NoticesAdapter(notices, mListener);
            final SlideInBottomAnimationAdapter adapter = new SlideInBottomAnimationAdapter(mAdapter);
            adapter.setInterpolator(new OvershootInterpolator());
            adapter.setDuration(1000);
            recyclerView.setAdapter(mAdapter);

            // Use to scroll to top to show recent added notice
            mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                    recyclerView.smoothScrollToPosition(0);
                }
            });


        }
        return view;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onNoticeSelected(Notice item);
    }
}
