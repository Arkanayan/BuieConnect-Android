package me.arkanayan.buieconnect.activities;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ViewSwitcher;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import me.arkanayan.buieconnect.R;
import me.arkanayan.buieconnect.adapters.NoticesAdapter;
import me.arkanayan.buieconnect.models.Notice;
import me.arkanayan.buieconnect.services.NoticeService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

            final List<Notice> notices = new ArrayList<Notice>();
//            for (int i = 0 ; i < 1; i++) {
//                Notice tempNotice = new Notice();
//                tempNotice.setTitle("## This is title* " + i);
//                tempNotice.setMessage("*This is message " + i + "*");
//                notices.add(tempNotice);
//            }
            // final ProgressBar dialog = (ProgressBar) view.findViewById(R.id.notice_progressbar);
            //recyclerView.setVisibility(View.GONE);
            final RelativeLayout indicatorView = (RelativeLayout) view.findViewById(R.id.loadingIndicatorView);

            final NoticesAdapter noticesAdapter = new NoticesAdapter(notices, mListener);
            final SlideInBottomAnimationAdapter adapter = new SlideInBottomAnimationAdapter(noticesAdapter);
            adapter.setInterpolator(new OvershootInterpolator());
            adapter.setDuration(500);
            recyclerView.setAdapter(adapter);

//            dialog.setVisibility(View.GONE);
//            recyclerView.setVisibility(View.VISIBLE);
            //todo fetch and display notices here
            NoticeService noticeService = new NoticeService();

            Call<List<Notice>> noticesCall = noticeService.getNoticesCall();
            noticesCall.enqueue(new Callback<List<Notice>>() {
                @Override
                public void onResponse(Call<List<Notice>> call, final Response<List<Notice>> response) {
                    if (response.isSuccessful()) {
//                        NoticesAdapter noticesAdapter = new NoticesAdapter((response.body()), mListener);
//                        ScaleInAnimationAdapter adapter = new ScaleInAnimationAdapter(noticesAdapter);
//                        adapter.setInterpolator(new OvershootInterpolator());
//                        adapter.setDuration(500);
//                        SlideInBottomAnimatorAdapter<NoticesAdapter.ViewHolder> adapter =
//                                new SlideInBottomAnimatorAdapter<>(noticesAdapter, recyclerView);
                        // dialog.setVisibility(View.GONE);

                        //   recyclerView.setAdapter(adapter);
                        indicatorView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                notices.addAll(response.body());
                                adapter.notifyItemRangeChanged(0, notices.size() - 1);
                            }
                        }, 100);

                        // adapter.notifyItemInserted(notices.size() - 1);
//                        synchronized (recyclerView) {
//                            recyclerView.notify();
//                        }

                    }
                }

                @Override
                public void onFailure(Call<List<Notice>> call, Throwable t) {

                }
            });
        }
        return view;
    }

    public void hideLoadingIndicator() {
        final RelativeLayout indicatorView = (RelativeLayout) getView().findViewById(R.id.loadingIndicatorView);
        if (indicatorView != null) {
            indicatorView.animate().alpha(0.0f).setDuration(500)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            indicatorView.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }

                    });
        }


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
