package sharent.product.com.telstraloadlist;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.List;

import sharent.product.com.telstraloadlist.adapter.CustomAdapter;
import sharent.product.com.telstraloadlist.models.Listdata;
import sharent.product.com.telstraloadlist.models.Row;
import sharent.product.com.telstraloadlist.utils.NetworkConnection;
import sharent.product.com.telstraloadlist.utils.constants;
import sharent.product.com.telstraloadlist.volley.AppController;

public class MainActivity extends AppCompatActivity {

    ListView mListView;
    CustomAdapter mCustomAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        Toolbarinit();
        mListView = (ListView) findViewById(R.id.listview);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        swipeToRefresh();
        Checknetwork_loaddata();
    }

    private void Checknetwork_loaddata(){
        if (new NetworkConnection(MainActivity.this).isConnected()) {
            loadData();
        }else{
            Toast.makeText(MainActivity.this, "Please check your internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private void swipeToRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });
    }

    void refreshItems() {
        loadData();
        onItemsLoadComplete();
    }

    void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void loadData(){
        getDatafromurl();
    }

    private void getDatafromurl() {
        findViewById(R.id.progressbar).setVisibility(View.VISIBLE);

        StringRequest sr = new StringRequest(Request.Method.GET, constants.REST_URL_DOMAIN, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    findViewById(R.id.progressbar).setVisibility(View.GONE);
                    ListResponse(response);
                } catch (Exception e) {

                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError response) {
                findViewById(R.id.progressbar).setVisibility(View.GONE);
            }
        }) {

        };
        AppController.getInstance().addToRequestQueue(sr, "string_req", MainActivity.this);
    }

    private void ListResponse(String response) {
        try {
            Gson gson = new Gson();
            Listdata sResult = gson.fromJson(response, Listdata.class);

            if (sResult != null) {
                mTextView.setText(sResult.getTitle());
                if (sResult.getRows().size() >= 1) {
                    LoaddatatoListview(sResult.getRows());
                }else{
                    Toast.makeText(MainActivity.this, "No data found", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "No data found", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {

        }
    }

    private void LoaddatatoListview(List<Row> rows) {
        mCustomAdapter = new CustomAdapter(MainActivity.this, rows);
        mListView.setAdapter(mCustomAdapter);
        mCustomAdapter.notifyDataSetChanged();
    }

    private void Toolbarinit() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.titleview, null);
        mTextView = (TextView) v.findViewById(R.id.txt_title);
        getSupportActionBar().setCustomView(v);
    }
}
