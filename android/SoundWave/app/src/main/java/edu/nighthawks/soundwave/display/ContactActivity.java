package edu.nighthawks.soundwave.display;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.nighthawks.soundwave.app.Contact;
import edu.nighthawks.soundwave.app.SoundWaveApplication;
import edu.nighthawks.soundwave.soundwave.R;

public class ContactActivity extends AppCompatActivity
{
    EditText nameTxt, phoneTxt, emailTxt, addressTxt;
    ImageView contactImageimgView;
    List<Contact> Contacts = new ArrayList<Contact>();
    ListView contactListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        nameTxt = (EditText) findViewById(R.id.txtName);
        emailTxt = (EditText) findViewById(R.id.txtEmail);
        contactListView = (ListView) findViewById(R.id.listView);
        contactImageimgView = (ImageView) findViewById(R.id.imgContactView);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);


        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("creator");
        tabSpec.setContent(R.id.Creator);
        tabSpec.setIndicator("Creator");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("storage");
        tabSpec.setContent(R.id.Storage);
        tabSpec.setIndicator("Storage");
        tabHost.addTab(tabSpec);

        final Button addBtn = (Button) findViewById(R.id.btnAdd);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContact(nameTxt.getText().toString(), emailTxt.getText().toString());
                populateList();
                Toast.makeText(getApplicationContext(), nameTxt.getText().toString() + " has been added to your contacts!", Toast.LENGTH_SHORT). show();
            }
        });

        nameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                addBtn.setEnabled(!nameTxt.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

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

    }

    public void onActivityResult(int reqCode, int resCode, Intent data)
    {
       if (resCode == RESULT_OK)
       {
           if (reqCode == 1)
               contactImageimgView.setImageURI(data.getData());
       }
    }

    private void populateList()
    {
        ArrayAdapter<Contact> adapter = new ContactListAdapter();
        contactListView.setAdapter(adapter);
    }

    private void  addContact(String name, String email)
    {
        Contacts.add(new Contact(name, email));
        SoundWaveApplication.getApplicationObject().soundWaveController.createContact(name, "Password123", email);
    }

    private class ContactListAdapter extends ArrayAdapter<Contact>
    {
        public ContactListAdapter()
        {
            super (ContactActivity.this, R.layout.storeview_item, Contacts);
        }

        @Override
        public View getView(int position, View view,ViewGroup parent)
        {
            if (view ==null)
                view = getLayoutInflater().inflate(R.layout.storeview_item, parent, false);

            Contact currentContact = Contacts.get(position);

            TextView name = (TextView) view.findViewById(R.id.contactName);
            name.setText(currentContact.getName());
            TextView email = (TextView) view.findViewById(R.id.emailAddress);
            email.setText(currentContact.getEmail());

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
