package com.example.wallet;

import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class KeyStoreActivity extends AppCompatActivity {

    private EditText mKeyStoreEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keystore);
        mKeyStoreEdit = findViewById(R.id.key_store);

        String keystore = getIntent().getStringExtra("keystore");
        mKeyStoreEdit.setText(keystore);

    }
}
