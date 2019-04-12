package com.pagatodo.apolo.activity.splash._presenter;

import android.util.Log;

import com.pagatodo.apolo.App;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.splash._presenter._interfaces.ISplashPresenter;
import com.pagatodo.apolo.activity.splash._presenter._interfaces.ISplashView;
import com.pagatodo.apolo.data.local.Preferences;
import com.pagatodo.apolo.data.model.factory.RequestImage;
import com.pagatodo.apolo.data.model.factory.request;
import com.pagatodo.apolo.data.model.webservice.remoteconfig.ResponseRemoteConfig;
import com.pagatodo.apolo.data.model.webservice.request.IniciativasRequest;
import com.pagatodo.apolo.data.model.webservice.request.InsertDomiPagoRequest;
import com.pagatodo.apolo.data.model.webservice.request.LoadImageRequest;
import com.pagatodo.apolo.data.model.webservice.request.TiendasRequest;
import com.pagatodo.apolo.data.model.webservice.response.GetBancosResponse;
import com.pagatodo.apolo.data.model.webservice.response.GetIniciativasResponse;
import com.pagatodo.apolo.data.model.webservice.response.GetPromotersResponse;
import com.pagatodo.apolo.data.model.webservice.response.GetResponseBines;
import com.pagatodo.apolo.data.model.webservice.response.GetTiendasResponse;
import com.pagatodo.apolo.data.model.webservice.response.InsertDomiPagoResponse;
import com.pagatodo.apolo.data.model.webservice.response.LoadImageResponse;
import com.pagatodo.apolo.data.remote.BuildRequest;
import com.pagatodo.apolo.data.remote.RemoteConfig;
import com.pagatodo.apolo.data.room.DatabaseManager;
import com.pagatodo.apolo.data.room.entities.Iniciativa;
import com.pagatodo.apolo.data.room.entities.Promotor;
import com.pagatodo.apolo.data.room.entities.Tienda;
import com.pagatodo.apolo.data.room.pojo.Bancos;
import com.pagatodo.apolo.data.room.pojo.Bines;
import com.pagatodo.apolo.ui.base.factorypresenters.BasePresenter;
import com.pagatodo.networkframework.DataManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.pagatodo.apolo.data.remote.RequestContract.GET_BANCOS;
import static com.pagatodo.apolo.data.remote.RequestContract.GET_BINES;
import static com.pagatodo.apolo.data.remote.RequestContract.GET_INICIATIVAS;
import static com.pagatodo.apolo.data.remote.RequestContract.GET_LOAD_IMG;
import static com.pagatodo.apolo.data.remote.RequestContract.GET_PROMOTERS;
import static com.pagatodo.apolo.data.remote.RequestContract.GET_REMOTE_CONFIG;
import static com.pagatodo.apolo.data.remote.RequestContract.GET_TIENDAS;
import static com.pagatodo.apolo.data.remote.RequestContract.POST_INSERTADATOS;
import static com.pagatodo.apolo.utils.Constants.ID_BANCO;
import static com.pagatodo.apolo.utils.Constants.ID_CLIENTE;
import static com.pagatodo.apolo.utils.Constants.NOMBRE_PROCESOIDP;
import static com.pagatodo.apolo.utils.Constants.NUM_TARJETA;
import static com.pagatodo.apolo.utils.Constants.SOLICITUD_IMPRESA;
import static com.pagatodo.networkframework.model.ResponseConstants.RESPONSE_CODE_OK;

/**
 * Created by jvazquez on 26/07/2017.
 *
 */

public class SplashPresenter extends BasePresenter<ISplashView> implements ISplashPresenter {

    public SplashPresenter(ISplashView view) {
        super(view);
    }

    public void getPromotersList() {
        if (isOnline()) {
            view.showProgress(getString(R.string.msg_update_afiliados));
            BuildRequest.updateRemoteConfig(this);
        } else {
            List<Promotor> promotors = new ArrayList<>();
            try {
                promotors = new DatabaseManager().getPromotorList();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            if (promotors.isEmpty()) {
                view.updateFailed(getString(R.string.not_internet), getString(R.string.network_error));
                return;
            }
            view.updatePromotorsSuccess();
        }

    }

    @Override
    public void getIniciativasList() {
        if (isOnline()) {
            view.showProgress(getString(R.string.msg_update_iniciativas));
            BuildRequest.getIniciativasRequest(this, new IniciativasRequest());
        } else {
            List<Iniciativa> iniciativas = new ArrayList<>();
            try {
                iniciativas = new DatabaseManager().getIniciativasList();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            if (iniciativas.isEmpty()) {
                view.updateFailed(getString(R.string.not_internet), getString(R.string.network_error));
                return;
            }
            view.updateIniciativasSuccess();
        }
    }

    @Override
    public void getTiendasList() {
        if (isOnline()) {
            view.showProgress(getString(R.string.msg_update_tiendas));
            BuildRequest.getTiendasRequest(this, new TiendasRequest());
        } else {
            List<Tienda> tiendas = new ArrayList<>();
            try {
                tiendas = new DatabaseManager().getTiendaList();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            if (tiendas.isEmpty()) {
                view.updateFailed(getString(R.string.not_internet), getString(R.string.network_error));
                return;
            }
            view.updateTiendasSuccess();
        }
    }

    @Override
    public void getBancosList() {
        if (isOnline()) {
            view.showProgress(getString(R.string.msg_update_bancos));
            BuildRequest.getBancosRequest(this, new TiendasRequest());
        } else {
                view.updateFailed(getString(R.string.not_internet), getString(R.string.network_error));
        }
    }

    @Override
    public void getBinesList() {
        if (isOnline()) {
            view.showProgress(getString(R.string.msg_update_bancos));
            BuildRequest.getBeansRequest(this, new TiendasRequest());
        } else {
            view.updateFailed(getString(R.string.not_internet), getString(R.string.network_error));
        }
    }

    @Override
    public void loadFrontCard(String Nombre, String DocumentoBase64, int Longitud, String SolicitudImpresa, int IDP) {

        if (isOnline()) {
            //Preferences pref = App.getInstance().getPrefs();
            view.showProgress(getString(R.string.msg_update_bancos));
            RequestImage requestImage = new RequestImage(Nombre,Longitud,SolicitudImpresa,IDP, DocumentoBase64);
            LoadImageRequest loadImageRequest = new LoadImageRequest();
            loadImageRequest.setRequest(requestImage);
            BuildRequest.loadImage(this, loadImageRequest);
        } else {
            view.updateFailed(getString(R.string.not_internet), getString(R.string.network_error));
        }
    }

    @Override
    public void InsertDomiciliacionPago() {
        if (isOnline()) {

            Preferences pref = App.getInstance().getPrefs();
            view.showProgress(getString(R.string.msg_update_bancos));
           // BuildRequest.insertaDatosRequest(this, new InsertDomiPagoRequest((long)pref.loadInt(ID_CLIENTE),pref.loadString(NOMBRE_PROCESOIDP),pref.loadInt(ID_BANCO),pref.loadString(NUM_TARJETA),pref.loadString(SOLICITUD_IMPRESA)));
            request request = new request((long)pref.loadInt(ID_CLIENTE),pref.loadString(NOMBRE_PROCESOIDP),pref.loadInt(ID_BANCO),pref.loadString(NUM_TARJETA),pref.loadString(SOLICITUD_IMPRESA));
            InsertDomiPagoRequest request1 = new InsertDomiPagoRequest();
            request1.setRequest(request);

            BuildRequest.insertaDatosRequest(this,request1);


        } else {
            view.updateFailed(getString(R.string.not_internet), getString(R.string.network_error));
        }

    }

    @Override
    public void onSuccess(DataManager dataManager) {
        //super.onSuccess(dataManager);
        if (dataManager.getData() != null) {
            switch (dataManager.getMethod()) {
                case GET_PROMOTERS:
                    processPromotersResponse((GetPromotersResponse) dataManager.getData());
                    //view.hideProgress();
                    break;
                    case GET_LOAD_IMG:
                    processLoadImageResponse((LoadImageResponse) dataManager.getData());
                    //view.hideProgress();
                    break;
                    case POST_INSERTADATOS:
                    processInsertResponse((InsertDomiPagoResponse) dataManager.getData());
                    //view.hideProgress();
                    break;
                    case GET_BANCOS:
                    processBancosResponse((GetBancosResponse) dataManager.getData());
                    //view.hideProgress();
                    break;

                    case GET_BINES:
                    processBinesResponse((GetResponseBines) dataManager.getData());
                    //view.hideProgress();
                    break;
                case GET_INICIATIVAS:
                    processIniciativasResponse((GetIniciativasResponse) dataManager.getData());
                    //view.hideProgress();
                    break;
                case GET_TIENDAS:
                    processTiendasResponse((GetTiendasResponse) dataManager.getData());
                    view.hideProgress();
                    break;
                case GET_REMOTE_CONFIG:
                    try {
                        Log.d("Remote-Config", RemoteConfig.updateAppConfig((ResponseRemoteConfig) dataManager.getData(), pref) ? "Updated success" : "not updated");
                    } catch (Exception e) {

                    }
                    BuildRequest.getPromotersRequest(this);
                    break;
            }
        }
    }

    private void processLoadImageResponse(LoadImageResponse data) {

        LoadImageResponse elemet = (LoadImageResponse) data;
        switch (data.getRespuesta().getCodigo()){
            case RESPONSE_CODE_OK:
                view.successLoadImagen();
                break;
            default:
                view.setInsertSuccess();
                view.showMessage(data.getRespuesta().getMensaje());

                break;

        }
    }

    private void processInsertResponse(InsertDomiPagoResponse data) {
        InsertDomiPagoResponse elemet = (InsertDomiPagoResponse) data;
        switch (data.getRespuesta().getCodigo()){
            case RESPONSE_CODE_OK:
                view.setInsertSuccess();
                break;
            default:
                view.setInsertSuccess();
                view.showMessage(data.getRespuesta().getMensaje());

                break;

        }
    }

    private void processBinesResponse(GetResponseBines data) {
        List<Bines> list = new ArrayList<>();
        GetResponseBines elemet = (GetResponseBines) data;
        switch (data.getCodigo()){
            case RESPONSE_CODE_OK:
                list = elemet.getListBines();
                view.getBinesSuccess(list);
                break;
            default:

                break;

        }

    }

    private void processBancosResponse(GetBancosResponse data) {
        GetBancosResponse elemet = (GetBancosResponse) data;
       List<Bancos> list = new ArrayList<>();

        switch (data.getCodigo()){
            case RESPONSE_CODE_OK:
                list = elemet.getListBancos();
                view.getBanksSuccess(list);
                break;
            default:

                break;

        }

    }

    @Override
    public void onFailed(DataManager dataManager) {
        super.onFailed(dataManager);
        if (dataManager.getData() != null) {
            switch (dataManager.getMethod()) {
                case GET_PROMOTERS:
                    List<Promotor> promotors = new ArrayList<>();
                    try {
                        promotors = new DatabaseManager().getPromotorList();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (promotors.isEmpty()) {
                        view.updateFailed(getString(R.string.error), (String) dataManager.getData());
                        return;
                    }
                    view.updatePromotorsSuccess();
                    break;
                case GET_INICIATIVAS:
                    List<Iniciativa> iniciativas = new ArrayList<>();
                    try {
                        iniciativas = new DatabaseManager().getIniciativasList();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (iniciativas.isEmpty()) {
                        view.updateFailed(getString(R.string.error), (String) dataManager.getData());
                        return;
                    }
                    view.updateIniciativasSuccess();
                    break;
                case GET_TIENDAS:
                    List<Tienda> tiendas = new ArrayList<>();
                    try {
                        tiendas = new DatabaseManager().getTiendaList();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (tiendas.isEmpty()) {
                        view.updateFailed(getString(R.string.error), (String) dataManager.getData());
                        return;
                    }
                    view.updateTiendasSuccess();
                    break;
                case GET_REMOTE_CONFIG:
                    BuildRequest.getPromotersRequest(this);
                    break;
                case POST_INSERTADATOS:
                    view.showMessage((String) dataManager.getData());
                    break;
                default:
                    view.showMessage((String) dataManager.getData());
                    break;
            }
        }
    }

    private void processPromotersResponse(GetPromotersResponse getPromotersResponse) {
        switch (getPromotersResponse.getRespuesta().getCodigo()) {
            case RESPONSE_CODE_OK:
                List<Promotor> promotorList = getPromotersResponse.getPromotorList();
                if (promotorList.isEmpty()) {
                    view.updateFailed(getString(R.string.not_have_promotores), getString(R.string.not_search_promotores));
                    return;
                }
                new DatabaseManager().deleteAllPromotores();
                new DatabaseManager().insertPromotores(promotorList);
                view.updatePromotorsSuccess();
                break;
            default:
                view.showMessage(getPromotersResponse.getRespuesta().getMensaje());
                break;
        }
    }

    private void processIniciativasResponse(GetIniciativasResponse getIniciativasResponse) {
        switch (getIniciativasResponse.getRespuesta().getCodigo()) {
            case RESPONSE_CODE_OK:
                List<Iniciativa> iniciativaList = getIniciativasResponse.getListIniciativas();
                if (iniciativaList.isEmpty()) {
                    view.updateFailed(getString(R.string.not_have_iniciativas), getString(R.string.not_search_iniciativas));
                    return;
                }
                new DatabaseManager().deleteAllIniciativas();
                new DatabaseManager().insertIniciativas(iniciativaList);
                view.updateIniciativasSuccess();
                break;
            default:
                view.showMessage(getIniciativasResponse.getRespuesta().getMensaje());
                break;
        }
    }

    private void processTiendasResponse(GetTiendasResponse getTiendasResponse) {
        switch (getTiendasResponse.getRespuesta().getCodigo()) {
            case RESPONSE_CODE_OK:
                List<Tienda> tiendaList = getTiendasResponse.getListTiendas();
                if (tiendaList.isEmpty()) {
                    view.updateFailed(getString(R.string.not_have_tiendas), getString(R.string.not_search_tiendas));
                    return;
                }
                new DatabaseManager().deleteAllTiendas();
                new DatabaseManager().insertTiendas(tiendaList);
                view.updateTiendasSuccess();
                break;
            default:
                view.showMessage(getTiendasResponse.getRespuesta().getMensaje());
                break;
        }
    }
}
