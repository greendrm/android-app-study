package com.msi.manning.chapter5.filestorage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

public class ReadXMLResourceFile extends Activity {

    private static final String LOGTAG = "FileStorage";

    private TextView readOutput;
    private Button gotoReadWriteSDCard;

    @Override
    public void onCreate(final Bundle icicle) {
        super.onCreate(icicle);
        this.setContentView(R.layout.read_xmlresource_file);

        this.readOutput = (TextView) findViewById(R.id.readxmlres_output);

        XmlPullParser parser = getResources().getXml(R.xml.people);
        StringBuffer sb = new StringBuffer();

        try {
            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                String name = parser.getName();
                Log.v(ReadXMLResourceFile.LOGTAG, "    parser NAME - " + name);
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
            Log.e(ReadXMLResourceFile.LOGTAG, e.getMessage(), e);
        }        

        this.gotoReadWriteSDCard = (Button) findViewById(R.id.readwritesdcard_button);
        this.gotoReadWriteSDCard.setOnClickListener(new OnClickListener() {
            public void onClick(final View v) {
                startActivity(new Intent(ReadXMLResourceFile.this, ReadWriteSDCardFile.class));
            }
        });

    }
}
