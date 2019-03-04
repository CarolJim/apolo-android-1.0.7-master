package com.pagatodo.apolo.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;

import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.login._presenter.LoginPresenterImpl;
import com.pagatodo.apolo.activity.login._presenter._interfaces.LoginPresenter;
import com.pagatodo.apolo.activity.login._presenter._interfaces.LoginView;
import com.pagatodo.apolo.ui.base.factoryactivities.BasePresenterPermissionActivity;
import com.pagatodo.apolo.utils.customviews.MaterialButton;
import com.pagatodo.apolo.utils.customviews.MaterialValidationEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pagatodo.apolo.activity.login.LoginActivity.IDPROMOTOR;
import static com.pagatodo.apolo.activity.login.LoginActivity.USER;

public class ResetContraseniaUser extends BasePresenterPermissionActivity<LoginPresenter> implements LoginView {

    @BindView(R.id.edtpasstochange)
    MaterialValidationEditText edtPassNew;

    @BindView(R.id.edtPassConfirm)
    MaterialValidationEditText edtPassConfirm;

    @BindView(R.id.btnChangePass)
    MaterialButton btnChangePass;

    String newPass,confirmNewPass;
    String iduser;
    String promotor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            iduser = extras.getString(IDPROMOTOR);
            promotor = extras.getString(USER);
            // and get whatever type user account id is
        }
        setContentView(R.layout.activity_reset_contrasenia_user);
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
        presenter = new LoginPresenterImpl(this);

    }

    private void initViews() {
        ButterKnife.bind(this);
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataForm();
                validateform();


            }
        });


    }

    private void validateform() {

        if (newPass.equals("")){
            return;
            //errror mensajes
        }
        if (confirmNewPass.equals("")){
            return;
            //mensajes de error
        }
        if (!confirmNewPass.equals(newPass)){
            return;
            //mensajes de error contraseñas no coinciden
        }


            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            String imei = telephonyManager.getDeviceId();

        presenter.changePass(Integer.parseInt(iduser),confirmNewPass,imei,false,promotor);
        finish();



    }

    private void getDataForm() {

        newPass = edtPassNew.getText().trim();
        confirmNewPass= edtPassConfirm.getText().trim();
    }

    @Override
    public void setUserNumberError() {

    }

    @Override
    public void setServerError() {

    }

    @Override
    public void setresetPass(int ID_Promotor, String usuario) {

    }

    @Override
    public void setPassError() {

    }

    @Override
    public void setUserPassError() {

    }

    @Override
    public void setNavigation() {

    }
}
