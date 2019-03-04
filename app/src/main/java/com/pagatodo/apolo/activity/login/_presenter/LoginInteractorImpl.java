package com.pagatodo.apolo.activity.login._presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.pagatodo.apolo.App;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.ResetContraseniaUser;
import com.pagatodo.apolo.activity.login._presenter._interfaces.LoginInteractor;
import com.pagatodo.apolo.data.local.Preferences;
import com.pagatodo.apolo.data.model.webservice.request.IniciativasRequest;
import com.pagatodo.apolo.data.model.webservice.request.ResetContraseniaRequest;
import com.pagatodo.apolo.data.model.webservice.request.ValidaUserRequest;
import com.pagatodo.apolo.data.model.webservice.response.GetPromotersResponse;
import com.pagatodo.apolo.data.model.webservice.response.ResetContraseniaResponse;
import com.pagatodo.apolo.data.model.webservice.response.ValidateUserResponse;
import com.pagatodo.apolo.data.remote.BuildRequest;
import com.pagatodo.apolo.data.room.DatabaseManager;
import com.pagatodo.apolo.data.room.entities.Promotor;
import com.pagatodo.networkframework.DataManager;
import com.pagatodo.networkframework.interfaces.IRequestResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.pagatodo.apolo.data.remote.RequestContract.GET_PROMOTERS;
import static com.pagatodo.apolo.data.remote.RequestContract.POST_RESETEAPASS;
import static com.pagatodo.apolo.data.remote.RequestContract.POST_VALIDAUSER;
import static com.pagatodo.networkframework.UtilsNet.isOnline;
import static com.pagatodo.networkframework.model.ResponseConstants.RESPONSE_CODE_OK;


/**
 * Created by rvargas on 21-07-17.
 * Modified by cjimenez on 25/02/2019
 */

public class LoginInteractorImpl implements LoginInteractor, IRequestResult  { //le voy a quitar el IRequestResult
    Preferences prefs = App.getInstance().getPrefs();
    Context context = App.getInstance();
    onLoginListener listener;

    private Promotor validateUser(String passPromotor) {
        List<Promotor> promotores = new ArrayList<>();
        try {
            promotores = new DatabaseManager().getPromotorList();
        } catch (ExecutionException|InterruptedException e) {
            e.printStackTrace();
        }
        for (Promotor promotor : promotores) {
            if (promotor.getPromotor().equalsIgnoreCase(passPromotor)) {
                return promotor;
            }
        }
        return null;
    }

    @Override
    public void onLogin(String username, onLoginListener listener) {
        //Promotor promotor = validateUser(username);
        if (username.isEmpty() ) {
            listener.onUserNumberError();
            //listener.onUserPassError();
        }



        /* else if (promotor != null) {
            listener.onSuccess(promotor);
        } else {
            listener.failure(context.getString(R.string.error_invalid_id));
        }*/
    }

    @Override
    public void onLoginNewI(String username, String pass, String imei, onLoginListener listener) {
        if (isOnline(this.context)) {
                BuildRequest.validateUserRequest(this, new ValidaUserRequest(username, pass, imei));
                this.listener = listener;
        }


    }

    @Override
    public void onChangePass(int idPromo, String pass, String imei, boolean resetcontr, String user, onLoginListener listener) {
        if (isOnline(this.context)) {
            BuildRequest.RessetContraseniaRequest(this, new ResetContraseniaRequest(idPromo, pass, imei,resetcontr,user));
            this.listener = listener;
        }
    }



    @Override
    public void onSuccess(DataManager dataManager) {

        if (dataManager.getData() != null) {
            switch (dataManager.getMethod()) {
                case POST_VALIDAUSER:
                    prosesgetValidateUserResponse( (ValidateUserResponse) dataManager.getData());
                break;
                case POST_RESETEAPASS:
                    prosesResetPassUserResponse( (ResetContraseniaResponse) dataManager.getData());
                break;

            }
        }


        /*
        if (dataManager.getData() != null) {
            switch (dataManager.getMethod()) {
                case POST_VALIDAUSER:

                    break;
                //case POST_RESETEAPASS:

                  //  prosesgetValidateUserResponse((ResetContraseniaResponse) dataManager.getData());
                   // break;
            }
        }*/

    }

    private void prosesgetValidateUserResponse(ValidateUserResponse data) {
        ValidateUserResponse element = (ValidateUserResponse)data;

        if (element.getCodigo()!=0  ){

            if (listener!=null)
                listener.failure(element.getMensaje());
        }else {

            if (element.getPromotor().isResetContraseña()) { //aqui es si reset contraseña en 0, se cambia para evaluar el otro servicio

            listener.onresetPass(element.getPromotor().getID_Promotor(),element.getPromotor().getPromotor());

            }else {
                /***
                 * Go to main menu with session
                  */
                 listener.onSuccess(element.getPromotor());
            }

        }


    }

    private void prosesResetPassUserResponse(ResetContraseniaResponse data) {
      //  ResetContraseniaResponse elementa = (ResetContraseniaResponse)data;


        int codigo = data.getCodigo();

        if (codigo==0){
            listener.failure(data.getMensaje());


        }else {
            listener.failure(data.getMensaje());
        }



        /*if (elementa.getCodigo()!=0  ){

            if (listener!=null)
                listener.failure(elementa.getMensaje());
        }else {

        }*/


    }

    @Override
    public void onFailed(DataManager dataManager) {
        if (listener!=null)
        listener.onServerError();



    }
}
