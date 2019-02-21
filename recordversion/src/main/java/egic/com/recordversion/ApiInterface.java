package egic.com.recordversion;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Mahmoud.Adel on 2/19/2018.
 */

public interface ApiInterface {
    @POST("AppVersion/addVersion")
    Single<GeneralResponse> addVersion(@Body AppVersion mAppVersion);
}
