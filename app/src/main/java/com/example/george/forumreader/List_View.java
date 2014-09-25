package com.example.george.forumreader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

import timber.log.Timber;

public class List_View extends ListFragment implements AsyncTaskResponse {
    ArrayAdapter<String> mArrayAdapter;
    ArrayList<String> postNames;
    ArrayList<Post> posts;
    //	String[] list_items;
    int fid;
    int count = 0;
    int pageNum = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        fid = bundle.getInt("fid");
        Timber.d("Fragment " + fid + " created!");
    }

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.list, container, false);
		return rootView;
	}

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnScrollListener(mOnScrollListener);

        // initiate mArrayAdapter
        pageNum = 0;
        postNames = new ArrayList<String>();
        posts = new ArrayList<Post>();
        mArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, postNames);
        setListAdapter(mArrayAdapter);
        getPosts(pageNum);
    }

    public void onResume() {
        super.onResume();
        Timber.d("onResume: " + (count++));
    }


    public void getPosts(int p) {
        String urlPosts = "http://htcbeautyfinder.appspot.com/topics?forum=" + fid +"&page=" + p + "&limit=15";
        GetJSONTask mGetJSONTask = new GetJSONTask();
        mGetJSONTask.callback = this;
        mGetJSONTask.execute(urlPosts);
    }

    @Override
    public void asyncTaskFinish(String output) {
        Gson gson = new Gson();
        Post[] postsOnePage = gson.fromJson(output, Post[].class);
        for(int i=0; i<postsOnePage.length; i++) {
            postNames.add(postsOnePage[i].n);
            posts.add(postsOnePage[i]);
        }
        mArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Timber.d("post [" + posts.get(position).n + "] is clicked");
        Intent intent = new Intent(getActivity().getApplicationContext(), GalleryActivity.class);
        intent.putExtra("images", posts.get(position).i);
        startActivity(intent);
    }

    // load more posts on scroll end
    private AbsListView.OnScrollListener mOnScrollListener;

    {
        mOnScrollListener = new AbsListView.OnScrollListener() {
            int currentFirstVisibleItem;
            int currentVisibleItemCount;
            int currentScrollState;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                currentScrollState = scrollState;
                isScrollCompleted();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                currentFirstVisibleItem = firstVisibleItem;
                currentVisibleItemCount = visibleItemCount;
            }

            private void isScrollCompleted() {
                if (this.currentVisibleItemCount > 0 && this.currentScrollState == SCROLL_STATE_IDLE) {
                    pageNum++;
                    getPosts(pageNum);
                }
            }
        };
    }

}
