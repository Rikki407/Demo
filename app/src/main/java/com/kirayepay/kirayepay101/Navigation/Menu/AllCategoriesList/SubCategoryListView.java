package com.kirayepay.kirayepay101.Navigation.Menu.AllCategoriesList;

import android.content.Context;
import android.widget.ExpandableListView;

/**
 * Created by rikki on 8/6/17.
 */

public class SubCategoryListView extends ExpandableListView {
    public SubCategoryListView(Context context) {
        super(context);
    }
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //999999 is a size in pixels. ExpandableListView requires a maximum height in order to do measurement calculations.
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(999999, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
