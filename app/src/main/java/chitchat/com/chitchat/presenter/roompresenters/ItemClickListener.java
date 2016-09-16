package chitchat.com.chitchat.presenter.roompresenters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Project: Chit-Chat
 * Package: chitchat.com.chitchat.presenter.roompresenters
 * Created by lusinabrian on 16/09/16 at 11:35
 * <p>
 * Description:
 */

public class ItemClickListener implements RecyclerView.OnItemTouchListener {

    GestureDetector mGestureDetector;
    private OnItemClickListener mListener;

    public ItemClickListener(Context context, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
            mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
        }
        return false;
    }

    @Override public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { }

    @Override public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        // do nothing
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

//=========================================================================================================================================================================================
//end
}
