package com.tjproductions.bowlingbuddy2.database;


import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class GameTable {

	public static final String TABLE_GAMES = "games";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_GAME_STRING = "game_string";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_HOUSE = "house";
	public static final String COLUMN_SHOT_TYPE = "shot_type";
	public static final String COLUMN_SPEED = "speed";
	public static final String COLUMN_FOOT_POSITION = "foot_position";
	public static final String COLUMN_TARGET_TYPE = "target_type";
	public static final String COLUMN_TARGET = "target";
	public static final String COLUMN_HIT_TARGET = "hit_target";
	public static final String COLUMN_BREAKPOINT = "breakpoint";
	public static final String COLUMN_BALL = "ball";
	public static final String COLUMN_LANE_NUMBERS = "lane_numbers";
	public static final String COLUMN_HIT_POCKET = "hit_pocket";
	public static final String COLUMN_NOTES = "notes";

	// Database creation SQL statement
	private static final String DATABASE_CREATE = "create table " 
			+ TABLE_GAMES
			+ "(" 
			+ COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_GAME_STRING + " text not null, " 
			+ COLUMN_DATE + " text not null, " 
			+ COLUMN_HOUSE + " text not null, " 
			+ COLUMN_SHOT_TYPE + " text not null, " 
			+ COLUMN_SPEED + " text not null, " 
			+ COLUMN_FOOT_POSITION + " text not null, " 
			+ COLUMN_TARGET_TYPE + " text not null, " 
			+ COLUMN_TARGET + " text not null, " 
			+ COLUMN_HIT_TARGET + " text not null, " 
			+ COLUMN_BREAKPOINT + " text not null, " 
			+ COLUMN_BALL + " text not null, " 
			+ COLUMN_LANE_NUMBERS + " text not null, " 
			+ COLUMN_HIT_POCKET + " text not null, " 
			+ COLUMN_NOTES + " text not null " 
			+ ");";

	//if the database doesn't exist, we create it here
	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	//if there's been a change in the database, we effectively create a new database
	//using the changes to the database schema
	//NOTE: THIS DESTROYS ALL OLD DATA!!!
	//CREATE NEW DATABASE VERSION ONLY WHEN ABOSLUTELY NECESARY!!
	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(GameTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
		onCreate(database);
	}
}
