package com.tristan.hansangdemos.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.tristan.hansangdemos.R;

public class TwoBtnDialogFragment extends DialogFragment {

    private OnDiagBtnClikListener mListener;

    public TwoBtnDialogFragment() {
        // Required empty public constructor
    }


    public TextView title;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two_btn_dialog, container, false);
        title = (TextView)view.findViewById(R.id.dialog_title);
        view.findViewById(R.id.confim_txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onConfimClick();
                getDialog().dismiss();
            }
        });
        view.findViewById(R.id.cacncel_txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelClick();
                getDialog().dismiss();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnDiagBtnClikListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window window = getDialog().getWindow();
        window.setWindowAnimations(R.style.dilaog_anim);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE,0);

    }

    @Override
    public void onResume() {
        super.onResume();
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth() / 5 * 4;
        int height = getActivity().getWindowManager().getDefaultDisplay().getHeight() / 3;
        getDialog().getWindow().setLayout(width, height);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnDiagBtnClikListener {
        // TODO: Update argument type and name
        public void onConfimClick();

        public void onCancelClick();
    }

}
