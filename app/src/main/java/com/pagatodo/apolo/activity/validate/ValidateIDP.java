package com.pagatodo.apolo.activity.validate;

import android.support.design.widget.CoordinatorLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pagatodo.apolo.R;
import com.pagatodo.apolo.activity.CheckIDP._presenter.IdpPresenterImpl;
import com.pagatodo.apolo.activity.CheckIDP._presenter._interfaces.IdpPresenter;
import com.pagatodo.apolo.activity.CheckIDP._presenter._interfaces.IdpView;
import com.pagatodo.apolo.activity.MenuActivity;
import com.pagatodo.apolo.activity.account.SelectBankActivity;
import com.pagatodo.apolo.activity.login._presenter.LoginPresenterImpl;
import com.pagatodo.apolo.activity.login._presenter._interfaces.LoginPresenter;
import com.pagatodo.apolo.activity.login._presenter._interfaces.LoginView;
import com.pagatodo.apolo.ui.base.factoryactivities.BasePresenterPermissionActivity;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IValidateForms;
import com.pagatodo.apolo.utils.ValidateForm;
import com.pagatodo.apolo.utils.customviews.MaterialButton;
import com.pagatodo.apolo.utils.customviews.MaterialValidationEditText;
import com.pagatodo.networkframework.DataManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ValidateIDP extends BasePresenterPermissionActivity<LoginPresenter> implements
        LoginView, IValidateForms {

    private final String TAG = ValidateIDP.class.getSimpleName();
    @BindView(R.id.edt2IDPNumber)
    MaterialValidationEditText edtIDP;
    @BindView(R.id.tv_error_msn)
    TextView errorMsn;
    @BindView(R.id.btnValidateIDP)
    MaterialButton btnValidate;
    @BindView(R.id.layoutValidateIDP)
    CoordinatorLayout layoutValidateIdp;
    private String numberIdp = "";
    public  static String IDPROMOTOR="IDPROMOTOR";
    public  static String  USER="USER";
    private String ID_Promotor = "";
    private String Pass_Promotor = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflateView(this, R.layout.activity_check2_idp );
        ButterKnife.bind(this);
        validateEditText(btnValidate, edtIDP);

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

    private void validateEditText(MaterialButton btnValidate, MaterialValidationEditText edtIDP) {
        if (edtIDP != null) {
            ValidateForm.enableBtn(false, btnValidate);
            ValidateForm.validateEditText(btnValidate, edtIDP);
        }
    }

    @OnClick(R.id.btnValidateIDP)
    public void login() {
        validateForm();
    }

    @Override
    protected int setIdCoordinatorLayout() {
        return R.id.layoutValidateIDP;
    }

    @Override
    public void setUserNumberError() {
        showMessage(getString((R.string.error_empty_id_afiliador)));

    }

    @Override
    public void setServerError() {
        showMessage("No se puede alcanzar el server");

    }

    @Override
    public void setresetPass(int ID_Promotor, String usuario) {

    }

    @Override
    public void setPassError() {
        showMessage("NO se pudo autenticar revisa tus datos");

    }

    @Override
    public void setUserPassError() {

    }

    @Override
    public void setNavigation() {
        showView(SelectBankActivity.class);
        finish();

    }

    @Override
    public void showMessage(String message) {
        super.showMessage(message);
    }

    @Override
    public void showProgress(String message) {
        super.showProgress(message);
        hideSoftKeyboard();
    }

    @Override
    public void setValuesDefaultForm() {

    }

    @Override
    public void validateForm() {
        getDataForm();
        if (ID_Promotor.isEmpty()) {
            showMessage(getString(R.string.error_empty_id_afiliador));
            return;
        }
        onValidationSuccess();

    }

    @Override
    public void onValidationSuccess() {
        presenter.loginNew(ID_Promotor,"", "");

    }

    @Override
    public void getDataForm() {
        if (edtIDP != null) {
            ID_Promotor = edtIDP.getText();
            return;
        }else {
            if (edtIDP == null) {
                showMessage(getString(R.string.error_empty_id_afiliador));
            }
        }
    }
}
