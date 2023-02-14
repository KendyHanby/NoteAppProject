package com.sxi.notes;

import static android.view.WindowInsetsAnimation.Callback.DISPATCH_MODE_STOP;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sxi.notes.databinding.FragmentTaskEditorBinding;

import java.util.List;

public class TaskEditorFragment extends BottomSheetDialogFragment {

    private FragmentTaskEditorBinding binding;
    private MySqlHelper db;
    private OnSave onSave;

    public TaskEditorFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new MySqlHelper(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTaskEditorBinding.inflate(inflater, container, false);

        this.getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        binding.save.setOnClickListener(v -> {
            String title = binding.taskTitle.getText().toString();
            long reminder = 0;
            boolean isDone = binding.taskCheck.isChecked();
            onSave.onSave(title, reminder, isDone ? 1 : 0);
            dismiss();
        });

        return binding.getRoot();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return dialog;
    }

    private int getKeyboardHeight() {
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().setOnShowListener(dialog -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog;
            FrameLayout bottomSheet = d.findViewById(R.id.sheet_bg);
            BottomSheetBehavior.from(bottomSheet).setPeekHeight(getKeyboardHeight());
        });

/*
        binding.getRoot().getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            binding.getRoot().getWindowVisibleDisplayFrame(r);
            int heightDiff = binding.getRoot().getRootView().getHeight() - (r.bottom - r.top);
            if (heightDiff > dpToPx(requireContext(), 200f)) {
                binding.getRoot().setPadding(0, 0, 0, heightDiff - dpToPx(requireContext(), 50f));
            } else {
                binding.getRoot().setPadding(0, 0, 0, 0);
            }
        });
*/
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View rootView = view.findViewById(R.id.sheet_bg);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            rootView.getWindowVisibleDisplayFrame(r);
            int screenHeight = rootView.getRootView().getHeight();
            int keyboardHeight = screenHeight - r.bottom;
            if (keyboardHeight > 0) {
                getDialog().show();
            } else {
                getDialog().dismiss();
            }
        });
    }

    public void setOnSave(OnSave onSave) {
        this.onSave = onSave;
    }

    private int dpToPx(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public interface OnSave {
        void onSave(String title, long reminder, int isDone);
    }
}