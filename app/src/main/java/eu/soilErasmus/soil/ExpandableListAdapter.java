package eu.soilErasmus.soil;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private final Context mContext;
    private final List<MenuModel> mMenuOptions;
    private final Map<MenuModel, String[]> mVideoOptions;


    private static final int VIDEOS = 0;

    public ExpandableListAdapter(Context context, List<MenuModel> menuOptions, Map<MenuModel, String[]> videoOptions) {
        this.mContext = context;
        this.mMenuOptions = menuOptions;
        this.mVideoOptions = videoOptions;
    }

    @Override
    public int getGroupCount() {
        return this.mMenuOptions.size();
    }

    @Override
    public int getChildrenCount(int i) {
        int childCount = 0;
        if (i == VIDEOS) {
            childCount = Objects.requireNonNull(mVideoOptions.get(mMenuOptions.get(i))).length;
        }
        return childCount;

    }

    @Override
    public MenuModel getGroup(int i) {
        return mMenuOptions.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {

        if (i == VIDEOS) return Objects.requireNonNull(mVideoOptions.get(mMenuOptions.get(i)))[i1];
        else return 0;
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
        return false;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(int i, boolean isExpanded, View view, ViewGroup viewGroup) {
        MenuModel groupModel  = getGroup(i);

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.expandable_list_header, null);

        TextView nameTxt = view.findViewById(R.id.categoryName);
        nameTxt.setTypeface(null, Typeface.BOLD);
        nameTxt.setText(groupModel.getMenuTitle());

        if (groupModel.getArrowImage() != -1){
            ImageView arrowImage = view.findViewById(R.id.arrowImage);
            arrowImage.setImageResource (isExpanded? R.drawable.baseline_keyboard_arrow_down_24
                                            : R.drawable.baseline_keyboard_arrow_right_24);

        }

        return view;
    }


    @SuppressLint("InflateParams")
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
        String childName = (String) getChild(groupPosition, childPosition);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.expandable_list_submenu, null);
        }

        TextView submenuText = view.findViewById(R.id.submenu_name);
        submenuText.setText(childName);

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}