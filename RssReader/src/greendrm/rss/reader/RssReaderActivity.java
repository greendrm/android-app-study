package greendrm.rss.reader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RssReaderActivity extends Activity {
	private ListView list;
	private ArrayList<String> rsses;
	private ArrayAdapter<String> adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        list = (ListView)findViewById(R.id.listView1);
        rsses = new ArrayList<String>();
        getRsses();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rsses);
        list.setAdapter(adapter);
    }
    
    private void getRsses() {
    	try {
    		//StringBuilder sb = new StringBuilder();

    		URL url = new URL("http://news.google.co.kr/news?pz=1&cf=all&ned=kr&hl=ko&output=rss");

    		XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
    		parser.setInput(url.openStream(), null);
    		int eventType = parser.getEventType();
    		
    		String tag;
    		boolean inTitle = false;
    		
    		while(eventType != XmlPullParser.END_DOCUMENT) {
    			switch(eventType) {
    			case XmlPullParser.TEXT:
    				tag = parser.getName();
    				if (inTitle) {
    					//sb.append(parser.getText()).append('\n');
    					rsses.add(parser.getText().toString());
    				}
    				break;
    			case XmlPullParser.END_TAG:
    				tag = parser.getName();
    				if (tag.compareTo("title") == 0) {
    					inTitle = false;
    				}
    				break;
    			case XmlPullParser.START_TAG:
    				tag = parser.getName();
    				if (tag.compareTo("title") == 0) {
    					inTitle = true;
    				}
    				break;
    			}
    			eventType = parser.next();
    		}
    		//rsses.add(sb.toString());
    	} catch (MalformedURLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void updateRsses() {
    	rsses.clear();
    	getRsses();
    	adapter.notifyDataSetChanged();
    }
}