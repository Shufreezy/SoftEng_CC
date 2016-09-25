package com.example.admin.cyclecalendar;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.admin.cyclecalendar.GlossaryItem;
import com.example.admin.cyclecalendar.R;

import java.util.ArrayList;

//Custom ExpandableListAdapter for Glossary Items
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<GlossaryItem> glossaryItems = new ArrayList<GlossaryItem>();
    private ArrayList<GlossaryItem> originalglossaryItems = new ArrayList<GlossaryItem>();

    ExpandableListAdapter(Context context, ArrayList<GlossaryItem> items) {
        this.context = context;
        this.glossaryItems.addAll(items);
        this.originalglossaryItems.addAll(items);
    }

    @Override
    public int getGroupCount() {
        return this.glossaryItems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return  1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.glossaryItems.get(groupPosition).getTitle();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.glossaryItems.get(groupPosition).getDescription();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.lblListItem);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void filterData(String query){
        query = query.toLowerCase();
        Log.e("MyListAdapter", String.valueOf(this.glossaryItems.size()));
        this.glossaryItems.clear();

        if(query.isEmpty()){
            this.glossaryItems.addAll(this.originalglossaryItems);
        }
        else {
            for(GlossaryItem item : this.originalglossaryItems) {
                if(item.getTitle().toLowerCase().contains(query)) {
                    glossaryItems.add(item);
                }
            }

            Log.e("MyListAdapter", String.valueOf(glossaryItems.size()));
        }

        notifyDataSetChanged();

    }
}