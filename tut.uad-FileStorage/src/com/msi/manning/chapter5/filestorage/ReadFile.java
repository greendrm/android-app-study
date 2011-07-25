package com.msi.manning.chapter5.filestorage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.IOException;

public class ReadFile extends Activity {

    private static final String LOGTAG = "FileStorage";

    private TextView readOutput;
    private Button gotoReadResource;

    @Override
    public void onCreate(final Bundle icicle) {
        super.onCreate(icicle);
        this.setContentView(R.layout.read_file);

        this.readOutput = (TextView) findViewById(R.id.read_output);

        FileInputStream fis = null;
        try {
            fis = openFileInput("filename.txt");
            byte[] reader = new byte[fis.available()];
            while (fis.read(reader) != -1) {}
            this.readOutput.setText(new String(reader));
        } catch (IOException e) {
            Log.e(ReadFile.LOGTAG, e.getMessage(), e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // swallow
                }
            }
        }

        this.gotoReadResource = (Button) findViewById(R.id.read_button);
        this.gotoReadResource.setOnClickListener(new OnClickListener() {

            public void onClick(final View v) {
                startActivity(new Intent(ReadFile.this, ReadRawResourceFile.class));
            }
        });

    }
}
