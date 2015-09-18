package edu.nighthawks.soundwave.display;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.nighthawks.soundwave.app.SoundWaveApplication;
import edu.nighthawks.soundwave.soundwave.R;


public class SoundWaveMainActivity extends Activity
{
    Button buttonTalk;
    Button buttonContacts;
    TextView textViewTransmitStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_wave_main);
        initControls();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sound_wave_main, menu);
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

    private void startContacts()
    {
        Intent intent = new Intent(this, ContactActivity.class);
        startActivity(intent);
    }

    private void initControls()
    {
        textViewTransmitStatus = (TextView)findViewById(R.id.textViewTransmitStatus);

        buttonTalk = (Button)findViewById(R.id.buttonTalk);
        buttonTalk.setOnTouchListener(new View.OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN)
                {
                    SoundWaveApplication.getApplicationObject().soundWaveController.setTransmit(true);
                }
                else if (event.getActionMasked() == MotionEvent.ACTION_UP)
                {
                    SoundWaveApplication.getApplicationObject().soundWaveController.setTransmit(false);
                    SoundWaveApplication.getApplicationObject().soundWaveController.send();
                }


                textViewTransmitStatus.setText(SoundWaveApplication.getApplicationObject().soundWaveController.getStatusString());
                return false;
            }
        });


        buttonContacts = (Button)findViewById(R.id.buttonContacts);
        buttonContacts.setOnTouchListener(new View.OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                startContacts();
                return false;
            }
        });
    }
}
