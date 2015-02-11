package com.example.syl.fragmentdemo;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FoodDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FoodDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodDetailFragment extends Fragment implements Animator.AnimatorListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public static final String TAG = "FoodDetailFragment";

    private static final String ARG_IMAGE_ID = "ImageID";
    private static final String ARG_TITLE = "Title";

    //private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoodDetailFragment.
     */
    public static FoodDetailFragment newInstance(int imageID, String title) {
        FoodDetailFragment fragment = new FoodDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE_ID, imageID);
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    public FoodDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // bind
        //ImageView iv = (ImageView)view.findViewById(R.id.food_image);
        //TextView tv = (TextView)view.findViewById(R.id.food_label);
        //
        //Bundle args = getArguments();
        //iv.setImageResource(args.getInt(ARG_PIC_ID));
        //tv.setText(args.getString(ARG_NAME));
    }

    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {
        //return super.onCreateAnimator(transit, enter, nextAnim);

        Animator animator = AnimatorInflater.loadAnimator(getActivity(),
                enter ? android.R.animator.fade_in : android.R.animator.fade_out);

        if(enter) {
            animator.addListener(this);
        }

        return animator;
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {

        Scene scene = Scene.getSceneForLayout((ViewGroup) getView(), R.layout.fragment_food_detail_content, getActivity());
        TransitionManager.go(scene);

        bind(scene.getSceneRoot());
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    private void bind(View parent) {
        Bundle args = getArguments();

        ImageView imageView = (ImageView)parent.findViewById(R.id.image);
        TextView textView = (TextView)parent.findViewById(R.id.title);

        imageView.setImageResource(args.getInt(ARG_IMAGE_ID));
        textView.setText(args.getString(ARG_TITLE));
    }

    // TODO: Rename method, update argument and hook method into UI event
    //public void onButtonPressed(Uri uri) {
    //    if (mListener != null) {
    //        mListener.onFragmentInteraction(uri);
    //    }
    //}

    //@Override
    //public void onAttach(Activity activity) {
    //    super.onAttach(activity);
    //    try {
    //        mListener = (OnFragmentInteractionListener) activity;
    //    } catch (ClassCastException e) {
    //        throw new ClassCastException(activity.toString()
    //                + " must implement OnFragmentInteractionListener");
    //    }
    //}

    //@Override
    //public void onDetach() {
    //    super.onDetach();
    //    mListener = null;
    //}

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
    //public interface OnFragmentInteractionListener {
    //    // TODO: Update argument type and name
    //    public void onFragmentInteraction(Uri uri);
    //}

}
