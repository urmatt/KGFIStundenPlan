package student.kgfi.kgfistundenplan;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

/**
 * Created by Urma on 4/22/2015.
 */
public class MoreNewsActivity extends ActionBarActivity{

    WebView webView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_news);
        webView = (WebView)findViewById(R.id.moreNewsWeb);
        /** Можно еще так:
         * http://urmapps.esy.es/mykgfi/getNews.php?group=all&news_count=1
         * здесь news_count=1 говорит о том что число отображаемых новостей равна 1
         */
        webView.loadUrl("http://urmapps.esy.es/mykgfi/getNews.php?group=all&news_count=10");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
