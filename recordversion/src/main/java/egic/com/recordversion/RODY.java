package egic.com.recordversion;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;


/**
 * Created by Mahmoud.Adel on 12/19/2017.
 */

public class RODY {
    Context mContext;
    String deviceIMEI;
    public   RODY(Context mContext)
    {
        this.mContext=mContext;
    }


    public void rody(final String USER_NAME,final String USER_CODE,final String APP_CODE)
    {
        String[] permissions = {Manifest.permission.READ_PHONE_STATE};
        String rationale = "Please provide Phone State permission ";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");

        Permissions.check(mContext, permissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                // do your task.
                try {
                    TelephonyManager tManager = (TelephonyManager) mContext
                            .getSystemService(Context.TELEPHONY_SERVICE);
                    deviceIMEI = tManager.getDeviceId();
                    try {
                        PackageInfo pInfo =mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
                        String verCode = pInfo.versionCode+"";
                        String vername = pInfo.versionName;
                        final String deviceId = Settings.Secure.getString(mContext.getContentResolver(),
                                Settings.Secure.ANDROID_ID);
                        AppVersion mAppVersion= PrefManager.getAppVersionObject(mContext);
                        if(mAppVersion!=null&&mAppVersion.VER_CODE!=null&&mAppVersion.VER_CODE.equals(verCode)&&mAppVersion.VER_NAME!=null&&mAppVersion.VER_NAME.equals(vername)&&mAppVersion.IMEI!=null&&mAppVersion.IMEI.equals(deviceIMEI)&&mAppVersion.DEVICE_ID!=null&&mAppVersion.DEVICE_ID.equals(deviceId))
                        {
                            // Dialogs.showShortToast(mContext,"the same version");
                        }
                        else
                        {
                            AppVersion mAppVersionNew=new AppVersion();
                            mAppVersionNew.USER_NAME=USER_NAME;
                            mAppVersionNew.USER_CODE=USER_CODE;
                            mAppVersionNew.VER_CODE=verCode;
                            mAppVersionNew.VER_NAME=vername;
                            mAppVersionNew.APP_CODE=APP_CODE;
                            mAppVersionNew.IMEI=deviceIMEI;
                            mAppVersionNew.DEVICE_ID=deviceId;
                            new AppVerManager(mContext).add(mAppVersionNew);
                        }
                    }
                    catch(Exception e){
                        Dialogs.showShortToast(mContext,e.getMessage());
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.

                android.os.Process.killProcess(android.os.Process.myPid());

            }

        });
    }
}
