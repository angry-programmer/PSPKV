package com.mif.pspkv;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class MainActivity extends SherlockFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getSupportActionBar().hide();
		OnClickListener onClick = new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
				int x = 0;
				switch (v.getId()) {
				case R.id.button1:
//					x = QuestionsActivity.TEST_1;
					break;
				case R.id.button2:
//					x = QuestionsActivity.TEST_2;
					break;
				case R.id.button3:
//					x = QuestionsActivity.TEST_3;
					break;
				case R.id.button4:
					x = QuestionsActivity.TEST_4;
					break;
				case R.id.button5:
//					x = QuestionsActivity.TEST_ALL;
					break;

				default:
					break;
				}
				intent.putExtra("test_no", x);
				startActivity(intent);
			}
		};
//		((Button) findViewById(R.id.button1)).setOnClickListener(onClick);
//		((Button) findViewById(R.id.button2)).setOnClickListener(onClick);
//		((Button) findViewById(R.id.button3)).setOnClickListener(onClick);
		((Button) findViewById(R.id.button4)).setOnClickListener(onClick);
//		((Button) findViewById(R.id.button5)).setOnClickListener(onClick);
	}
    
}
