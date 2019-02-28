package com.pagatodo.apolo.activity.login._presenter._interfaces;


import com.pagatodo.apolo.data.room.entities.Promotor;

/**
 * Created by rvargas on 21-07-17.
 */

public interface LoginInteractor {

    interface onLoginListener{
        void onSuccess(Promotor promotor);
        void failure(String message);
        void onUserNumberError();
        void onServerError();
        void onresetPass( String usuario, int ResetContrasenia, int ID_Promotor);
        void onPasswordError();
        void onUserPassError();
    }
    void onLogin(String username, onLoginListener listener);
    void onLoginNewI(String username,String pass,String imei, onLoginListener listener);
}
