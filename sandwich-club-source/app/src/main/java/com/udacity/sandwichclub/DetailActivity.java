package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    ImageView ingredientsIv;
    TextView aka,ingredients,poo,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        initComponent();

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void initComponent(){
        aka = (TextView)findViewById(R.id.also_known_tv);
        ingredients = (TextView)findViewById(R.id.ingredients_tv);
        poo = (TextView)findViewById(R.id.origin_tv);
        description = (TextView)findViewById(R.id.description_tv);
    }

    private void populateUI(Sandwich sandwich) {

        poo.setText(sandwich.getPlaceOfOrigin());
        description.setText(sandwich.getDescription());

        //aka
        if (sandwich.getAlsoKnownAs().size()>1) {
            String val="";

            for (String s : sandwich.getAlsoKnownAs())
                val = val.concat(s + ", ");

            aka.setText(val);
            String a = aka.getText().toString().trim();

            //remove last comma
            aka.setText(a.substring(0,a.length()-1));
        }
        else
            aka.setText("N/A");

        //ingredients
        if (sandwich.getIngredients().size()>1) {
            String val="";

            for (String s : sandwich.getIngredients())
                val = val.concat(s + ", ");

            ingredients.setText(val);

            String a = ingredients.getText().toString().trim();

            //remove last comma
            ingredients.setText(a.substring(0,a.length()-1));
        }
        else
            ingredients.setText("N/A");
    }
}
