/* $Id: TestLayout.java 57 2007-11-21 18:31:52Z steven $
 * 
 * Copyright 2007 Steven Osborn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      <!-- m --><a class="postlink" href="http://www.apache.org/licenses/LICENSE-2.0">http://www.apache.org/licenses/LICENSE-2.0</a><!-- m -->
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package greendrm.iconize;

import android.app.ListActivity;
import android.os.Bundle;

public class AndroidIconizeActivity extends ListActivity {

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        IconifiedTextListAdapter itla = new IconifiedTextListAdapter(this);

        // Add four items
        itla.addItem(new IconifiedText(
        		"Surf Web", getResources().getDrawable(R.drawable.favicon)));
        itla.addItem(new IconifiedText(
        		"Report Bug", getResources().getDrawable(R.drawable.bug)));
        itla.addItem(new IconifiedText(
        		"Speak Spanish", getResources().getDrawable(R.drawable.locale)));
        itla.addItem(new IconifiedText(
        		"Vidoop", getResources().getDrawable(R.drawable.vidoop)));
        // Display it
        setListAdapter(itla);
    }
}