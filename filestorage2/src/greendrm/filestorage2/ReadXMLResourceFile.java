package greendrm.filestorage2;

import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ReadXMLResourceFile extends Activity {
	private final static String TAG = "readxmlresource";
	
	TextView readOutput;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.readxmlresource);

		readOutput = (TextView) findViewById(R.id.textView6);
		XmlPullParser parser = getResources().getXml(R.xml.people);
		StringBuffer sb = new StringBuffer();
		try {
			while (parser.next() != XmlPullParser.END_DOCUMENT) {
				String name = parser.getName();
				Log.v(ReadXMLResourceFile.TAG, " parser NAME - " + name);
				String first = null;
				String last = null;
				if ((name != null) && name.equals("person")) {
					int size = parser.getAttributeCount();
					for (int i = 0; i < size; i++) {
						String attrName = parser.getAttributeName(i);
						String attrValue = parser.getAttributeValue(i);
						if ((attrName != null) && attrName.equals("firstname")) {
							first = attrValue;
						} else if ((attrName != null) && attrName.equals("lastname")) {
							last = attrValue;
						}
					}
					if ((first != null) && (last != null)) {
						sb.append(last + ", " + first + "\n");
					}
				}
			}
			this.readOutput.setText(sb.toString());
		} catch (Exception e) {
			Log.e(ReadXMLResourceFile.TAG, e.getMessage(), e);
		}
	}
	
	public void onClickSDcard (View v) {
		startActivity(new Intent(ReadXMLResourceFile.this, ReadWriteSD.class));
	}

}
