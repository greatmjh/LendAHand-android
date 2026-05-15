package com.example.lendahand.helpers;

public class PreviousView {
    static Class<?> prevView;

    public static void setPrevView(Class<?> className){
        prevView = className;
    }

    public static Class<?> getPrevView(){
        return prevView;
    }

}
