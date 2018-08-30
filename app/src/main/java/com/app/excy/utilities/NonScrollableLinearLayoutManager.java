package com.app.excy.utilities;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * Created by erin.kelley on 4/29/17.
 */

/**
 * This class is used as a work-around to get the RecyclerView in the MeActivity to not scroll.
 * Should have just made a linear layout with 5 views (since the customer only wants 5 workouts
 * displayed at a time), but didn't know that at the time and am doing this solution for time
 * purposes.
 */
public class NonScrollableLinearLayoutManager extends LinearLayoutManager {

    private boolean isScrollEnabled = true;

    public NonScrollableLinearLayoutManager(Context context) {
        super(context);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}
