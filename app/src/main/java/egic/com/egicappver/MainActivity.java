package egic.com.egicappver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import egic.com.recordversion.RODY;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new RODY(this).rody("mahmoud","123","1");
    }
}
