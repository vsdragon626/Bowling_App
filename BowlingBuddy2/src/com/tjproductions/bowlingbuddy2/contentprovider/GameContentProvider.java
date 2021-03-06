package com.tjproductions.bowlingbuddy2.contentprovider;

import java.util.Arrays;
import java.util.HashSet;

import com.tjproductions.bowlingbuddy2.database.GameDatabaseHelper;
import com.tjproductions.bowlingbuddy2.database.GameTable;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class GameContentProvider extends ContentProvider {

	// database
	private GameDatabaseHelper database;

	// Used for the UriMacher
	private static final int GAMES = 10;
	private static final int GAME_ID = 20;

	private static final String AUTHORITY = "com.tjproductions.bowlingbuddy2.contentprovider";

	private static final String BASE_PATH = "games";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH);

	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/games";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/game";

	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(AUTHORITY, BASE_PATH, GAMES);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", GAME_ID);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsDeleted = 0;
		switch (uriType) {
		case GAMES:
			rowsDeleted = sqlDB.delete(GameTable.TABLE_GAMES, selection,
					selectionArgs);
			break;
		case GAME_ID:
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsDeleted = sqlDB.delete(GameTable.TABLE_GAMES,
						GameTable.COLUMN_ID + "=" + id, 
						null);
			} else {
				rowsDeleted = sqlDB.delete(GameTable.TABLE_GAMES,
						GameTable.COLUMN_ID + "=" + id 
						+ " and " + selection,
						selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsDeleted;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		@SuppressWarnings("unused")
		int rowsDeleted = 0;
		long id = 0;
		switch (uriType) {
		case GAMES:
			id = sqlDB.insert(GameTable.TABLE_GAMES, null, values);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return Uri.parse(BASE_PATH + "/" + id);
	}

	@Override
	public boolean onCreate() {
		database = new GameDatabaseHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		// Using SQLiteQueryBuilder instead of query() method
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

		// Check if the caller has requested a column which does not exists
		checkColumns(projection);

		// Set the table
		queryBuilder.setTables(GameTable.TABLE_GAMES);

		int uriType = sURIMatcher.match(uri);
		switch (uriType) {
		case GAMES:
			break;
		case GAME_ID:
			// Adding the ID to the original query
			queryBuilder.appendWhere(GameTable.COLUMN_ID + "="
					+ uri.getLastPathSegment());
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		SQLiteDatabase db = database.getWritableDatabase();
		Cursor cursor = queryBuilder.query(db, projection, selection,
				selectionArgs, null, null, sortOrder);
		// Make sure that potential listeners are getting notified
		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsUpdated = 0;
		switch (uriType) {
		case GAMES:
			rowsUpdated = sqlDB.update(GameTable.TABLE_GAMES, 
					values, 
					selection,
					selectionArgs);
			break;
		case GAME_ID:
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				rowsUpdated = sqlDB.update(GameTable.TABLE_GAMES, 
						values,
						GameTable.COLUMN_ID + "=" + id, 
						null);
			} else {
				rowsUpdated = sqlDB.update(GameTable.TABLE_GAMES, 
						values,
						GameTable.COLUMN_ID + "=" + id 
						+ " and " 
						+ selection,
						selectionArgs);
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsUpdated;
	}
	
	private void checkColumns(String[] projection) {
		String[] available = { GameTable.COLUMN_GAME_STRING,
				GameTable.COLUMN_HOUSE, GameTable.COLUMN_DATE,
				GameTable.COLUMN_SHOT_TYPE, GameTable.COLUMN_SPEED,
				GameTable.COLUMN_FOOT_POSITION, GameTable.COLUMN_TARGET_TYPE,
				GameTable.COLUMN_TARGET, GameTable.COLUMN_HIT_TARGET,
				GameTable.COLUMN_BREAKPOINT, GameTable.COLUMN_BALL,
				GameTable.COLUMN_LANE_NUMBERS, GameTable.COLUMN_HIT_POCKET,
				GameTable.COLUMN_NOTES,	GameTable.COLUMN_ID };
		if (projection != null) {
			HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
			HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
			// Check if all columns which are requested are available
			if (!availableColumns.containsAll(requestedColumns)) {
				throw new IllegalArgumentException("Unknown columns in projection");
			}
		}
	}

}
