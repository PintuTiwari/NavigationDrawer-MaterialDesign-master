package epbit.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.cabbookinghome.CabMoney;

import epbit.Login.LoginDetails;
import epbit.constants.ProjectURLs;


public class LoadWebViewwithProgressDialog 
{	
	public static Context mycontext;
	
	
	public static void loadWebViewWithProgressDialog(WebView web,Context context,String url)
	{
		
		mycontext=context;
		 web.setWebViewClient(new WebViewClient() {

	            private ProgressDialog progrDialog;
				public boolean shouldOverrideUrlLoading(WebView view, String url) {              
	                view.loadUrl(url);
	                return true;
	            }
	            public void onLoadResource (WebView view, String url) {
	                if (progrDialog == null) {
	                    progrDialog = new ProgressDialog(mycontext);
	                    progrDialog.setMessage("Loading...");
	                    progrDialog.show();
	                }
	            }
	            
	            public void onPageStarted(WebView view, String url) {
	                progrDialog = ProgressDialog.show(mycontext, "", "Loading...", true);
	            }
	            public void onPageFinished(WebView view, String url) {
	                try{
	                if (progrDialog.isShowing()) {
	                    progrDialog.dismiss();
	                    progrDialog = null;
	                }
	                }catch(Exception exception){
	                    exception.printStackTrace();
	                }
	            }
	        }); 
		
		
		
		
		web.loadUrl(url);
	}
	
	
	public static void LoadWebviewwithDialog(WebView web,Context context,String url)
	{
		
	}
	
}