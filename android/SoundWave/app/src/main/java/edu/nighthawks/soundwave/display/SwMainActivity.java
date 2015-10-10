package edu.nighthawks.soundwave.display;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import edu.nighthawks.soundwave.app.SoundWaveApplication;
import edu.nighthawks.soundwave.contacts.Contact;
import edu.nighthawks.soundwave.soundwave.R;

/**
 * This class controls the main soundWave user interface. It has three main pieces:
 *
 * 1. Main soundWave screen for sending and receiving sound messages
 * 2. Registering
 * 3. Creating contacts
 */
public class SwMainActivity extends AppCompatActivity
{
    EditText contactNameTxt, contactEmailTxt, contactAddressTxt, displayNameTxt, passwordTxt, accountEmailTxt;
    ImageView contactImageimgView;
    //List<Contact> Contacts = new ArrayList<Contact>();
    ListView contactListView;
    ArrayAdapter<Contact> adapter;

    @Override
    protected void onResume()
    {
        super.onResume();

        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    private void setTitle()
    {
        try
        {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            this.setTitle(this.getTitle().toString() + " " + pInfo.versionName);
        }
        catch (Exception ex)
        {
            Log.i(SwMainActivity.class.toString(), "Failed to get version.");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle();

        contactNameTxt = (EditText) findViewById(R.id.txtName);
        contactEmailTxt = (EditText) findViewById(R.id.txtEmail);
        contactListView = (ListView) findViewById(R.id.listView);
        contactImageimgView = (ImageView) findViewById(R.id.imgContactView);
        displayNameTxt = (EditText) findViewById(R.id.editTextDisplayName);
        passwordTxt = (EditText) findViewById(R.id.editTextPassword);
        accountEmailTxt = (EditText) findViewById(R.id.editTextEmailAddress);

        if (SoundWaveApplication.getApplicationObject().soundWaveConfig.getUserEmail().isEmpty() == false)
            accountEmailTxt.setText(SoundWaveApplication.getApplicationObject().soundWaveConfig.getUserEmail());
        if (SoundWaveApplication.getApplicationObject().soundWaveConfig.getUserName().isEmpty() == false)
            displayNameTxt.setText((SoundWaveApplication.getApplicationObject().soundWaveConfig.getUserName()));


        adapter = new ContactListAdapter();
        contactListView.setAdapter(adapter);


        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);


        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("main");
        tabSpec.setContent(R.id.main);
        tabSpec.setIndicator("Sound Wave");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("creator");
        tabSpec.setContent(R.id.creator);
        tabSpec.setIndicator("Add contact");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("registration");
        tabSpec.setContent(R.id.registration);
        tabSpec.setIndicator("Register");
        tabHost.addTab(tabSpec);

        final Button addBtn = (Button) findViewById(R.id.btnAdd);
        addBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addContact(contactNameTxt.getText().toString(), contactEmailTxt.getText().toString());
                //populateList();
                Toast.makeText(getApplicationContext(), contactNameTxt.getText().toString() + " has been added to your contacts!", Toast.LENGTH_SHORT).show();
            }
        });

        final Button registerBtn = (Button) findViewById(R.id.buttonRegister);
        registerBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                registerAccount(displayNameTxt.getText().toString(), accountEmailTxt.getText().toString(), passwordTxt.getText().toString());
                // check result of call above if time need web response
                Toast.makeText(getApplicationContext(), accountEmailTxt.getText().toString() + " has been registered.", Toast.LENGTH_SHORT).show();
            }
        });

        contactNameTxt.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                addBtn.setEnabled(!contactNameTxt.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        contactImageimgView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Contact Image"), 1);
            }
        });

/*        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

            }
        });*/

    }

    public void onActivityResult(int reqCode, int resCode, Intent data)
    {
       if (resCode == RESULT_OK)
       {
           if (reqCode == 1)
               contactImageimgView.setImageURI(data.getData());
       }
    }


/*    contactListView.setOnItemClickListener(new OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = ((TextView)view).getText().toString();
        String[] strArray = item.split("\\;");

        cli.load(strArray[0].toString());
        td.setAdapter(myarrayAdapter2);
        listItems2.clear();
        listItems2.add("Nome: " + cli.getNome());
        listItems2.add("Morada: " + cli.getMorada());
        listItems2.add("Localidade: " + cli.getLoca());
        listItems2.add("Código Postal: " + cli.getCp());
        listItems2.add("Pais: " + cli.getPais());
        listItems2.add("Nif: " + cli.getNif());
        listItems2.add("Tel: " + cli.getTel());
        listItems2.add("Tlm: " + cli.getTlm());
        listItems2.add("Tipo Preço: " + cli.getTipoPvn());
        listItems2.add("Cond. Pagamento: " + cli.getCpg());
        listItems2.add("Obs: " + cli.getObs());
        td.setAdapter(myarrayAdapter2);
        myarrayAdapter2.notifyDataSetChanged();
    }
});*/

    private void  addContact(String name, String email)
    {
        try
        {
            SoundWaveApplication.getApplicationObject().soundWaveController.createContact(name, email);
        }
        catch (IOException ex)
        {
            Toast.makeText(SwMainActivity.this, "Failed to add contact: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void registerAccount(String displayName, String accountEmail, String password)
    {
        try
        {
            SoundWaveApplication.getApplicationObject().soundWaveController.createAccount(displayName, password, accountEmail);
        }
        catch (IOException ex)
        {
            Toast.makeText(SwMainActivity.this, "Failed to create account: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private class ContactListAdapter extends ArrayAdapter<Contact>
    {
        public ContactListAdapter()
        {
            super(SwMainActivity.this, R.layout.storeview_item, SoundWaveApplication.getApplicationObject().soundWaveConfig.mContactList);
        }

        @Override
        public View getView(int position, View view,ViewGroup parent)
        {
            if (view ==null)
                view = getLayoutInflater().inflate(R.layout.storeview_item, parent, false);

            view.setBackgroundColor(Color.LTGRAY);

            final Contact currentContact = SoundWaveApplication.getApplicationObject().soundWaveConfig.mContactList.get(position);

            TextView name = (TextView) view.findViewById(R.id.contactName);
            name.setText(currentContact.getName());
            TextView email = (TextView) view.findViewById(R.id.emailAddress);
            email.setText(currentContact.getEmail());
            LinearLayout layoutRecord = (LinearLayout) view.findViewById(R.id.layoutRecord);
            LinearLayout layoutPlay = (LinearLayout) view.findViewById(R.id.layoutPlay);

            layoutPlay.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    SoundWaveApplication.getApplicationObject().soundWaveController.playMessage(currentContact.getmCountactId());
                }
            });

            layoutRecord.setOnTouchListener(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    boolean ret = false;
                    switch (event.getActionMasked())
                    {
                        case MotionEvent.ACTION_DOWN:
                            if (SoundWaveApplication.getApplicationObject().soundWaveController.getTransmit() == false)
                                SoundWaveApplication.getApplicationObject().soundWaveController.setTransmit(true);

                            v.setBackgroundColor(Color.BLUE);
                            ret = true;
                            break;
                        case MotionEvent.ACTION_UP:
                            SoundWaveApplication.getApplicationObject().soundWaveController.setTransmit(false);
                            SoundWaveApplication.getApplicationObject().soundWaveController.send(currentContact.getmCountactId());
                            v.setBackgroundColor(Color.LTGRAY);
                            break;
                    }

                    return ret;
                }
            });

            return view;
        }
    }
        @Override
        public boolean onCreateOptionsMenu(Menu menu)
        {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item)
        {
            int id = item.getItemId();
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

    }
