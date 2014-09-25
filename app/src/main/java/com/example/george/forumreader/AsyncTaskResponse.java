package com.example.george.forumreader;

/**
 * Created by george on 2014/9/24.
 *
 * This interface is for GetJSONTask to callback to the ForumReaderActivity
 * and create the tags with all the forum names in the action bar.
 */
public interface AsyncTaskResponse {
    void asyncTaskFinish(String output);
}
