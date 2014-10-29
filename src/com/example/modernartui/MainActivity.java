package com.example.modernartui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends ActionBarActivity implements
		OnSeekBarChangeListener {

	private SeekBar seekbar;
	private LinearLayout rectContainer;
	private LinearLayout rectSubContainer;
	private View blackRect;
	private View blueRect;
	private View brownRect1;
	private View brownRect2;
	private View brownRect3;
	private View dgreyRect1;
	private View dgreyRect2;
	private View greyRect1;
	private View greyRect2;
	private View greyRect3;
	private View redRect;

	private int lastProgress;

	private static final String MOMA_URL = "http://www.moma.org/collection/artist.php?artist_id=4293";

	// Value to be added or subtracted from the view whenever the SeekBar is
	// dragged
	private static final float WEIGHT_OFFSET = 0.1f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Get references to the Views and Layout declared in the layout file
		rectContainer = (LinearLayout) findViewById(R.id.rectangles_container);
		rectSubContainer = (LinearLayout) findViewById(R.id.rectangle_subcontainer_2);
		blackRect = findViewById(R.id.black_rectangle);
		blueRect = findViewById(R.id.blue_rectangle);
		brownRect1 = findViewById(R.id.brown_rectangle_1);
		brownRect2 = findViewById(R.id.brown_rectangle_2);
		brownRect3 = findViewById(R.id.brown_rectangle_3);
		dgreyRect1 = findViewById(R.id.dark_grey_rectangle_1);
		dgreyRect2 = findViewById(R.id.dark_grey_rectangle_2);
		greyRect1 = findViewById(R.id.grey_rectangle_1);
		greyRect2 = findViewById(R.id.grey_rectangle_2);
		greyRect3 = findViewById(R.id.grey_rectangle_3);
		redRect = findViewById(R.id.red_rectangle);

		seekbar = (SeekBar) findViewById(R.id.seekbar);
		seekbar.setOnSeekBarChangeListener(this);

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
									Intent intent = new Intent(
											Intent.ACTION_VIEW, Uri
													.parse(MOMA_URL));
									if (intent
											.resolveActivity(getPackageManager()) != null) {
										startActivity(intent);
									}

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

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {

		// Determine difference between last and current progress (user can skip
		// steps touching the SeekBar)
		int diff = progress - lastProgress;

		// This value determines direction and by how much the weight will be
		// affected
		float offset = WEIGHT_OFFSET * diff;

		lastProgress = progress;

		// Adjust weight and color in the different views to give the effect of
		// movement
		setViewWeightColor(blackRect, offset, progress);
		setViewWeightColor(blueRect, -offset, progress);
		setViewWeightColor(redRect, 0, progress);
		setViewWeightColor(brownRect1, offset, progress);
		setViewWeightColor(greyRect1, offset, progress);
		setViewWeightColor(dgreyRect1, -offset, progress);
		setViewWeightColor(dgreyRect2, offset, progress);
		setViewWeightColor(brownRect2, 0, progress);
		setViewWeightColor(greyRect2, -offset, progress);
		setViewWeightColor(greyRect3, offset, progress);
		setViewWeightColor(brownRect3, -offset, progress);

		// Needed for views to redraw themselves given their new weight values
		rectContainer.requestLayout();
		rectSubContainer.requestLayout();
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	private void setViewWeightColor(View view, float weightOffSet, int progress) {
		// Change the weight of each the view by the value in weightOffSet
		((LinearLayout.LayoutParams) view.getLayoutParams()).weight += weightOffSet;
		// Level used with level lists in order to change color
		view.getBackground().mutate().setLevel(progress);
	}
}
