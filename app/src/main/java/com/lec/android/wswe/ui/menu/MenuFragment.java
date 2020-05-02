package com.lec.android.wswe.ui.menu;

import android.accessibilityservice.AccessibilityService;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lec.android.wswe.R;
import com.lec.android.wswe.database.Restaurant;

import java.util.List;

public class MenuFragment extends Fragment {

    private MenuViewModel menuViewModel;
    MenuAdapter menuAdapter;
    RecyclerView recyclerView;
    public static final int ADD_REST_REQUEST = 101;
    public static final int EDIT_REST_REQUEST = 102;
    Dialog addMenuDialog;
    EditText etName, etPhone;
    RatingBar stars;

    TextView tvId, tvName, tvPhone, tvVisited, tvRecentdate, tvLatitude, tvLongitude, tvRandomnum;
    EditText etDetailName, etDetailPhone;
    RatingBar showStar;
    ImageView imageView, detailEdit;
    Button btnConfirm, btnCancel;
    ActionBar actionBar;
    String restName;
    Dialog dialog;
    InputMethodManager manager;
    boolean isKeyUp = false;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuViewModel =
                ViewModelProviders.of(getActivity()).get(MenuViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_menu, container, false);
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int rootViewHeight = root.getRootView().getHeight();
                int relativeHeight = root.getHeight();
                int diff = rootViewHeight - relativeHeight;
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                float dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, metrics);
                if (diff > dp) {
                    isKeyUp = true;
                } else {
                    isKeyUp = false;
                }
            }
        });

        setHasOptionsMenu(true);

        // recyclerView
        recyclerView = root.findViewById(R.id.list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        menuAdapter = new MenuAdapter();
        recyclerView.setAdapter(menuAdapter);

        menuViewModel.getAllRest().observe(getViewLifecycleOwner(), new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(List<Restaurant> restaurants) {
                menuAdapter.setRestaurantList(restaurants);
            }
        });

        // activity_add_restaurant Dialog...
        manager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        addMenuDialog = new Dialog(getActivity());
        addMenuDialog.setContentView(R.layout.activity_add_restaurant);
        addMenuDialog.setOwnerActivity(getActivity());
        addMenuDialog.setCanceledOnTouchOutside(false);
        addMenuDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder
                        .setMessage("식당 추가를 취소하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialogCleaner();
                                addMenuDialog.dismiss();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                addMenuDialog.show();
                            }
                        })
                ;
                builder.show();
            }
        });
        etName = addMenuDialog.findViewById(R.id.etName);
        etPhone = addMenuDialog.findViewById(R.id.etPhone);
        stars = addMenuDialog.findViewById(R.id.stars);
        Button btnAdd = addMenuDialog.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                float starNum = stars.getRating();

                if (checkValue(name)) {
                    Toast.makeText(getContext(), "식당 이름은 필수항목 입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Restaurant restaurant = new Restaurant(name, phone, starNum);
                    menuViewModel.insert(restaurant);
                    if (isKeyUp) {
                        manager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    }
                    dialogCleaner();
                    addMenuDialog.dismiss();
                }// end if
            } // end onClick
        }); // end btnAdd.setOnClickListener

        menuAdapter.setOnClickDeleteListener(new MenuAdapter.OnClickDeleteListener() {
            @Override
            public void onClickDelete(final Restaurant restaurant) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("정말 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                menuViewModel.delete(restaurant);
                            }
                        })
                        .setNegativeButton("취소", null)
                ;
                builder.show();
            }
        }); // end menuAdapter.setOnClickDeleteListener

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.detail_rest);
        dialog.setOwnerActivity(getActivity());
        dialog.setCanceledOnTouchOutside(false);
        Point size = new Point();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        display.getSize(size);
        Window window = dialog.getWindow();
        int x = (int) (size.x * 1f);
        int y = (int) (size.y * 1f);
        window.setLayout(x, y);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.view222);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        menuAdapter.setItemClickListener(new MenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Restaurant restaurant) {
                Log.d("myLog", "setItemClickListener : " + restaurant.getRest_id());

//                Intent intent = new Intent(getActivity(), DetailRest.class);
//                intent.putExtra(DetailRest.EXTRA_ID, restaurant.getRest_id());
//                intent.putExtra(DetailRest.EXTRA_NAME, restaurant.getRest_name());
//                intent.putExtra(DetailRest.EXTRA_PHONE, restaurant.getTelephone());
//                intent.putExtra(DetailRest.EXTRA_STARS, restaurant.getStar());
//                intent.putExtra(DetailRest.EXTRA_VISITED, restaurant.getVisited());
//                intent.putExtra(DetailRest.EXTRA_RECENTDATE, restaurant.getRecentDate());
//                intent.putExtra(DetailRest.EXTRA_LATITUDE, restaurant.getLatitude());
//                intent.putExtra(DetailRest.EXTRA_LONGITUDE, restaurant.getLongitude());
//                intent.putExtra(DetailRest.EXTRA_RANDOMNUM, restaurant.getRandomNum());
//                startActivityForResult(intent, EDIT_REST_REQUEST);
                setDetailTextView(restaurant);
                stateViewMode(restaurant);
                dialog.show();
            }
        }); // end menuAdapter.setItemClickListener()

        return root;
    } // onCreateView()

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
        menu.getItem(0).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
//                Intent intent = new Intent(getContext(), AddRestaurant.class);
//                startActivityForResult(intent, ADD_REST_REQUEST);
                addMenuDialog.show();
                etName.requestFocus();
                if (etName.isFocused()) {
                    manager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogCleaner() {
        etName.setText("");
        etPhone.setText("");
        stars.setRating(5);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == ADD_REST_REQUEST && resultCode == RESULT_OK) {
//            String name = data.getStringExtra(AddRestaurant.EXTRA_NAME);
//            String phone = data.getStringExtra(AddRestaurant.EXTRA_PHONE);
//            float starNum = data.getFloatExtra(AddRestaurant.EXTRA_STARS, 0);
//
//            Restaurant restaurant = new Restaurant(name, phone, starNum);
//            menuViewModel.insert(restaurant);
//
//            Toast.makeText(getContext(), "성공적으로 추가되었습니다.", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(getContext(), "식당 추가가 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
//        } // end if-else
//    } // end onActivityResult()


    public void setDetailTextView(final Restaurant restaurant) {
        tvId = dialog.findViewById(R.id.tvDetailId);
        tvName = dialog.findViewById(R.id.tvDetailName);
        tvPhone = dialog.findViewById(R.id.tvDetailPhone);
        tvVisited = dialog.findViewById(R.id.detailVisited);
        tvRecentdate = dialog.findViewById(R.id.detailRecentDate);
        tvLatitude = dialog.findViewById(R.id.detailLatitude);
        tvLongitude = dialog.findViewById(R.id.detailLongitude);
        tvRandomnum = dialog.findViewById(R.id.detailRandomNum);
        showStar = dialog.findViewById(R.id.detailStar);
        detailEdit = dialog.findViewById(R.id.detail_edit);
        etDetailName = dialog.findViewById(R.id.etDetailName);
        etDetailPhone = dialog.findViewById(R.id.etDetailPhone);
        btnConfirm = dialog.findViewById(R.id.btnConfirm);
        btnCancel = dialog.findViewById(R.id.btnCancel);
        tvId.setText("" + restaurant.getRest_id());
        tvVisited.setText("" + restaurant.getVisited());
        tvRecentdate.setText("" + restaurant.getRecentDate());
        tvLatitude.setText("" + restaurant.getLatitude());
        tvLongitude.setText("" + restaurant.getLongitude());
        tvRandomnum.setText("" + restaurant.getRandomNum());

        detailEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateEditMode();
            }
        }); // end detailEdit

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rest_name = etDetailName.getText().toString().trim();
                if (checkValue(rest_name)) {
                    Toast.makeText(getContext(), "식당 이름은 필수항목 입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    restaurant.setRest_name(etDetailName.getText().toString().trim());
                    restaurant.setTelephone(etDetailPhone.getText().toString().trim());
                    restaurant.setStar(showStar.getRating());
                    menuViewModel.update(restaurant);
                    Toast.makeText(getContext(), "성공적으로 수정되었습니다.", Toast.LENGTH_SHORT).show();
                    stateViewMode(restaurant);
                }
            }
        }); // end btnConfirm

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("정말로 취소하시겠습니까?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                stateViewMode(restaurant);
                            }
                        })
                        .setNegativeButton("아니오", null);
                builder.show();
            }
        });
    } // end setDetailTextView()

    public void stateViewMode(Restaurant restaurant) {
        tvName.setText(restaurant.getRest_name());
        tvPhone.setText(restaurant.getTelephone());
        showStar.setRating(restaurant.getStar());
        etDetailName.setVisibility(View.INVISIBLE);
        tvName.setVisibility(View.VISIBLE);
        etDetailPhone.setVisibility(View.INVISIBLE);
        tvPhone.setVisibility(View.VISIBLE);
        detailEdit.setVisibility(View.VISIBLE);
        btnConfirm.setVisibility(View.INVISIBLE);
        btnCancel.setVisibility(View.INVISIBLE);
        showStar.setIsIndicator(true);
        if (isKeyUp) {
            manager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
    } // end stateViewMode()

    public void stateEditMode() {
        etDetailName.setText(tvName.getText());
        etDetailName.setVisibility(View.VISIBLE);
        tvName.setVisibility(View.INVISIBLE);
        etDetailPhone.setText(tvPhone.getText());
        etDetailPhone.setVisibility(View.VISIBLE);
        tvPhone.setVisibility(View.INVISIBLE);
        detailEdit.setVisibility(View.INVISIBLE);
        btnConfirm.setVisibility(View.VISIBLE);
        btnCancel.setVisibility(View.VISIBLE);
        showStar.setIsIndicator(false);
        etDetailName.setSelection(etDetailName.length());
    }

    public boolean checkValue(String restName) {
        restName = restName.trim();
        if (restName.isEmpty() || restName.equals("") || restName == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isKeyUp) {
            manager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
    }
} // end Fragment
