package com.software.team2.footprint;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioButton;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RadioGroup;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.view.Menu;
import android.widget.DatePicker;

import org.w3c.dom.Text;


public class Transaction extends AppCompatActivity implements View.OnClickListener {
    DatabaseHelper db;
    EditText mTextStockName;
    EditText mTextStockSymbol;
    EditText mTextPrice;
    EditText mTextTotalShares;
    EditText mTextTotalMoney;
    EditText mTextDate;
    EditText mTextPurchasedPrice;
    TextView alert_for_purchase;
    TextView mTextOr;
    Button mButtonRecord;
    DatePickerDialog fromDatePickerDialog;
    SimpleDateFormat dateFormatter;
    String transaction_type;
    Calendar newDate;
    Button home_button;
    Button search;
    private static final String TAG = "MyActivity";
    String final_date;
    private static DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        db = new DatabaseHelper(this);
        mTextStockName = (EditText)findViewById(R.id.edittext_stock_name);
        mTextStockSymbol = (EditText)findViewById(R.id.edittext_stock_symbol);
        mTextPrice = (EditText)findViewById(R.id.edittext_price);
        mTextTotalShares = (EditText)findViewById(R.id.edittext_shares);
        mTextTotalMoney =(EditText) findViewById(R.id.edittext_total_money);
        mButtonRecord = (Button)findViewById(R.id.button_t_submit);
        alert_for_purchase = (TextView)findViewById(R.id.alert_for_purchase);
        mTextPurchasedPrice =(EditText) findViewById(R.id.edittext_purchasedPrice_each_stock);
        mTextOr = (TextView)findViewById(R.id.textView_or);
        home_button = findViewById(R.id.home_button);
        search = findViewById(R.id.search);
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.RGroup);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                Log.i(TAG, "inside ");
                if(checkedId==R.id.button_bought)
                    transaction_type="B";
                else if(checkedId==R.id.button_sold) {
                    transaction_type = "S";


                }
                else
                    transaction_type="null";
                Log.i("Transaction    ",transaction_type);

            }
        });

        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        findViewsById();
        setDateTimeField();

        // Log.i(TAG, "DATEEEE   "+final_date);
        mButtonRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stock_name = mTextStockName.getText().toString().trim();
                String stock_symbol = mTextStockSymbol.getText().toString().trim();

                float price=0;
                float total_money=0;
                int total_shares=0;
                float purchased_price_for_each=0;

                String temp_price= mTextPrice.getText().toString().trim();
                if((temp_price != null && !temp_price.isEmpty())) {
                    try {
                        price = Float.parseFloat(temp_price);

                        Log.i(TAG, "price   "+price);
                    } catch (NumberFormatException e) {
                       price=0;
                    }
                }

                String temp_shares=mTextTotalShares.getText().toString().trim();
                String temp_money=mTextTotalMoney.getText().toString().trim();
                Log.i(TAG, "TEMP_SHARES   "+temp_shares);
                Log.i(TAG, "TEMP_MONEY   "+temp_money);
                if((temp_shares != null && !temp_shares.isEmpty())) {
                    try {
                            total_shares = Integer.parseInt(temp_shares);
                            total_money = total_shares * price;
                        Log.i(TAG, "TEMP_SHARES   "+total_shares);

                    } catch (NumberFormatException e) {
                        total_shares=0;
                    }
                }
                if((temp_money != null && !temp_money.isEmpty())) {
                    try {
                            total_money = Float.parseFloat(mTextTotalMoney.getText().toString().trim());
                            total_shares = (int) (total_money/price);
                        Log.i(TAG, "TEMP_MONEY   "+total_money);
                    } catch (NumberFormatException e) {
                        total_money=0;
                    }
                }
                String purchased_price = mTextPurchasedPrice.getText().toString().trim();
                Log.i("price for ",purchased_price);
                if((purchased_price != null && !purchased_price.isEmpty()) && transaction_type.equals("S")) {
                    try {
                            purchased_price_for_each = Float.parseFloat(mTextPurchasedPrice .getText().toString().trim());
                        Log.i(TAG, "TEMP_PURCHASE   "+purchased_price_for_each);
                    } catch (NumberFormatException e) {
                        purchased_price_for_each=0;
                    }
                }
                df.format(price);
                df.format(purchased_price_for_each);
                df.format(total_money);
                long result=0;
                stock_symbol =  stock_symbol.toUpperCase();

                if((stock_name != null && !stock_name.isEmpty()))
                {
                    if((stock_symbol != null && !stock_symbol.isEmpty())) {

                        if(price>0)
                        {
                            if(total_money>0 && total_shares>0)
                            {
                                if(transaction_type!=null)
                                {
                                    Log.i("transaction tyepepee","dmfisfiejfi");
                                        if(transaction_type.equals("S")) {
                                            if(purchased_price_for_each>0) {
                                                Log.i("inside s","dmfisfiejfi");
                                                result = db.record_transaction(db.transaction_user_key(), stock_name, stock_symbol, price, total_shares, total_money, transaction_type, final_date, purchased_price_for_each);
                                                if (result > 0) {
                                                    Toast.makeText(Transaction.this, "Record submitted successfully", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(Transaction.this, "Record failed, Try again!", Toast.LENGTH_SHORT).show();
                                                    Intent LoginIntent = new Intent(Transaction.this, Transaction.class);
                                                    startActivity(LoginIntent);
                                                }
                                            }else
                                            {
                                                alert_for_purchase.setError(" ");
                                                Toast.makeText(Transaction.this, "Please enter the purchase price!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                          else  if((transaction_type.equals("B")))
                                        {
                                            if( purchased_price_for_each==0) {
                                                result = db.record_transaction(db.transaction_user_key(), stock_name, stock_symbol, price, total_shares, total_money, transaction_type, final_date,purchased_price_for_each);
                                                if (result > 0) {
                                                    Toast.makeText(Transaction.this, "Record submitted successfully", Toast.LENGTH_SHORT).show();
                                                    Intent LoginIntent = new Intent(Transaction.this, Transaction.class);
                                                    startActivity(LoginIntent);
                                                } else {
                                                    Toast.makeText(Transaction.this, "Record failed, Try again!", Toast.LENGTH_SHORT).show();
                                                    Intent LoginIntent = new Intent(Transaction.this, Transaction.class);
                                                    startActivity(LoginIntent);
                                                }
                                            }else
                                            {
                                                Toast.makeText(Transaction.this, "Do not enter purchase price for bought stocks", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                }
                                else
                                {

                                    Toast.makeText(Transaction.this, "Please select transaction type!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                mTextOr.setError("  ");
                                Toast.makeText(Transaction.this, "Total shares or Total_money entered not valid!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            mTextPrice.setError("Enter valid stock price");
                            //Toast.makeText(Transaction.this, "Price for each stock is not valid!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        mTextStockSymbol.setError("Enter stock symbol ");
                       // Toast.makeText(Transaction.this, "Please enter the stock symbol", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    mTextStockName.setError("Enter stock name ");
                    //Toast.makeText(Transaction.this, "Please enter the stock name", Toast.LENGTH_SHORT).show();
                }

            }
        });

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Transaction.this, home.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Transaction.this, SearchActivity.class);
                startActivity(intent);
            }
        });

    }

    private void findViewsById() {
        mTextDate = (EditText) findViewById(R.id.edittext_date);
        mTextDate.setInputType(InputType.TYPE_NULL);
        mTextDate.requestFocus();

    }

    private void setDateTimeField() {
        mTextDate.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                 newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                mTextDate.setText(dateFormatter.format(newDate.getTime()));
                final_date = mTextDate.getText().toString().trim();
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onClick(View view) {
        if (view == mTextDate) {
            fromDatePickerDialog.show();

        }
    }




}
