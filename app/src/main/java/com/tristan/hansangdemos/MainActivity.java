package com.tristan.hansangdemos;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linearlistview.LinearListView;
import com.loopj.android.http.RequestParams;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.tristan.hansangdemos.fragments.FragmentFac;
import com.tristan.hansangdemos.fragments.Item;
import com.tristan.hansangdemos.fragments.QiniuFragment;
import com.tristan.hansangdemos.fragments.TwoBtnDialogFragment;
import com.tristan.hansangdemos.utils.FragmentType;
import com.tristan.hansangdemos.utils.HttpUtils;
import com.tristan.hansangdemos.utils.PhotoUtils;

import org.apache.http.params.HttpParams;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

import de.greenrobot.event.EventBus;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,TwoBtnDialogFragment.OnDiagBtnClikListener{

    private LinearListView listView;
    private Context context;
    private ImageView img;
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
        findViewById(R.id.swipe_btn).setOnClickListener(this);
        findViewById(R.id.empty_btn).setOnClickListener(this);
        findViewById(R.id.dialog_btn).setOnClickListener(this);
        findViewById(R.id.photo_btn).setOnClickListener(this);
        findViewById(R.id.disklrucache_btn).setOnClickListener(this);
        findViewById(R.id.diqiniu_btn).setOnClickListener(this);
        findViewById(R.id.fwd_btn).setOnClickListener(this);
        findViewById(R.id.swipe1_btn).setOnClickListener(this);
        img = (ImageView)findViewById(R.id.photo_img);
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

    public final static String TYPE = "type";
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context,ContentActivity.class);
        switch (v.getId()){
            case R.id.toolbar_btn:
                intent.putExtra(TYPE, FragmentType.TOOLBAR);
                break;


            case R.id.recycle_btn:
                intent.putExtra(TYPE,FragmentType.RECYCLER);
                break;



            case R.id.eventbus_btn:
                intent.putExtra(TYPE,FragmentType.EVENTBUS);
                break;

            case R.id.swipe_btn:
                intent = new Intent(context,SwipeActivity.class);
                break;
            case R.id.empty_btn:
                intent.putExtra(TYPE,FragmentType.EMPTYVIEW);
                break;
            case R.id.disklrucache_btn:
                intent.putExtra(TYPE,FragmentType.DISKLRUCACHE);
                break;

            case R.id.diqiniu_btn:
                intent.putExtra(TYPE,FragmentType.QINIU);
                break;
            case R.id.swipe1_btn:
                intent.putExtra(TYPE,FragmentType.SWIPE);
                break;
            case R.id.photo_btn:


                PhotoUtils.getPhoto(this,PhotoUtils.TAKEPHOTO,PhotoUtils.PICKPHTOO, photoName);
                return;
            case R.id.fwd_btn:
                getJson();
                return;
            case  R.id.dialog_btn:
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                TwoBtnDialogFragment ff = new TwoBtnDialogFragment();
                ff.show(transaction, "dialog");
                return;

        }
        startActivity(intent);
    }

    private final static String photoName = "sangtemp.jpg";

    private File zoomFile ;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case PhotoUtils.PICKPHTOO:
                    Log.i("-->>","图库选图");
                    File filePath = PhotoUtils.onPhotoFromPick(this,data);
                    if (filePath!=null){
                        Bitmap bm = PhotoUtils.getLocalImage(filePath,480,800);
                        Log.i("-->>", "bitmap size = " + bm.getByteCount());
                        img.setImageBitmap(bm);
                        PhotoUtils.compressImage(bm, filePath, 60);
                        UploadManager manager = QiniuFragment.initQiNiu();
                        manager.put(filePath, "test4.jpg", "Uky_VknD9UzIGsU4_7vRVvz5jaMihA-XtDVFvnM5:iFPdLHOxDPP01CLEM5bkBWrdBaI=:eyJzY29wZSI6ImN1aWppamkiLCJkZWFkbGluZSI6MTQzOTE5NDMwN30=", new UpCompletionHandler() {
                            @Override
                            public void complete(String s, ResponseInfo responseInfo, JSONObject jsonObject) {
                                Log.i("-->>","info = "+responseInfo);
                            }
                        },null);
                    }

//                    zoomFile = PhotoUtils.photoZoom(this, Uri.fromFile(filePath),PhotoUtils.ZOOMPHOTO,1,1);
                    break;
                case PhotoUtils.TAKEPHOTO:
                    Log.i("-->>", "照相选图");
                    File filepath = PhotoUtils.onPhotoFromCamera(this,photoName);
//                    if (filepath!=null){
//                        Bitmap bm = PhotoUtils.getLocalImage(filepath,300,300);
//                        Log.i("-->>","bitmap size = "+bm.getByteCount());
//                        img.setImageBitmap(bm);
//                    }
                    zoomFile = PhotoUtils.photoZoom(this, Uri.fromFile(filepath),PhotoUtils.ZOOMPHOTO,1,1);
                    break;
                case PhotoUtils.ZOOMPHOTO:
//                    Bitmap bbm = PhotoUtils.getLocalImage(zoomFile, 500, 500);
                    Bitmap bbm = BitmapFactory.decodeFile(zoomFile.getAbsolutePath());
                    if (bbm!=null){
                        Log.i("-->>","success");
                    }
                    img.setImageBitmap(bbm);
                    break;
        }
        }

    }

    /**
     * eventbus 在不同的act间使用，
     */
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

    @Override
    public void onConfimClick() {
        Toast.makeText(this, "对，我就是菊花哥~", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelClick() {
        Toast.makeText(this,"滚犊子!纯爷们,削你！><",Toast.LENGTH_SHORT).show();
    }
    String url = "http://121.201.7.55:8080"+"/buyer/Product/found";
    private void getJson(){
        RequestParams params = new RequestParams();
        params.put("page",1);
       HttpUtils.doPost(this, url, params, new HttpUtils.RequestCallBack() {
           @Override
           public void onStart() {

           }

           @Override
           public void onSuccess(String response) {

               Log.i("-->>","response = "+response);
           }

           @Override
           public void onFail(String response) {

           }
       });
    }
}
