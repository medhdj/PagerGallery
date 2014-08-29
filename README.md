# PagerGallery
A simple and efficient replacement for The Gallery widget in Android.
PagerGallery is based on a gist developped by @devunwired, and adding to it, basic interactivity(swip,click,...) to be used as a Gallery widget
![Screenshot]()

# Usage
For a working implementation of PagerGAllery take a look at **PagerGallerySample**
#### 1. Include the library
#### 2. Add PagerGalleryContainer to your layout
``` xml
<com.medhdj.pagergallery.PagerGalleryContainer
  android:id="@+id/pg_container"
  android:layout_width="match_parent"
  android:layout_height="100dp" >

  <android.support.v4.view.ViewPager
      android:id="@+id/pager_gallery"
      android:layout_width="140dp"
      android:layout_height="90dp"
      android:layout_gravity="center_horizontal|bottom" />
</com.medhdj.pagergallery.PagerGalleryContainer>
```
#### 3. In your onCreate method (or onCreateView for a fragment)
``` android
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	mPgContainer = (PagerGalleryContainer) findViewById(R.id.pg_container);
	mPagerGallery = mPgContainer.getViewPager();
	//set an Adapter to your ViewPager  
	//see PagerGallerySample for a working example
	PagerAdapter pgAdapter = new PagerGalleryAdapter(this);
	mPagerGallery.setAdapter(pgAdapter);
	// Necessary or the pager will only have one extra page to show
	// make this at least however many pages you can see
	mPagerGallery.setOffscreenPageLimit(pgAdapter.getCount());
	// A little space between pages
	mPagerGallery.setPageMargin(5);
	mPgContainer.setCurrentItem(3);
}
```
#### 4. (Optional) If you want to use a listener over the selected item in the PagerGallery
``` android
mPgContainer.setOnItemChangedListener(new OnItemChangedListener() {

    @Override
	public void onItemSelected(View item, int position) {
	    Toast.makeText(MainActivity.this, "item " + (position + 1) + " selected",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemUnSelected(View item, int position) {

	}
});
```

License
----
> The MIT License (MIT)

> * Copyright (c) 2012 Wireless Designs, LLC.
> * Copyright (c) 2014 medhdj.

> Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

> The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

> THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
