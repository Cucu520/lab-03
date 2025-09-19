package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {

    public interface EditCityDialogListener {
        void onCityEdited(int position, String newName, String newProvince);
    }

    private EditCityDialogListener listener;

    public static EditCityFragment newInstance(int position, String name, String province) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("name", name);
        args.putString("province", province);

        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);

        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        int position = getArguments().getInt("position");
        String oldName = getArguments().getString("name");
        String oldProvince = getArguments().getString("province");

        editCityName.setText(oldName);
        editProvinceName.setText(oldProvince);

        return new AlertDialog.Builder(requireContext())
                .setView(view)
                .setTitle("Edit City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", (dialog, which) -> {
                    String newName = editCityName.getText().toString().trim();
                    String newProvince = editProvinceName.getText().toString().trim();
                    listener.onCityEdited(position, newName, newProvince);
                })
                .create();
    }
}
