package com.github.motassemja.moauth;

import android.os.AsyncTask;

import com.github.motassemja.moauth.exceptions.MoAuthException;
import com.github.motassemja.moauth.exceptions.MoAuthExceptionManager;
import com.github.motassemja.moauth.exceptions.MoAuthExceptionReason;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by moja on 12.06.2017.
 */

public class RequestTask extends AsyncTask<Void, Void, String> {
    private Request mRequest;
    private OnTaskFinishedListener mListener;

    public interface OnTaskFinishedListener {
        void onTaskSuccess(String body);

        void onTaskFailed(MoAuthException ex);
    }

    public RequestTask(Request request, OnTaskFinishedListener listener) {
        this.mRequest = request;
        this.mListener = listener;
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    @Override
    protected String doInBackground(Void... params) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS).readTimeout(15, TimeUnit.SECONDS).writeTimeout(15, TimeUnit.SECONDS)
                .build();
        try {
            Response response = client.newCall(mRequest).execute();
            if (response != null) {
                String res = "";
                if (response.isSuccessful()) {
                    res = response.body().string();
                } else {
                    res = "Err" + response.code();
                }
                return res;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if ((s != null && !s.isEmpty()) && !s.startsWith("Err ")) {
            mListener.onTaskSuccess(s);
            return;
        }
        int errCode = 0;
        if (s != null) {
            errCode = Integer.parseInt(s.split(" ")[1]);
        }
        mListener.onTaskFailed(parseError(errCode));
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
                reason = MoAuthExceptionReason.REASON_NOT_AUTHENTICATED;
                break;
            default:
                reason = MoAuthExceptionReason.REASON_SERVER_ERROR;
                break;
        }
        return new MoAuthExceptionManager("Error: " + errCode, reason);
    }

}
