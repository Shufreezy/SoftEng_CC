package com.example.admin.cyclecalendar;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GlossaryFragment extends Fragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener{
    ExpandableListAdapter listAdapter;
    ExpandableListView lv;
    SearchView search;
    ArrayList<GlossaryItem> glossaryitems;

    public GlossaryFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_glossary, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        search = (SearchView) view.findViewById(R.id.search);
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(this);
        search.setOnCloseListener(this);

        lv = (ExpandableListView) view.findViewById(R.id.expListView);
        listAdapter = new ExpandableListAdapter(getContext(), glossaryitems);
        lv.setAdapter(listAdapter);

        // closes previously opened listitem
        lv.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    lv.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });
    }

    // SEARCH
    @Override
    public boolean onClose() {
        listAdapter.filterData("");
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        listAdapter.filterData(query);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        listAdapter.filterData(query);
        return false;
    }

    //Initialize Data for Glossary
    private void loadData() {

        glossaryitems = new ArrayList<GlossaryItem>();
        glossaryitems.add(new GlossaryItem("Menstruation", "Menstruation is a blah blah blah blah. This is a test."));
        glossaryitems.add(new GlossaryItem("Period", "Period is a blah blah blah blah. This is a test."));
        glossaryitems.add(new GlossaryItem("Abdmomen", "noun\n\n" + "the part of the body below the chestthat contains the stomach and other organs. This is a test"));
        glossaryitems.add(new GlossaryItem("Test1", "This is just a test"));
        glossaryitems.add(new GlossaryItem("Test2", "This is just a test"));
        glossaryitems.add(new GlossaryItem("Test3", "This is just a test"));
        glossaryitems.add(new GlossaryItem("Hello", "This is just a test"));
        glossaryitems.add(new GlossaryItem("World", "This is just a test"));
        glossaryitems.add(new GlossaryItem("Case", "This is just a test"));
        glossaryitems.add(new GlossaryItem("Alpha", "This is just a test"));
        glossaryitems.add(new GlossaryItem("Beta", "This is just a test"));
        glossaryitems.add(new GlossaryItem("Charlie", "This is just a test"));
    }
}
