package com.pagatodo.apolo.activity.login._presenter._interfaces;


import com.pagatodo.apolo.data.room.entities.Promotor;

/**
 * Created by rvargas on 21-07-17.
 *Modified by cjimenez on 25/02/2019.
 */

public interface LoginInteractor {

    interface onLoginListener{
        void onSuccess(Promotor promotor);
        void failure(String message);
        void onUserNumberError();
        void onServerError();
        void onPasswordError();
        void onUserPassError();

        void onresetPass(int ID_Promotor, String usuario);
    }
    void onLogin(String username, onLoginListener listener);
    void onLoginNewI(String username,String pass,String imei, onLoginListener listener);
    void onChangePass(int idPromo, String pass, String imei, int resetcontr, String user, onLoginListener listener);
}
