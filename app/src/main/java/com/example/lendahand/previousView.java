package com.example.lendahand;

public class previousView {
    static Class<?> prevView;

    public static void setPrevView(Class<?> className){
        prevView = className;
    }

    public static Class<?> getPrevView(){
        return prevView;
    }

}
