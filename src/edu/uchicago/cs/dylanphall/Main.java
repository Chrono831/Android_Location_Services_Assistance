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
        assignAndListenToButtons();
    }

    private void assignAndListenToButtons() {
        try {
            goto_actual = (Button) findViewById(R.id.actual_button);
            goto_actual.setOnClickListener(this);
            goto_mock = (Button) findViewById(R.id.mock_button);
            goto_mock.setOnClickListener(this);
        } catch (Exception e) {
            Log.e("location", "Error initializing and listening to buttons : " + e);
        }
    }

    @Override
    protected void onPause() {
        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onClick(View v) {
        int button = v.getId();
        Log.i("location", "Button selected : " + button);
        switch (button) {
            case R.id.actual_button:
                Log.i("location", "Actual button selected");
                Intent actual = new Intent(this, ActualLocationActivity.class);
                startActivity(actual);
                break;
            case R.id.mock_button:
                Log.i("location", "Mock button selected");
                Intent mock = new Intent(this, MockLocationActivity.class);
                startActivity(mock);
                break;
            default:
                Log.e("location", "Illegal button selected");

        }
    }
}

