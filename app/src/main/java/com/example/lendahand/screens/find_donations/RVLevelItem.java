package com.example.lendahand.screens.find_donations;

import com.example.lendahand.helpers.ItemCategory;

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
