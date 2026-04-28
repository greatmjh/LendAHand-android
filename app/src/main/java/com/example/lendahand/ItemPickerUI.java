package com.example.lendahand;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.util.ArrayList;

public class ItemPickerUI {
    //callback function
    public interface ItemPickerCallback {
        void onComplete(ItemCategory result);
    }

    //function called by user
    public static void chooseFromScreen(Context context, ItemPickerCallback callback) {
        //build list of options
        ArrayList<ItemCategory> roots = ItemCategory.getRoots();
        String[] options = new String[roots.size()];
        for (int i = 0; i < roots.size(); i++) {
            options[i] = roots.get(i).getItemName();
        }
        //Base category picker
        final int[] resultIndex = {-1}; //sneaky trick to let us modify from inside the onclick
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select item category");
        builder.setSingleChoiceItems(options, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resultIndex[0] = which;
                dialog.dismiss();
            }
        });
        builder.setOnDismissListener(dialog -> {
            //only go down if the user actually clicked something
            if (resultIndex[0] != -1) {
                ItemCategory next = roots.get(resultIndex[0]);
                internalCallback.run(context, callback, next);
            }

        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    //internal functions
    private interface InternalCallback {
        void run(Context context, ItemPickerCallback finalCallback, ItemCategory result);
    }

    private static final InternalCallback internalCallback = new InternalCallback() {
        @Override
        public void run(Context context, ItemPickerCallback finalCallback, ItemCategory result) {
            chooseFromScreenRec(context, result, finalCallback);
        }
    };

    private static void chooseFromScreenRec(Context context, ItemCategory parent, ItemPickerCallback finalCallback) {
        //build list of options
        String[] options = new String[parent.getChildren().size()+1];
        for (int i = 0; i < parent.getChildren().size(); i++) {
            options[i] = parent.getChildren().get(i).getItemName();
        }
        options[parent.getChildren().size()] = "No subcategory";

        //Base category picker
        final int[] resultIndex = {-1}; //sneaky trick to let us modify from inside the onclick
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select subcategory");
        builder.setSingleChoiceItems(options, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resultIndex[0] = which;
                dialog.dismiss();
            }
        });
        builder.setOnDismissListener(dialog -> {

            if (resultIndex[0] == -1 || resultIndex[0] == parent.getChildren().size()) {
                //Exit case 1: user clicked no subcategory or they clicked nothing
                finalCallback.onComplete(parent);
            } else if (parent.getChildren().get(resultIndex[0]).getChildren().isEmpty()){
                //Exit case 2: user clicked on a leaf
                finalCallback.onComplete(parent.getChildren().get(resultIndex[0]));
            } else {
                //Recurse deeper
                internalCallback.run(context, finalCallback, parent);
            }

        });
        builder.create().show();

    }
}
