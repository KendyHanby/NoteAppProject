package com.sxi.notes;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sxi.notes.adapter.SubTaskEditorAdapter;
import com.sxi.notes.databinding.FragmentTaskEditorBinding;
import com.sxi.notes.model.SubTaskModel;
import com.sxi.notes.model.TaskModel;

import java.util.List;

public class TaskEditorFragment extends BottomSheetDialogFragment {

    private FragmentTaskEditorBinding binding;
    private MySqlHelper db;

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
        binding = FragmentTaskEditorBinding.inflate(inflater,container,false);

        binding.listSubTask.setHasFixedSize(true);
        binding.listSubTask.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        binding.listSubTask.setAdapter(new SubTaskEditorAdapter(null,requireContext()));

        binding.button.setOnClickListener(v->{
            dismiss();
        });

        return binding.getRoot();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        String title = binding.taskTitle.getText().toString();

        db.saveTask(new TaskModel());
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.getRoot().getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            binding.getRoot().getWindowVisibleDisplayFrame(r);
            int heightDiff = binding.getRoot().getRootView().getHeight() - (r.bottom - r.top);
            if (heightDiff > dpToPx( requireContext(),200f)) {
                binding.getRoot().setPadding(0, 0, 0, heightDiff - dpToPx( requireContext(),50f));
            } else {
                binding.getRoot().setPadding(0, 0, 0, 0);
            }
        });
        /*getDialog().setOnShowListener(dialog -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog;
            FrameLayout bottomSheet = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
            behavior.setPeekHeight(bottomSheet.getHeight());
        });*/
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    private int dpToPx(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

}