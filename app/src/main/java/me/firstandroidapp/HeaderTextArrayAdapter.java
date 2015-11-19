package me.firstandroidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import me.firstandroidapp.HeaderTextArrayAdapter.Item;

import java.util.List;

/**
 * Courtesy of antew - http://stackoverflow.com/questions/13590627/android-listview-headers
 */
// An adapter than takes a list of <Item> interface
public class HeaderTextArrayAdapter extends ArrayAdapter<Item> {
    private LayoutInflater mInflater;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    public enum RowType {
        LIST_ITEM, HEADER_ITEM
    }

    public HeaderTextArrayAdapter(Context context, List<Item> items) {
        super(context, 0, items);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getViewTypeCount() {
        return RowType.values().length;

    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int rowType = getItemViewType(position);
        View View;
        if (convertView == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.text_view_layout, null);
                    holder.View=getItem(position).getView(mInflater, convertView);
                    break;
                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.header_layout, null);
                    holder.View=getItem(position).getView(mInflater, convertView);
                    break;
            }
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    public static class ViewHolder {
        public  View View;
    }

    // An interface that Driver and Passenger profile lists implement to separate headers and content
    public interface Item {
        int getViewType();
        View getView(LayoutInflater inflater, View convertView);
    }

    // A class to implement <Item> and inflate the correct layout.
    public static class Header implements Item {
        private final String name;

        public Header(String name) {
            this.name = name;
        }

        @Override
        public int getViewType() {
            return RowType.HEADER_ITEM.ordinal();
        }

        @Override
        public View getView(LayoutInflater inflater, View convertView) {
            View view;
            if (convertView == null) {
                view = (View) inflater.inflate(R.layout.header_layout, null);
                // Do some initialization
            } else {
                view = convertView;
            }

            TextView text = (TextView) view.findViewById(R.id.separator);
            text.setText(name);

            return view;
        }
    }

    public static class ListItem implements Item {
        private final String str1;
        private final String str2;

        public ListItem(String text1, String text2) {
            this.str1 = text1;
            this.str2 = text2;
        }

        @Override
        public int getViewType() {
            return RowType.LIST_ITEM.ordinal();
        }

        @Override
        public View getView(LayoutInflater inflater, View convertView) {
            View view;
            if (convertView == null) {
                view = (View) inflater.inflate(R.layout.driver_profile_layout, null);
                // Do some initialization
            } else {
                view = convertView;
            }

            TextView text1 = (TextView) view.findViewById(R.id.date_text);
            TextView text2 = (TextView) view.findViewById(R.id.riders_text);

            text1.setText(str1);
            text2.setText(str2);

            return view;
        }

    }
}
