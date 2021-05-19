package com.interstellar.elite.makemodel;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.interstellar.elite.BaseActivity;
import com.interstellar.elite.R;
import com.interstellar.elite.core.APIResponse;
import com.interstellar.elite.core.IResponseSubcriber;
import com.interstellar.elite.core.controller.register.RegisterController;
import com.interstellar.elite.core.model.MakeEntity;
import com.interstellar.elite.core.model.UserEntity;
import com.interstellar.elite.core.response.VehicleMasterResponse;
import com.interstellar.elite.facade.PrefManager;

import com.interstellar.elite.utility.Constants;

import java.util.ArrayList;
import java.util.List;

public class MakeActivity extends BaseActivity implements View.OnClickListener,IResponseSubcriber {

    PrefManager prefManager;
    UserEntity loginEntity;
    RecyclerView rvCity;
    MakeNewAdapter mAdapter;
    List<MakeEntity> MakeList;
    LinearLayout lyOtherCity;
    private SearchView.OnQueryTextListener queryTextListener;     // for Option Menu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_model);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFinishOnTouchOutside(false);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        prefManager = new PrefManager(this);
        MakeList = new ArrayList<>();

        initialize();

        MakeList = prefManager.getMake();

        mAdapter = new MakeNewAdapter(MakeActivity.this, MakeList);
        rvCity.setAdapter(mAdapter);


        if (MakeList.size() == 0) {
            showDialog();
            new RegisterController(MakeActivity.this).getCarVehicleMaster(MakeActivity.this);
        }
    }


    private void initialize() {

        prefManager = new PrefManager(this);
        loginEntity = prefManager.getUserData();

        lyOtherCity = (LinearLayout) findViewById(R.id.lyOtherCity);
        rvCity = (RecyclerView) findViewById(R.id.rvCity);
        rvCity.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MakeActivity.this);
        rvCity.setLayoutManager(layoutManager);
        rvCity.setItemAnimator(new DefaultItemAnimator());

        lyOtherCity.setOnClickListener(this);

        //  recyclerLead.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
        }

        EditText editText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);

        editText.setFilters(new InputFilter[]{
                new InputFilter.AllCaps(),
                new InputFilter.LengthFilter(20),
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        if (source.equals(" ")) { // for backspace
                            return source;
                        }
                        if (source.toString().matches("[a-zA-Z]+")) {
                            return source;
                        }
                        return "";
                    }
                }
        });

        if (searchView != null && MakeList != null && MakeList.size() > 0) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {


                    if (newText.length() > 0) {
                        mAdapter.MakeList = MakeList;
                        mAdapter.getFilter().filter(newText);
                    } else {
                        mAdapter.findAll(MakeList);
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);


        }
        return super.onCreateOptionsMenu(menu);

    }


    public void getMakeData(MakeEntity makeEntity) {
        Intent intent = new Intent();
        intent.putExtra(Constants.SEARCH_MAKE_DATA, makeEntity);
        setResult(Constants.SEARCH_MAKE_CODE, intent);
        finish();      //finishing activity


    }

    @Override
    public void onClick(View view) {

//        if(view.getId() == R.id.lyOtherCity)
//        {
//            Intent intent=new Intent();
//            intent.putExtra(Constants.SEARCH_CITY_DATA,"OTHER CITY");
//            intent.putExtra(Constants.SEARCH_CITY_ID,"2653");
//            setResult(Constants.SEARCH_CITY_CODE,intent);
//            finish();
//        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }


    @Override
    public void OnSuccess(APIResponse response, String message) {
        cancelDialog();
        if (response instanceof VehicleMasterResponse) {
            if (response.getStatus_code() == 0) {

                if (((VehicleMasterResponse) response).getData() != null) {

                    MakeList = ((VehicleMasterResponse) response).getData().getVehicleMasterResult().getMake();
                    mAdapter.findAll(MakeList);
                }
            }
        }
    }

    @Override
    public void OnFailure(String error) {
        cancelDialog();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}