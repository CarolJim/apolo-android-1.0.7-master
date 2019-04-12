package com.pagatodo.apolo.activity.splash._presenter._interfaces;

import com.pagatodo.apolo.data.room.pojo.Bancos;
import com.pagatodo.apolo.data.room.pojo.Bines;
import com.pagatodo.apolo.ui.base.factoryinterfaces.IEventOnView;

import java.util.List;

/**
 * Created by jvazquez on 26/07/2017.
 */

public interface ISplashView extends IEventOnView {

    void updatePromotorsSuccess();

    void updateIniciativasSuccess();

    void updateTiendasSuccess();

    void getBanksSuccess(List<Bancos> bancosList);

    void getBinesSuccess(List<Bines> binesList);

    void setInsertSuccess();

    void successLoadImagen();

    void updateFailed(String title, String message);
}
