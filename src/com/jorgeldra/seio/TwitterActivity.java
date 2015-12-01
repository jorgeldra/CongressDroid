package com.jorgeldra.seio;

import java.util.ArrayList;

import com.jorgeldra.seio.R;
import com.jorgeldra.seio.adaptador.ListFavouriteAdapter;
import com.jorgeldra.seio.adaptador.ListTweetsAdapter;
import com.jorgeldra.seio.adaptador.NavigationDrawerAdapter;
import com.jorgeldra.seio.entidad.Tweet;
import com.jorgeldra.seio.utils.AlertDialogManager;
import com.jorgeldra.seio.utils.ConnectionDetector;
import com.jorgeldra.seio.utils.ForceUpdateBBDD;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class TwitterActivity extends Activity implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener {
	
	//menu lateral
	private DrawerLayout mDrawer;
	private ActionBarDrawerToggle mDrawerToggle;
	private ListView mDrawerList;
	private String[] mSeioTitles;
		
	//busqueda
	private SearchView mSearchView;
	
	// Constants
    /**
     * Register your here app https://dev.twitter.com/apps/new and get your
     * consumer key and secret
     * */
    static String TWITTER_CONSUMER_KEY = "zJ3vyA7cI7UPSEPODz9A";
    static String TWITTER_CONSUMER_SECRET = "VQe9ikDJ0qkAbv1cQmO90gLcW7F0vXST5mJ6oZvauAg";
 
    // Preference Constants
    static String PREFERENCE_NAME = "twitter_oauth";
    static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
    static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
    static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";
 
    static final String TWITTER_CALLBACK_URL = "oauth://t4jsample";
    //static final String TWITTER_CALLBACK_URL = "http://www.seio2012.com/es/";
    // Twitter oauth urls
    static final String URL_TWITTER_AUTH = "auth_url";
    static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
    static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";
 
    // Login button
    Button btnLoginTwitter;
    // Update status button
    Button btnUpdateStatus;
    // Logout button
    Button btnLogoutTwitter;
    // EditText for update
    EditText txtUpdate;
    // lbl update
    TextView lblUpdate;
    TextView lblUserName;
 
    // Progress dialog
    ProgressDialog pDialog;
 
    // Twitter
    private static Twitter twitter;
    private static RequestToken requestToken;
     
    // Shared Preferences
    private static SharedPreferences mSharedPreferences;
     
    // Internet Connection detector
    private ConnectionDetector cd;
     
    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();
    
    //ArrayList de tweets+
    ArrayList<Tweet> tweets;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twitter);
		
		//menu lateral google
		mSeioTitles = getResources().getStringArray(R.array.seio_navigation_array);
				
		getActionBar().setDisplayHomeAsUpEnabled(true);

		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer,R.drawable.ic_drawer,R.string.drawer_open,R.string.drawer_close) {
		  
		};

		// Set the drawer toggle as the DrawerListener
		mDrawer.setDrawerListener(mDrawerToggle);
				
				
		//de esta manera utilizaria un adaptador propio
		NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(this,mSeioTitles);
				
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(this);
				
		//fin menu lateral google
		
		cd = new ConnectionDetector(getApplicationContext());
		 
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(TwitterActivity.this, "Error con conexión a Internet","Disculpe las molestias, es necesaria tener activa una conexión a Internet para poder seguir el congreso.", false);
            // stop executing code by return
            return;
        }
         
        // Check if twitter keys are set
        if(TWITTER_CONSUMER_KEY.trim().length() == 0 || TWITTER_CONSUMER_SECRET.trim().length() == 0){
            // Internet Connection is not present
            alert.showAlertDialog(TwitterActivity.this, "Twitter oAuth tokens", "Please set your twitter oauth tokens first!", false);
            // stop executing code by return
            return;
        }
        
     // All UI elements
        btnLoginTwitter = (Button) findViewById(com.jorgeldra.seio.R.id.btnLoginTwitter);
        btnUpdateStatus = (Button) findViewById(com.jorgeldra.seio.R.id.btnUpdateStatus);
        btnLogoutTwitter = (Button) findViewById(com.jorgeldra.seio.R.id.btnLogoutTwitter);
        txtUpdate = (EditText) findViewById(com.jorgeldra.seio.R.id.txtUpdateStatus);
        lblUpdate = (TextView) findViewById(com.jorgeldra.seio.R.id.lblUpdate);
        lblUserName = (TextView) findViewById(com.jorgeldra.seio.R.id.lblUserName);
 
        // Shared Preferences
        mSharedPreferences = getApplicationContext().getSharedPreferences("MyPref", 0);
        
        /**
         * Twitter login button click event will call loginToTwitter() function
         * */
        btnLoginTwitter.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View arg0) {
                // Call login twitter function
                loginToTwitter();
            }
        });
        
        /**
         * Button click event to Update Status, will call updateTwitterStatus()
         * function
         * */
        btnUpdateStatus.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View v) {
                // Call update status function
                // Get the status from EditText
                String status = txtUpdate.getText().toString();
                status = "#CongresoSEIO " + status; 
                // Check for blank text
                if (status.trim().length() > 0) {
                    // update status
                    new updateTwitterStatus().execute(status);
                } else {
                    // EditText is empty
                    Toast.makeText(getApplicationContext(),
                            "Por favor, introduzca un mensaje", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
 
        /**
         * Button click event for logout from twitter
         * */
        btnLogoutTwitter.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View arg0) {
                // Call logout twitter function
                logoutFromTwitter();
            }
        });
 
        /** This if conditions is tested once is
         * redirected from twitter page. Parse the uri to get oAuth
         * Verifier
         * */
        if (!isTwitterLoggedInAlready()) {
            Uri uri = getIntent().getData();
            if (uri != null && uri.toString().startsWith(TWITTER_CALLBACK_URL)) {
                // oAuth verifier
                String verifier = uri
                        .getQueryParameter(URL_TWITTER_OAUTH_VERIFIER);
 
                try {
                    // Get the access token
                    AccessToken accessToken = twitter.getOAuthAccessToken(
                            requestToken, verifier);
 
                    // Shared Preferences
                    Editor e = mSharedPreferences.edit();
 
                    // After getting access token, access token secret
                    // store them in application preferences
                    e.putString(PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
                    e.putString(PREF_KEY_OAUTH_SECRET,
                            accessToken.getTokenSecret());
                    // Store login status - true
                    e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
                    e.commit(); // save changes
 
                    Log.e("Twitter OAuth Token", "> " + accessToken.getToken());
 
                    // Hide login button
                    btnLoginTwitter.setVisibility(View.GONE);
 
                    // Show Update Twitter
                    lblUpdate.setVisibility(View.VISIBLE);
                    txtUpdate.setVisibility(View.VISIBLE);
                    btnUpdateStatus.setVisibility(View.VISIBLE);
                    btnLogoutTwitter.setVisibility(View.VISIBLE);
                     
                    // Getting user details from twitter
                    // For now i am getting his name only
                    long userID = accessToken.getUserId();
                    User user = twitter.showUser(userID);
                    String username = user.getName();
                     
                    // Displaying in xml ui
                    lblUserName.setText(Html.fromHtml("<b>Bienvenido " + username + "</b>"));
                } catch (Exception e) {
                    // Check log for login errors
                    Log.e("Twitter Login Error", "> " + e.getMessage());
                }
            }
        }else{
        	  //AccessToken accessToken = new AccessToken(mSharedPreferences.getString(PREF_KEY_OAUTH_TOKEN, ""),mSharedPreferences.getString(key, defValue),"");
        	 //Toast.makeText(getApplicationContext(),"Token session "+ mSharedPreferences.getString(PREF_KEY_OAUTH_TOKEN, "") , Toast.LENGTH_SHORT).show();
        	 
        	// Access Token 
             String access_token = mSharedPreferences.getString(PREF_KEY_OAUTH_TOKEN, "");
             // Access Token Secret
             String access_token_secret = mSharedPreferences.getString(PREF_KEY_OAUTH_SECRET, "");

             AccessToken accessToken = new AccessToken(access_token, access_token_secret);
             ConfigurationBuilder builder = new ConfigurationBuilder();
             builder.setUseSSL(true); 
             builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
             builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
             Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);
             // Getting user details from twitter
             // For now i am getting his name only
             long userID = accessToken.getUserId();
             User user;
			try {
				user = twitter.showUser(userID);
				 String username = user.getName();
				// Displaying in xml ui
	             lblUserName.setText(Html.fromHtml("<b>Bienvenido " + username + "</b>"));
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			//Twitter twitterTwo = TwitterFactory.getSingleton();
			Query query = new Query("#CongresoSEIO"); //hashtag a buscar
			query.setCount(100);
			//query.setLang("es");
			//query.setSince("2013-08-20"); 
	        //query.setUntil("2013-08-28"); 
			QueryResult result = null;
			try {
				result = twitter.search(query);
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//inicializamos arraylist de tweets
			tweets = new ArrayList<Tweet>();
			
			
			for (Status status : result.getTweets()) {
				Tweet tweet = new Tweet(status.getUser().getProfileImageURL(),status.getUser().getName(),"@"+status.getUser().getScreenName(),status.getCreatedAt(),status.getText());
				
				//Toast.makeText(getApplicationContext(),"@ " + status.getUser().getScreenName() + ":" + status.getText(), Toast.LENGTH_SHORT).show();
	        	 tweets.add(tweet);
				
			}
		
        	btnLoginTwitter.setVisibility(View.GONE);
        	lblUpdate.setVisibility(View.VISIBLE);
        	txtUpdate.setVisibility(View.VISIBLE);
        	btnUpdateStatus.setVisibility(View.VISIBLE);
        	btnLogoutTwitter.setVisibility(View.VISIBLE);
        	
        	//añadimos el arraylist de tweets al adaptador
        	initializeListTweets(tweets);
        }
	}

	/* menu horizontal barra de herramientas */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.navigation_drawer, menu); //barra herramientas abajo
		
		MenuItem searchItem = menu.findItem(R.id.menu_search);
		mSearchView = (SearchView) searchItem.getActionView();
		mSearchView.setQueryHint("Buscar...");
		mSearchView.setOnQueryTextListener(this);
		return true;
	}
	
	/**
     * Function to login twitter
     * */
    private void loginToTwitter() {
        // Check if already logged in
        if (!isTwitterLoggedInAlready()) {
            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setUseSSL(true); 
            builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
            builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
            Configuration configuration = builder.build();
             
            TwitterFactory factory = new TwitterFactory(configuration);
            twitter = factory.getInstance();
 
            try {
                requestToken = twitter.getOAuthRequestToken(TWITTER_CALLBACK_URL);
                this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(requestToken.getAuthenticationURL())));
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        } else {
            // user already logged into twitter
            Toast.makeText(getApplicationContext(),
                    "Ya se encuentra identificado en twitter", Toast.LENGTH_LONG).show();
            
        }
    }
    
    /**
     * Function to update status
     * */
    class updateTwitterStatus extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(TwitterActivity.this);
            pDialog.setMessage("Updating to twitter...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        /**
         * getting Places JSON
         * */
        protected String doInBackground(String... args) {
            Log.d("Tweet Text", "> " + args[0]);
            String status = args[0];
            try {
                ConfigurationBuilder builder = new ConfigurationBuilder();
                builder.setUseSSL(true); 
                builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
                builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
                 
                // Access Token 
                String access_token = mSharedPreferences.getString(PREF_KEY_OAUTH_TOKEN, "");
                // Access Token Secret
                String access_token_secret = mSharedPreferences.getString(PREF_KEY_OAUTH_SECRET, "");
                 
                AccessToken accessToken = new AccessToken(access_token, access_token_secret);
                Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);
                 
                // Update status
                twitter4j.Status response = twitter.updateStatus(status);
                 
                Log.d("Status", "> " + response.getText());
            } catch (TwitterException e) {
                // Error in updating status
                Log.d("Twitter Update Error", e.getMessage());
            }
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog and show
         * the data in UI Always use runOnUiThread(new Runnable()) to update UI
         * from background thread, otherwise you will get error
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),
                            "tweet enviado correctamente", Toast.LENGTH_SHORT)
                            .show();
                    // Clearing EditText field
                    txtUpdate.setText("");
                }
            });
        }
 
    }
 
    /**
     * Function to logout from twitter
     * It will just clear the application shared preferences
     * */
    private void logoutFromTwitter() {
        // Clear the shared preferences
        Editor e = mSharedPreferences.edit();
        e.remove(PREF_KEY_OAUTH_TOKEN);
        e.remove(PREF_KEY_OAUTH_SECRET);
        e.remove(PREF_KEY_TWITTER_LOGIN);
        e.commit();
 
        // After this take the appropriate action
        // I am showing the hiding/showing buttons again
        // You might not needed this code
        btnLogoutTwitter.setVisibility(View.GONE);
        btnUpdateStatus.setVisibility(View.GONE);
        txtUpdate.setVisibility(View.GONE);
        lblUpdate.setVisibility(View.GONE);
        lblUserName.setText("");
        lblUserName.setVisibility(View.GONE);
 
        btnLoginTwitter.setVisibility(View.VISIBLE);
    }
 
    
    /**
     * Check user already logged in your application using twitter Login flag is
     * fetched from Shared Preferences
     * */
    private boolean isTwitterLoggedInAlready() {
        // return twitter login status from Shared Preferences
        return mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
    }
 
    protected void onResume() {
        super.onResume();
    }
    
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	  //Handle the back button
	  if (keyCode == KeyEvent.KEYCODE_BACK) {
		  Intent i = new Intent(this, HomeActivity.class);
		
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
	    return true;
	  }

	  return super.onKeyDown(keyCode, event);
	}
    
 // métodos necesarios para implementar navbar lateral izquierda
 	@Override
 	public boolean onOptionsItemSelected(MenuItem item) {
 		switch (item.getItemId()) {
 			case android.R.id.home:
 				if (mDrawer.isDrawerOpen(mDrawerList)) {
 					mDrawer.closeDrawers();
 				} else {
 					mDrawer.openDrawer(mDrawerList);
 				}
 				return true;
 			case R.id.menu_refresh:
 				/*Intent i = new Intent(getApplicationContext(), ForceUpdateBBDD.class);
 				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
 				startActivity(i); */
 				finish();
 		        Intent intent = new Intent(TwitterActivity.this, TwitterActivity.class);
 		        overridePendingTransition(0, 0);
 		        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
 		        startActivity(intent);
 				break;
 		}

 		return super.onOptionsItemSelected(item);
 	}
    
    public void initializeListTweets(ArrayList<Tweet> arrayTweets){
    	TextView textEmpty = (TextView) findViewById(com.jorgeldra.seio.R.id.emptyListaTweets);
    	textEmpty.setVisibility(View.GONE);
    	ListView list = (ListView) findViewById(com.jorgeldra.seio.R.id.listaTweets);
    
    	ListTweetsAdapter adaptador = new ListTweetsAdapter(this,arrayTweets);
		list.setAdapter(adaptador);
    }

    @Override
	public boolean onQueryTextChange(String newText) {
	 
	     //Toast.makeText(this, newText, Toast.LENGTH_SHORT).show();
	 
	     return false;
	 }
	@Override
	public boolean onQueryTextSubmit(String text) {
	
		//Toast.makeText(this, "Searching for " + text, Toast.LENGTH_LONG).show();
		
		Intent i;
		i = new Intent(this, BusquedaActivity.class);
		i.putExtra("busqueda", text);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	
	   return false;
	}


	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int itemId, long l) {
		// TODO Auto-generated method stub
		//Toast.makeText(this, "Pulsado " + mSeioTitles[i], Toast.LENGTH_SHORT).show();
		
		Intent i;
		switch (itemId){
			case 0: 
					 i= new Intent(this, HomeActivity.class);
					//i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_INSTANCE);
					//i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					break;
			case 1:
					i = new Intent(this, ProgramActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					break;
			case 2:
					i = new Intent(this, MyProgramActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					break;
			case 3:
					i = new Intent(this, AutoresActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					break;
			case 4:
					i = new Intent(this, ConfPlenariasActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					break;
			case 5:
					i = new Intent(this, PremioRamiroActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					break;
			case 6:
					i = new Intent(this, FechasActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					break;
			case 7:
					//i = new Intent(this, SedesActivity.class);
					i = new Intent(this, LocalizacionesActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					break;
			case 8:
					i = new Intent(this, CuotasActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					break;
			case 9:
					i = new Intent(this, InscripcionActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					break;
			case 10:
					i = new Intent(this, ComoLlegarActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
					break;
			
				
		}
	
		mDrawer.closeDrawers();
		
	}
	
	//estos dos metodos siguientes son necesarios para poder cambiar el icono de la action bar
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		   super.onPostCreate(savedInstanceState);
		    // Sync the toggle state after onRestoreInstanceState has occurred.
		    mDrawerToggle.syncState();
	}
	
	
		
		


}
