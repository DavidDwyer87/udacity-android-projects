package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    static Sandwich sandwich = new Sandwich();
    public static Sandwich parseSandwichJson(String json) {
        Log.i("JsonUtils",""+getKey(json,4));
        Log.i("JsonUtils",""+json);

        sandwich.setMainName(getMainName(json));
        sandwich.setAlsoKnownAs(getAKA(json));
        sandwich.setIngredients(getIngredients(json));
        sandwich.setDescription(getDescription(json));
        sandwich.setImage("https:"+getImage(json));
        sandwich.setPlaceOfOrigin(getOrigin(json));
        return sandwich;
    }

    private static String getKey(String json,int position){
        String[] name = json.split(":");
        return name[position]
                .replace("{","")
                .replace("}","")
                .replace("\"","");
    }

    private static String getValue(String json,int position){

        return json.split(",")[position];
    }

    private static String getMainName(String json){
        String key = getKey(json,2);
        String value = getValue(key,0);
        return value;
    }

    private static String getDescription(String json){
        String key = getKey(json,5);

        return key.replace(",image","").replace("\\","'");
    }

    private static String getImage(String json){
        return getKey(json,7).replace(",ingredients","");
    }

    private static String getOrigin(String json){
        return getKey(json,4).replace(",description","");
    }

    private static ArrayList<String> getAKA(String json){
        String key = getKey(json,3);
        String[] value = key
                .replace("[","")
                .replace("]","")
                .split(",");
        ArrayList<String> list = new ArrayList<>();

        if (value.length>0 ) {
            for (String s : value) {
                list.add(s);
                //Log.i("JsonUtils", "" + s);
            }
            list.remove(list.size()-1);
        }
        return list ;
    }

    private static ArrayList<String> getIngredients(String json){
        String key = getKey(json,8);
        String[] value = key
                .replace("[","")
                .replace("]","")
                .split(",");
        ArrayList<String> list = new ArrayList<>();

        if (value.length>0 ) {
            for (String s : value) {
                list.add(s);
                //Log.i("JsonUtils", "" + s);
            }
            list.remove(list.size()-1);
        }
        return list ;
    }

}
