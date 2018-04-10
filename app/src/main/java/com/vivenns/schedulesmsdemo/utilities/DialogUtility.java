package com.vivenns.schedulesmsdemo.utilities;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

class DialogUtility {
    private static final DialogUtility ourInstance = new DialogUtility();
    private ProgressBar progressBar;
    static DialogUtility getInstance() {
        return ourInstance;
    }

    private DialogUtility() {
    }

    private void showProgressBar(Context contex){
        progressBar = new ProgressBar(contex);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
    }
    private void cancelProgressBar(Context context){
        progressBar.setVisibility(View.GONE);
    }

}
