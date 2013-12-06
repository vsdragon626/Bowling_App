package com.tjproductions.bowlingbuddy2;

import com.tjproductions.bowlingbuddy2.contentprovider.GameContentProvider;
import com.tjproductions.bowlingbuddy2.database.GameTable;
import com.tjproductions.bowlingbuddy2.DatePickerFragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameActivity extends Activity {

	private CheckBox pin1;
	private CheckBox pin2;
	private CheckBox pin3;
	private CheckBox pin4;
	private CheckBox pin5;
	private CheckBox pin6;
	private CheckBox pin7;
	private CheckBox pin8;
	private CheckBox pin9;
	private CheckBox pin10;
	private TextView gameView;
	private TextView hintString;
	private Button next;
	private EditText mHouseEdit;
	//	private RelativeLayout houseLayout;
	//	private Button houseSave;
	//	private Button houseCancel;
	private TextView mDate;
	private EditText mShotType;
	private EditText mSpeed;
	private EditText mFootPos;
	//	private RadioGroup mTargetType;
	private RadioButton mTargetDots;
	private RadioButton mTargetArrows;
	private EditText mTarget;
	//	private RadioGroup mHit;
	private RadioButton mYHit;
	private RadioButton mNHit;
	private EditText mBreakpoint;
	private EditText mBall;
	//	private RelativeLayout ballLayout;
	//	private Button ballSave;
	//	private Button ballCancel;
	private EditText mLaneNum;
	//	private RadioGroup mPocketHit;
	private RadioButton mPFHit;
	private RadioButton mPLHit;
	private RadioButton mPHHit;
	private RadioButton mPNHit;
	private MultiAutoCompleteTextView mPocketHitDet;
	private Button save;
	private Button cancel;
	private String gameString = "";
	private int ball = 1;
	private int score = 0;
	private int frame = 1;
	private boolean[] frame10 = new boolean[]{false,false,false,false};
	private Uri itemUri;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		pin1 = (CheckBox) findViewById(R.id.radioButton1);
		pin2 = (CheckBox) findViewById(R.id.RadioButton01);
		pin3 = (CheckBox) findViewById(R.id.RadioButton02);
		pin4 = (CheckBox) findViewById(R.id.RadioButton03);
		pin5 = (CheckBox) findViewById(R.id.RadioButton04);
		pin6 = (CheckBox) findViewById(R.id.RadioButton05);
		pin7 = (CheckBox) findViewById(R.id.RadioButton06);
		pin8 = (CheckBox) findViewById(R.id.RadioButton07);
		pin9 = (CheckBox) findViewById(R.id.RadioButton08);
		pin10 = (CheckBox) findViewById(R.id.RadioButton09);
		gameView = (TextView) findViewById(R.id.gameView);
		hintString = (TextView) findViewById(R.id.hintString);
		next = (Button) findViewById(R.id.nextButton);
		mHouseEdit = (EditText) findViewById(R.id.houseEdit);
		//		houseLayout = (RelativeLayout) findViewById(R.id.saveHouseLayout);
		//		houseSave = (Button) findViewById(R.id.housesaveButton);
		//		houseCancel = (Button) findViewById(R.id.housecancelButton);
		mDate = (TextView) findViewById(R.id.dateEdit);
		mShotType = (EditText) findViewById(R.id.shotEdit);
		mSpeed = (EditText) findViewById(R.id.speedEdit);
		mFootPos = (EditText) findViewById(R.id.footPositionEdit);
		//		mTargetType = (RadioGroup) findViewById(R.id.targetGroup);
		mTargetDots = (RadioButton) findViewById(R.id.targetDCheck);
		mTargetArrows = (RadioButton) findViewById(R.id.targetACheck);
		mTarget = (EditText) findViewById(R.id.targetEdit);
		//		mHit = (RadioGroup) findViewById(R.id.targetHitGroup);
		mYHit = (RadioButton) findViewById(R.id.hitYCheck);
		mNHit = (RadioButton) findViewById(R.id.hitNCheck);
		mBreakpoint = (EditText) findViewById(R.id.breakpointEdit);
		mBall = (EditText) findViewById(R.id.ballEdit);
		//		ballLayout = (RelativeLayout) findViewById(R.id.saveBallLayout);
		//		ballSave = (Button) findViewById(R.id.ballsaveButton);
		//		ballCancel = (Button) findViewById(R.id.ballcancelButton);
		mLaneNum = (EditText) findViewById(R.id.laneEdit);
		//		mPocketHit = (RadioGroup) findViewById(R.id.pocketGroup);
		mPFHit = (RadioButton) findViewById(R.id.pocketFCheck);
		mPLHit = (RadioButton) findViewById(R.id.pocketLCheck);
		mPHHit = (RadioButton) findViewById(R.id.pocketHCheck);
		mPNHit = (RadioButton) findViewById(R.id.pocketNCheck);
		mPocketHitDet = (MultiAutoCompleteTextView) findViewById(R.id.pocketDetails);
		save = (Button) findViewById(R.id.saveButton);
		cancel = (Button) findViewById(R.id.cancelButton);
		pin1.setChecked(true);
		pin2.setChecked(true);
		pin3.setChecked(true);
		pin4.setChecked(true);
		pin5.setChecked(true);
		pin6.setChecked(true);
		pin7.setChecked(true);
		pin8.setChecked(true);
		pin9.setChecked(true);
		pin10.setChecked(true);

		Bundle extras = getIntent().getExtras();

		save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				saveState();
				finish();
			}

		});

		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				finish();
			}

		});

		mDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new DatePickerFragment((TextView) mDate);
				newFragment.show(getFragmentManager(), "datePicker");
			}
		});

		next.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				addFrame();
				if(frame == 10 && ball == 4){
					next.setText("Done");
					pin1.setChecked(true);
					pin2.setChecked(true);
					pin3.setChecked(true);
					pin4.setChecked(true);
					pin5.setChecked(true);
					pin6.setChecked(true);
					pin7.setChecked(true);
					pin8.setChecked(true);
					pin9.setChecked(true);
					pin10.setChecked(true);
					next.setClickable(false);
					pin1.setClickable(false);
					pin2.setClickable(false);
					pin3.setClickable(false);
					pin4.setClickable(false);
					pin5.setClickable(false);
					pin6.setClickable(false);
					pin7.setClickable(false);
					pin8.setClickable(false);
					pin9.setClickable(false);
					pin10.setClickable(false);
				}
				gameView.setText(gameString);
				if(!hintString.getText().equals("")){
					hintString.setText("");
				}
			}

		});

		// Check from the saved Instance
		itemUri = (savedInstanceState == null) ? null : (Uri) savedInstanceState
				.getParcelable(GameContentProvider.CONTENT_ITEM_TYPE);

		// Or passed from the other activity
		if (extras != null) {
			itemUri = extras
					.getParcelable(GameContentProvider.CONTENT_ITEM_TYPE);

			fillData(itemUri);
		}
	}

	private void addFrame(){
		int num = 0;
		boolean p1 = true,p2 = true,p3 = true,p4 = true,p5 = true,p6 = true,p7 = true,p8 = true,p9 = true,p10 = true;
		if(!pin1.isChecked()){
			num++;
			p1 = false;
		}
		if(!pin2.isChecked()){
			num++;
			p2 = false;
		}
		if(!pin3.isChecked()){
			num++;
			p3 = false;
		}
		if(!pin4.isChecked()){
			num++;
			p4 = false;
		}
		if(!pin5.isChecked()){
			num++;
			p5 = false;
		}
		if(!pin6.isChecked()){
			num++;
			p6 = false;
		}
		if(!pin7.isChecked()){
			num++;
			p7 = false;
		}
		if(!pin8.isChecked()){
			num++;
			p8 = false;
		}
		if(!pin9.isChecked()){
			num++;
			p9 = false;
		}
		if(!pin10.isChecked()){
			num++;
			p10 = false;
		}

		if(frame==10){
			if(num==0){
				gameString = gameString+"-";
			}
			else if(num != 10){
				if(ball == 2 && !frame10[ball]){
					gameString = gameString+(num-score);
				}
				else{
					gameString = gameString+num;
					score = num;
				}
			}
			else if(num == 10 && (frame10[ball] || (ball == 1 || ball == 3))){
				gameString = gameString+"X";
				frame10[ball] = true;
			}
			else if(num == 10 && ball == 2){
				gameString = gameString+"/";
				frame10[ball] = true;
			}
		}
		else{
			if(num==0){
				gameString = gameString+"-";
			}
			else if(num==10 && ball == 1){
				gameString = gameString+"X ";
				ball = 2;
			}
			else if(num==10){
				gameString = gameString+"/ ";
			}
			else{
				if((num-score) == 0){
					gameString = gameString+"-";
				}
				else{
					gameString = gameString+(num-score);
				}
			}
		}


		if(frame == 10){
			if(ball == 1){
				ball = 2;
				if(frame10[ball-1]){
					pin1.setChecked(true);
					pin2.setChecked(true);
					pin3.setChecked(true);
					pin4.setChecked(true);
					pin5.setChecked(true);
					pin6.setChecked(true);
					pin7.setChecked(true);
					pin8.setChecked(true);
					pin9.setChecked(true);
					pin10.setChecked(true);
				}
				else{
					pin1.setChecked(p1);
					pin2.setChecked(p2);
					pin3.setChecked(p3);
					pin4.setChecked(p4);
					pin5.setChecked(p5);
					pin6.setChecked(p6);
					pin7.setChecked(p7);
					pin8.setChecked(p8);
					pin9.setChecked(p9);
					pin10.setChecked(p10);
				}
			}
			else if(ball == 2){
				if(frame10[ball]){
					ball = 3;
					score = 0;
					pin1.setChecked(true);
					pin2.setChecked(true);
					pin3.setChecked(true);
					pin4.setChecked(true);
					pin5.setChecked(true);
					pin6.setChecked(true);
					pin7.setChecked(true);
					pin8.setChecked(true);
					pin9.setChecked(true);
					pin10.setChecked(true);
				}
				else{
					ball = 4;
				}
			}
			else if(ball == 3){
				ball = 4;
				pin1.setChecked(true);
				pin2.setChecked(true);
				pin3.setChecked(true);
				pin4.setChecked(true);
				pin5.setChecked(true);
				pin6.setChecked(true);
				pin7.setChecked(true);
				pin8.setChecked(true);
				pin9.setChecked(true);
				pin10.setChecked(true);
			}
		}
		else{
			if(ball==1){
				ball = 2;
				score += num;
				pin1.setChecked(p1);
				pin2.setChecked(p2);
				pin3.setChecked(p3);
				pin4.setChecked(p4);
				pin5.setChecked(p5);
				pin6.setChecked(p6);
				pin7.setChecked(p7);
				pin8.setChecked(p8);
				pin9.setChecked(p9);
				pin10.setChecked(p10);
			}
			else if(ball == 2){
				ball = 1;
				score = 0;
				frame++;
				gameString = gameString+" ";
				pin1.setChecked(true);
				pin2.setChecked(true);
				pin3.setChecked(true);
				pin4.setChecked(true);
				pin5.setChecked(true);
				pin6.setChecked(true);
				pin7.setChecked(true);
				pin8.setChecked(true);
				pin9.setChecked(true);
				pin10.setChecked(true);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	private void saveState() {
		String game = gameString;
		String house = mHouseEdit.getText().toString();
		String date = mDate.getText().toString();
		String shot = mShotType.getText().toString();
		String speed = mSpeed.getText().toString();
		String foot = mFootPos.getText().toString();
		String targetType = "";
		if(mTargetDots.isChecked()){
			targetType = "Dots";
		}
		else if(mTargetArrows.isChecked()){
			targetType = "Arrows";
		}
		else{
			targetType = "";
		}
		String target = mTarget.getText().toString();
		String hit = "";
		if(mYHit.isChecked()){
			hit = "Yes";
		}
		else if(mNHit.isChecked()){
			hit = "No";
		}
		else{
			hit = "";
		}
		String breakpoint = mBreakpoint.getText().toString();
		String ball = mBall.getText().toString();
		String lane = mLaneNum.getText().toString();
		String hit_type = "";
		if(mPFHit.isChecked()){
			hit_type = "Flush";
		}
		else if(mPLHit.isChecked()){
			hit_type = "Light";
		}
		else if(mPHHit.isChecked()){
			hit_type = "Heavy";
		}
		else if(mPNHit.isChecked()){
			hit_type = "No";
		}
		else{
			hit_type = "";
		}
		String hit_det = mPocketHitDet.getText().toString();

		ContentValues values = new ContentValues();

		values.put(GameTable.COLUMN_GAME_STRING, game);
		values.put(GameTable.COLUMN_DATE, date);
		values.put(GameTable.COLUMN_HOUSE, house);
		values.put(GameTable.COLUMN_SHOT_TYPE, shot);
		values.put(GameTable.COLUMN_SPEED, speed);
		values.put(GameTable.COLUMN_FOOT_POSITION, foot);
		values.put(GameTable.COLUMN_TARGET_TYPE, targetType);
		values.put(GameTable.COLUMN_TARGET, target);
		values.put(GameTable.COLUMN_HIT_TARGET, hit);
		values.put(GameTable.COLUMN_BREAKPOINT, breakpoint);
		values.put(GameTable.COLUMN_BALL, ball);
		values.put(GameTable.COLUMN_LANE_NUMBERS, lane);
		values.put(GameTable.COLUMN_HIT_POCKET, hit_type);
		values.put(GameTable.COLUMN_NOTES, hit_det);

		if (itemUri == null) {
			// New item
			itemUri = getContentResolver().insert(GameContentProvider.CONTENT_URI, values);
		} else {
			// Update item
			getContentResolver().update(itemUri, values, null, null);
		}
	}

	private void fillData(Uri uri) {
		String[] projection = {GameTable.COLUMN_GAME_STRING, GameTable.COLUMN_HOUSE,
				GameTable.COLUMN_DATE, GameTable.COLUMN_SHOT_TYPE,
				GameTable.COLUMN_SPEED, GameTable.COLUMN_FOOT_POSITION,
				GameTable.COLUMN_TARGET_TYPE, GameTable.COLUMN_TARGET,
				GameTable.COLUMN_HIT_TARGET, GameTable.COLUMN_BREAKPOINT,
				GameTable.COLUMN_BALL, GameTable.COLUMN_LANE_NUMBERS,
				GameTable.COLUMN_HIT_POCKET, GameTable.COLUMN_NOTES };				
		if (uri != null) {
			Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
			cursor.moveToFirst();

			gameView.setText(cursor.getString(cursor
					.getColumnIndexOrThrow(GameTable.COLUMN_GAME_STRING)));
			gameString = cursor.getString(cursor
					.getColumnIndexOrThrow(GameTable.COLUMN_GAME_STRING));
			mHouseEdit.setText(cursor.getString(cursor
					.getColumnIndexOrThrow(GameTable.COLUMN_HOUSE)));
			mDate.setText(cursor.getString(cursor
					.getColumnIndexOrThrow(GameTable.COLUMN_DATE)));
			mShotType.setText(cursor.getString(cursor
					.getColumnIndexOrThrow(GameTable.COLUMN_SHOT_TYPE)));
			mSpeed.setText(cursor.getString(cursor
					.getColumnIndexOrThrow(GameTable.COLUMN_SPEED)));
			mFootPos.setText(cursor.getString(cursor
					.getColumnIndexOrThrow(GameTable.COLUMN_FOOT_POSITION)));
			String targetType = cursor.getString(cursor
					.getColumnIndexOrThrow(GameTable.COLUMN_TARGET_TYPE));
			mTarget.setText(cursor.getString(cursor
					.getColumnIndexOrThrow(GameTable.COLUMN_TARGET)));
			String hit = cursor.getString(cursor
					.getColumnIndexOrThrow(GameTable.COLUMN_HIT_TARGET));
			mBreakpoint.setText(cursor.getString(cursor
					.getColumnIndexOrThrow(GameTable.COLUMN_BREAKPOINT)));
			mBall.setText(cursor.getString(cursor
					.getColumnIndexOrThrow(GameTable.COLUMN_BALL)));
			mLaneNum.setText(cursor.getString(cursor
					.getColumnIndexOrThrow(GameTable.COLUMN_LANE_NUMBERS)));
			String hit_type = cursor.getString(cursor
					.getColumnIndexOrThrow(GameTable.COLUMN_HIT_POCKET));
			mPocketHitDet.setText(cursor.getString(cursor
					.getColumnIndexOrThrow(GameTable.COLUMN_NOTES)));

			if(targetType.equals("Dots")){
				mTargetDots.setChecked(true);
			}
			else if(targetType.equals("Arrows")){
				mTargetArrows.setChecked(true);
			}

			if(hit.equals("Yes")){
				mYHit.setChecked(true);
			}
			else if(hit.equals("No")){
				mNHit.setChecked(true);
			}

			if(hit_type.equals("Flush")){
				mPFHit.setChecked(true);
			}
			else if(hit_type.equals("Light")){
				mPLHit.setChecked(true);
			}
			else if(hit_type.equals("Heavy")){
				mPHHit.setChecked(true);
			}
			else if(hit_type.equals("No")){
				mPNHit.setChecked(true);
			}

			if(gameString.contains(" ")){
				frame = 0;
				for(int i = 0; i<gameString.length();i++){
					if(gameString.charAt(i) == ' '){
						frame++;
					}
				}
				if (frame >= 10){
					next.setText("Done");
					next.setClickable(false);
					pin1.setClickable(false);
					pin2.setClickable(false);
					pin3.setClickable(false);
					pin4.setClickable(false);
					pin5.setClickable(false);
					pin6.setClickable(false);
					pin7.setClickable(false);
					pin8.setClickable(false);
					pin9.setClickable(false);
					pin10.setClickable(false);
					gameView.setText(gameString);
				}
			}
			else{
				frame = 1;
			}

			// Always close the cursor
			cursor.close();
		}
	}

}
