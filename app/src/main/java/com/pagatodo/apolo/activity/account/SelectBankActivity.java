package com.pagatodo.apolo.activity.account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pagatodo.apolo.App;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.splash._presenter.SplashPresenter;
import com.pagatodo.apolo.activity.splash._presenter._interfaces.ISplashPresenter;
import com.pagatodo.apolo.activity.splash._presenter._interfaces.ISplashView;
import com.pagatodo.apolo.data.room.pojo.Bancos;
import com.pagatodo.apolo.data.room.pojo.Bines;
import com.pagatodo.apolo.ui.base.factoryactivities.BasePresenterActivity;
import com.pagatodo.apolo.utils.ValidateForm;
import com.pagatodo.apolo.utils.customviews.MaterialButton;
import com.pagatodo.apolo.utils.customviews.MaterialValidationEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pagatodo.apolo.utils.Constants.IDP;
import static com.pagatodo.apolo.utils.Constants.ID_BANCO;
import static com.pagatodo.apolo.utils.Constants.NOMBRE_PROCESOIDP;
import static com.pagatodo.apolo.utils.Constants.NUM_TARJETA;

public class SelectBankActivity extends BasePresenterActivity<ISplashPresenter> implements ISplashView {


    @BindView(R.id.tv_idp)
    TextView tv_idp;
    @BindView(R.id.ed_num_tarjeta)
    EditText ed_num_tarjeta;
    @BindView(R.id.ed_confirm_num_tarjeta)
    EditText ed_confirm_num_tarjeta;
    @BindView(R.id.tv_nombre_completo)
    TextView tv_nombre_completo;
    @BindView(R.id.sp_bancos)
    Spinner sp_bancos;
    boolean isValidBanck = false;
    boolean isValid = true;
    List<Bancos> listBancosserv = new ArrayList<>();
    List<Bines> listBinesserv = new ArrayList<>();

    @BindView(R.id.btnContinuar)
    MaterialButton btnContinuar;

    String tarjeta, tarjetaConfirm;
    int idBancotoSend =0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bank);
        ButterKnife.bind(this);
        validateEditText(btnContinuar, sp_bancos,ed_num_tarjeta, ed_confirm_num_tarjeta);
        initViews();
    }

    @Override
    protected int setIdContainerFragments() {
        return 0;
    }

    @Override
    protected int setIdMainView() {
        return 0;
    }

    @Override
    protected void initializePresenter() {
        presenter = new SplashPresenter(this);
    }

    private void initViews() {
        tv_nombre_completo.setText(App.getInstance().getPrefs().loadString(NOMBRE_PROCESOIDP));
                tv_idp.setText(App.getInstance().getPrefs().loadInt(IDP)+"");
                presenter.getBancosList();
                presenter.getBinesList();

        ed_num_tarjeta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(ed_num_tarjeta.getText().length()==6){
                validaBin(ed_num_tarjeta.getText().toString());
            }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void validateEditText(MaterialButton btnContinuar, Spinner sp_bancos, EditText ed_num_tarjeta, EditText ed_confirm_num_tarjeta){
        if (ed_num_tarjeta != null && ed_confirm_num_tarjeta != null && sp_bancos.getSelectedItemPosition() != 0){
            ValidateForm.enableBtn(true, btnContinuar);
            //ValidateForm.validateEditText(btnContinuar, sp_bancos, ed_num_tarjeta, ed_confirm_num_tarjeta);
        }
    }

    private void validaBin(String currentBin) {
        int spinnerBankSlected  = sp_bancos.getSelectedItemPosition()-1;
        int idBankbean =0;
        for (Bines bines : listBinesserv){

            try {
                if (bines.getBIN() == Integer.parseInt(currentBin)) {
                    idBankbean = bines.getID_Banco();
                    idBancotoSend =idBankbean;
                } else {
                    isValidBanck = false;

                }
            }catch (Exception e){
                isValidBanck =false;
            }
        }
        if (listBancosserv.get(spinnerBankSlected).getID_Banco()==idBankbean){
         isValidBanck =true;
            Toast.makeText(this, "Banco correcto", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this, "Revisa la Información de tu Tarjeta", Toast.LENGTH_SHORT).show();



    }

    public  void validate(){
        getdata();
        isValid=true;
        if (tarjeta.isEmpty()){
            isValid = false;
        }if (tarjetaConfirm.isEmpty()){
            isValid = false;
        }if (idBancotoSend ==0){
            isValid = false;
        }
        if (!tarjeta.equals(tarjetaConfirm)){
            isValid = false;
            Toast.makeText(this, "Las tarjetas no coinciden", Toast.LENGTH_SHORT).show();
        }
        if (isValid){
            Toast.makeText(this, "Listo a tomar Fotos", Toast.LENGTH_SHORT).show();
            App.getInstance().getPrefs().saveData(NUM_TARJETA,tarjeta);
            App.getInstance().getPrefs().saveDataInt(ID_BANCO,idBancotoSend);

        }else{
            Toast.makeText(this, "Revisa tu información", Toast.LENGTH_SHORT).show();}

        onSuccess();
    }

    private void getdata() {
        tarjeta = ed_num_tarjeta.getText().toString();
        tarjetaConfirm = ed_confirm_num_tarjeta.getText().toString();

        //idBancotoSend = sp_bancos.getSelectedItemPosition();

    }

    @OnClick(R.id.btnContinuar)
    public void continuar(){validate();}

    @Override
    public void updatePromotorsSuccess() {

    }

    @Override
    public void updateIniciativasSuccess() {

    }

    @Override
    public void updateTiendasSuccess() {

    }

    @Override
    public void getBanksSuccess(List<Bancos> bancosList) {
        List<String> nameBanks = new ArrayList<String>();
        listBancosserv = bancosList;
        nameBanks.add("Selecciona tu Banco");
        for (Bancos bancos : bancosList){
            nameBanks.add(bancos.getDescripcion());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, nameBanks);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_bancos.setAdapter(dataAdapter);
    }

    @Override
    public void getBinesSuccess(List<Bines> binesList) {
        listBinesserv = binesList;
    }

    @Override
    public void setInsertSuccess() {

        showView(TakePicturesActivity.class);
        finish();

    }

    @Override
    public void successLoadImagen() {

    }

    @Override
    public void updateFailed(String title, String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void onSuccess(){
        presenter.InsertDomiciliacionPago();
       // showView(TakePicturesActivity.class);
        //finish();
    }
}
