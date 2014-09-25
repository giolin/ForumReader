package com.example.george.forumreader;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;


public class ForumReaderActivity extends FragmentActivity implements AsyncTaskResponse {

    private final String urlForums = "http://htcbeautyfinder.appspot.com/forums";
    static public final String NAME_TAG = "mmpud";

    private ViewPager pager;
    private TabsAdapter mTabsAdapter;

    private ActionBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_forum_reader);
        pager = new ViewPager(this);
        pager.setId(R.id.pager);
        setContentView(pager);

        bar = getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        GetJSONTask mGetJSONTask = new GetJSONTask();
        mGetJSONTask.callback = this;
        mGetJSONTask.execute(urlForums);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.forum_reader, menu);
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
        } else if (id == R.id.action_refresh) {
            // do the refresh function
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void asyncTaskFinish(String output) {
        Gson gson = new Gson();
        Forum[] forums = gson.fromJson(output, Forum[].class);
        mTabsAdapter = new TabsAdapter(this, pager);
        int fid_temp = -1;
        for(int i=0;i < forums.length; i++) {
            Bundle bundle = new Bundle();
            if(Integer.parseInt(forums[i].fid) != fid_temp) {
                fid_temp = Integer.parseInt(forums[i].fid);
                bundle.putInt("fid", Integer.parseInt(forums[i].fid));
                mTabsAdapter.addTab(bar.newTab().setText(forums[i].fn), List_View.class, bundle);
            }
        }
        // after adding, update all the tabs
        mTabsAdapter.updateTabs();
    }

    public void gotoGalleryActivity() {
        Intent intent = new Intent(this, GalleryActivity.class);
        intent.putExtra("images", "ker");
    }
}
