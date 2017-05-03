package ru.rubicon.salary.presentation.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import ru.rubicon.salary.BuildConfig;
import ru.rubicon.salary.R;

public class DeleteDialog extends DialogFragment {

    private static final String ID = "id";
    private DeleteDialogListener listener;
    private int id;

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DeleteDialogListener){
            this.listener = (DeleteDialogListener)context;
        }
        if (getParentFragment() instanceof DeleteDialogListener){
            this.listener = (DeleteDialogListener)getParentFragment();
        }

        if (listener == null){
            throw new IllegalArgumentException("context must implement DeleteDialogListener");
        }
    }

    @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        readID();
        View form = createUIView();
        return buildDialog(form);
    }

    private View createUIView(){
        View form = getActivity().getLayoutInflater()
                .inflate(R.layout.delete_item_dialog, null, true);
        TextView delete = (TextView) form.findViewById(R.id.delete);
        delete.setOnClickListener(v -> {
            listener.onDialogComplete(id);
            dismiss();
        });
        return form;
    }

    private Dialog buildDialog(View form){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(form);
        Dialog dialog = builder.create();
        return dialog;
    }

    private void readID(){
        Bundle bundle = getArguments();
        id = bundle.getInt(ID, -1);
        if (id == -1 && BuildConfig.DEBUG){
            throw new IllegalArgumentException("id must be real");
        }
    }

    public static DialogFragment getInstance(int id){
        DeleteDialog fragment = new DeleteDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    public interface DeleteDialogListener{
        void onDialogComplete(int id);
    }
}
