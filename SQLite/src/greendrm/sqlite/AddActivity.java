package greendrm.sqlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddActivity extends Activity {
	EditText eFirstName;
	EditText eLastName;
	EditText eCountry;
	EditText eAge;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);
		
		eFirstName = (EditText)findViewById(R.id.editText2);
		eLastName = (EditText)findViewById(R.id.editText1);
		eCountry = (EditText)findViewById(R.id.editText3);
		eAge = (EditText)findViewById(R.id.editText4);
	}
	
	public void onClickOK(View v) {
		Intent data = new Intent();
		data.putExtra("firstname", eFirstName.getText().toString());
		data.putExtra("lastname", eLastName.getText().toString());
		data.putExtra("country", eCountry.getText().toString());
		data.putExtra("age", eAge.getText().toString());
		setResult(Activity.RESULT_OK, data);
		finish();
	}
	
	public void onClickCancel(View v) {
		Intent data = new Intent();
		setResult(Activity.RESULT_CANCELED, data);
		finish();
	}

}
