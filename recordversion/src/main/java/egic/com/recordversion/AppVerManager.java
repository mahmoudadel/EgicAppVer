package egic.com.recordversion;

import android.content.Context;


import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
/**
 * Created by Mahmoud.Adel on 12/19/2017.
 */

public class AppVerManager {
    Context context;
    CompositeDisposable disposable;
    public AppVerManager(Context context) {
        this.context = context;
        disposable= new CompositeDisposable();
    }

    public void add(final AppVersion mAppVersion) {
       ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        disposable.add(
                apiService.addVersion(mAppVersion)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<GeneralResponse>() {

                            @Override
                            public void onSuccess(GeneralResponse mGR) {
                                try
                                {
                                    if (mGR != null) {
                                        if (mGR.status != -1) {
                                            //  Dialogs.showShortToast(context, mGeneralResponse.message);
                                            Gson gson = new Gson();
                                            String json = gson.toJson(mAppVersion);
                                            PrefManager.saveAppVersion(context,json);
                                            Dialogs.showShortToast(context, mGR.message);
                                        } else {
                                            Dialogs.showShortToast(context, mGR.message);
                                        }

                                    }
                                } catch (Exception e) {


                                    Dialogs.showShortToast(context,"LOG VERSION"+ e.getMessage() );
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Dialogs.showShortToast(context,"LOG VERSION"+ e.getMessage() );
                            }
                        }));
    }

}
