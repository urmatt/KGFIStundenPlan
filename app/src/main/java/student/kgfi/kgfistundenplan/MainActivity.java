package student.kgfi.kgfistundenplan;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.Button;
import android.graphics.drawable.Drawable;


public class MainActivity extends ActionBarActivity {

    String [] groups = new String []{"ИГ-1-11", "ИГ-1-12", "ИГ-1-13", "ИГ-2-13", "ИГ-1-14"};

    GridLayout gLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gLayout = (GridLayout) findViewById(R.id.mainGrid);
        for(String g: groups){
            Button b = new Button(this);
            b.setText(g);
            b.setSingleLine();
//            b.setBackground(getDrawable(R.drawable.main_button));
            b.setTextColor(255);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
