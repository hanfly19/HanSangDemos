package com.tristan.hansangdemos;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.linearlistview.LinearListView;
import com.tristan.hansangdemos.fragments.Item;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearListView listView;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        listView = (LinearListView)findViewById(R.id.list);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {

            list.add("韩桑的夏天！~");
        }

        ArrayAdapter<String > adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,list);
        listView.setAdapter(adapter);

        initViews();
        EventBus.getDefault().register(this);
    }



    private void initViews(){

        findViewById(R.id.toolbar_btn).setOnClickListener(this);
        findViewById(R.id.eventbus_btn).setOnClickListener(this);
        findViewById(R.id.recycle_btn).setOnClickListener(this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context,ContentActivity.class);
        switch (v.getId()){
            case R.id.toolbar_btn:
                intent.putExtra("type",ContentActivity.TOOLBAR);
                break;






            case R.id.eventbus_btn:
                intent.putExtra("type",ContentActivity.EVENTBUS);
                break;

        }
        startActivity(intent);
    }

    public void onEventMainThread(Item item){
        if (item!=null){
            Toast.makeText(this,item.getName(),Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
