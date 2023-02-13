package com.sxi.notes;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sxi.notes.databinding.FragmentTaskEditorBinding;
import com.sxi.notes.model.TaskModel;

public class TaskEditorFragment extends BottomSheetDialogFragment {

    private FragmentTaskEditorBinding binding;
    private MySqlHelper db;
    private OnDismiss onDismiss;

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

        binding.button.setOnClickListener(v -> {
            String title = binding.taskTitle.getText().toString();
            long reminder = 0;
            boolean isDone = binding.taskCheck.isChecked();
            if (db.saveTask(new TaskModel(title, reminder, isDone)) == -1) {
                Toast.makeText(requireContext(), "Fail to save task!", Toast.LENGTH_SHORT).show();
            }
            onDismiss.onDismiss();
            dismiss();
        });

        return binding.getRoot();
    }

    public void setOnDismiss(OnDismiss onDismiss){
        this.onDismiss = onDismiss;
    }

    @Override
    public void onStart() {
        super.onStart();
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
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    private int dpToPx(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
    public interface OnDismiss{
        void onDismiss();
    }
}