package com.example.bankingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Detail extends AppCompatActivity {

    final Context context = this;
    String Name="Not Exist";
    String Email="Not Exist";
    String Account_Number="Not Exist";
    String Phone_Number="Not Exist";
    private int Balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle extras = getIntent().getExtras();

        if(extras!=null) {
            Name = extras.getString("name");
            Email = extras.getString("email");
            Balance = extras.getInt("balance", -1);
            Account_Number = extras.getString("account");
            Phone_Number = extras.getString("phone");

            TextView txt_name = (TextView) findViewById(R.id.txtName1);
            txt_name.setText(Name);

            TextView txt_email = (TextView) findViewById(R.id.txtEmail1);
            txt_email.setText(Email);

            TextView txt_balance = (TextView) findViewById(R.id.txtBalance1);
            txt_balance.setText("" + Balance);

            TextView txt_account = (TextView) findViewById(R.id.txtAccNo1);
            txt_account.setText(Account_Number);

            TextView txt_phone = (TextView) findViewById(R.id.txtContact1);
            txt_phone.setText(Phone_Number);


        }
        Button transfer_btn = (Button)findViewById(R.id.btnTransfer);
        transfer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterAmount();
            }
        });

    }
    private void enterAmount(){
        LayoutInflater li = LayoutInflater.from(context);
        View promptView = li.inflate(R.layout.prompts,null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptView);

        final EditText userInput = (EditText)promptView.findViewById(R.id.editTextDialogBox);

        alertDialogBuilder.
                setCancelable(false).
                setPositiveButton("SEND", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //transaction history m edit
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //transaction history m
                dialog.dismiss();
                cancelTransaction();
            }
        });

        AlertDialog alertdialog = alertDialogBuilder.create();
        alertdialog.show();
        alertdialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  InputNumber=userInput.getText().toString();

                int FinalValue = 0;
                try {
                    FinalValue = Integer.parseInt(InputNumber);
                } catch (NumberFormatException nfe){
                    nfe.printStackTrace();
                }
                if(InputNumber.length()>9)
                    FinalValue = Integer.MAX_VALUE;

                if(TextUtils.isEmpty(InputNumber)) {
                    Toast toast=Toast. makeText(getApplicationContext(),"Amount Can't be Empty",Toast. LENGTH_SHORT);
                    toast. show();
                }
                else if(FinalValue >  Balance ){
                    Toast toast=Toast. makeText(getApplicationContext(),"Your Account Don't Have Sufficient Balance",Toast. LENGTH_SHORT);
                    toast. show();
                }
                else {
                    Toast toast=Toast. makeText(getApplicationContext(),"Proceeding",Toast. LENGTH_SHORT);
                    toast. show();
                    Intent intent = new Intent(Detail.this,RecieverList.class);
                    intent.putExtra("transfer amount", FinalValue);
                    intent.putExtra("name",Name);
                    intent.putExtra("email",Email);
                    intent.putExtra("Current Balance",Balance);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
    private void cancelTransaction(){
        AlertDialog.Builder builder_exitbutton = new AlertDialog.Builder(Detail.this);
        builder_exitbutton.setTitle("Do you want to cancel the transaction?").setCancelable(false)
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new DatabaseHelper(Detail.this).insertTransferData(Name, "Not selected", 0, "FAILED");
                        Toast.makeText(Detail.this, "Transaction Cancelled!", Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                enterAmount();
            }
        });
        AlertDialog alert_exit = builder_exitbutton.create();
        alert_exit.show();
    }
}