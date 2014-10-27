package com.example.modernartui;

import java.util.Random;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Path.FillType;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class MainActivity extends ActionBarActivity {

	// Random number generator
	private Random ran = new Random();

	// Define maximum weight a view can have
	private static final int MAX_WEIGHT = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		LinearLayout layoutContainer = (LinearLayout) findViewById(R.id.layout_container);
		paintLayout(layoutContainer);

	}

	private void paintLayout(LinearLayout container) {

		// Get the number of columns, each columns is represented by a
		// LinearLayout child from layout_container
		int columnCount = container.getChildCount();

		// Populate each LinearLayout column with random number of row
		// Views (5 or 6)
		LinearLayout column;
		for (int i = 0; i < columnCount; i++) {
			column = (LinearLayout) container.getChildAt(i);
		}
	}

	// Method for generating a View with a randomized weight and background.
	private View generateRandomizedView() {

		int weight = ran.nextInt(MAX_WEIGHT) + 1;

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 0,
				weight);

		View view = new View(this);
		view.setLayoutParams(params);

		return view;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.action_settings:
			new VisitMomaDialogFragment().show(getSupportFragmentManager(),
					"visitMoma");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private class VisitMomaDialogFragment extends DialogFragment {

		@Override
		@NonNull
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage(R.string.alert_moma_message)
					.setPositiveButton(R.string.alert_moma_positive,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Go to activity with a web view
									// showing moma's website

								}
							})
					.setNegativeButton(R.string.alert_moma_negative,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dismiss();
								}
							});

			return builder.create();
		}

	}
}
