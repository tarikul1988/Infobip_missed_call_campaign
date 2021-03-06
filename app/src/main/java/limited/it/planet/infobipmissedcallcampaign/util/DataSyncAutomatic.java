package limited.it.planet.infobipmissedcallcampaign.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.ArrayList;

import limited.it.planet.infobipmissedcallcampaign.constant.Constants;
import limited.it.planet.infobipmissedcallcampaign.database.DataHelper;

/**
 * Created by Tarikul on 3/13/2018.
 */

public class DataSyncAutomatic extends BroadcastReceiver{
    public static final String RESPONSE_LOG = Constants.LOG_TAG_ONLINE;
    DataHelper dataHelper;
    String userNumber = "";
    String smsText = "";

    @Override
    public void onReceive(Context context, Intent intent) {
        dataHelper = new DataHelper(context);
        dataHelper.open();

        if (checkInternet(context)) {

                ArrayList<ViewLogModel> trackingList = dataHelper.checkFailedDataFromTable("failled");
                for (int i = 0; i < trackingList.size(); i++) {
                    userNumber = trackingList.get(i).getUserNumber();
                    smsText = trackingList.get(i).getSmsText();
                }

                Log.d("Network", "Internet YAY");
        }
    }
    protected boolean checkInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

}
