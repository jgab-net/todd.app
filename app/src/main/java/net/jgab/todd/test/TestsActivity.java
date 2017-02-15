package net.jgab.todd.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.jgab.todd.R;
import net.jgab.todd.ToddApplication;

public class TestsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        ToddApplication.getComponent().inject(this);
    }
}
