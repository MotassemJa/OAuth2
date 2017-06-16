package motassemja.github.com.moauth;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import motassemja.github.com.moauth.exceptions.MoAuthException;
import motassemja.github.com.moauth.exceptions.MoAuthExceptionManager;
import motassemja.github.com.moauth.exceptions.MoAuthExceptionReason;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by moja on 12.06.2017.
 */

public class RequestTask extends AsyncTask<Void, Void, Response> {
    private Request mRequest;
    private MoAuthClient.MoAuthCallback mAuthCallback;

    public RequestTask(Request request, MoAuthClient.MoAuthCallback callback) {
        mRequest = request;
        mAuthCallback = callback;
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    @Override
    protected Response doInBackground(Void... params) {
        OkHttpClient client = new OkHttpClient();
        try {
            Response response = client.newCall(mRequest).execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);
        if (response != null) {
            if (response.isSuccessful()) {
                try {
                    MoAuthTokenResult token = parseResponseData(response.body().string());
                    mAuthCallback.onComplete(token, null);
                    return;
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }
            mAuthCallback.onComplete(null, parseError(response.code()));
        }
    }

    /**
     * Parse the error code and get a corresponding OAuthException with the reason behind the error
     *
     * @param errCode
     * @return
     */
    private MoAuthException parseError(int errCode) {
        // TODO COMPLETE THIS METHOD
        MoAuthExceptionReason reason;
        switch (errCode) {
            case 400:
                reason = MoAuthExceptionReason.REASON_INVALID_REQUEST;
                break;
            case 401:
                reason = MoAuthExceptionReason.REASON_INVALID_GRANT;
                break;
            default:
                reason = MoAuthExceptionReason.REASON_SERVER_ERROR;
                break;
        }
        return new MoAuthExceptionManager("Error: " + errCode, reason);
    }

    /**
     * Parse the response which represents the token
     *
     * @param data String containing the json object
     * @return OAuthTokenResult
     * @throws JSONException
     */
    private MoAuthTokenResult parseResponseData(String data) throws JSONException {
        JSONObject jsonObject = new JSONObject(data);
        String accessToken = jsonObject.getString("access_token");
        String refreshToken = jsonObject.isNull("refresh_token") ? "" : jsonObject.getString("refresh_token");
        int expiresIn = jsonObject.getInt("expires_in");

        return new MoAuthTokenResult(accessToken, refreshToken, expiresIn);
    }
}
