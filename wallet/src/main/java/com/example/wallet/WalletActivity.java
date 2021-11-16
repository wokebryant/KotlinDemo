package com.example.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class WalletActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
    }

    public void onStartBitcoinWallet(View view) {
        Intent intent = new Intent(this, BitcoinWalletActivity.class);
        startActivity(intent);
    }

    public void onstartEthWallet(View view) {
        Intent intent = new Intent(this, EthereumWalletActivity.class);
        startActivity(intent);
    }
}
