package com.pagatodo.apolo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ResetContraseniaUser extends BasePresenterPermissionActivity<LoginPresenter> implements LoginView {

    @BindView(R.id.edtPassNew)
    MaterialValidationEditText edtPassNew;

    @BindView(R.id.edtPassConfirm)
    MaterialValidationEditText edtPassConfirm;

    @BindView(R.id.btnChangePass)
    MaterialButton btnChangePass;

    String newPass,confirmNewPass;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);


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

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataForm();
                validateform();


            }
        });


    }

    private void validateform() {
        boolean isValid = true ;
        if (newPass.isEmpty()){
            isValid = false;
            //errror mensajes
        }
        if (confirmNewPass.isEmpty()){
            isValid = false;
            //mensajes de error

        }

        if (isValid){
        //presenter.changePass();
        }


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
    public void setresetPass(String usuario, int ResetContrasenia, int ID_Promotor) {


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
