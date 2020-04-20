package com.lec.android.wswe.ui.cafe;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CafeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CafeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is cafe fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}