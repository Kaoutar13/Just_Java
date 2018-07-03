package com.example.android.just_java;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCream = findViewById(R.id.checkBox);
        Boolean hasCream = whippedCream.isChecked();
        //Log.i("MainActivity.java",hasCream.toString());


        //get the name of the user
        EditText name = findViewById(R.id.name);
        String userName = name.getText().toString();

        CheckBox chocolate = findViewById(R.id.chocolate_checkbox);
        Boolean hasChocolate = chocolate.isChecked();

        int price = calculatePrice(hasCream, hasChocolate);
//        String priceMessage = price + " dollars for " + quantity + " cups of coffee. Pay up.";
//        priceMessage += "\n Thank you !";

        String msg = createOrderSummary(price, hasCream, hasChocolate, userName);
        //displayMessage(msg);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this

        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.just_java_order) + userName);
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }


    private int calculatePrice(boolean addCream, boolean addChocolate) {
        int basePrice = 5;

        if (addCream) {
            basePrice += 1;
        }

        if (addChocolate) {
            basePrice += 2;
        }

        return basePrice * quantity;

    }

    private String createOrderSummary(int priceOfCoffee, boolean hasCream, boolean hasChocolate, String name) {
        String toppingCream = " NO Thanks", toppingChocolate = " NO Thanks";
        if (hasCream){
          toppingCream = " SURE ;)";
        }
        if (hasChocolate){
             toppingChocolate = " WHY NOT ^_^";
        }


        String order = getString(R.string.order_summary_name,name);
        order += "\n" + getString(R.string.add_whipped_cream) + toppingCream;
        order += "\n" + getString(R.string.add_chocolate) + toppingChocolate;
        order += "\n"+ getString(R.string.quantity) + " : " + quantity + "\n" + getString(R.string.total) + " " + priceOfCoffee;
        order += "\n" + getString(R.string.thank_you);

        return order;


    }


    /**
     * This method is to increment the quantity
     */
    public void increment(View view) {
        if (quantity < 100) {
            quantity++;
        } else {
            Context context = getApplicationContext();
            CharSequence text = getString(R.string.many_cups);
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        displayQuantity(quantity);


    }

    /**
     * This is to decrement the quantity
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Context context = getApplicationContext();
            CharSequence text = getString(R.string.few_cups);
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        quantity--;
        displayQuantity(quantity);


    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int num) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + num);
    }


    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}