package sharent.product.com.telstraloadlist.utils;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkConnection {

    // Context variable for this dialogue need to be displayed.
    private Context context;
    // Constructor to initialize class with context
	public NetworkConnection(Context context) {
		// TODO Auto-generated constructor stub
		this.context= context;
	}

    /**
     * This method will find whether device have a stable network connection or not
     * @return    true if connection avilabile else false.
     */
	public boolean isConnected() {
		// TODO Auto-generated constructor stub

       // variable to return connection status
        boolean connection;

        ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final android.net.NetworkInfo wifi = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		final android.net.NetworkInfo mobile = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if(wifi.isConnected()|| mobile.isConnected()){
			//AppConstants.getGlobalParams(context);
			return connection =true;
		}
		else{
			return connection =false;
		}
	}
}
