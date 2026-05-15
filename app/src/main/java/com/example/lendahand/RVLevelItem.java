package com.example.lendahand;

import android.content.ClipData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class RVLevelItem {
    public ItemCategory[] subcategories;

    public RVLevelItem(){

    }

     public void setSubcategories(ItemCategory[] subcategories){
        this.subcategories = subcategories;
    }

    public void initialSet(){
         setSubcategories(ItemCategory.getRoots());
    }
}
