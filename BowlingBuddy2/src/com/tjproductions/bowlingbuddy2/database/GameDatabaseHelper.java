package com.tjproductions.bowlingbuddy2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GameDatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "gametable.db";
	//this is where we change the database version to cause a forced database update
	//REMEMBER!!! THIS WILL DELETE ALL SAVED DATA IN THE DATABASE!!!
	private static final int DATABASE_VERSION = 1;

	public GameDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		GameTable.onCreate(database);
	}

	// Method is called during an upgrade of the database,
	// e.g. if you increase the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		GameTable.onUpgrade(database, oldVersion, newVersion);
	}
}
