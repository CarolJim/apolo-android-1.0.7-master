package com.pagatodo.apolo.activity.register;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.CaptureActivity;
import com.pagatodo.apolo.activity.ConfirmateActivity;
import com.pagatodo.apolo.activity.MenuActivity;
import com.pagatodo.apolo.activity.PreviewImageActivity;
import com.pagatodo.apolo.activity.login.LoginActivity;
import com.pagatodo.apolo.activity.register._presenter.RegisterPresenterImpl;
import com.pagatodo.apolo.activity.register._presenter._interfaces.RegisterPresenter;
import com.pagatodo.apolo.activity.register._presenter._interfaces.RegisterView;
import com.pagatodo.apolo.activity.smsverification.SmsActivity;
import com.pagatodo.apolo.data.adapters.CustomAdapter;
import com.pagatodo.apolo.data.model.Cards;
import com.pagatodo.apolo.data.model.Documento;
import com.pagatodo.apolo.data.model.FormularioAfiliacion;
import com.pagatodo.apolo.data.room.entities.Promotor;
import com.pagatodo.apolo.ui.base.factoryactivities.BasePresenterPermissionActivity;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IValidateForms;
import com.pagatodo.apolo.utils.Constants;
import com.pagatodo.apolo.utils.ValidateForm;
import com.pagatodo.apolo.utils.customviews.MaterialButton;
import com.pagatodo.apolo.utils.customviews.MaterialTextView;
import com.pagatodo.apolo.utils.customviews.MaterialValidationEditText;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pagatodo.apolo.App.instance;
import static com.pagatodo.apolo.ui.UI.showSnackBar;
import static com.pagatodo.apolo.ui.base.BaseEventContract.DOCUMENTS_RV_ITEM_SELECTED;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_REGISTERED;
import static com.pagatodo.apolo.ui.base.BaseEventContract.EVENT_REGISTER_REINTENT;
import static com.pagatodo.apolo.ui.base.BaseEventContract.KEY_FOLIO;
import static com.pagatodo.apolo.utils.Constants.CAPTURE_REQUEST_CODE;
import static com.pagatodo.apolo.utils.Constants.PREVIEW_REQUEST_CODE;

public class RegisterActivity extends BasePresenterPermissionActivity<RegisterPresenter> implements RegisterView, IValidateForms {
    private static final String DIALOG_PROGRESS_REGISTER = "dialogProgressRegister";

    private final String TAG = "MainActivity";
    private CustomAdapter adapter;
    private List<Cards> cardsList = Constants.DOCUMENTS;
    @BindView(R.id.recycler_view_card) RecyclerView recyclerView;
    @BindView(R.id.btnRegister) MaterialButton btnRegister;
    @BindView(R.id.layoutRegister) CoordinatorLayout layoutRegister;
    @BindView(R.id.edtCellPhone) MaterialValidationEditText edtCellPhone;
    @BindView(R.id.edtPhone) MaterialValidationEditText edtPhone;
    @BindView(R.id.tv_name_afiliado) MaterialTextView tvAfiliado;
    @BindView(R.id.ivVerify) AppCompatImageView ivVerify;
    private Promotor mPromotor = new Promotor();
    private StatusProgresFragment statusProgresFragment = null;
    private Documento rvSelectedItem;
    int maxLengthPhone = 10;
    int comprobanteOptional = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        inflateView(this, R.layout.activity_main);
        ButterKnife.bind(this);
        adapter = new CustomAdapter(this, cardsList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        validateEditText(btnRegister);
        initData();
        mPromotor = pref.getCurrentPromotor();
        setValuesDefaultForm();
        initFragments();
    }
    private void initFragments() {
        Log.e("....", " tasks " + getTasks());
        statusProgresFragment = StatusProgresFragment.newInstance(getTasks());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString(Constants.SOL_CELULAR, instance.get(Constants.SOL_CELULAR));
        outState.putString(Constants.SOL_TELEFONO, instance.get(Constants.SOL_TELEFONO));
        outState.putString(Constants.SOL_TARJETA, instance.get(Constants.SOL_TARJETA));
        outState.putString(Constants.SOL_IFE_FRENTE, instance.get(Constants.SOL_IFE_FRENTE));
        outState.putString(Constants.SOL_IFE_VUELTA, instance.get(Constants.SOL_IFE_VUELTA));
        outState.putString(Constants.SOL_CREDITO_SIMPLE, instance.get(Constants.SOL_CREDITO_SIMPLE));
        outState.putString(Constants.SOL_COMPROBANTE_DOM, instance.get(Constants.SOL_COMPROBANTE_DOM));
        outState.putString(Constants.SOL_OTRA_FRENTE, instance.get(Constants.SOL_OTRA_FRENTE));
        outState.putString(Constants.SOL_OTRA_REVERSO, instance.get(Constants.SOL_OTRA_REVERSO));
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        initData();
    }

    @Override
    protected int setIdMainView() {
        return 0;
    }

    @Override
    protected int setIdContainerFragments() {
        return 0;
    }

    @Override
    protected void initializePresenter() {
        presenter = new RegisterPresenterImpl(this);
    }

    private void validateEditText(MaterialButton btnRegister){
        if(edtCellPhone != null && btnRegister != null){
            ValidateForm.enableBtn(false, btnRegister);
            ValidateForm.validateEditText(btnRegister);
        }
    }

    @Override
    protected int setIdCoordinatorLayout() {
        return R.id.layoutRegister;//super.setIdCoordinatorLayout();
    }

    @Override
    public void showProgress(String message) {
        super.showProgress(message);
        hideSoftKeyboard();
    }

    @OnClick(R.id.ivVerify)
    public void sms(){
        assignData();
        getDataForm();
        if(!edtCellPhone.isValidField()) {
            showMessage(getString(R.string.error_cellphone_invalid));
            return;
        } else if(isOnline()) {
            showView(SmsActivity.class);
        } else {
            hideSoftKeyboard();
            showMessage(getString(R.string.network_error));
        }
    }

    @OnClick(R.id.logout)
    public void endLogout(final View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.option_menu, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem items) {
                switch (items.getItemId()) {
                    case R.id.logoutPromotor:
                        presenter.logout();
                        break;
                    default:
                       break;
                }
                return true;
            }
        });
    }


    @Override
    public void logoutActivity() {
        pref.destroySession();
        instance.clearHashMap();
        showView(LoginActivity.class);
        finish();
    }

    @Override
    public void setNavigation() {
        showView(ConfirmateActivity.class);
    }

    @Override
    public void returnData(){
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        super.showMessage(message);
    }

    @Override
    public void errorRegister(String message) {
        if(statusProgresFragment != null){
            statusProgresFragment.setErrorRegister(message);
        }
    }

    @Override
    public void successRegister() {
        if(statusProgresFragment != null){
            statusProgresFragment.isSuccessRegister();
        }
    }

    @Override
    public void errorUploadDocument(Documento documento, String message) {
        if(statusProgresFragment != null){
            statusProgresFragment.upladDocumentFailed();
       }
    }

    @Override
    public void successUploadDocument(Documento documento) {
        statusProgresFragment.uploadDocumentSuccess();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isDeviceSupportCamera()) {
            showSnackBar(layoutRegister, getString((R.string.not_compatible_camera)));
            finish();
        }
        //enableVerificateSMS(RemoteConfig.isEnableVerificateSMS());
    }

    @Override
    public void onBackPressed() {
        //moveTaskToBack(true);
        showView(MenuActivity.class);
        finish();
    }

    public void assignData(){
        instance.put(Constants.SOL_CELULAR, edtCellPhone.getText());
        instance.put(Constants.SOL_TELEFONO, edtPhone.getText());
    }
    public void initData(){
        pref.saveDataBool(String.valueOf(Constants.CODIGO_VERIFICADO),false);
        pref.saveDataBool(String.valueOf(Constants.ENABLE_VERIFY), true);
        edtCellPhone.setText(instance.get(Constants.SOL_CELULAR));
        edtPhone.setText(instance.get(Constants.SOL_TELEFONO));

        edtCellPhone.setAddTextChangedListener(cellPhoneTextWatcher);
        edtPhone.setAddTextChangedListener(phoneTextWatcher);
    }

    private TextWatcher cellPhoneTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String cellphoneLength = edtCellPhone.getText().toString().trim();
            if(cellphoneLength.length() == maxLengthPhone){
                enableVerificateSMS(true);
                edtPhone.requestFocus();
            } else {
                enableVerificateSMS(false);
            }
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void afterTextChanged(Editable s) {}
    };

    private TextWatcher phoneTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String phoneLength = edtPhone.getText().toString().trim();
            if(phoneLength.length() == maxLengthPhone){
                hideSoftKeyboard();
            }
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void afterTextChanged(Editable s) {}
    };

    @Override
    public void onEvent(String event, Object data) {
        super.onEvent(event, data);
        switch (event) {
            case DOCUMENTS_RV_ITEM_SELECTED:
                rvSelectedItem = (Documento) data;
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE});
                break;
            case EVENT_REGISTER_REINTENT:
                statusProgresFragment = StatusProgresFragment.newInstance(getTasks());
                if(getFormularioAfiliacion().getFolio().isEmpty()){
                    presenter.requestRegister();
                }else{
                    presenter.uploadPendingDocument();
                }
                showProgressFragment();
                break;
            case EVENT_REGISTERED:
                Intent i = new Intent(RegisterActivity.this, ConfirmateActivity.class);
                i.putExtra(KEY_FOLIO, presenter.getFolio());
                startActivity(i);
                finish();
                break;
        }
    }


    private int getTasks() {
        if(!getFormularioAfiliacion().getFolio().isEmpty()){
            int pendingTasks = 0;
            for(Documento documento: getFormularioAfiliacion().getDocumentos()){
                if(!documento.isUploaded()){
                    pendingTasks++;
                }
            }
            return pendingTasks;
        }
        return getFormularioAfiliacion().getDocumentos().size() + 1;
    }

    protected void doPermissionsGrantedAction() {
        assignData();
        HashMap<String, Serializable> extras = new HashMap<>();

        if(presenter.doesDocumentExist(rvSelectedItem)) {
            extras.put(Constants.SELECTED_DOCUMENT_KEY, getFormularioAfiliacion().getDocumentos().get(presenter.getDocumentPosition(rvSelectedItem)));
            startActivityForResult(PreviewImageActivity.class, PREVIEW_REQUEST_CODE, extras);
        }
        else {
            extras.put(Constants.SELECTED_DOCUMENT_KEY, rvSelectedItem);
            startActivityForResult(CaptureActivity.class, CAPTURE_REQUEST_CODE, extras);
        }
    }

    @Override
    public void setValuesDefaultForm() {
        tvAfiliado.setText(getNamePromotor());
    }

    private String getNamePromotor(){
        String nombre           = mPromotor.getNombre() != null ? mPromotor.getNombre():"";
        String apellido         = mPromotor.getApellidoPaterno() != null ? mPromotor.getApellidoPaterno(): "";
        String apellidoMaterno  = mPromotor.getApellidoMaterno() != null ? mPromotor.getApellidoMaterno(): "";
        return  getString(R.string.name_agente_format, nombre, apellido, apellidoMaterno);
    }
    @OnClick(R.id.btnRegister)
    public void goRegister(){
        validateForm();
    }
    @Override
    public void validateForm() {
        getDataForm();

        /**
         * Mostrara un mensaje de error si ambas cadenas de telefono vienen vacias.
         */
        if(getFormularioAfiliacion().getTelefonoMovil().isEmpty() && getFormularioAfiliacion().getTelefonoCasa().isEmpty()){
            showMessage(getString(R.string.error_number_validate));
            return;
        }
        /**
        * Se modifica logica para realizar la validación de otra identificación
        */
        String errorDocument = "";
        int numeroTaskMenos = 1;
        boolean hasOtra = validateHasOtraIdentificacion();
        for(Documento documento: getFormularioAfiliacion().getDocumentos()){
            if( (documento.getNombre().equals(getString(R.string.comprobante_domicilio)) && documento.getDocumentoBase64().isEmpty()) ||( (documento.getNombre().equals("OTRA IDENTIFICACIÓN FRENTE") && documento.getDocumentoBase64().isEmpty()) ) || ((documento.getNombre().equals("OTRA IDENTIFICACIÓN REVERSO") && documento.getDocumentoBase64().isEmpty())) ) {
                errorDocument =  "";
                numeroTaskMenos++;
                //statusProgresFragment = StatusProgresFragment.newInstance(getTasks()-1);
                //break;
            } else if (documento.getDocumentoBase64().isEmpty() || documento.getLongitud() == 0) {
                errorDocument = documento.getNombre();
                break;
            }
        }
        if(!errorDocument.isEmpty()){
            showMessage(getString(R.string.error_listaDocumentoVacio, errorDocument));
            return;
        }
        if(numeroTaskMenos > 1)
        {
            statusProgresFragment = StatusProgresFragment.newInstance(getTasks()- numeroTaskMenos);
        }
        onValidationSuccess();
    }

    private boolean validateHasOtraIdentificacion()
    {
        for(Documento documento: getFormularioAfiliacion().getDocumentos()){
            if((documento.getNombre().equals(getString(R.string.otra_identificacion_frente)) && documento.getDocumentoBase64().isEmpty()) || (documento.getNombre().equals(getString(R.string.otra_identificacion_reverso)) && documento.getDocumentoBase64().isEmpty()) )
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onValidationSuccess() {
        if(isOnline()) {
            showProgressFragment();
            presenter.requestRegister();
        } else {
            hideSoftKeyboard();
            showMessage(getString(R.string.network_error));
        }
    }

    @Override
    public void getDataForm() {
        getFormularioAfiliacion().setTelefonoCasa(edtPhone.getText());
        getFormularioAfiliacion().setTelefonoMovil(edtCellPhone.getText());
    }
    private void enableVerificateSMS(boolean enable){
        Boolean verificadoState = pref.loadBoolean(String.valueOf(Constants.CODIGO_VERIFICADO));
        Boolean enableVerify    = pref.loadBoolean(String.valueOf(Constants.ENABLE_VERIFY));

        if(enableVerify){
            ivVerify.setVisibility(enable && edtCellPhone.isValidField() ? View.VISIBLE : View.GONE);
            ivVerify.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_verificar_ap));
        } else if(!enable){
            ivVerify.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_verificar_ap));
        } else {
            ivVerify.setEnabled(verificadoState ? false : true);
            ivVerify.setImageDrawable(
                    verificadoState ?
                    ContextCompat.getDrawable(this, R.drawable.ic_verificado_ap) :
                    ContextCompat.getDrawable(this, R.drawable.ic_noverificado_ap) );
        }
    }
    public void showProgressFragment() {
        if (statusProgresFragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Fragment prev = getSupportFragmentManager().findFragmentByTag(DIALOG_PROGRESS_REGISTER);
            if (prev != null) {
                ft.remove(prev);
            }
            statusProgresFragment.setCancelable(false);
            statusProgresFragment.show(ft, DIALOG_PROGRESS_REGISTER);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAPTURE_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK)
                    if (data.getExtras().containsKey(RESULT_KEY))
                        updateList((Documento) data.getSerializableExtra(RESULT_KEY), true);
                break;
            case PREVIEW_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK)
                    updateList(rvSelectedItem, false);
                break;
        }
    }


    private void updateList(Documento currentDocument, boolean shouldAddDocument)
    {
        int currentIndex = presenter.getDocumentPosition(currentDocument);
        View currentView = recyclerView.getLayoutManager().findViewByPosition(presenter.getListPosition(currentDocument));
        AppCompatImageView ivCheck = (AppCompatImageView) currentView.findViewById(R.id.ivCheck);
        ArrayList<Documento> documents = (ArrayList<Documento>) getFormularioAfiliacion().getDocumentos();

        if(shouldAddDocument) {
            ValidateForm.enableBtn(true, btnRegister);
            ivCheck.setImageResource(R.drawable.ic_check_ap);
            documents.remove(currentIndex);
            documents.add(currentIndex, currentDocument);
        }else {
            ivCheck.setImageResource(R.drawable.ic_nocheck_ap);
            documents.get(currentIndex).setLongitud(0);
        }
    }
    private FormularioAfiliacion getFormularioAfiliacion(){
        return presenter.getFormularioAfiliacion();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}