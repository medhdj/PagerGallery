/*
 * Copyright (c) 2012 Wireless Designs, LLC
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.medhdj.pagergallery;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.TransitionDrawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * PagerContainer: A layout that displays a ViewPager with its children that are
 * outside the typical pager bounds.
 */
public class PagerGalleryContainer extends FrameLayout implements ViewPager.OnPageChangeListener {
	private ViewPager mPager;
	boolean mNeedsRedraw = false;
	private Point mCenter = new Point();
	private Point mInitialTouch = new Point();
	private int mPreviousSelectedPosition;
	private OnItemChangedListener mOnItemChangedListener;

	public PagerGalleryContainer(Context context) {
		super(context);
		init();
	}

	public PagerGalleryContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PagerGalleryContainer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		// Disable clipping of children so non-selected pages are visible
		setClipChildren(false);

		// Child clipping doesn't work with hardware acceleration in Android
		// 3.x/4.x
		// You need to set this value here if using hardware acceleration in an
		// application targeted at these releases.
		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	}

	@Override
	protected void onFinishInflate() {
		try {
			mPager = (ViewPager) getChildAt(0);
			mPager.setOnPageChangeListener(this);
			mPager.setClipChildren(false);
			mPager.setOnTouchListener(new View.OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					v.getParent().requestDisallowInterceptTouchEvent(true);
					return false;
				}
			});

		} catch (Exception e) {
			throw new IllegalStateException("The root child of PagerContainer must be a ViewPager");
		}
	}

	public ViewPager getViewPager() {
		return mPager;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mCenter.x = w / 2;
		mCenter.y = h / 2;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int marginClick = 10;
		// We capture any touches not already handled by the ViewPager
		// to implement scrolling from a touch outside the pager bounds.		
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mInitialTouch.x = (int) ev.getX();
			mInitialTouch.y = (int) ev.getY();
			break;
		case MotionEvent.ACTION_UP:
			// capturing the click on the PagerGalleryContainer to perform
			// selection of an item in the PagerGallery
			if ((int) ev.getX() >= (mInitialTouch.x - marginClick)
					&& (int) ev.getX() <= (mInitialTouch.x + marginClick)
					&& (int) ev.getY() >= mInitialTouch.y - marginClick
					&& (int) ev.getY() <= mInitialTouch.y + marginClick) {
				float pageWidth = mPager.getChildAt(0).getWidth();
				float pageMargin = mPager.getPageMargin();
				int jump = Math.round((ev.getX() - mCenter.x) / (pageWidth + pageMargin));

				jump = mPager.getCurrentItem() + jump;
				if (jump < 0) {
					jump = 0;
				} else if (jump > mPager.getChildCount()) {
					jump = mPager.getChildCount() - 1;
				}
				mPager.setCurrentItem(jump, true);
				return true;
			}
			break;
		default:
			break;
		}
		ev.offsetLocation(mCenter.x - mInitialTouch.x, mCenter.y - mInitialTouch.y);
		return mPager.dispatchTouchEvent(ev);

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		// Force the container to redraw on scrolling.
		// Without this the outer pages render initially and then stay static
		if (mNeedsRedraw)
			invalidate();
	}

	@Override
	public void onPageSelected(int position) {
		if (position == mPreviousSelectedPosition) {
			return;
		}
		View v = mPager.getChildAt(position);
		if (v != null) {
			if (v.getBackground() instanceof TransitionDrawable) {
				TransitionDrawable transition = (TransitionDrawable) v.getBackground();
				if (transition != null) {
					transition.startTransition(700);
				}

			}
		}

		View mprev = mPager.getChildAt(mPreviousSelectedPosition);
		if (mprev != null) {
			if (mprev.getBackground() instanceof TransitionDrawable) {
				TransitionDrawable transition = (TransitionDrawable) mprev.getBackground();
				if (transition != null) {
					transition.reverseTransition(700);
					transition.resetTransition();
				}

			}
		}

		if (mOnItemChangedListener != null) {
			mOnItemChangedListener.onItemSelected(v, position);
			mOnItemChangedListener.onItemUnSelected(mprev, mPreviousSelectedPosition);
		}

		mPreviousSelectedPosition = position;
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		mNeedsRedraw = (state != ViewPager.SCROLL_STATE_IDLE);
	}

	public void setCurrentItem(final int item) {
		if (mPager.getChildCount() > 0) {
			mPager.setCurrentItem(item, false);
		} else {
			mPager.post(new Runnable() {

				@Override
				public void run() {
					mPager.setCurrentItem(item, false);
				}
			});
		}

	}

	public void setOnItemChangedListener(OnItemChangedListener listener) {
		mOnItemChangedListener = listener;
	}

	public final OnItemChangedListener getOnItemSelectedListener() {
		return mOnItemChangedListener;
	}

	/**
	 * 
	 * @author medhdj interface to listen to items position changing
	 * 
	 */
	public interface OnItemChangedListener {
		/**
		 * 
		 * @param item
		 *            The item beeing selected
		 * @param position
		 *            The position of the item whithin the Pager
		 */
		void onItemSelected(View item, int position);

		/**
		 * 
		 * @param item
		 *            The previous selected item
		 * @param position
		 *            The previous selcted item position
		 */
		void onItemUnSelected(View item, int position);
	}

}