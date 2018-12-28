package net.offbeatpioneer.android.components.pinpad;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.offbeatpioneer.android.auxiliary.ActivityHelper;
import net.offbeatpioneer.android.components.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

/**
 * A PinPad fragment.
 * <p>
 * Activities that contain this fragment must implement the
 * {@link PinPadFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PinPadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PinPadFragment extends Fragment implements View.OnClickListener {
    ViewGroup rootView;
    public static String ARG_PARAM_1 = "pinLength";
    public static String ARG_PARAM_2 = "showSuccessFragment";
    PinData pinData = new PinData("", 4);
    int currentPin = -1;
    boolean showSuccessFragment = false;
    List<ImageView> pinCountList = new ArrayList<>();
    String pinEntered = "";
    private PinPadInteraction pinPadListener;

    private OnFragmentInteractionListener mListener;

    public PinPadFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pinData
     * @param showSuccessFragment
     * @return A new instance of fragment PinPadFragment.
     */
    public static PinPadFragment newInstance(PinData pinData, boolean showSuccessFragment) {
        PinPadFragment fragment = new PinPadFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_1, pinData);
        args.putBoolean(ARG_PARAM_2, showSuccessFragment);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pinData = (PinData) getArguments().getSerializable(ARG_PARAM_1);
            showSuccessFragment = getArguments().getBoolean(ARG_PARAM_2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_pinpad, container, false);
        LinearLayout pinContent = (LinearLayout) rootView.findViewById(R.id.pinContent);
        createPinCircles(pinContent);
        ActivityHelper activityHelper = ActivityHelper.getInstance().init(getContext());
        for (int i = 0; i < 9; i++) {
            Button btn = (Button) rootView.findViewById(activityHelper.getResourceId("btn_" + i, "id"));
            btn.setOnClickListener(this);
        }
        ImageButton btnDelete = (ImageButton) rootView.findViewById(R.id.btn_delete_pin);
        btnDelete.setOnClickListener(this);
        return rootView;
    }

    public void createPinCircles(LinearLayout pinContent) {
        pinContent.removeAllViews();
        pinCountList.clear();
        for (int i = 0; i < pinData.getPinLength(); i++) {
            ImageView circle;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                circle = new ImageView(getContext(), null, 0, R.style.PinPad_Circle_Style);
            } else {
                circle = new AppCompatImageView(getContext(), null, 0);
            }
            circle.setBackgroundResource(R.drawable.shape_pin_circle);
            pinContent.addView(circle);
            pinCountList.add(circle);
        }

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onCompletedInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {

        if (currentPin == -1 && pinPadListener != null) pinPadListener.interaction();
        if (view.getId() == R.id.btn_delete_pin) {
            if (currentPin != -1) {
                pinCountList.get(currentPin).setBackgroundResource(R.drawable.shape_pin_circle);
                currentPin = currentPin <= 0 ? -1 : currentPin - 1;
                pinEntered = removeLastCharacter(pinEntered);
            }
        } else {
            pinEntered += ((Button) view).getText();
            if (currentPin + 1 < pinData.getPinLength() - 1) {
                pinCountList.get(currentPin + 1).setBackgroundResource(R.drawable.shape_pin_circle_full);
                currentPin++;
                pinCountList.get(currentPin).setScaleX(0.34f);
                pinCountList.get(currentPin).setScaleY(0.34f);
                pinCountList.get(currentPin).animate().scaleX(1f).scaleY(1f).setDuration(250).start();
            } else {
                currentPin = pinData.getPinLength() - 1; //currentPin < pinLength ? currentPin + 1 : pinLength - 1;
                pinCountList.get(currentPin).setBackgroundResource(R.drawable.shape_pin_circle_full);
                String hash = "";
                try {
                    hash = ActivityHelper.generateSHA256(pinEntered);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ActivityHelper.enableDisableViewGroup(rootView, false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resetPins();
                        ActivityHelper.enableDisableViewGroup(rootView, true);
                    }
                }, 2000);
                if (pinData.getPinHash().equals(hash)) {
                    mListener.onCompletedInteraction(null);
                } else {
                    mListener.onWrongPin();
                }

            }
        }
    }

    public void setPinPadListener(PinPadInteraction pinPadListener) {
        this.pinPadListener = pinPadListener;
    }

    public void resetPins() {
        currentPin = -1;
        pinEntered = "";
        for (ImageView each : pinCountList) {
            each.setBackgroundResource(R.drawable.shape_pin_circle);
        }
    }

    private String removeLastCharacter(String input) {
        return input.substring(0, input.length() - 1);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onCompletedInteraction(Uri uri);

        void onWrongPin();
    }

    /**
     * Only for intern communication between the {@link PinPadView} and this fragment.
     */
    interface PinPadInteraction {
        void interaction();
    }
}
