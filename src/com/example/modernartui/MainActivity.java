package com.example.modernartui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
	private static final int MAX_WEIGHT = 4;

	// Define number of rows per column
	private static final int MAX_ROWS = 6;

	// Available colors array
	private static final int[] AVAILABLE_COLORS = { R.color.blue,
			R.color.purple, R.color.green, R.color.orange, R.color.red,
			R.color.grey };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//LinearLayout layoutContainer = (LinearLayout) findViewById(R.id.layout_container);
		//paintLayout(layoutContainer);

	}

	private void paintLayout(LinearLayout container) {

		// Get the number of columns, each columns is represented by a
		// LinearLayout child from layout_container
		int columnCount = container.getChildCount();

		// Populate each LinearLayout column with Views
		LinearLayout column;

		// ArrayList used to keep track of which colors have been already used
		// in each row
		ArrayList<Integer> colors;

		// View to be added
		View tempView;

		for (int i = 0; i < columnCount; i++) {
			column = (LinearLayout) container.getChildAt(i);
			colors = getArrayListFromArray(AVAILABLE_COLORS);
			// Generate MAX_ROWS randomized views and add them to the current
			// column
			for (int j = 0; j < MAX_ROWS; j++) {
				tempView = generateRandomizedView();
				colorView(colors, tempView);
				column.addView(tempView);
			}
		}
	}

	// Method for generating a View with a randomized weight.
	private View generateRandomizedView() {

		int weight = ran.nextInt(MAX_WEIGHT) + 1;

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 0,
				weight);

		View view = new View(this);
		view.setLayoutParams(params);

		return view;
	}

	// Select a Random color from an ArrayList containing the id's from the
	// available color resources, a color can only be used once per row
	private void colorView(ArrayList<Integer> colors, View view) {
		if (colors != null && view != null) {
			int position = ran.nextInt(colors.size());
			int color = (Integer) (colors.get(position));

			view.setBackgroundResource(color);
			// Remove used color from the list so it cannot be used again.
			colors.remove(position);
		}
	}

	private ArrayList<Integer> getArrayListFromArray(int[] array) {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for (int i = 0; i < array.length; i++) {
			temp.add(array[i]);
		}
		return temp;
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
