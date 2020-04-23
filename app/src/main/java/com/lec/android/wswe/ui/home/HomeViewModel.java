package com.lec.android.wswe.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Java/com.lec.android.wswe/ui/home/HomeViewMedel test");
    }

    public LiveData<String> getText() {
        return mText;
    }
}