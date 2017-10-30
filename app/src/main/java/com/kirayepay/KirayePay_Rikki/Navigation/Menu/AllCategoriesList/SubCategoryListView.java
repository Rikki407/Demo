package com.kirayepay.KirayePay_Rikki.Navigation.Menu.AllCategoriesList;

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
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(999999, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
