package com.pagatodo.apolo.activity.login._presenter._interfaces;

import com.pagatodo.apolo.ui.base.factoryinterfaces.IEventOnView;

/**
 * Created by rvargas on 21-07-17.
 */

public interface LoginView extends IEventOnView{
    void setUserNumberError();
    void setServerError();
    void setresetPass(String usuario, int ResetContrasenia, int ID_Promotor);
    void setPassError();
    void setUserPassError();
    void setNavigation();
    void showMessage(String message);
}
