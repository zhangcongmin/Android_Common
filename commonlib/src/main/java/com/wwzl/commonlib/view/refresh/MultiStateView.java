package com.wwzl.commonlib.view.refresh;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.IntDef;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import com.wwzl.commonlib.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * @author: weibin.chen
 * @time: 2020/1/7
 * @des : 加载页面显示    loadding  content   empty  errror   几种状态
 */
public class MultiStateView extends FrameLayout {


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ViewState.VIEW_STATE_UNKNOWN, ViewState.VIEW_STATE_CONTENT, ViewState.VIEW_STATE_ERROR,
            ViewState.VIEW_STATE_EMPTY, ViewState.VIEW_STATE_LOADING})
    public @interface ViewState {
        int VIEW_STATE_UNKNOWN = -1;

        int VIEW_STATE_CONTENT = 0;

        int VIEW_STATE_ERROR = 1;

        int VIEW_STATE_EMPTY = 2;

        int VIEW_STATE_LOADING = 3;
    }

    private LayoutInflater mInflater;

    private View mContentView;

    private View mLoadingView;

    private View mErrorView;

    private View mEmptyView;


    private boolean mAnimateViewChanges = false;

    @Nullable
    private StateListener mListener;

    @ViewState
    private int mViewState = ViewState.VIEW_STATE_UNKNOWN;

    public MultiStateView(Context context) {
        this(context, null);
    }

    public MultiStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MultiStateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mInflater = LayoutInflater.from(getContext());
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MultiStateView);

        int loadingViewResId = a.getResourceId(R.styleable.MultiStateView_msv_loadingView, -1);
        if (loadingViewResId > -1 && mInflater != null) {
            mLoadingView = mInflater.inflate(loadingViewResId, this, false);
            addView(mLoadingView, mLoadingView.getLayoutParams());
        }

        int emptyViewResId = a.getResourceId(R.styleable.MultiStateView_msv_emptyView, -1);
        if (emptyViewResId > -1 && mInflater != null) {
            mEmptyView = mInflater.inflate(emptyViewResId, this, false);
            addView(mEmptyView, mEmptyView.getLayoutParams());
        }

        int errorViewResId = a.getResourceId(R.styleable.MultiStateView_msv_errorView, -1);
        if (errorViewResId > -1 && mInflater != null) {
            mErrorView = mInflater.inflate(errorViewResId, this, false);
            addView(mErrorView, mErrorView.getLayoutParams());
        }

        int viewState = a.getInt(R.styleable.MultiStateView_msv_viewState, ViewState.VIEW_STATE_CONTENT);
        mAnimateViewChanges = a.getBoolean(R.styleable.MultiStateView_msv_animateViewChanges, false);

        switch (viewState) {
            case ViewState.VIEW_STATE_CONTENT:
                mViewState = ViewState.VIEW_STATE_CONTENT;
                break;

            case ViewState.VIEW_STATE_ERROR:
                mViewState = ViewState.VIEW_STATE_ERROR;
                break;

            case ViewState.VIEW_STATE_EMPTY:
                mViewState = ViewState.VIEW_STATE_EMPTY;
                break;

            case ViewState.VIEW_STATE_LOADING:
                mViewState = ViewState.VIEW_STATE_LOADING;
                break;

            case ViewState.VIEW_STATE_UNKNOWN:
            default:
                mViewState = ViewState.VIEW_STATE_UNKNOWN;
                break;
        }

        a.recycle();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mContentView == null) {
            throw new IllegalArgumentException("Content view is not defined");
        }
        setView(ViewState.VIEW_STATE_UNKNOWN);
    }

    @Override
    public void addView(View child) {
        if (isValidContentView(child)) {
            mContentView = child;
        }
        super.addView(child);
    }

    @Override
    public void addView(View child, int index) {
        if (isValidContentView(child)) {
            mContentView = child;
        }
        super.addView(child, index);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (isValidContentView(child)) {
            mContentView = child;
        }
        super.addView(child, index, params);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        if (isValidContentView(child)) {
            mContentView = child;
        }
        super.addView(child, params);
    }

    @Override
    public void addView(View child, int width, int height) {
        if (isValidContentView(child)) {
            mContentView = child;
        }
        super.addView(child, width, height);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params) {
        if (isValidContentView(child)) {
            mContentView = child;
        }
        return super.addViewInLayout(child, index, params);
    }

    @Override
    protected boolean addViewInLayout(View child, int index, ViewGroup.LayoutParams params, boolean preventRequestLayout) {
        if (isValidContentView(child)) {
            mContentView = child;
        }
        return super.addViewInLayout(child, index, params, preventRequestLayout);
    }

    @Nullable
    public View getView(@ViewState int state) {
        switch (state) {
            case ViewState.VIEW_STATE_LOADING:
                return mLoadingView;

            case ViewState.VIEW_STATE_CONTENT:
                return mContentView;

            case ViewState.VIEW_STATE_EMPTY:
                return mEmptyView;

            case ViewState.VIEW_STATE_ERROR:
                return mErrorView;

            default:
                return null;
        }
    }

    @ViewState
    public int getViewState() {
        return mViewState;
    }

    public void setViewState(@ViewState int state) {
        if (state != mViewState) {
            int previous = mViewState;
            mViewState = state;
            setView(previous);
            if (mListener != null) {
                mListener.onStateChanged(mViewState);
            }
        }
    }


    private void setView(@ViewState int previousState) {
        switch (mViewState) {
            case ViewState.VIEW_STATE_LOADING:
                if (mLoadingView == null) {
                    throw new NullPointerException("Loading View");
                }
                if (mContentView != null) {
                    mContentView.setVisibility(View.GONE);
                }
                if (mErrorView != null) {
                    mErrorView.setVisibility(View.GONE);
                }
                if (mEmptyView != null) {
                    mEmptyView.setVisibility(View.GONE);
                }

                if (mAnimateViewChanges) {
                    animateLayoutChange(getView(previousState));
                } else {
                    mLoadingView.setVisibility(View.VISIBLE);
                }
                break;

            case ViewState.VIEW_STATE_EMPTY:
                if (mEmptyView == null) {
                    throw new NullPointerException("Empty View");
                }


                if (mLoadingView != null) {
                    mLoadingView.setVisibility(View.GONE);
                }
                if (mErrorView != null) {
                    mErrorView.setVisibility(View.GONE);
                }
                if (mContentView != null) {
                    mContentView.setVisibility(View.GONE);
                }

                if (mAnimateViewChanges) {
                    animateLayoutChange(getView(previousState));
                } else {
                    mEmptyView.setVisibility(View.VISIBLE);
                }
                break;

            case ViewState.VIEW_STATE_ERROR:
                if (mErrorView == null) {
                    throw new NullPointerException("Error View");
                }


                if (mLoadingView != null) {
                    mLoadingView.setVisibility(View.GONE);
                }
                if (mContentView != null) {
                    mContentView.setVisibility(View.GONE);
                }
                if (mEmptyView != null) {
                    mEmptyView.setVisibility(View.GONE);
                }

                if (mAnimateViewChanges) {
                    animateLayoutChange(getView(previousState));
                } else {
                    mErrorView.setVisibility(View.VISIBLE);
                }
                break;

            case ViewState.VIEW_STATE_CONTENT:
            default:
                if (mContentView == null) {
                    // Should never happen, the view should throw an exception if no content view is present upon creation
                    throw new NullPointerException("Content View");
                }


                if (mLoadingView != null) {
                    mLoadingView.setVisibility(View.GONE);
                }
                if (mErrorView != null) {
                    mErrorView.setVisibility(View.GONE);
                }
                if (mEmptyView != null) {
                    mEmptyView.setVisibility(View.GONE);
                }

                if (mAnimateViewChanges) {
                    animateLayoutChange(getView(previousState));
                } else {
                    mContentView.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    /**
     * Checks if the given {@link View} is valid for the Content View
     *
     * @param view The {@link View} to check
     * @return
     */
    private boolean isValidContentView(View view) {
        if (mContentView != null && mContentView != view) {
            return false;
        }

        return view != mLoadingView && view != mErrorView && view != mEmptyView;
    }

    public void setViewForState(View view, @ViewState int state, boolean switchToState) {
        switch (state) {
            case ViewState.VIEW_STATE_LOADING:
                if (mLoadingView != null) {
                    removeView(mLoadingView);
                }
                mLoadingView = view;
                addView(mLoadingView);
                break;

            case ViewState.VIEW_STATE_EMPTY:
                if (mEmptyView != null) {
                    removeView(mEmptyView);
                }
                mEmptyView = view;
                addView(mEmptyView);
                break;

            case ViewState.VIEW_STATE_ERROR:
                if (mErrorView != null) {
                    removeView(mErrorView);
                }
                mErrorView = view;
                addView(mErrorView);
                break;

            case ViewState.VIEW_STATE_CONTENT:
                if (mContentView != null) {
                    removeView(mContentView);
                }
                mContentView = view;
                addView(mContentView);
                break;
        }

        setView(ViewState.VIEW_STATE_UNKNOWN);
        if (switchToState) {
            setViewState(state);
        }
    }

    public void setViewForState(View view, @ViewState int state) {
        setViewForState(view, state, false);
    }

    public void setViewForState(@LayoutRes int layoutRes, @ViewState int state, boolean switchToState) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(getContext());
        }
        View view = mInflater.inflate(layoutRes, this, false);
        setViewForState(view, state, switchToState);
    }

    public void setViewForState(@LayoutRes int layoutRes, @ViewState int state) {
        setViewForState(layoutRes, state, false);
    }

    /**
     * Sets whether an animate will occur when changing between {@link ViewState}
     */
    public void setAnimateLayoutChanges(boolean animate) {
        mAnimateViewChanges = animate;
    }

    public void setStateListener(StateListener listener) {
        mListener = listener;
    }

    private void animateLayoutChange(@Nullable final View previousView) {
        if (previousView == null) {
            getView(mViewState).setVisibility(View.VISIBLE);
            return;
        } else {
            previousView.setVisibility(View.VISIBLE);
        }
        ObjectAnimator anim = ObjectAnimator.ofFloat(previousView, "alpha", 1.0f, 0.0f).setDuration(250L);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                previousView.setVisibility(View.GONE);
                getView(mViewState).setVisibility(View.VISIBLE);
                ObjectAnimator.ofFloat(getView(mViewState), "alpha", 0.0f, 1.0f).setDuration(250L).start();
            }
        });
        anim.start();
    }

    public interface StateListener {
        void onStateChanged(@ViewState int viewState);
    }



}
