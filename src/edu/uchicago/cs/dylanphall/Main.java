package edu.uchicago.cs.dylanphall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Main extends Activity implements View.OnClickListener {
    Button goto_actual;
    Button goto_mock;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        goto_actual = (Button) findViewById(R.id.actual_button);
        goto_mock = (Button) findViewById(R.id.mock_button);
    }

    @Override
    protected void onPause() {
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onClick(View v) {
        int button = v.getId();
        switch (button) {
            case R.id.actual_button:
                Intent actual = new Intent(this, ActualLocationActivity.class);
                startActivityForResult(actual, RESULT_OK);
                break;
            case R.id.mock_button:
                Intent mock = new Intent(this, MockLocationActivity.class);
                startActivityForResult(mock, RESULT_OK);
                break;
            default:
                Log.e("location", "Illegal button selected");

        }
    }
}

