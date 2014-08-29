/*
 * Copyright (c) 2014 medhdj
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
package com.example.pagergallerysample;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PagerGalleryAdapter extends PagerAdapter {

	WeakReference<Context> mContext;
	LayoutInflater mInflater;

	public PagerGalleryAdapter(Context context) {
		super();
		mContext = new WeakReference<Context>(context);
		mInflater = LayoutInflater.from(mContext.get());
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		View root = mInflater.inflate(R.layout.item, null);
		ImageView img = (ImageView) root.findViewById(R.id.item_img);
		Resources res = mContext.get().getResources();
		int resId = res.getIdentifier("item" + (position + 1), "drawable", mContext.get()
				.getPackageName());
		img.setImageResource(resId);
		TextView txt = (TextView) root.findViewById(R.id.item_label);
		txt.setText("item " + (position + 1));
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(100,
				RelativeLayout.LayoutParams.MATCH_PARENT);
		root.setLayoutParams(lp);
		container.addView(root);
		return root;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public int getCount() {
		return 7;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return (view == object);
	}

}