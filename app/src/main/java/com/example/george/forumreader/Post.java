package com.example.george.forumreader;

/**
 * Created by george on 2014/9/25.
 */
public class Post {
    public final String id;
    public final String fid;
    public final String fn;
    public final String n;
    public final String u;
    public final String t;
    public final String[] i;

    public Post(String id, String fid, String fn, String n, String u, String t, String[] i) {
        this.id = id;
        this.fid = fid;
        this.fn = fn;
        this.n = n;
        this.u = u;
        this.t = t;
        this.i = i;
    }
}
