package com.tjproductions.bowlingbuddy2;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.tjproductions.bowlingbuddy2.database.GameTable;

public class StatsView extends Activity {

	List <String> games = new ArrayList<String>(); 
	List <String> houses = new ArrayList<String>();
	int hits = 0;
	int strike = 0;
	int spare = 0;
	List <String> balls = new ArrayList<String>();
	TextView numGames, numHouses, numBalls, numHit, numStrike, numSpare, numOpen, ave;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats_view);
		numGames = (TextView) findViewById(R.id.numGames);
		numHouses = (TextView) findViewById(R.id.numHouses);
		numBalls = (TextView) findViewById(R.id.numBalls);
		numHit = (TextView) findViewById(R.id.numHit);
		numStrike = (TextView) findViewById(R.id.numStrike);
		numSpare = (TextView) findViewById(R.id.numSpare);
		numOpen = (TextView) findViewById(R.id.numOpen);
		ave = (TextView) findViewById(R.id.ave);
		//Get all rows
		getRows();
		getStrike();
		getSpare();
		numGames.setText(Integer.toString(games.size()));
		numHouses.setText(Integer.toString(houses.size()));
		numBalls.setText(Integer.toString(balls.size()));
		numHit.setText(Integer.toString(hits));
		numStrike.setText(Integer.toString(strike));
		numSpare.setText(Integer.toString(spare));
		numOpen.setText(Integer.toString((games.size()*10)-(strike+spare)));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stats_view, menu);
		return true;
	}
	
	private void getRows(){
		String[] projection = {GameTable.COLUMN_GAME_STRING, GameTable.COLUMN_HOUSE, 
							   GameTable.COLUMN_HIT_TARGET, GameTable.COLUMN_BALL };
		Uri uri = Uri.parse("content://" + "com.tjproductions.bowlingbuddy2.contentprovider" + "/" + "games");
		Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
		cursor.moveToFirst();
		if (cursor != null) {
			if (cursor .moveToFirst()) {
	            while (cursor.isAfterLast() == false) {
	                String game = cursor.getString(cursor
	    					.getColumnIndexOrThrow(GameTable.COLUMN_GAME_STRING));
	                String house = cursor.getString(cursor
	    					.getColumnIndexOrThrow(GameTable.COLUMN_HOUSE));
	                String hit = cursor.getString(cursor
	    					.getColumnIndexOrThrow(GameTable.COLUMN_HIT_TARGET));
	                String ball = cursor.getString(cursor
	    					.getColumnIndexOrThrow(GameTable.COLUMN_BALL));
	                

	                games.add(game);
	                if(!houses.contains(house) && !houses.isEmpty()){
	                	houses.add(house);
	                }
	                else if(houses.isEmpty()){
	                	houses.add(house);
	                }
	                if(hit.equals("Yes")){
	                	hits++;
	                }
	                if(!balls.contains(ball) && !balls.isEmpty()){
	                	balls.add(ball);
	                }
	                else if(balls.isEmpty()){
	                	balls.add(ball);
	                }
	                cursor.moveToNext();
	            }
	        }
        }
		cursor.close();
	}
	
	private void getStrike(){
		for(int i = 0;i<games.size();i++){
			String temp = games.get(i);
			if(temp.contains("X")){
				for(int j = 0; j<temp.length();j++){
					if(temp.charAt(j) == 'X'){
						strike++;
					}
				}
			}
		}
	}
	
	private void getSpare(){
		for(int i = 0;i<games.size();i++){
			String temp = games.get(i);
			if(temp.contains("/")){
				for(int j = 0; j<temp.length();j++){
					if(temp.charAt(j) == '/'){
						spare++;
					}
				}
			}
		}
	}

}
