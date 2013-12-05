package com.tjproductions.bowlingbuddy2;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.tjproductions.bowlingbuddy2.contentprovider.GameContentProvider;
import com.tjproductions.bowlingbuddy2.database.GameTable;

public class ViewGames extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor> {
	private static final int DELETE_ID = Menu.FIRST + 1;
	// private Cursor cursor;
	private SimpleCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_games);
		this.getListView().setDividerHeight(2);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		fillData();
		registerForContextMenu(getListView());
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case DELETE_ID:
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
			.getMenuInfo();
			Uri uri = Uri.parse(GameContentProvider.CONTENT_URI + "/"
					+ info.id);
			getContentResolver().delete(uri, null, null);
			fillData();
			return true;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(this, GameActivity.class);
		Uri gameUri = Uri.parse(GameContentProvider.CONTENT_URI + "/" + id);
		i.putExtra(GameContentProvider.CONTENT_ITEM_TYPE, gameUri);

		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_games, menu);
		return true;
	}

	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		String[] projection = { GameTable.COLUMN_ID, GameTable.COLUMN_HOUSE, GameTable.COLUMN_DATE };
		CursorLoader cursorLoader = new CursorLoader(this,
				GameContentProvider.CONTENT_URI, projection, null, null, null);
		return cursorLoader;
	}

	private void fillData() {

		// Fields from the database (projection)
		String[] from = new String[] { GameTable.COLUMN_HOUSE, GameTable.COLUMN_DATE};
		// Fields on the UI to which we map
		int[] to = new int[] { R.id.houselabel, R.id.datelabel};

		getLoaderManager().initLoader(0, null, this);
		adapter = new SimpleCursorAdapter(this, R.layout.list_row, null, from,
				to, 0);

		setListAdapter(adapter);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, DELETE_ID, 0, R.string.menu_delete);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor data) {
		adapter.swapCursor(data);		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		adapter.swapCursor(null);
	}

}
