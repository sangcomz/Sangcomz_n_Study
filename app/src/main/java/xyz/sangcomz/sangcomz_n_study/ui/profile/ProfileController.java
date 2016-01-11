package xyz.sangcomz.sangcomz_n_study.ui.profile;

import android.app.ProgressDialog;
import android.view.Window;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import cz.msebera.android.httpclient.Header;
import xyz.sangcomz.sangcomz_n_study.R;
import xyz.sangcomz.sangcomz_n_study.bean.Member;
import xyz.sangcomz.sangcomz_n_study.core.SharedPref.SharedPref;
import xyz.sangcomz.sangcomz_n_study.core.http.HttpClient;
import xyz.sangcomz.sangcomz_n_study.define.SharedDefine;
import xyz.sangcomz.sangcomz_n_study.define.UrlDefine;

/**
 * Created by sangc on 2016-01-11.
 */
public class ProfileController {
    ProfileActivity profileActivity;

    public ProfileController(ProfileActivity profileActivity) {
        this.profileActivity = profileActivity;
    }


    public void getMember(String memberSrl) {

        // 프로그레스
        final ProgressDialog progressDialog = new ProgressDialog(profileActivity, R.style.MyProgressBarDialog);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setProgressStyle(android.R.style.Widget_Material_ProgressBar_Small);
        progressDialog.show();

        RequestParams params = new RequestParams();


        params.put("member_srl", (new SharedPref(profileActivity)).getStringPref(SharedDefine.SHARED_MEMBER_SRL));
        params.put("profile_member_srl", memberSrl);
        HttpClient.get(UrlDefine.URL_ACCOUNT_GET_ACCOUNT, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progressDialog.dismiss();
                System.out.println("onSuccess JSONObject :::: " + response.toString());

                try {
                    JSONObject jsonObject = response.getJSONObject("members");
                    Gson gson = new Gson();
                    String jsonOutput = jsonObject.toString();

                    Type type = new TypeToken<Member>() {
                    }.getType();
                    Member member = gson.fromJson(jsonOutput, type);
                    profileActivity.setProfileView(member);

//                    searchFriendFragment.setFollowMembers((ArrayList<FollowMember>) followMembers);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                progressDialog.dismiss();
                System.out.println("onFailure responseString :::: " + throwable.toString());
            }
        });
    }
}
