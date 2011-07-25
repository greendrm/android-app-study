package com.msi.manning.chapter5.filestorage;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class ReadRawResourceFile extends Activity {

    private static final String LOGTAG = "FileStorage";

    private TextView readOutput;
    private Button gotoReadXMLResource;

    @Override
    public void onCreate(final Bundle icicle) {
        super.onCreate(icicle);
        this.setContentView(R.layout.read_rawresource_file);

        this.readOutput = (TextView) findViewById(R.id.readrawres_output);

        Resources resources = getResources();
        InputStream is = null;
        try {
            is = resources.openRawResource(R.raw.people);
            byte[] reader = new byte[is.available()];
            while (is.read(reader) != -1) {
            }
            this.readOutput.setText(new String(reader));
        } catch (IOException e) {
            Log.e(ReadRawResourceFile.LOGTAG, e.getMessage(), e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // swallow
                }
            }
        }

        this.gotoReadXMLResource = (Button) findViewById(R.id.readrawres_button);
        this.gotoReadXMLResource.setOnClickListener(new OnClickListener() {
            public void onClick(final View v) {
                startActivity(new Intent(ReadRawResourceFile.this, ReadXMLResourceFile.class));
            }
        });

    }
}
