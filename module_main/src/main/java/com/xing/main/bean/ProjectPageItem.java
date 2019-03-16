package com.xing.main.bean;

import android.support.v4.app.Fragment;

public class ProjectPageItem {

    private int id;
    private String name;
    private Fragment fragment;

    public ProjectPageItem() {
    }

    public ProjectPageItem(int id, String name, Fragment fragment) {
        this.id = id;
        this.name = name;
        this.fragment = fragment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
