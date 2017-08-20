package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity, pricePerCup=5;
    String priceMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void increment(View view)
    {
        quantity++;
        if(quantity>100){
            quantity=100;
            Toast.makeText(this,getString(R.string.toastIncrement), Toast.LENGTH_SHORT).show();
        }
        display(quantity);


    }
    public void mailOrder(View view){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.orderSummary));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"abc@xyz.com"});
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage );
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    public void decrement(View view)
    {
        quantity--;
        if(quantity<1){
            quantity=1;
            Toast.makeText(this,getString(R.string.toastDecrement), Toast.LENGTH_SHORT).show();
        }

        display(quantity);

    }
    public void submitOrder(View view) {

        EditText nameField = (EditText) findViewById(R.id.name_text);
        String name = nameField.getText().toString();
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox Chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = Chocolate.isChecked();
        String chocolateString=getString(R.string.toppingNo), whippedCreamString=getString(R.string.toppingNo);
        int chocolate=0, whippedCream=0;
        if(hasChocolate)
        {
            chocolateString = getString(R.string.toppingYes);
            chocolate+=2;
        }
        if(hasWhippedCream)
        {
            whippedCreamString = getString(R.string.toppingYes);
            whippedCream+=1;
        }
        int price;
        price = calculatePrice(chocolate, whippedCream,quantity, pricePerCup);
        priceMessage = createOrderSummary(price, chocolateString, whippedCreamString, name);
        displayMessage(priceMessage);

    }

    private String createOrderSummary(int total, String chocolate, String whippedCream, String customer ){
        return getString(R.string.nameOS,customer)+
                "\n" + getString(R.string.whipOS)  +
                whippedCream+ "\n" +
                getString(R.string.chocolateOS)  +
                chocolate +"\n" +
                getString(R.string.quantityOS) +quantity+"\n"
                + getString(R.string.totalOS)
                + "\u20B9" +total+"\n" +
                getString(R.string.thankyouOS);
    }

    private int calculatePrice(int chocoTop, int whipTop,int quantity, int price){

        return (chocoTop+whipTop+price)*quantity;
    }
    public void displayMessage(String message){
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given price on the screen.
     */


}