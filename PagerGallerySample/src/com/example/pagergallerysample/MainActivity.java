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

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.medhdj.pagergallery.PagerGalleryContainer;
import com.medhdj.pagergallery.PagerGalleryContainer.OnItemChangedListener;

public class MainActivity extends Activity {
	private PagerGalleryContainer mPgContainer;
	ViewPager mPagerGallery;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPgContainer = (PagerGalleryContainer) findViewById(R.id.pg_container);
		mPagerGallery = mPgContainer.getViewPager();
		PagerAdapter pgAdapter = new PagerGalleryAdapter(this);
		mPagerGallery.setAdapter(pgAdapter);
		// Necessary or the pager will only have one extra page to show
		// make this at least however many pages you can see
		mPagerGallery.setOffscreenPageLimit(pgAdapter.getCount());
		// A little space between pages
		mPagerGallery.setPageMargin(5);
		mPgContainer.setCurrentItem(3);
	}

	@Override
	protected void onStart() {
		super.onStart();
		mPgContainer.setOnItemChangedListener(new OnItemChangedListener() {

			@Override
			public void onItemSelected(View item, int position) {
				Toast.makeText(MainActivity.this, "item " + (position + 1) + " selected",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onItemUnSelected(View item, int position) {

			}
		});

	}
}
