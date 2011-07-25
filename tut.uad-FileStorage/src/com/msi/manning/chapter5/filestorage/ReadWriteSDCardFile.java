package com.msi.manning.chapter5.filestorage;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReadWriteSDCardFile extends Activity {

    private static final String LOGTAG = "FileStorage";

    private TextView readOutput;

    @Override
    public void onCreate(final Bundle icicle) {
        super.onCreate(icicle);
        this.setContentView(R.layout.read_write_sdcard_file);

        this.readOutput = (TextView) findViewById(R.id.readwritesd_output);

        String fileName = "testfile-" + System.currentTimeMillis() + ".txt";

        // create structure /sdcard/unlocking_android and then WRITE
        File sdDir = new File("/sdcard/");
        if (sdDir.exists() && sdDir.canWrite()) {
            File uadDir = new File(sdDir.getAbsolutePath() + "/unlocking_android");
            uadDir.mkdir();
            if (uadDir.exists() && uadDir.canWrite()) {
                File file = new File(uadDir.getAbsolutePath() + "/" + fileName);
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    Log.e(ReadWriteSDCardFile.LOGTAG, "error creating file", e);
                }

                // now that we have the structure we want, write to the file
                if (file.exists() && file.canWrite()) {
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(file);
                        fos.write("I fear you speak upon the rack, where men enforced do speak anything.".getBytes());
                    } catch (FileNotFoundException e) {
                        Log.e(ReadWriteSDCardFile.LOGTAG, "ERROR", e);
                    } catch (IOException e) {
                        Log.e(ReadWriteSDCardFile.LOGTAG, "ERROR", e);
                    } finally {
                        if (fos != null) {
                            try {
                                fos.flush();
                                fos.close();
                            } catch (IOException e) {
                                // swallow
                            }
                        }
                    }
                } else {
                    Log.e(ReadWriteSDCardFile.LOGTAG, "error writing to file");
                }

            } else {
                Log.e(ReadWriteSDCardFile.LOGTAG, "ERROR, unable to write to /sdcard/unlocking_android");
            }
        } else {
            Log
                .e(
                    ReadWriteSDCardFile.LOGTAG,
                    "ERROR, /sdcard path not available "
                        + "(did you create an SD image with the mksdcard tool, and start emulator with -sdcard <path_to_file> option?");
        }

        // READ
        File rFile = new File("/sdcard/unlocking_android/" + fileName);
        if (rFile.exists() && rFile.canRead()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(rFile);
                byte[] reader = new byte[fis.available()];
                while (fis.read(reader) != -1) {
                }
                this.readOutput.setText(new String(reader));
            } catch (IOException e) {
                Log.e(ReadWriteSDCardFile.LOGTAG, e.getMessage(), e);
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        // swallow
                    }
                }
            }
        } else {
            this.readOutput.setText("Unable to read/write sdcard file, see logcat output");
        }
    }
}
