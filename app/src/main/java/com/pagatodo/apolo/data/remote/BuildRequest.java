package com.pagatodo.apolo.data.remote;

import android.util.Log;

import com.pagatodo.apolo.App;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.CheckIDP._presenter.IdpInteractorImpl;
import com.pagatodo.apolo.data.model.factory.Respuesta;
import com.pagatodo.apolo.data.model.webservice.remoteconfig.ResponseRemoteConfig;
import com.pagatodo.apolo.data.model.factory.ModelPattern;
import com.pagatodo.apolo.data.model.webservice.request.CheckIdpRequest;
import com.pagatodo.apolo.data.model.webservice.request.CreditRequestRegisterRequest;
import com.pagatodo.apolo.data.model.webservice.request.DocumentUploadRequest;
import com.pagatodo.apolo.data.model.webservice.request.IniciativasRequest;
import com.pagatodo.apolo.data.model.webservice.request.SMSCodeValidationRequest;
import com.pagatodo.apolo.data.model.webservice.request.SMSValidationRequest;
import com.pagatodo.apolo.data.model.webservice.request.TiendasRequest;
import com.pagatodo.apolo.data.model.webservice.request.ValidaUserRequest;
import com.pagatodo.apolo.data.model.webservice.response.CheckIdpResponse;
import com.pagatodo.apolo.data.model.webservice.response.CreditRequestRegisterResponse;
import com.pagatodo.apolo.data.model.webservice.response.GeneralServiceResponse;
import com.pagatodo.apolo.data.model.webservice.response.GetIniciativasResponse;
import com.pagatodo.apolo.data.model.webservice.response.GetPromotersResponse;
import com.pagatodo.apolo.data.model.webservice.response.GetTiendasResponse;
import com.pagatodo.apolo.data.remote.notifications.model.webservice.mega.GetPushStatus;
import com.pagatodo.apolo.data.remote.notifications.model.webservice.mega.MegaBaseModel;
import com.pagatodo.apolo.data.remote.notifications.model.webservice.requestinbox.RequestGetInbox;
import com.pagatodo.apolo.data.remote.notifications.model.webservice.requestregistrodispositivo.RequestRegistroDispositivo;
import com.pagatodo.apolo.data.remote.notifications.model.webservice.requestregistrousuario.RequestRegistroUsuario;
import com.pagatodo.apolo.data.remote.notifications.model.webservice.response.ResponseInbox;
import com.pagatodo.apolo.data.remote.notifications.model.webservice.response.ResponseRegistroDispositivo;
import com.pagatodo.apolo.data.remote.notifications.model.webservice.response.ResponseRegistroUsuario;
import com.pagatodo.apolo.data.remote.notifications.model.webservice.updateState.RequestUpdateStatus;
import com.pagatodo.networkframework.RequestBuilder;
import com.pagatodo.networkframework.interfaces.IRequestResult;

import java.util.HashMap;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;
import static com.pagatodo.apolo.data.remote.RequestContract.CHECK_IDP;
import static com.pagatodo.apolo.data.remote.RequestContract.DOCUMENT_UPLOAD;
import static com.pagatodo.apolo.data.remote.RequestContract.DO_CREDIT_REQUEST_REGISTER;
import static com.pagatodo.apolo.data.remote.RequestContract.GET_INICIATIVAS;
import static com.pagatodo.apolo.data.remote.RequestContract.GET_PROMOTERS;
import static com.pagatodo.apolo.data.remote.RequestContract.GET_REMOTE_CONFIG;
import static com.pagatodo.apolo.data.remote.RequestContract.GET_TIENDAS;
import static com.pagatodo.apolo.data.remote.RequestContract.MEGA_ACTIVATE_PUSH;
import static com.pagatodo.apolo.data.remote.RequestContract.MEGA_GET_PUSH_STATUS;
import static com.pagatodo.apolo.data.remote.RequestContract.MEGA_OBTENER_MSJ_INBOX;
import static com.pagatodo.apolo.data.remote.RequestContract.MEGA_REGISTER_DEVICE;
import static com.pagatodo.apolo.data.remote.RequestContract.MEGA_REGISTER_USER;
import static com.pagatodo.apolo.data.remote.RequestContract.POST_VALIDAUSER;
import static com.pagatodo.apolo.data.remote.RequestContract.SEND_SMS_CONFIRMATION;
import static com.pagatodo.apolo.data.remote.RequestContract.SMS_CODE_VALIDATION;
import static com.pagatodo.apolo.utils.Constants.DEBUG;
import static com.pagatodo.apolo.utils.Constants.TIMEOUT;

/**
 * Created by jvazquez on 26/07/2017.
 */

public class BuildRequest {
    private static final boolean LOG_DEBUG = DEBUG;

    //  REMOTE CONFIG
    public static void updateRemoteConfig(IRequestResult result) {
        new RequestBuilder().request(App.getInstance(),
                LOG_DEBUG,
                GET_REMOTE_CONFIG,
                GET,
                null,
                RemoteConfig.getUrlRemoteConfig(),
                null,
                result,
                TIMEOUT,
                ResponseRemoteConfig.class);
    }

    //    REQUEST MEGA
    public static void registerDeviceMega(HashMap<String, String> headers, RequestRegistroDispositivo request, IRequestResult result) {
        new RequestBuilder().request(App.getInstance(),
                LOG_DEBUG,
                MEGA_REGISTER_DEVICE,
                POST,
                headers,
                RemoteConfig.getUrlMega() + MEGA_REGISTER_DEVICE,
                request,
                result,
                TIMEOUT,
                ResponseRegistroDispositivo.class);
    }

    public static void registerUserMega(HashMap<String, String> headers, RequestRegistroUsuario request, IRequestResult result) {
        new RequestBuilder().request(App.getInstance(),
                LOG_DEBUG,
                MEGA_REGISTER_USER,
                POST,
                headers,
                RemoteConfig.getUrlMega() + MEGA_REGISTER_USER,
                request,
                result,
                TIMEOUT,
                ResponseRegistroUsuario.class);
    }

    public static void requestGetInboxs(HashMap<String, String> headers, RequestGetInbox request, IRequestResult result) {
        new RequestBuilder().request(App.getInstance(),
                LOG_DEBUG,
                MEGA_OBTENER_MSJ_INBOX,
                POST,
                headers,
                RemoteConfig.getUrlMega() + MEGA_OBTENER_MSJ_INBOX,
                request,
                result,
                TIMEOUT,
                ResponseInbox.class);
    }

    public static void getPushNotificationStatus(HashMap<String, String> headers, IRequestResult result) {
        new RequestBuilder().request(App.getInstance(),
                LOG_DEBUG,
                MEGA_GET_PUSH_STATUS,
                POST,
                headers,
                RemoteConfig.getUrlMega() + MEGA_GET_PUSH_STATUS,
                "",
                result,
                TIMEOUT,
                GetPushStatus.class);
    }

    public static void requestUpdatePushIndicator(HashMap<String, String> headers, RequestUpdateStatus request, IRequestResult result) {
        new RequestBuilder().request(App.getInstance(),
                LOG_DEBUG,
                MEGA_ACTIVATE_PUSH,
                POST,
                headers,
                RemoteConfig.getUrlMega() + MEGA_ACTIVATE_PUSH,
                request,
                result,
                TIMEOUT,
                MegaBaseModel.class);
    }

    public static void getPromotersRequest(IRequestResult result) {
        new RequestBuilder().requestPinning(App.getInstance(),
                R.raw.pagatodo,
                LOG_DEBUG,
                GET_PROMOTERS,
                POST,
                null,
                RemoteConfig.getUrlServer() + GET_PROMOTERS,
                new ModelPattern() {
                    @Override
                    public String toString() {
                        return super.toString();
                    }
                },
                result,
                TIMEOUT,
                GetPromotersResponse.class);
    }

    public static void getIniciativasRequest(IRequestResult result, IniciativasRequest body) {
        new RequestBuilder().requestPinning(App.getInstance(),
                R.raw.pagatodo,
                LOG_DEBUG,
                GET_INICIATIVAS,
                POST,
                null,
                RemoteConfig.getUrlServer() + GET_INICIATIVAS,
                body,
                result,
                TIMEOUT,
                GetIniciativasResponse.class);
    }

    public static void validateUserRequest(IRequestResult result, ValidaUserRequest body) {
        new RequestBuilder().requestPinning(App.getInstance(),
                R.raw.pagatodo,
                LOG_DEBUG,
                POST_VALIDAUSER,
                POST,
                null,
                RemoteConfig.getUrlServer() + POST_VALIDAUSER,
                body,
                result,
                TIMEOUT,
                GetIniciativasResponse.class);
    }

    public static void getTiendasRequest(IRequestResult result, TiendasRequest body) {
        new RequestBuilder().requestPinning(App.getInstance(),
                R.raw.pagatodo,
                LOG_DEBUG,
                GET_TIENDAS,
                POST,
                null,
                RemoteConfig.getUrlServer() + GET_TIENDAS,
                body,
                result,
                TIMEOUT,
                GetTiendasResponse.class);
    }

    public static void doCreditRequestRegister(IRequestResult result, CreditRequestRegisterRequest body, HashMap<String, String> headers) {
        new RequestBuilder().requestPinning(App.getInstance(),
                R.raw.pagatodo,
                LOG_DEBUG,
                DO_CREDIT_REQUEST_REGISTER,
                POST,
                headers,
                RemoteConfig.getUrlServer() + DO_CREDIT_REQUEST_REGISTER,
                body,
                result,
                TIMEOUT,
                CreditRequestRegisterResponse.class);
    }

    public static void doDocumentUpload(IRequestResult result, DocumentUploadRequest body, HashMap<String, String> headers) {
        new RequestBuilder().requestPinning(App.getInstance(),
                R.raw.pagatodo,
                LOG_DEBUG,
                DOCUMENT_UPLOAD,
                POST,
                headers,
                RemoteConfig.getUrlServer() + DOCUMENT_UPLOAD,
                body,
                result,
                TIMEOUT,
                GeneralServiceResponse.class);
    }

    public static void sendCheckIdp(IdpInteractorImpl result, CheckIdpRequest body, HashMap<String, String> headers) {
        new RequestBuilder().requestPinning(App.getInstance(),
                R.raw.pagatodo,
                LOG_DEBUG,
                CHECK_IDP,
                POST,
                headers,
                RemoteConfig.getUrlServer() + CHECK_IDP,
                body,
                result,
                TIMEOUT,
                CheckIdpResponse.class);
    }

    public static void sendSMSConfirmation(IRequestResult result, SMSValidationRequest body, HashMap<String, String> headers) {
        new RequestBuilder().requestPinning(App.getInstance(),
                R.raw.pagatodo,
                LOG_DEBUG,
                SEND_SMS_CONFIRMATION,
                POST,
                headers,
                RemoteConfig.getUrlServer() + SEND_SMS_CONFIRMATION,
                body,
                result,
                TIMEOUT,
                GeneralServiceResponse.class);
    }

    public static void sendSMSCodeValidation(IRequestResult result, SMSCodeValidationRequest body, HashMap<String, String> headers) {
        new RequestBuilder().requestPinning(App.getInstance(),
                R.raw.pagatodo,
                LOG_DEBUG,
                SMS_CODE_VALIDATION,
                POST,
                headers,
                RemoteConfig.getUrlServer() + SMS_CODE_VALIDATION,
                body,
                result,
                TIMEOUT,
                GeneralServiceResponse.class);
    }
}
