package com.dullcomputers.a001;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class Motion extends ListActivity {
    /** Called when the activity is first created. */
    
	Context mCtx;SharedPreferences mReg;Editor mEdt;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        m01.sendEmptyMessage(1);
    }
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
	
		super.onListItemClick(l, v, position, id);
		Log.i(G,"click of " + position);
		
    }
    
    OnClickListener r001 = new OnClickListener(){
    	public void onClick(View v){
    		{Message ml = new Message(); Bundle bx = new Bundle(); bx.putInt("id",v.getId());
    		ml.setData(bx);
    		s001.sendMessage(ml);}
    	}
    };
    Handler s001 = new Handler(){
    	String K = "s001";
    	public void handleMessage(Message msg){
    		final Bundle msb= msg.getData();
    		Thread tx = new Thread(){int position = 1;
    		public void run(){
    			Bundle gb = msb;
    			//gb.getInt(K+".position", 1);gb.putInt(K+".position",position+1);
    			Message ml = new Message();
    			ml.setData(gb);
    			switch(position++){
    				case 1: c001.sendMessage(ml);break;
    				case 2: c002.sendMessage(ml);break;
    			}
    		}	
    		Handler c001 = new Handler(){public void handleMessage(Message msg){String R = "c001";Bundle gb = msg.getData();Log.i(G,K+" "+R);run();}};
    		Handler c002 = new Handler(){public void handleMessage(Message msg){String R = "c002";Bundle gb = msg.getData();Log.i(G,K+" "+R);run();}};
    	};tx.start();
    	}
    	
    };
    
    
    
    
    
    
    Handler m01 = new Handler(){
    	public void handleMessage(Message msg){
    		Context ctx = getApplicationContext();
    		
    		
    		if(getListAdapter() == null){
    		
    		
    		
    		RelativeLayout r1 = new RelativeLayout(ctx);r1.setId((int)SystemClock.uptimeMillis());r1.setLayoutParams(new ListView.LayoutParams(-1,-2)); 
    		r1.setBackgroundColor(Color.argb(90, 0, 237, 5));r1.setGravity(Gravity.CENTER); 
    		TextView t1 = new TextView(ctx);t1.setId((int)SystemClock.uptimeMillis());t1.setLayoutParams(new RelativeLayout.LayoutParams(-2,-2));
    		t1.setTextSize((float)21);t1.setText("~");
    		r1.addView(t1);
    		TextView t2 = new TextView(ctx);t2.setId((int)SystemClock.uptimeMillis());t2.setLayoutParams(new RelativeLayout.LayoutParams(-1,-2));
    		t2.setTextSize((float)21);t2.setText("~");t2.setPadding(3, 30, 3, 3);t2.setTextColor(Color.DKGRAY);
    		r1.addView(t2);
    		r1.setOnClickListener(r001);
    		getListView().addHeaderView(r1, null, false);
    		
    		
    		//r1.setOnClickListener(this);
    		
    		
    		{Message ml = new Message(); Bundle bx = new Bundle();bx.putInt("id", t1.getId());bx.putString("text", "Connect"); ml.setData(bx);setText.sendMessage(ml);}
    		{Message ml = new Message(); Bundle bx = new Bundle();bx.putInt("id", t2.getId());bx.putString("text", "Touch and Release to Engage"); ml.setData(bx);setText.sendMessage(ml);}
    		getListView().setSelectionFromTop(1, 20);getListView().requestFocusFromTouch();
    		
    		Uri contenturi = Uri.withAppendedPath(Uri.parse("content://com.dullcomputers"), "mail");
    		String[] columns = new String[]{"_id","status"};
    		String[] showing = new String[]{"_id","status"};
    		int[] to = new int[]{R.id.listrow_title,R.id.listrow_author};
            
    		Cursor c1 = SqliteWrapper.query(ctx, ctx.getContentResolver(), contenturi , 
            		columns,
            		"status > 0", // Future configurable time to expire seen and unread
            		null, 
            		"updated asc "+" limit 250");// + startrow + "," + numrows
    		
    		startManagingCursor(c1);
    		
            SimpleCursorAdapter entries = new SimpleCursorAdapter(ctx, R.layout.listrow, c1, showing, to);
    		
    		
    		
    		setListAdapter(entries);
    		getListView().requestFocusFromTouch();
    		}
            
    	}
    };		
    
    private static String G = "A001";
    Handler setText = new Handler(){
    	public void handleMessage(Message msg){
    		Bundle bd = msg.getData();
    		if(!bd.containsKey("id") || !bd.containsKey("text")){Log.e(G,"setText");return;}
    		int id = bd.getInt("id",0);
    		String txt = bd.getString("text");
    		TextView t1 = (TextView) findViewById(id);
    		t1.setText(txt);t1.invalidate();
    		if(bd.containsKey("color")){t1.setTextColor(bd.getInt("color"));}
    	}
    };
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		return super.onMenuItemSelected(featureId, item);
	}
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		// TODO Auto-generated method stub
		return super.onMenuOpened(featureId, menu);
	}
	
    	
    	
    	
    //load list
    
    //Menu

	
	
	
	private Handler logoly = new Handler(){public void handleMessage(Message msg){Bundle bx = msg.getData();int l = bx.getInt("l");String text = bx.getString("text");switch(l){case 2:Log.e(G,":"+text);break;case 3:Log.w(G,":"+text);break;default:Log.i(G,":"+
			
			text);break;}}};
	
	
	
	
	
			private Handler parsePage = new Handler(){
		    	int pRate = 55;
		    	String U_SAVED = "saved";
				public void handleMessage(Message msg){final Bundle gb = msg.getData();
				//if(parsePageSmooth > SystemClock.uptimeMillis()){
		    		//Message mx = new Message(); mx.setData(gb);mExamine.sendMessageDelayed(mx, pRate);return;
				//}
				//parsePageSmooth = SystemClock.uptimeMillis() + pRate;
		    	
		    	Thread pt = new Thread(){public void run(){Bundle bdl = new Bundle(gb);
		    	
		    	//String loc = bdl.getString("storloc");String procg = bdl.getString("procg");String titlr = bdl.getString("title");String dest = bdl.getString("murl");
		    	/*if(mReg.contains(loc) && mReg.contains(loc+U_SAVED)){
					if( mReg.getLong(loc+U_SAVED, 10) > System.currentTimeMillis()-bdl.getLong("age",1*180000)){
						{Message mxm = new Message(); Bundle bxb = new Bundle(); bxb.putString("title",procg + " " + titlr);bxb.putString("subtitle",(int)(System.currentTimeMillis() - mReg.getLong(loc+U_SAVED, 33))/1000+" Second Cache " + titlr +" for "+loc+".\n"+dest ); mxm.setData(bxb);easyViewerHandler.sendMessageDelayed(mxm,10);}
						{Message mxx = new Message(); mxx.setData(bdl);taskDone.sendMessage(mxx);}
						return;
					}
				}//*/
		
				final int stage = bdl.getInt("parsePage", 1);
				{Message ml = new Message(); Bundle bl = new Bundle();bl.putInt("l",3); bl.putString("text", "parsePage "+stage + "/3 "+bdl.getInt("parseBg",0)+"/"+bdl.getInt("parseBgl",0)); ml.setData(bl);logoly.sendMessageDelayed(ml,75);}
				bdl.putInt("parsePage", stage+1);
				
				{Message ml = new Message(); ml.setData(bdl);
		    		
		    		switch(stage){
		    		
		    		case 1:parseA.sendMessageDelayed(ml,pRate);break;
		    		case 2:parseB.sendMessageDelayed(ml,pRate);break;
		    		//case 3:parseC(bx);break;
		    		//case 4:parseD(bx);break;
		    		//case 5:parseE(bx);break;
		    		//case 6:parseT(bx);break;
		    		case 3:parsePageG.sendMessageDelayed(ml,pRate);break;
		    		default:break;//taskDone.sendMessageDelayed(ml,pRate);break;
		    		}}
		  	}};pt.start();
		    }
		    public Handler parseA = new Handler(){public void handleMessage(Message msg){final Bundle gb = msg.getData();
		    Thread tx = new Thread(){public void run(){Bundle bdl = new Bundle(gb);
		    //protected void parseA(Bundle bdl){
		    	//String procg = bdl.getString("procg");String titlr = bdl.getString("title");
		    //Thread tx = new Thread(){public void run(){Bundle bdl = new Bundle(gb);
	    	//try {Thread.sleep(pRate);} catch (InterruptedException e) {}
		    String mhp = bdl.getString("mhp");
		    	//String murl = bdl.getString("murl");
		    	//boolean getScripts = bdl.getBoolean("getscripts");
		    	String hatch = bdl.getString("storloc");
				if(mhp == null ){
					{Message mx = new Message(); Bundle bx = new Bundle();bx.putInt("l", 2);bx.putString("text", "Empty Page received by parsePage loc("+hatch+")"); mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
					mhp = "";
				}
		    	if( mhp.length() == 0 ){
		    		{Message mx = new Message(); Bundle bx = new Bundle();bx.putInt("l", 2);bx.putString("text", "Empty Page received by parsePage loc("+hatch+")"); mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
		    		mhp = " ";
		    	}
					//mReg = getSharedPreferences("Preferences",MODE_WORLD_WRITEABLE);mEdt = mReg.edit();
					
					
		    	{Message mx = new Message();mx.setData(bdl);parsePage.sendMessageDelayed(mx,pRate);}
		    }};tx.start();}};//public ContentValues rseLue;public ContentValues bel;public int starttime=10;
		    
		    public Handler parseB = new Handler(){public void handleMessage(Message msg){final Bundle gb = msg.getData();
		    Thread tx = new Thread(){public void run(){Bundle bdl = new Bundle(gb);
		    //protected void parseB(Bundle bdl){
			//String mhp = mhpIn;
			String mhp = bdl.containsKey("mhp")?bdl.getString("mhp"):"<html>No Content</html>";
			String murl = bdl.getString("murl");
			boolean getScripts = bdl.getBoolean("getscripts");//getScripts = false;
			//if( mhp == null || mhp.length() == 0){mhp = "";}
			int starttime = (int) SystemClock.uptimeMillis();
			mhp = mhp.replaceAll("<", ">:SPLIT:<");
			ContentValues glue = null; ContentValues oupId = new ContentValues();ContentValues rseLue = new ContentValues();
			String getBel = "";
			ContentValues bel = new ContentValues();ContentValues rseId = new ContentValues(); ContentValues rseName = new ContentValues();
			bdl.putInt("starttime", starttime);
			String g[] = mhp.split(">");
			int oc = 0;int ix = 0;String b12 = "";int ec = 0;String ript = "";int ctId = 0;
			//String tionName = "";String tionTion = "";
		
			int rmId = 0;int taCount=0; int tionCount = 0;int trCount = 0;int nstart = 0;String l = "";//String tle = "";String ctle = "";int tleCount = 0;
			String pable = ""; String bl = "";boolean formBody = false; boolean quote = true; boolean tick =  false; boolean selectBody = false; boolean tionBody = false;
			Set<Entry<String, Object>> xx = null;Object[] xb = null; String ta ="";String lt = "";String ee  = "";String[] at = null ;String lue ="";
			//Log.w(G,"Parsing HTML Page prefor took " + ((int)SystemClock.uptimeMillis() - starttime)  + " milliseconds."); 
			int tionCountA = 0;int downc = 0;boolean conb = true;long df = 34; long smooth = SystemClock.uptimeMillis() + 1750;int i = 0;//bdl.putInt("parseBgl", g.length);bdl.getInt("parseBg",0)
			String appkey = bdl.getString("appkey");
			for(i = 0; i < g.length; i++){
				if(SystemClock.uptimeMillis() > smooth){smooth = SystemClock.uptimeMillis() + 1750;//df = SystemClock.uptimeMillis() - smooth;
					
					if(appkey != null){
						int con = Color.argb(200, 0, 200, 50);
					if(conb){con = Color.argb(200, 0, 255, 50);conb=false;}else{conb=true;}
						{Message mx = new Message(); Bundle blx = new Bundle(); blx.putInt("id",Integer.parseInt(appkey));blx.putInt("color", con);mx.setData(blx);rlColorBg.sendMessageDelayed(mx,pRate);}bdl.putInt("doerprogress", bdl.getInt("doerprogress",0)+1);
						//{Message mx = new Message(); Bundle bx = new Bundle();bx.putInt("id", Integer.parseInt(bdl.getString("appkey")));bx.putInt("color", con);mx.setData(bx);rlColorBg.sendMessageDelayed(mx,pRate);}
					}else{
					{Message mx = new Message(); Bundle bx = new Bundle();bx.putInt("l", 3);bx.putString("text", "parsePage Parse Page at "+i+"/"+g.length); mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
					}//SystemClock.sleep(100);
					try{Thread.sleep(227);}//+(df<1000?df:34)
					catch(InterruptedException e){}
					smooth = SystemClock.uptimeMillis() + 1750;
				}
					l = g[i].replaceAll("\r", " ").replaceAll("\n", " ").replaceFirst(":SPLIT:<", "<").trim().replaceAll(" +", " ");//.trim();
				if(l.length() == 0){continue;
				}else 
				if( l.charAt(0) == '<' ){
				
					l = l.replaceFirst("<", "").trim();
					lt = l.replaceFirst(" .*", "").toLowerCase();
					if(lt.length() == 0){continue;}
					
					if(lt.contentEquals("/td") || lt.contentEquals("/tr") || lt.contentEquals("/th") || lt.contentEquals("/table") || lt.contentEquals("/div")){
						//if( lt.contentEquals("/table") ){ trCount++; }
						if(lt.contentEquals("/tr")){ //if(!ctle.contentEquals(tle)){tle = ctle;} 
							
						}
						continue;
					}else
					if(lt.contentEquals("td") || lt.contentEquals("tr") || lt.contentEquals("th") || lt.contentEquals("table") || lt.contentEquals("div")){
						//if( lt.contentEquals("table") ){tleCount++;}
						if( lt.contentEquals("tr") ){ trCount++;taCount = 0; }
						if( lt.contentEquals("td") || lt.contentEquals("th")){ if(trCount > 0){taCount++;} }
		    			continue;
		    		}
					else if( lt.contentEquals("br") || lt.contentEquals("caption") || lt.contentEquals("/caption") ||  lt.contentEquals("hr") || lt.contentEquals("h1") || lt.contentEquals("col") || lt.contentEquals("font") || lt.contentEquals("/col") || lt.contentEquals("/h1") || lt.contentEquals("/html") || lt.contentEquals("/body") || lt.contentEquals("/font") ){continue;}
		//|| 
					else if(lt.contentEquals("a") || lt.contentEquals("input") || lt.contentEquals("option") ){
						tionBody = true;
						tionCount++;
						//if(!ctle.contentEquals(tle)){trCount = 1;taCount = 1;tleCount++; ctle = tle; Log.i(G,"^^^^^^^^^^^^^^" + ctle);}
					}
					else if(lt.contentEquals("img") ){tionCount++;}
					else if(lt.contentEquals("/a") || lt.contentEquals("/option")){ tionBody = false;continue;}
					else if(lt.contentEquals("/form")){rmId=0;formBody = false;continue;}
					else if(lt.contentEquals("/select")){ctId=0;selectBody = false;continue;}
					else if(lt.contentEquals("form")){tionCount++;rmId=tionCount;formBody = true;}
					else if(lt.contentEquals("select")){tionCount++;ctId=tionCount;selectBody = true;}
					else if(lt.contentEquals("/label")){getBel = "";continue;}
					else if(lt.startsWith("!--") || lt.startsWith("=") || lt.length()>30){
						//Log.w(G, "almost got through as lt "+lt);
					continue;}
					
					ec = 0;
					glue = new ContentValues();
					quote = false; tick = false;
					ee = l.replaceFirst(lt, "").replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("\\\\\"", "&quot;").replaceAll("\\\\'", "&tick;").replaceAll(" =", "=").replaceAll("= ", "=");
					while(ee.length() > 0 && ee.contains("=")){
						at = ee.trim().split("=", 2);
						lue = at[1].trim();
						if(lue.charAt(0) == '"'){
							quote = true;tick = false;
							lue = lue.substring(1, lue.indexOf("\"", 1));
						}else if(lue.startsWith("'")){
							tick = true;quote = false;
							lue = lue.substring(1, lue.indexOf("'", 1));
							//lue = lue.replaceFirst("'", "");
							//lue = lue.replaceFirst("'.*", "");
						}else{quote = false; tick = false;
							//lue = lue.replaceFirst(" .*", "");
						if(at[1].indexOf(" ")>0){
							lue = lue.substring(0, lue.indexOf(" ", 1));
						}
						}
						ec++;glue.put(at[0], lue);
						//String ta = "Y";if( lt.contentEquals("a") || lt.matches("input|select") ){ ta = "A";}else if( tionBody ){ ta = "e";}
						//Log.i(G,"("+tleCount + ")"+ctle +" "+ trCount +"x"+taCount+ta+tionCount+"P"+rmId+"> "+lt+ " " + at[0] + "("+lue+")");
						if(ee.length() > at[0].length() + 2){
							
							if( quote ){
								ee = at[1].trim().substring(at[1].indexOf("\"",1)+1);
							//	ee = at[1].trim().substring(1);
							//	ee = ee.replaceFirst(".*?\"", "");//.substring(ee.indexOf(Character.getNumericValue('"'))).trim();
							}else if(tick){
								//ee = at[1].trim().substring(1);
								//ee = ee.substring(ee.indexOf(Character.getNumericValue('\''))).trim();
								ee = at[1].trim().substring(at[1].indexOf("'",1)+1);
							}else{
								if(at[1].indexOf(" ")>0){
								ee = at[1].trim().substring(at[1].indexOf(" "));}
							//	ee = at[1].trim();
							//	if(ee.replaceFirst(".*? ","").length() < ee.length()){
							//	ee = ee.replaceFirst(".*? ","");
							else{ee = "";}
							}
						}else{
							ee = "";
						}
		
						//Log.i(G,"" + at[0] + ": >>>>" + lue + "<<<< :" + ee + " tick("+tick+") quote("+quote+")");
						//Log.w(G,ec+" " + ee);
						//if(ec > 100){break;}
					}
					
					if( glue.containsKey("id") ){ rseId.put(glue.getAsString("id"), tionCount);}	
					if( glue.containsKey("name")){ rseName.put(glue.getAsString("name"), tionCount); }
					
					if( lt.contentEquals("label") && glue.containsKey("for") ){ getBel = glue.getAsString("for");//Log.i(G,"for " + getBel + " " + l);
					continue;}
					
					ta = "Y";if( lt.contentEquals("a") || lt.contentEquals("input") || lt.contentEquals("option") || lt.contentEquals("form") || lt.contentEquals("select") ){ ta = "A";tionCountA++;}else if( tionBody ){ ta = "e";}
					//atDom[eCrementCount] = glue;
					//String ne = "("+tleCount + ")("+ctle.substring(0, ctle.length() < 10?ctle.length():10) +") "+ trCount +"x"+taCount+ta+tionCount+"c"+(ctId>0?ctId:rmId)+"> "+lt;
					//if( tionBody && tionName.length() == 0 ){ if(glue.containsKey("title")){tionName = glue.getAsString("title");}if(glue.containsKey("alt")){tionName = glue.getAsString("alt");}if(tionName.length() > 0){Log.i(G,"))))))))))" + tionName);}}
					 xx = glue.valueSet();
					xb = xx.toArray();String gluelist = lt+" ";
					//if(oupId.containsKey(""+trCount)){
						//int tionGroup = oupId.getAsInteger("" + trCount);
						//gluelist += "group="+tionGroup+"%0a";}
					//else{oupId.put(("" + trCount), tionCount);gluelist += "group="+tionCount+"%0a";}
					gluelist += "group=" + trCount+";HS;";
					if(formBody ){gluelist += "formid="+rmId+";HS;";}
					if(selectBody ){gluelist += "selectid="+ctId+";HS;";}
					int bi = 0;for( bi = 0; bi < xb.length;bi++){
						gluelist += xb[bi] + ";HS;";
					}//Log.w(G,tleCount+","+tionCount+","+trCount+","+ta+") "+ne);
					
					
					if( lt.contentEquals("img") ){
					
						//String hn = glue.containsKey("alt")?";alt="+glue.getAsString("alt"):"";
						//hn += glue.containsKey("src")?";src="+glue.getAsString("src"):"";
						//hn += glue.containsKey("class")?";class="+glue.getAsString("class"):"";
						//bl = rseLue.containsKey(tionCount+",c") ? rseLue.getAsString(tionCount+",c").trim().replaceAll(" +", " ") : "";
						//if(hn.length() > 0 ){
							rseLue.put((tionCount)+",A", gluelist);tionCountA++;
						//}
					}else	if( lt.contentEquals("script") ){
						ript = "";
						nstart = (int) SystemClock.uptimeMillis();
						if(glue.containsKey("src")){
							String url = fullpath(glue.getAsString("src"), murl);
							ript = "get url " + url;// + glue.getAsString("src");
							
							if(getScripts){
							
							/*
							if( !url.startsWith("http") && murl.length() > 10){
								int tx = murl.contains("?") ? murl.indexOf("?") : murl.length(); String turl = murl.substring(0,tx);
							Log.e(G,"parse; get src url("+url+") murl("+murl+") turl("+turl+")");	
							if( !url.startsWith("/") ){
								int d = turl.lastIndexOf("/") > -1 ? turl.lastIndexOf("/") : turl.length(); url = turl.substring(0, d) + "/" + url;}
								else{int d = turl.indexOf("/", 10) > -1 ? turl.indexOf("/", 10) : turl.length();url = turl.substring(0, d) + url;}
							}//*/
							
							
							//Log.i(G,"parse; get script url("+url+") mUrl("+httpUrl+") ");
							HttpGet httpGet = new HttpGet(url);
				        	//logoff.owa?canary=
				        	//String cc = mHP;
				        	String mHP = "";
				        	String urlcache = url.replaceAll("/|:|\\.", "");
							if(mReg.contains("cache"+urlcache)){
				        		long gh = mReg.getLong("cache"+urlcache, 0);
				        		if(gh > System.currentTimeMillis() - 60 * 60000){
				        			mHP = mReg.getString("cache"+urlcache+"data", "");
				        			//Log.w(G, "parse; get script using cache of " + url + " " + (System.currentTimeMillis() - gh)/1000 + " seconds old.");
				        		}
				        	}
				        	if(mHP.length() == 0){
				        		mHP = url;
				        		String loc = "parse"+SystemClock.uptimeMillis();
				        		downc ++;
				        		String[] reply = safeHttpGet(G + ":rsePage:933", httpGet, loc);
				        		mHP = mReg.getString(loc,"");
				        		//mEdt.remove(loc);mEdt.remove(loc+"url");
				        		//mEdt.commit();
				        		
		
					      		//{Message mx = new Message(); Bundle bxb = new Bundle();
					      		//bxb.putString("remove", loc+","+loc+"url");
					      		//mx.setData(bxb);setrefHandler.sendMessageDelayed(mx, 50);}
				        		
				        		//Log.w(G, "parse; get script reply " + reply[0]);
				        	
				        	
		    					//mEdt.putString(, mHP);
		    					//mEdt.putLong(, System.currentTimeMillis());
		    					//mEdt.commit();
		    					
		
					      		/*{Message mx = new Message(); Bundle bxb = new Bundle();
					      		bxb.putString("long", "cache"+urlcache);bxb.putLong("cache"+urlcache,System.currentTimeMillis());
					      		bxb.putString("string", "cache"+urlcache+"data");bxb.putString("cache"+urlcache+"data",mHP);
					      		mx.setData(bxb);setrefHandler.sendMessageDelayed(mx, 50);}//*/
		    					mEdt.putLong("cache"+urlcache, System.currentTimeMillis());
		    					mEdt.putString("cache"+urlcache+"data", mHP);
		    					mEdt.commit();
				        	}
				        	ript = mHP;//*///mHP = cc;
							}
							}else{
						for(int ri=i+1;ri<g.length;ri++){
							if(g[ri].toLowerCase().contains("/script")){i = ri-1;break;}if(ript.length() == 0){ript = g[ri];continue;}
							ript += new String(">"+g[ri]).replaceAll(">:SPLIT:<", "<");
						}}
						//String[] rl = ript.split("\n");for(int bb = 0; bb < rl.length; bb++){Log.i(G,"SCRIPT: " + rl[bb]);}
						//Log.i(G,"SCRIPT " + ript);
						tionCount++;
						rseLue.put(tionCount+",A", ":SCRIPT:"+ript.trim());int d = ((int) SystemClock.uptimeMillis() - nstart);
						tionCountA++;
						starttime += d;
						//Log.i(G,"parse; Parsing HTML Page Stage 1 detour took " + (d) + " milliseconds and was removed from the total parse time.");
						continue;
					}else{
					
						rseLue.put(tionCount+","+ta, gluelist.replaceAll("[\\s\\n\\t\\r]+$|^[\\s\\n\\t\\r]+","").replaceAll("\n", "%0a"));
							
					}
					//if(lt.matches("input|select")){tionBody = false;}
					
					//int ec = 0;
					
					
					
					
						
					++oc;/*
					if(tionBody){
						
					
						Log.w(G,"("+tleCount+")"+ctle+" "+trCount+"x"+taCount+"B"+tionCount +"P"+rmId+ "> " + lt + " ("+l.replaceAll("&quot;", "\"").replaceAll("&trim;", "\"")+")");
					}else{
						Log.w(G,"("+tleCount+")"+ctle+" "+trCount+"x"+taCount+"N"+tionCount+"P"+rmId+"> " + lt + " ("+l.replaceAll("&quot;", "\"").replaceAll("&trim;", "\"")+")");
		    				
					}//*/
					
				}else{
					//content
					//b += "\t";
					pable = l.replaceAll("&nbsp;", "").trim().replaceAll(" +", " ");
					if(getBel.length() > 0){
						if( pable.length() > 0 ){
							bel.put(getBel, pable);getBel = "";//Log.w(G,"found bel " + getBel + " " + pable);
						}
						continue;
					}/*
					if(tionBody){oc++;
						//if( tionBody && tionName.length() == 0 ){tionName = l.replaceAll("&nbsp;", "").trim().replaceAll(" +", " "); Log.i(G,"//////////" + tionName);}
						Log.w(G,"("+tleCount + ")("+ctle.substring(0, ctle.length() < 10?ctle.length():10) +") "+trCount+"x"+taCount+"C"+tionCount+"c"+(ctId>0?ctId:rmId)+"> " + pable );
					}else{//if(trCount > 0){taCount++;}
						tle = (pable.length() > 0 && trCount <= 1) ? pable : ctle;/*if( !tle.contentEquals(ctle)) {if( trl.length() > 0 && b.contains(trl) ){tionCount = 0;}}*/
						//oc++;Log.w(G,"("+tleCount + ")("+ctle.substring(0, ctle.length() < 10?ctle.length():10) +") "+" "+trCount+"x"+taCount+"c"+(ctId>0?ctId:rmId)+"> " + pable );//+b
					//}
					//Log.w(G,tleCount+","+tionCount+","+trCount+",c) "+ pable);
					//if(oupId.containsKey(tleCount + "," + trCount)){int tionGroup = oupId.getAsInteger(tleCount + "," + trCount);pable += "group="+tionGroup+"%0a";}
					bl = rseLue.containsKey(tionCount+",c") ? rseLue.getAsString(tionCount+",c").trim().replaceAll(" +", " ") : "";
					rseLue.put(tionCount+",c", bl + pable + "%0a");
					
				}//if( oc > 2000 ){break;}
				
			}
			
			{Message mx = new Message(); Bundle bx = new Bundle();bx.putInt("l", 2);bx.putString("text", "Parse Page end at "+i+"/"+g.length + " moving onto tion list"); mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
			//if( i < g.length ){
				//bdl.putInt("parsePage", bdl.getInt("parsePage")-1);
				//bdl.putInt("parseBg", i);
			//	{Message mx = new Message();mx.setData(bdl);parsePage.sendMessage(mx);}
			//return;
			//}
				
			boolean bo = false;String ts = ""; int ncnt = 0;
			String[] tionList = new String[tionCountA+2];String addinfo = "";int depthc = 0;int trc = 34;int tCT = 0;
			//for(int tlec = 0; tlec < 100; tlec++){
				//if( !rseLue.containsKey(tlec+",1,1,A") ){continue;}
				//for(int tic = 0; tic < 100; tic++){
					//if( !rseLue.containsKey(tlec+","+tic+",1,A") ){continue;}
					for(trc = 0; trc < tionCountA+2; trc++){
						if( !rseLue.containsKey(trc+",A") ){ncnt++; if( ncnt > 5 ){break;} continue;}
						if(rseLue.containsKey(trc + ",A")){addinfo = "";depthc = 0;
							String tion = rseLue.getAsString(trc+",A");
							if( tion.contains("id=") ){
								String id = tion.replaceFirst(".*id=","").replaceFirst(";HS;.*","");
								//String id=tion.substring(tion.indexOf("=",tion.indexOf("id=")), tion.indexOf("%0a", tion.indexOf("id=")));
								//Log.w(G,"found id: " + id);
								if(bel.containsKey(id)){addinfo += "label="+bel.getAsString(id);//Log.w(G,"parse; found label #1 id("+id+") label("+bel.getAsString(id)+")");
								}
							}else if( tion.contains("name=") ){
								String id = tion.replaceFirst(".*name=","").replaceFirst(";HS;.*","");
								//String id=tion.substring(tion.indexOf('=',tion.indexOf("name=")), tion.indexOf("%0a", tion.indexOf("name=")));
								//Log.w(G,"found name: " + id);
								if(bel.containsKey(id)){addinfo += "label="+bel.getAsString(id);//Log.w(G,"parse; found label #2 id("+id+") label("+bel.getAsString(id)+")");
								}
							}
							//Log.w(G,"----------- rseReview " + trc + ": " + rseLue.getAsString(trc + ",A"));
							if(rseLue.containsKey(trc + ",e")){
								String c = rseLue.getAsString(trc + ",e").replaceAll("&nbsp;", " ").trim().replaceAll(" +", " ");
								//Log.w(G,"----------- rseReview " + trc + "+ " + c);
								if( c.length() > 0 ){addinfo += "content"+ (++depthc) +"="+c+";HS;";}
							}
							if(rseLue.containsKey(trc + ",c")){
								String c = rseLue.getAsString(trc + ",c").replaceAll("&nbsp;", " ").trim().replaceAll(" +", " ");
								//Log.w(G,"----------- rseReview " + trc + "++" + c);
								if( c.length() > 0 ){addinfo += "content"+ (++depthc) +"="+c+";HS;";}
							}
							tionList[tCT++] = tion + addinfo;
						}
						if(bo){break;}}
					//if(bo){break;}}
			//if(bo){break;}}
		    	//bdl.putStringArray("tionlist", tionList);
					{Message mx = new Message(); Bundle bx = new Bundle();bx.putInt("l", 2);bx.putString("text", "Parse Page end "+i+"/"+g.length+" forming cache from " +tionList.length +" entries"); mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
					String cache = "";int nct = 2;//:
		    	for(int b= 0; b < tCT; b++){
		       		if( tionList[b] == null) {nct++;if(nct > 50){break;}continue;}
		       		cache += tionList[b]+"\n";
		       	}
		bdl.putString("cache",cache);bdl.putInt("downc", downc);
		{Message mx = new Message();mx.setData(bdl);if(!parsePage.sendMessage(mx)){parsePage.sendMessageDelayed(mx, pRate);}}	
		    }};tx.start();
		    }};
		    protected void parseC(Bundle bdl){
		    	
		    	{Message mx = new Message();mx.setData(bdl);parsePage.sendMessageDelayed(mx,pRate);}
		    }//public String[] tionList;
		protected void parseD(Bundle bdl){
			
		    	{Message mx = new Message();mx.setData(bdl);parsePage.sendMessageDelayed(mx,pRate);}
		    }
		protected void parseE(Bundle bdl){
			
			{Message mx = new Message();mx.setData(bdl);parsePage.sendMessageDelayed(mx,pRate);}
		}
		protected void parseT(Bundle bdl){
			
			{Message mx = new Message();mx.setData(bdl);parsePage.sendMessageDelayed(mx,pRate);}
		}
		
		public Handler parsePageG = new Handler(){public void handleMessage(Message msg){final Bundle gb = msg.getData();
	    Thread tx = new Thread(){public void run(){Bundle bdl = new Bundle(gb);
		
		//protected void parsePage(final Bundle bdl){//final String mhpIn, final String murl, final boolean getScripts, final String hatch) {
				// TODO Auto-generated method stub
				
				//Thread ps = new Thread(){public void run(){
					String procg = bdl.getString("procg");String titlr = bdl.getString("title");
				//String mhp = bdl.getString("mhp");
		    	String murl = bdl.getString("murl");
		    	
				
		    	//Log.i(G,"parse; Parsing HTML Page Stage 1 took " + (SystemClock.uptimeMillis() - starttime) + " milliseconds.");
		    	
		    	//rseName(name,tionid)
		    	//rseId(id,tionid)
		    	//bel(name/id,Lable)
		
		/*				
						Log.w(G,"Parsing HTML Page Stage 3 in for " + (SystemClock.uptimeMillis() - starttime) + " milliseconds.");
						
						for(int b121 = 0; b121 < tionList.length; b121++){
							if( tionList[b121] == null ){continue;}
			        		Log.i(G,"Tion Parsed; " + b121 + ": " + tionList[b121]);
			        	}
						//*/
				//String[] tionList = bdl.getStringArray("tionlist");
				String hatch = bdl.getString("storloc");
		    	
				{Message mx = new Message(); Bundle bx = new Bundle();bx.putInt("l", 3);bx.putString("text", "Parse Page end  writing " + hatch); mx.setData(bx);logoly.sendMessageDelayed(mx,75);}
		    	if(bdl.containsKey("cache")){//2"+i+"/"+g.length+"
		    	String cache = bdl.getString("cache");		
		    	{Message ml = new Message(); Bundle bl = new Bundle(); bl.putString("text", "preparing cache for parse consumers " + hatch);bl.putInt("l", 3);ml.setData(bl);logoly.sendMessageDelayed(ml,75);}
						mReg = getSharedPreferences("Preferences", MODE_WORLD_WRITEABLE);mEdt = mReg.edit();
						mEdt.putString(hatch, cache);mEdt.commit();mEdt.putString(hatch+"url", murl);mEdt.commit();mEdt.putLong(hatch+U_SAVED, System.currentTimeMillis());mEdt.commit();
						
						//{Message mx = new Message();Bundle bxb = new Bundle(); bxb.putString("string", hatch+","+hatch+"url");bxb.putString(hatch+"url",murl);bxb.putString(hatch, cache);mx.setData(bxb);setrefHandler.sendMessageDelayed(mx, 50);}
						{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("title", procg);bx.putString("subtitle",titlr+" took " + (SystemClock.uptimeMillis() - bdl.getInt("starttime")) + " msecs "+(bdl.getInt("downc")>0?"assets("+bdl.getInt("downc")+")":"")+" "+(mReg.contains(hatch)?" and saved":"error when saving")+" "+cache.length()+"b");bx.putInt("l",1);mx.setData(bx);easyViewerHandler.sendMessageDelayed(mx,pRate);}
						
				}else{
					Log.e(G,"tionlist cache support");
					mReg = getSharedPreferences("Preferences", MODE_WORLD_WRITEABLE);mEdt = mReg.edit();
					String oloc = bdl.getString("loc");
					if(oloc != null && mReg.contains(oloc) && mReg.contains(oloc+U_SAVED)){
						mReg = getSharedPreferences("Preferences", MODE_WORLD_WRITEABLE);
						mEdt = mReg.edit();
						mEdt.remove(oloc);mEdt.remove(oloc+U_SAVED);mEdt.remove(oloc+"url");mEdt.commit();
						Log.w(G,"Removed source cache for future freshness");
					}
				}{Message mx = new Message();mx.setData(bdl);parsePage.sendMessageDelayed(mx,pRate);}
			//}};ps.start();
						
			}};tx.start();	
		}};};
	
	private Handler listStudy = new Handler(){//Bundle bx;
		int pRate = 75;			
		String U_SAVED = "saved";
		
		public void handleMessage(Message msg){
						Bundle bl = msg.getData();String procg = bl.getString("procg");String titlr = bl.getString("titlr");
						final int stage = bl.getInt("listStudy",1);
						if(bl.containsKey("listStudyRun") ){long g = System.currentTimeMillis() - bl.getLong("listStudyRun");if(g > 300){Message ml = new Message(); Bundle blm = new Bundle(); blm.putString("text","review process " + (stage -1) + " took " + g + " msecs.");blm.putInt("l", 3);ml.setData(blm);logoly.sendMessageDelayed(ml, pRate);}}
						bl.putLong("listStudyRun", System.currentTimeMillis());	
						
						//{Message ml = new Message();Bundle bx = new Bundle();bx.putString("text",lgen("listStudy",bl)+stage+"/6 "+bl.getInt("hat",1)+"/"+bl.getInt("hatm", 1));ml.setData(bx);logoly.sendMessageDelayed(ml,72);}
						bl.putInt("listStudy", bl.getInt("listStudy")+1);
						final Bundle bx = new Bundle(bl);
						Thread tx = new Thread(){public void run(){
							{Message ml = new Message();ml.setData(bx);
							switch (stage){
						case 1: run01.sendMessageDelayed(ml,pRate);break;
						//case 2: run02(bx);break;
						case 2: run03.sendMessageDelayed(ml,pRate);break;
						//case 4: run04(bx);break;
						//case 5: run05(bx);break;
						case 3: run06.sendMessageDelayed(ml,pRate);break;
						default:break;//{Message dm = new Message();dm.setData(bx);taskDone.sendMessageDelayed(dm,pRate);}break;
							}}
						}};tx.start();
						//bx = msg.getData();Bundle bm = new Bundle(bx);run();
						//{Message dm = new Message();dm.setData(bm);taskDone.sendMessageDelayed(dm, 100);}
					//}public void run00(Bundle bx){
						//{Message dm = new Message();dm.setData(bx);taskDone.sendMessageDelayed(dm, 100);}
					}
					public Handler run01 = new Handler(){public void handleMessage(Message msg){final Bundle gb = msg.getData();	
					
					Thread tx = new Thread(){public void run(){Bundle bx = new Bundle(gb);
					//public void run01(Bundle bx){
					mReg = getSharedPreferences("Preferences", MODE_WORLD_WRITEABLE);mEdt = mReg.edit();	
					final String loc = bx.containsKey("loc")?bx.getString("loc"):"";final String typr = bx.getString("type"); final String procg = bx.containsKey("procg")?bx.getString("procg"):"Harbor";final String titlr = bx.containsKey("title")?bx.getString("title"):"Singer"; final String strong = bx.containsKey("strong")?bx.getString("strong"):"normal";
						final String appkey =bx.getString("appkey");// (loc.length()>0?loc:(titlr.length()>0?titlr:typr));
						String mhp = bx.getString("mhp");
						final String murl = bx.getString("murl");
						String origtypr = bx.getString("origtypr");
						//Thread tx = new Thread(){public void run(){
					//private void listStudy(String mhp, String murl) {
						// TODO Auto-generated method stub
						
						bx.putInt("hat", 0);if(mhp == null){mhp = "\n \n \n";}
						bx.putStringArray("h",mhp.split("\n"));//easyStatus("Processing Contact " + h.length);
			           	if(mhp.contains("onClick=return onClkRcpt")){
			           		//mReg = getSharedPreferences("Preferences",MODE_WORLD_WRITEABLE);mEdt = mReg.edit();
			    			//mEdt.putLong("contactsave", System.currentTimeMillis());
			    			//mEdt.putString("contactparsed", mhp);mEdt.putString("contacturl", murl);
			    			//mEdt.commit();
			    			/*{Message mx = new Message(); Bundle bxb = new Bundle(); 
			     			bxb.putString("long", "contactsave");
			     				bxb.putLong("contactsave", System.currentTimeMillis());
			     			bxb.putString("string", "contactparsed,contacturl");
			     				bxb.putString("contacturl",murl);
			     				bxb.putString("contactparsed", mhp);
			     			mx.setData(bxb);setrefHandler.sendMessageDelayed(mx, 50);}//*/
			    		}//
			    		if(!bx.containsKey("pageconnectknow") ){
			           	//bx.putString("pageconnectknow", "");
			    		Log.e(G, "Page Connect Know Empty, how to know? " + procg + " " + titlr + " " + typr);
			    		//easyStatus("Received");
			    		
			    		}else	if( mhp.contains(bx.getString("pageconnectknow")) && !murl.contains("auth/logon")){//,"checkmessages"checkmessages
						easyStatus("Connected");
						mEdt.putLong("connect", System.currentTimeMillis());
						mEdt.putLong("connectsaved", System.currentTimeMillis());	 
						mEdt.commit();
				        
				        /*{Message mx = new Message(); Bundle bxb = new Bundle(); 
				 			bxb.putString("long", "connect");
				 				bxb.putLong("connect", System.currentTimeMillis());
				 				bxb.putLong("connectsaved", System.currentTimeMillis());
				 			mx.setData(bxb);setrefHandler.sendMessageDelayed(mx, 50);}//*/
			           	}else{
						if(!bx.containsKey("listStudy-connect")){bx.putInt("listStudy-connect", 1);
							easyStatus("Not Connected, Connecting");
							mEdt.remove(loc+U_SAVED);
							mEdt.remove("connect");
							mEdt.commit();
							/*{Message mx = new Message(); Bundle bxb = new Bundle(); 
							bxb.putString("remove", "connect,"+loc+U_SAVED);
								//bxb.putLong("connect", System.currentTimeMillis());
								//bxb.putLong("cache_email_busy", System.currentTimeMillis());
							mx.setData(bxb);setrefHandler.sendMessageDelayed(mx, 50);}//*/
							//{Message ml = new Message();Bundle cc = new Bundle();cc.putString("procg", "Connect");cc.putString("title", "Connect Logon");cc.putString("type", "connectlogon");
							//ml.setData(cc);cc.putInt("solo", 1);mExamine.sendMessageDelayed(ml,72);}
							}else{
								//{Message ml = new Message();Bundle cc = new Bundle();cc.putString("procg", bx.getString("procg"));
								//cc.putString("title", bx.getString("title"));cc.putString("type", bx.getString("type"));
								//ml.setData(cc);cc.putInt("solo", 1);mExamine.sendMessageDelayed(ml);}
								return;
							}
			           		
			           	}
			           		
			           		{Message dm = new Message();dm.setData(bx);listStudy.sendMessageDelayed(dm,pRate);}
					}};tx.start();
					}};
					
					public Handler run02 = new Handler(){public void handleMessage(Message msg){Bundle bx = msg.getData();
					//public void run02(Bundle bx){
						/*/if(bx.getString("mhp").contains("checkmessages") || bx.getString("mhp").contains("Manage Calendars") || bx.getString("mhp").contains("Manage Contacts") ){
				        	 
						}else{
							
						}//*/
						{Message dm = new Message();dm.setData(bx);listStudy.sendMessageDelayed(dm,pRate);}
					}};
					public Handler run03 = new Handler(){public void handleMessage(Message msg){final Bundle gb = msg.getData();	boolean conb = true;
					
					Thread tx = new Thread(){public void run(){Bundle bx = new Bundle(gb);
					//public void run03(final Bundle bdl){Bundle bx = new Bundle(bdl);
					//if(!bx.containsKey("table")){Log.e(G,"No table reported with app doer");return;}	
					String murl = bx.getString("murl");long sm = SystemClock.uptimeMillis()+1750;
						String[] h = bx.getStringArray("h");int b = bx.getInt("hat",0);long bt = SystemClock.uptimeMillis()+3200;bx.putInt("hatm", h.length);
						int groupid = 34;
						
						int lpg = 0;//incomming
						Bundle rr = new Bundle();
						boolean conb = true;
						for(; b < h.length && bt > SystemClock.uptimeMillis(); b++){
			        		if( h[b] == null) {continue;}
			        		
			        		if(sm < SystemClock.uptimeMillis()){
			        			int con = Color.argb(100+(10*bx.getInt("doerprogress",0)+1), 20, 80, 200);
							if(conb){con = Color.argb(100+(10*bx.getInt("doerprogress",0)+1), 20, 80, 200);conb=false;}else{conb=true;}
							
							if(bx.containsKey("appkey")){
							//{Message mx = new Message(); Bundle bl = new Bundle(); bl.putInt("id",Integer.parseInt(appkey));bl.putInt("color", Color.argb(100+(10*bx.getInt("doerprogress",0)+1), 20, 80, 200));mx.setData(bl);rlColorBg.sendMessageDelayed(mx,pRate);}
			        			{Message mx = new Message(); Bundle bxl = new Bundle();bxl.putInt("id", Integer.parseInt(bx.getString("appkey")));bxl.putInt("color", con);mx.setData(bxl);rlColorBg.sendMessageDelayed(mx,pRate);}bx.putInt("doerprogress", bx.getInt("doerprogress",0)+1);
							}
			        			try {
									Thread.sleep(272);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}sm = SystemClock.uptimeMillis()+1750;
			        		}
			        		
			        		
			        		//{Message mx = new Message(); Bundle bxl = new Bundle();bxl.putString("text",bx.getString("table")+"; line("+h[b]+")");bxl.putInt("l",1);mx.setData(bxl);logoly.sendMessageDelayed(mx,pRate);}
			        		//Log.i(G,"> " + b + ": " + h[b]);  && !bx.getString("table").contains("event")
			    			
							
							
							
							
							
							
			        		//{Message ml = new Message(); Bundle bl = new Bundle(); bl.putInt("l", 3); bl.putString("text",h[b]); ml.setData(bl); logoly.sendMessageDelayed(ml, pRate);}
							
							if(h[b].contains(";id=lnkLstPg")){String lps = h[b].replaceAll(".*return onClkPg..", "").replaceAll("'.*", "");lpg = Integer.parseInt(lps!=null&&lps.length()>0?lps:"1");//Log.w(G,"found page limit at "+lpg);
			    			//mEdt.putInt("contactpages", lpg);mEdt.commit();
			    			//if(bx.containsKey("loc") && bx.getString("loc").matches("show")){continue;}
			    			
							
			    			
			    			
			    			
			    			
			    			
			    			
			    			
			    			
			    			}if(bx.getString("table")!=null && bx.getString("table").length() > 0 && lpg > 0){
			    				mReg = getSharedPreferences("Preferences", MODE_WORLD_WRITEABLE);mEdt = mReg.edit();mEdt.putInt("pages", lpg);mEdt.commit();
			    				//{Message mx = new Message(); Bundle bxb = new Bundle();bxb.putString("int", bx.getString("table")+"pages");bxb.putInt(bx.getString("table")+"pages", lpg);mx.setData(bxb);setRef.sendMessageDelayed(mx, 50);}
							
			    				 lpg = 0;
			    				
			    			}
			    			
			    			
			    			
			    			
			    			
			    			
			    			
			    			
			    			if(h[b].contains("return onClkM")){rr = new Bundle();
			        		rr.putString("table", "event");bx.putString("table",rr.getString("table"));
			        		rr.putString("path", Uri.withAppendedPath(DataProvider.CONTENT_URI,rr.getString("table")).toString());
			        		String title = "";String subtitle = "";String startdate = "";String enddate = "";
			        			String itemid = ""; 
			        			
			        			
			        			String gg = h[b].replaceAll(".*group=", "");gg = gg.replaceAll(";.*", "");
								groupid = Integer.parseInt(gg);
								for(int sgi=b+5;sgi > b-15;sgi--){
			    					if( sgi < 0 || h[sgi] == null){continue;}
			    					if( !h[sgi].contains("group="+groupid+";")){continue;}
								
			    					if(h[sgi].contains("return onClkM") ){
			    					itemid = h[sgi].replaceFirst(".* onClkM..", "");itemid = itemid.replaceAll("'.*", "");itemid = itemid.replaceAll("/", "%2f").replaceAll("\\+", "%2b");
			    					title = h[sgi].replaceFirst(".*title=", "");title = title.replaceFirst(";..;.*", "");
			    					subtitle = h[sgi].replaceFirst(".*content1=", "");subtitle = subtitle.replaceFirst(";..;.*", "");
			    					if(title.contains(" , ")){
			    					String bda = title.replaceAll(" , .*", "");
			    					if(bda.contains(" - ")){ String[] dd = bda.split(" - ",2);startdate = fixDate((dd[0].indexOf(':') < 4?bx.getString("date")+" ":"")+dd[0]);enddate = fixDate((dd[1].indexOf(':') < 4?bx.getString("date")+" ":"")+dd[1]);}
			    					title = title.replaceFirst(".*? , ", "");
			    					}
			    						
			    					}
			    					//else if(h[sgi].contains("title="))
								}
			        			
			        		//	itemid = h[b];
			        			
			        			
			        			
			        			
			        			
			        			
			        			//itemid = itemid.replaceFirst(".* onClkM..", "");itemid = itemid.replaceAll("'.*", "");
			        			//String remoteid = itemid;
			        			//String remoteid = fullpath("/OWA/?ae=PreFormAction&IPF.Appointment&a=Open&id="+itemid,murl);
			        				
			        			Cursor cx = null;
			    				long rowId = -1;int st = 1;
			    				cx = SqliteWrapper.query(mCtx, mCtx.getContentResolver(),Uri.parse(rr.getString("path")) , new String[] {"_id","status"}, "remoteid=\""+itemid+"\"", null, null);
			    				if( cx != null){if( cx.moveToFirst() ){ rowId = cx.getLong(0); st = cx.getInt(1);} cx.close();}
			    				Message ml = new Message();
			    				
			    				if( rowId <= 0 ){
			    					//cv = new ContentValues();
			    	    			//title = h[b];title = title.replaceFirst(".*title=", "");title = title.replaceFirst(";HS;.*", " , ; ");
			        			//String[] tp = title.split(" , ", 2);if(tp.length < 2){ tp = new String[]{"", title};}
			        			//String[] tpp = tp[1].split(";", 2);if(tpp.length < 2){ tpp = new String[]{title, ""};}
			        			//title = tpp[0].replaceAll(" , $|;$| , ;$", "");subtitle = tpp[1];subtitle = subtitle.replaceAll(" , $|;$| , ;$", "");
			        			//tp[0] += " - ";
			        			//String[] dp = tp[0].split(" \\- ");
			        			//startdate = dp[0].trim(); enddate = dp[1].trim();
			        			//if(startdate.indexOf(':')<3){startdate = fixDate(bx.getString("date")+" "+startdate);}else{startdate = fixDate(startdate);}
			        			//if(enddate.indexOf(':')<3){enddate = fixDate(bx.getString("date")+" "+enddate);}else{enddate = fixDate(enddate);}
			        			
			        			{Message mx = new Message(); Bundle bxl = new Bundle();bxl.putString("text","event; insert td("+bx.getString("date")+") title("+title+") sub("+subtitle+") start("+startdate+") end("+enddate+") remoteid("+itemid+") ");mx.setData(bxl);logoly.sendMessageDelayed(mx,pRate);}
			        		//if(title.length() < 1 || )
			        			String[] p = new String[]{"remoteid",itemid,"title", title,"subtitle", subtitle,"startdate", startdate,"enddate", enddate, "#status","1", "seenurl", murl};
			    	    			rr.putStringArray("postable", p);
			    	    			rr.putString("type","insert");
			    				}else{
			    					{Message mx = new Message(); Bundle bxl = new Bundle();bxl.putString("text","event; update td("+bx.getString("date")+") title("+title+") sub("+subtitle+") start("+startdate+") end("+enddate+") remoteid("+itemid+") ");mx.setData(bxl);logoly.sendMessageDelayed(mx,pRate);}
			    					String[] p = new String[]{"#status","1","title", title,"subtitle", subtitle,"startdate", startdate,"enddate", enddate};rr.putStringArray("postable",p);
			    					rr.putString("type", "update");
			    					rr.putString("query", "_id="+rowId);
			    					//bx.putString("path",bx.getString("path")+"/"+Long.toString(rowId));
			    				}//ml.setData(bx);intoDaB.sendMessageDelayed(ml,72);
			    			}else
			    			
			    				
			    				
			    				
			    				
			    				
			    				
			    				
			    				
			    				
			    				
			    				if(h[b].contains("onClick=onClkRdMsg")){rr = new Bundle();
			    				rr.putString("table","mail");bx.putString("table",rr.getString("table"));
			    				rr.putString("path", Uri.withAppendedPath(DataProvider.CONTENT_URI, rr.getString("table")).toString());Cursor cx = null;
			     				
			    				//int sg = h[b].indexOf("group=");
								//if(sg > 2 && h[b].length() > sg+7){
									String gg = h[b].replaceAll(".*group=", "");gg = gg.replaceAll(";.*", "");
									groupid = Integer.parseInt(gg);//h[b].substring(sg+6, h[b].indexOf(";", sg+6))
								//}
			    				//Log.w(G,"no keeper // groupid = " + groupid);
								String msgstatus = "";
								int attcnt = 0;
								String priority = "";String vkey = "";String sender = ""; String title = "";String size = "";String js01 = ""; String js02 = "";
								String sent = "";
								for(int sgi=b+5;sgi > b-15;sgi--){
			    					if( sgi < 0 || h[sgi] == null){continue;}//h[sgi].startsWith("img ") &&
			    					if( h[sgi].contains("group="+groupid+";")){
			    				
			    						if(h[sgi].contains("class=sI")){
			    					msgstatus = h[sgi].replaceAll(".*alt=", ""); msgstatus = msgstatus.replaceAll(";HS;.*", "");
			    						}	else if(h[sgi].contains("class=atch")){attcnt=1;
			    						}   else if(h[sgi].contains("name=chkmsg")){vkey = h[sgi].replaceFirst(".*?value=", "");vkey = vkey.replaceFirst(";.*", "");vkey = vkey.replaceAll("/", "%2f").replaceAll("\\+", "%2b");
			    						
			    						sender = h[sgi].replaceAll(".*content1=", ""); sender = sender.replaceAll("%0a","");sender = sender.replaceAll(";.*", "");
			    						}   else if(h[sgi].contains("onClick=onClkRdMsg")){
			    						String[] s = h[sgi].replaceAll(".*content1=", "").split("%0a");
			    							if(s.length > 3){
			    								title = s[0];
			    							sent = fixDate(s[2]);size = s[3];
			    							}
			    							String c = h[sgi].replaceAll(".*onClkRdMsg.", "");c = c.replaceAll(".;.*", "");String[] v = c.split(",");
			    							if(v.length > 3){js01 = v[1].replaceAll("[ ']", "");js02 = v[3].replaceAll("[ ']","");}
			    						}	else if(h[sgi].contains("class=imp")){
			    							priority = h[sgi].replaceAll(".*alt=", ""); priority = priority.replaceAll(";HS;.*", "");	
			    						}
			    							
			    							
			    							
			    					}
			    				}
								/*for(int sgi=b+5;sgi > b-15;sgi--){
			    					if( sgi < 0 || h[sgi] == null){continue;}//h[sgi].startsWith("img ") &&
			    					if( h[sgi].contains("group="+groupid+";")){
			    				
			    						if(h[sgi].contains("class=sI")){
			    					msgstatus = h[sgi].replaceAll(".*alt=", ""); msgstatus = msgstatus.replaceAll(";HS;.*", "");
			    						}	else if(h[sgi].contains("class=atch")){attcnt=1;
			    						}   else if(h[sgi].contains("name=chkmsg")){vkey = h[b-1].replaceFirst(".*?value=", "");vkey = vkey.replaceFirst(";.*", "");
			    						}	else if(h[sgi].contains("class=imp")){
			    							priority = h[sgi].replaceAll(".*alt=", ""); priority = priority.replaceAll(";HS;.*", "");	
			    						}
			    							
			    							
			    							
			    					}
			    				}//*/
			    					
			    				
			    				//String nurl = fullpath("/OWA/?ae=Item&id="+vkey.replaceAll("/", "%2f").replaceAll("\\+", "%2b"), murl);
			    				int rowId = -1;int st = 1;//Uri.parse(bx.getString("path"))
			    				 cx = SqliteWrapper.query(mCtx, mCtx.getContentResolver(), Uri.withAppendedPath(DataProvider.CONTENT_URI, rr.getString("table")), new String[] {"_id","status"}, "remoteid=\""+vkey+"\"", null, "_id asc limit 1");
			     				if( cx != null){if( cx.moveToFirst() ){ rowId = cx.getInt(0); st = cx.getInt(1);} cx.close();}
			 Message ml = new Message();    				
			     	    			//bx.putString("table", "mail");
			 	    			if( rowId <= 0 ){
			
			     	    			
			     	    			//String[] tp = h[b].replaceAll(".*content1=", "").replaceAll(";HS;.*", "%0a %0a %0a %0a %0a %0a ").split("%0a");,"priority",priority
			     	    			//String title = tp[0];
			     	    			//String sent = fixDate(tp[2]);
			     	    		
			     	    			String[] pb = new String[]{"remoteid",vkey, "title", title, "sent", sent, "sender", sender,"#status", "1","msgstatus",msgstatus,"priority",priority,"#attcnt",attcnt+"","seenurl", murl,"msgsize", size,"js01",js01,"js02",js02};
			
			     	    			rr.putStringArray("postable", pb);rr.putString("type", "insert");
			     	    			{Message mx = new Message(); Bundle bxl = new Bundle();bxl.putString("text","mail insert; sent("+sent+") sender("+sender+") title("+title+") priority("+priority+") msgstatus("+msgstatus+") attcnt("+attcnt+") remoteid("+vkey+")");bxl.putInt("l",1);mx.setData(bxl);logoly.sendMessageDelayed(mx,pRate);}
			     	    			//{ ml.setData(bx); intoDaB.sendMessage(ml);}
			 	    			}else{
			 	    				{Message mx = new Message(); Bundle bxl = new Bundle();bxl.putString("text","mail update; priority("+priority+") msgstatus("+msgstatus+")");bxl.putInt("l",1);mx.setData(bxl);logoly.sendMessageDelayed(mx,pRate);}
			 	    				String[] gs = new String[]{"#status",st+"","msgstatus",msgstatus,"priority",priority,"seenurl", murl,"msgsize", size,"js01",js01,"js02",js02};
			 		    			//Message ml = new Message();
			 		    			rr.putStringArray("postable", gs);
			 		    			rr.putString("query", "_id="+rowId);
			 		    			rr.putString("type","update");
			 		    			//bx.putString("path",bx.getString("path")+"/"+Long.toString(rowId));
			 	    			}//ml.setData(rr);intoDaB.sendMessageDelayed(ml,72);
			    			}else
						
			    				
			    				
			    				
			    				
			    				
			    				
			    				
			    				
			    				
			    				
			    				if(h[b].contains("onClick=return onClkRcpt")){
			    			rr.putString("table", "contact");bx.putString("table",rr.getString("table"));
			    			rr.putString("path", Uri.withAppendedPath(DataProvider.CONTENT_URI,rr.getString("table")).toString());
			    			String name = "";String email = "";String phone = "";String title = "";String company = "";
			    			String itemid = ""; String phonetype = "";
			    			
			    			String gg = h[b].replaceAll(".*group=", "");gg = gg.replaceAll(";.*", "");
							groupid = Integer.parseInt(gg);
							for(int sgi=b+5;sgi > b-15;sgi--){
		    					if( sgi < 0 || h[sgi] == null){continue;}
		    					if( !h[sgi].contains("group="+groupid+";")){continue;}
			    			
		    					if( h[sgi].contains("onClkRcpt")){itemid = h[sgi].replaceFirst(".*;id=", "");itemid = itemid.replaceAll(";.*", "");itemid = itemid.replaceAll("/", "%2f").replaceAll("\\+", "%2b");
		    					String[] tp = h[sgi].replaceFirst(".*content1=","").split("%0a");
				    			if(tp.length >2){
		    					name = tp[0];email = tp[2].trim();//phone = tp[2].trim();
				    			//if(phone.contains("@")){email = phone; phone = tp[3].trim();}
				    			if(!email.contains("@")){email = "";}
				    			name = name.replaceFirst("\\(.*", "");
		    					//if(tp.length > 5){title = tp[4];company = tp[5].replaceFirst(";..;.*", "");}
				    			}
				    			}else if( h[sgi].contains("class=cPh")){
		    					String[] u = h[sgi].replaceAll(".*content1=", "").split("%0a");
				    			phone = u[0];title=u[1];company = u[2].replaceFirst(";..;.*", "");
		    					phonetype = h[sgi].contains("mobile")?"mobile":h[sgi].contains("work")?"work":"";
				    			}
				    				
							}

			    			{Message mx = new Message(); Bundle bxl = new Bundle();bxl.putString("text","contact; name("+name+")! email("+email+")* phone("+phone+""+(phonetype.length()>0?";"+phonetype:"")+")* title("+title+") company("+company+") itemid("+itemid+") seenurl("+murl+")");bx.putInt("l",3);mx.setData(bxl);logoly.sendMessageDelayed(mx,pRate);}
		
			    			//{Message mx = new Message(); Bundle bxl = new Bundle();bxl.putString("text",bx.getString("table")+"; lv("+h[b]+")");bxl.putInt("l",3);mx.setData(bxl);logoly.sendMessageDelayed(mx,pRate);}
			    			
			    			if(name.length() == 0 || (email.length() == 0 && phone.length() == 0) ){continue;}
			    			
			    		
			    			//String remoteid = "NO:"+itemid;
			    			//remoteid = fullpath("/OWA/?ae=Item&IPF.Contact&id="+itemid.replaceAll("/", "%2f").replaceAll("\\+", "%2b"), murl);
			    			//String remoteid = itemid.replaceAll("/", "%2f").replaceAll("\\+", "%2b");
			    			rr.putString("remoteid", itemid);
			    			
			    			/*
			    			// tL[b].replaceAll(".*;action=", "").replaceAll(";HS;.*", "");
			    			if( !au.startsWith("http") && murl.length() > 10){int tx = murl.contains("?") ? murl.indexOf("?") : murl.length(); String turl = murl.substring(0,tx);
			    			//Log.e(G,"au("+au+") mUrl("+murl+") turl("+turl+")");
			    			if( !au.startsWith("/") ){
			    					int d = turl.lastIndexOf("/") > -1 ? turl.lastIndexOf("/") : turl.length();
			    					
			    					remoteid = turl.substring(0, d) + "/" + au;
			    				}else{
			    					
			    					
			    						int d = turl.indexOf("/", 10) > -1 ? turl.indexOf("/", 10) : turl.length();
			    					remoteid = turl.substring(0, d) + au;
			    					
			    				}
			    			}//*/
			    			
			    			Cursor cx = null;
							long rowId = -1;int st = 1;
							cx = SqliteWrapper.query(mCtx, mCtx.getContentResolver(), Uri.parse(rr.getString("path")), new String[] {"_id","status"}, "remoteid=\""+itemid+"\"", null, null);
							if( cx != null){if( cx.moveToFirst() ){ rowId = cx.getLong(0);st = cx.getInt(1); } cx.close();}
							Message ml = new Message();
							if( rowId <= 0 ){
								//String gh = h[b].replaceFirst(".*;content1=", "");gh = gh.replaceAll(";.*", "%0a %0a %0a %0a %0a %0a %0a");
			    			
								//ContentValues cv = new ContentValues();
				    			String[] gs = new String[]{ "remoteid",itemid, "pname", name,"email", email,"phone", phone,"title", title,"company", company, "#status", "1", "seenurl", murl};
				    			//Message ml = new Message();
				    			rr.putStringArray("postable", gs);
				    			//bx.putString("table", "contact");
				    			rr.putString("type", "insert");
			    			
				    			
				    			//cv.put("hp", mHP);
				    		}else{
				    			String[] gs = new String[]{"#status",st+"", "pname", name,"email", email,"phone", phone,"title", title,"company", company};
				    			
				    			rr.putStringArray("postable", gs);
				    			rr.putString("query", "_id="+rowId);
				    			rr.putString("type","");//update 
				    			//bx.putString("path",bx.getString("path")+"/"+Long.toString(rowId));
				    			//bx.putString("path", Uri.withAppendedPath(DataProvider.CONTENT_URI, bx.getString("table")).toString());ml.setData(bx);intoDaB.sendMessage(ml);
				    		//	ContentValues uv = new ContentValues();uv.put("status",st);//Log.w(G, "update noticed status("+st+") rowId("+rowId+")");
						
					
					} 	
								
							}else{
						//{Message mx = new Message(); Bundle bxl = new Bundle();bxl.putString("text",bx.getString("table")+"; line("+h[b]+")");bxl.putInt("l",3);mx.setData(bxl);logoly.sendMessageDelayed(mx,pRate);}
						}if(rr.containsKey("type") && rr.getString("type").matches("insert|update")){ Message ml = new Message();ml.setData(rr);
				    			intoDaB.sendMessageDelayed(ml,pRate);}}
						//}};tx.start();
					
						if(b < h.length){
						bx.putInt("hat",b);	
						bx.putInt("listStudy", bx.getInt("listStudy")-1);
							//{Message dm = new Message();dm.setData(bx);listStudy.sendMessage(dm);}
						
						}else{bx.remove("hat");bx.remove("h");
							
						}
					
					{Message dm = new Message();dm.setData(bx);listStudy.sendMessageDelayed(dm,pRate);}
					}};tx.start();
					
					}};
					public void run04(Bundle bx){
						
				
				
					
					
						{Message dm = new Message();dm.setData(bx);listStudy.sendMessageDelayed(dm,pRate);}
					}
					public void run05(Bundle bx){
						{Message dm = new Message();dm.setData(bx);listStudy.sendMessageDelayed(dm,pRate);}
					}
					public Handler run06 = new Handler(){public void handleMessage(Message msg){Bundle bx = msg.getData();
					//public void run06(Bundle bl){
						String procg = bx.getString("procg");String titlr = bx.getString("title");int stage = bx.getInt("listStudy");
						{Message ml = new Message();Bundle bxb = new Bundle();bxb.putInt("l", 3);bxb.putString("text","listStudy "+stage+"/6 "+bx.getInt("hat",1)+"/"+bx.getInt("hatm", 1));ml.setData(bxb);logoly.sendMessageDelayed(ml,pRate);} 
						
						{Message dm = new Message();dm.setData(bx);listStudy.sendMessageDelayed(dm,pRate);}
					}};	
					};

					
					
					
					private Handler intoDaB = new Handler(){
			
						int pRate = 55;
						public long smooth = 34;int smoothc = 1;
						public void handleMessage(Message msg){
							Bundle bdl = msg.getData();
							if(smooth > SystemClock.uptimeMillis() && smoothc < 5 ){smoothc++;
								{Message ml = new Message(); ml.setData(bdl);intoDaB.sendMessageDelayed(ml,pRate);}
								return;
							}
							smooth = SystemClock.uptimeMillis() + pRate;smoothc = 1;
							
							final Bundle bl = new Bundle(bdl);
							Thread tx = new Thread(){public void run(){
								String[] rs = bl.getStringArray("postable");//cv.put("status",1);
			
								ContentValues gsr = new ContentValues();String vw = "";
								for(int xb = 0; xb < rs.length;xb++){if(rs[xb]==null ){continue;}if(rs[xb+1]==null){continue;}if(rs[xb].charAt(0) == '#'){gsr.put(rs[xb].replaceFirst("#", ""), Integer.parseInt(rs[++xb]));}else{gsr.put(rs[xb],rs[++xb]);vw += rs[xb].length()<50&&rs[xb].length()>0?rs[xb-1]+"("+rs[xb]+") ":rs[xb-1]+"("+rs[xb].length()+")b ";}}
								if(bl.getString("type").contentEquals("insert")){
									Uri newrecord = Uri.parse("");
									try{
										newrecord = SqliteWrapper.insert(mCtx, mCtx.getContentResolver(), Uri.parse(bl.getString("path")), gsr);//withAppendedPath(DataProvider.CONTENT_URI,bl.getString("table"))
				
										String newid = newrecord.getLastPathSegment();
			//long newid = mDataStore.addEntry("contactStore", new String[] {"remoteid"}, new String[] {remoteid});
										if( newid != null && Long.parseLong(newid) > 0 ){
											long rowId = Long.parseLong(newid);
				//Log.w(G,"Record created "+rowId+" "+newrecord.toString());
				//updateCountHandler.sendEmptyMessageDelayed(2,100);
											{Message mx = new Message(); Bundle bxm = new Bundle(); bxm.putString("title",bl.getString("procg"));bxm.putString("subtitle","New Record "+newid+" Added\n"+vw);mx.setData(bxm);easyViewerHandler.sendMessageDelayed(mx,pRate);}
			//	{Message mx = new Message(); Bundle bxm = new Bundle(); bxm.putString("table", bl.getString("table"));mx.setData(bxm);countHandler.sendMessageDelayed(mx,pRate);}
										}else{
											{Message mx = new Message(); Bundle bxm = new Bundle(); bxm.putString("title",bl.getString("procg"));bxm.putString("subtitle","Review Inbound Record "+vw);mx.setData(bxm);easyViewerHandler.sendMessageDelayed(mx,pRate);}
										}
				
									}
			
					
									catch(android.database.sqlite.SQLiteConstraintException e){
										Log.w(G,"SQLiteConstraintException " + e.getLocalizedMessage() );	
										Log.w(G,"fail insert on path " + bl.getString("path"));
									}
									catch(SQLException e){
										Log.w(G,"SQLException " + e.getLocalizedMessage() );	
										bl.putString("type", "update");bl.putString("query", "remoteid='"+gsr.getAsString("remoteid")+"'");	
									}	
			
								} if( bl.getString("type").contentEquals("update")){
									SqliteWrapper.update(mCtx, mCtx.getContentResolver(), Uri.parse(bl.getString("path")), gsr, bl.getString("query"), new String[0]);
								}else if( bl.getString("type").contentEquals("ask")){
				
								}
			}};tx.start();
			}
			};


			public String datetime(){
				int pRate = 55;
				String g = "";
				Date d = new Date();
				g = (d.getYear()+1900)+"-"+((d.getMonth() < 9)?"0":"")+((d.getMonth()+1))+"-"+((d.getDate() < 10)?"0":"")+d.getDate()+"T"+((d.getHours() < 10)?"0":"")+d.getHours()+":"+((d.getMinutes() < 10)?"0":"")+d.getMinutes()+":00";
				{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","generated date "+g);bx.putInt("l",1);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
				return g;
			}

			private String fixDate(String updated) {
				int pRate = 55;
				//day, month dd, yyyy hh:mm tt
				//m/d/year hh:mm tt
				String[] dateparts = updated.replaceFirst("T", " ").split(" ");
				Log.i(G,"fixDate ("+updated+")");
				if(updated.length() > 25){return datetime();}if(dateparts[0].contains("/")
						&& dateparts[0].contains(":")){
					
					
					int year = Integer.parseInt(dateparts[0].substring(dateparts[0].lastIndexOf("/")+1, dateparts[0].lastIndexOf("/")+5));
					int mon = Integer.parseInt(dateparts[0].substring(0, dateparts[0].indexOf("/")));
					int day = Integer.parseInt(dateparts[0].substring(dateparts[0].indexOf("/")+1, dateparts[0].lastIndexOf("/")));
					if( mon < 10 ){
						updated = year + "-0" + mon + "-";
					}else{
						updated = year + "-" + mon + "-";
					}
					if( day < 10 ){updated += "0"+ day + " ";}else{updated += day + " ";}
					int h = 0;int m = 0;
					h = Integer.parseInt(dateparts[0].substring(dateparts[0].indexOf(":")-2, dateparts[0].lastIndexOf(":")));
					m = Integer.parseInt(dateparts[0].substring(dateparts[0].indexOf(":")+1));
					if(dateparts[1].toLowerCase().contains("pm") && h < 12){
						h+=12;
					}if(dateparts[1].toLowerCase().contains("am") && h == 12){
						h-=12;
					}
					if( h < 10 ){updated += "0"+ h + ":";}else{updated += h + ":";}
					if( m < 10 ){updated += "0"+ m;}else{updated += m;}
					
					{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","Updated date to SQLite Format("+updated+") #3");bx.putInt("l",1);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
					
				}
				if(dateparts[0].contains("/")
						&& dateparts[1].contains(":")){
					String[] dp = dateparts[0].split("/");
					int year = Integer.parseInt(dp[2]);
					int mon = Integer.parseInt(dp[0]);
					int day = Integer.parseInt(dp[1]);
					if( mon < 10 ){
						updated = year + "-0" + mon + "-";
					}else{
						updated = year + "-" + mon + "-";
					}
					if( day < 10 ){updated += "0"+ day + " ";}else{updated += day + " ";}
					int h = 0;int m = 0;
					String[] t = dateparts[1].split(":");
					h = Integer.parseInt(t[0]);
					m = Integer.parseInt(t[1]);
					if(dateparts[2].toLowerCase().contains("pm") && h < 12){
						h+=12;
					}if(dateparts[2].toLowerCase().contains("am") && h == 12){
						h-=12;
					}
					if( h < 10 ){updated += "0"+ h + ":";}else{updated += h + ":";}
					if( m < 10 ){updated += "0"+ m;}else{updated += m;}
					
					{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","Updated date to SQLite Format("+updated+") #2");bx.putInt("l",1);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
				}
				if( dateparts.length > 5 ){
					String[] month = new String("xxx Jan Feb Mar Apr May Jun Jul Aug Sep Oct Nov Dec xxx").split(" ");
					int mon = 0;
					for(;mon < month.length; mon++){
						if( month[mon].equalsIgnoreCase(dateparts[2]) ){ break; } 
						if(dateparts[1].startsWith(month[mon])){
							break;
						}
					}
					if( mon == 13 ){
						{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","Unable to determine month in fixDate("+updated+")");bx.putInt("l",2);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
						return updated;
					}
					int year = 2010;
					if(dateparts[3].length() == 4){
						year = Integer.parseInt(dateparts[3]);
					}else{
						{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","Unable to determine year in fixDate("+updated+")");bx.putInt("l",2);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
					}
					int day = 1;
					dateparts[2] = dateparts[2].replaceAll(",", "");
					if(dateparts[2].length() > 0){
						day = Integer.parseInt(dateparts[2]);
					}
					
					if( mon < 10 ){
						updated = year + "-0" + mon + "-";
					}else{
						updated = year + "-" + mon + "-";
					}
					if( day < 10 ){updated += "0"+ day + " ";}else{updated += day + " ";}
					int h = 0;int m = 0;
					String[] t = dateparts[4].split(":");
					h = Integer.parseInt(t[0]);
					m = Integer.parseInt(t[1]);
					if(dateparts[5].toLowerCase().contains("pm") && h < 12){
						h+=12;
					}if(dateparts[5].toLowerCase().contains("am") && h == 12){
						h-=12;
					}
					if( h < 10 ){updated += "0"+ h + ":";}else{updated += h + ":";}
					if( m < 10 ){updated += "0"+ m;}else{updated += m;}
					
					{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","Updated date to SQLite Format("+updated+")");bx.putInt("l",1);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
				}
				
				return updated;
			}
	
			
			public String[] safeHttpGet(String who, HttpGet httpget, final String loc) {
				int pRate = 55;
				String[] reply = new String[3];
				//Log.w(G,"safeHttpGet() 1033 getURI("+httpget.getURI()+") for " + who);
				if( httpget.getURI().toString() == "" ){
					{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","safeHttpGet 1035 Blocked empty request for " + who);bx.putInt("l",2);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
					return reply;
				}
				
				String responseCode = ""; String mHP = "";
				//CookieStore c = mHC.getCookieStore();
				//mHC = new DefaultHttpClient();mHC.setCookieStore(c);
				//CookieStore cs = (mHC != null) ? mHC.getCookieStore(): new DefaultHttpClient().getCookieStore();
				CookieStore cs = new DefaultHttpClient().getCookieStore();
				DefaultHttpClient mHC = new DefaultHttpClient();
				mReg = mCtx.getSharedPreferences("Preferences", MODE_WORLD_WRITEABLE);mEdt = mReg.edit();
				String cshort = mReg.getString("lastcookies","");
				String[] clist = cshort.split("\n");ContentValues cg = new ContentValues();
				for(int h=0; h < clist.length; h++){
					String[] c = clist[h].split(" ",2);
					if(c.length == 2 && c[0].length() > 3){if(cg.containsKey(c[0]) == false){
						//cg.put(c[0], c[1]);
						//Log.w(G,"Carry Cookie safeHttpGet " + c[0] + ":"+c[1]);
						Cookie logonCookie = new BasicClientCookie(c[0], c[1]);
						cs.addCookie(logonCookie);//TODO
					}}
				}
				
				mHC.setCookieStore(cs);
				
				try {
					
					
					
					mHC.setRedirectHandler(new RedirectHandler(){
			
						public URI getLocationURI(HttpResponse arg0,
								HttpContext arg1) throws ProtocolException {
							
							if( arg0.containsHeader("Location")){
							String url = arg0.getFirstHeader("Location").getValue();
							//Log.w(G,"getLocationURI url("+url+")  " + arg0.getStatusLine().getReasonPhrase() + ": " + arg1.toString());
							//mUrl = url;mUrl("+mUrl+")
							URI uri = URI.create(url);
							
							//{Message mxm = new Message(); Bundle bxb = new Bundle();bxb.putString("string", loc+"url");bxb.putString(loc+"url",url );mxm.setData(bxb);setrefHandler.sendMessageDelayed(mxm,10);}
							mEdt.putString(loc+"url", url); mEdt.commit();
							return uri;
							}else{
								return null;
							}
						}
			
						public boolean isRedirectRequested(HttpResponse arg0,
								HttpContext arg1) {//Log.w(G,"isRedirectRequested " + arg0.getStatusLine().getReasonPhrase() + ": " + arg1.toString() + " ");
								if( arg0.containsHeader("Location") ){
									String url = arg0.getFirstHeader("Location").getValue();
									//{Message mxm = new Message(); Bundle bxb = new Bundle();bxb.putString("string", loc+"url");bxb.putString(loc+"url",url );mxm.setData(bxb);setrefHandler.sendMessageDelayed(mxm,10);}
									//Log.w(G,"isRedirectRequested url(" + url+") ");
									mEdt.putString(loc+"url", url); mEdt.commit();
									return true;
								}
							return false;
						}
						
					});
			
					String mUrl = httpget.getURI().toString();
					//{Message mxm = new Message(); Bundle bxb = new Bundle();bxb.putString("string", loc+"url");bxb.putString(loc+"url",mUrl );mxm.setData(bxb);setrefHandler.sendMessageDelayed(mxm,1);}
					//Log.w(G,"safeHttpGet() 1044 httpclient.execute() mUrl("+mUrl+") for " + who);
					reply[2] = mUrl;
					HttpResponse mHR = mHC.execute(httpget);
					//reply[2] = mReg.getString(loc+"url", mUrl);mUrl = reply[2];
			
					if( mHR != null ){
				        //Log.w(G,"safeHttpGet() 1048 " + mHR.getStatusLine() + " " + " for " + who);
				        
				        //Log.w(G,"safeHttpGet() 1050 response.getEntity() for " + who);
				        HttpEntity mHE = mHR.getEntity();
			
				        if (mHE != null) {
					        //byte[] bytes = ;
					        mHP = new String(EntityUtils.toByteArray(mHE));
					        if(loc.length() > 0 ){
					        	//final String murl = mUrl;final String mhp = mHP;
					        	//Thread eb = new Thread(){public void run(){
					        	mReg = getSharedPreferences("Preferences",MODE_WORLD_WRITEABLE);mEdt = mReg.edit();
					        	//mEdt.putString(loc+"url", murl);
					        	mEdt.putString(loc, mHP);mEdt.putLong(loc+"saved", System.currentTimeMillis());mEdt.commit();//}};eb.start();
					      		//mReg = getSharedPreferences("Preferences",MODE_WORLD_WRITEABLE);mEdt = mReg.edit();
					      		//mEdt.putString(loc, mHP);mEdt.commit();
					        	//{Message mx = new Message();Bundle bxb = new Bundle();// bxb.putString("string", loc+",");bxb.putString(loc, mHP);
					      		//bxb.putString("long", "lasthttp");bxb.putLong("lasthttp", System.currentTimeMillis());
					      		// mx.setData(bxb);setrefHandler.sendMessageDelayed(mx,50);}//*/
					        	}
					        {Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","Downloaded status(" + mHR.getStatusLine() + ") loc("+loc+") mUrl("+mUrl+") " + mHP.length() + " bytes. for " + who);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
					        //Log.w(G,"safeHttpGet() 1056 ");
					        final CookieStore cs2 = mHC.getCookieStore();
					        Thread tc = new Thread(){public void run(){
					        //mHO = mHC.getCookieStore().getCookies();
					        
					      	List<Cookie> cl2 = cs2.getCookies();
					      	String cshort2 = "";//ContentValues hh = new ContentValues();
					      	Bundle co = new Bundle();
					      	for(int i = cl2.size()-1; i >= 0; i--){
					      		Cookie c3 = cl2.get(i);
					      		if(co.containsKey(c3.getName())){continue;}
					      		co.putInt(c3.getName(), 1);
					      	//for(int i = 0; i < cl2.size(); i++){
					      		//if(mReg.getInt("ask", 1)>3){easyStatus("Cookie "+(i+1));}
					      		//Cookie c3 = cl2.get(i);
					      		
					      		//Log.w(G, "safeHttpGet() 2173 Cookie(): "+c3.getName() + " " + c3.getValue() + " (" + c3.getDomain()+" p" + c3.isPersistent()+" s" + c3.isSecure() +" " + c3.getPath()+" " +c3.getVersion() +")");
					      		//cshort2 += c3.getName() +" " + c3.getValue() +"; expires="+c3.getExpiryDate()+"\n";
					      	//	bdl.getString("clock")
					      		cshort2 += c3.getName() +" " + c3.getValue()+(c3.getExpiryDate()!=null?"; expires="+c3.getExpiryDate():"")+(c3.getPath()!=null?"; path="+c3.getPath():"")+(c3.getDomain()!=null?"; domain="+c3.getDomain():"")+"\n";
						      	
					      	
					      	}
					      	if(cshort2.length() > 0 ){
					      		//final String s = cshort2; 
					      		
					      		mReg = getSharedPreferences("Preferences",MODE_WORLD_WRITEABLE);mEdt = mReg.edit();
					      		mEdt.putString("lastcookies", cshort2);
					      		mEdt.commit();
					      		//Log.w(G,"lastcookies: " + cshort2);
					      		//{Bundle bxb = new Bundle(); bxb.putString("string", "lastcookies");bxb.putString("lastcookies", cshort2);
					      		//bxb.putString("long", "lasthttp");bxb.putLong("lasthttp", System.currentTimeMillis());
					      		//Message mx = new Message(); mx.setData(bxb);setrefHandler.sendMessageDelayed(mx,50);}
					      		
					      		/*
					      		Thread eb = new Thread(){public void run(){mReg = getSharedPreferences("Preferences",MODE_WORLD_WRITEABLE);mEdt = mReg.edit();
					      	mEdt.putLong("lasthttp",System.currentTimeMillis());
					      	mEdt.putString("lastcookies", s);
					      	mEdt.commit();
					      		}};eb.start();//*/
					      	}}};tc.start();
					      	
					      	
					        
					      	
					        //
					        // Print Cookies
					        //if ( !mHttpCookie.isEmpty() ) { for (int i = 0; i < mHttpCookie.size(); i++) { Log.w(TAG,"safeHttpGet() Cookie: " + mHttpCookie.get(i).toString()); } }
					        
					        //
					        // Print Headers
				        	//Header[] h = mHttpResponse.getAllHeaders(); for( int i = 0; i < h.length; i++){ Log.w(TAG,"safeHttpGet() Header: " + h[i].getName() + ": " + h[i].getValue()); }
					        //mUrl = httpget.getURI().toString();
					        //Log.w(G,"safeHttpGet() " + mUrl);
					        mHE.consumeContent();
						}
					}
					
			        responseCode = mHR.getStatusLine().toString();
					
				} catch (ClientProtocolException e) {
					{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","safeHttpGet() 1121 ClientProtocolException for " + who);bx.putInt("l",2);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
					{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","safeHttpGet() 1122 IO Exception Message " + e.getLocalizedMessage());bx.putInt("l",2);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
					e.printStackTrace();//{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text",e.printStackTrace());bx.putInt("l",2);mx.setData(bx);logoly.sendMessageDelayed(mx,100);}
					responseCode = " " + e.getLocalizedMessage() + " HTTP ERROR";
				} catch (NullPointerException e) {
					{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","safeHttpGet() 1126 NullPointer Exception for " + who);bx.putInt("l",2);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
					{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","safeHttpGet() 1127 IO Exception Message " + e.getLocalizedMessage());bx.putInt("l",2);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
					e.printStackTrace();//{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text",e.printStackTrace());bx.putInt("l",2);mx.setData(bx);logoly.sendMessageDelayed(mx,100);}
				} catch (IOException e) {
					{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","safeHttpGet() 1130 IO Exception for " + who);bx.putInt("l",2);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
					//if( e.getLocalizedMessage().contains("Host is unresolved") ){ SystemClock.sleep(1880); }
					{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","safeHttpGet() 1132 IO Exception Message " + e.getLocalizedMessage());bx.putInt("l",2);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
					StackTraceElement[] err = e.getStackTrace();
					//for(int i = 0; i < err.length; i++){
						//Log.w(G,"safeHttpGet() 1135 IO Exception Message " + i + " class(" + err[i].getClassName() + ") file(" + err[i].getFileName() + ") line(" + err[i].getLineNumber() + ") method(" + err[i].getMethodName() + ")");
					//}
					responseCode = e.getLocalizedMessage();
				} catch (IllegalStateException e) {
					{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","safeHttpGet() 1139 IllegalState Exception for " + who);bx.putInt("l",2);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
					{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","safeHttpGet() 1140 IO Exception Message " + e.getLocalizedMessage());bx.putInt("l",2);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
					e.printStackTrace();//{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text",e.printStackTrace());bx.putInt("l",2);mx.setData(bx);logoly.sendMessageDelayed(mx,100);}
					//if( responseCode == "" ){
						//responseCode = "440"; //440 simulates a timeout condition and recreates the client.
					//}
				}
				reply[0] = responseCode;
				reply[1] = mHP;
				return reply;
			}
			
			
			
			private String fullpath(String url, String murl){
				int pRate = 55;
				String au = url;if( murl == null){murl = "";}
				if(murl.length() < 10 ){
					if(mReg.contains("mailpage1url") && murl.length() < 10){murl = mReg.getString("mailpage1url", "");}
					if(mReg.contains("contactpage1url") && murl.length() < 10){murl = mReg.getString("contactpage1url", "");}
					if(mReg.contains("showpage1url") && murl.length() < 10){murl = mReg.getString("showpage1url", "");}
					//if(mReg.contains("logon-mailstudyurl") && murl.length() < 10){murl = mReg.getString("logon-mailstudyurl", "");}
					if(mReg.contains("study-logonurl") && murl.length() < 10){murl = mReg.getString("study-logonurl", "");}
					//murl = mReg.getString("saveconnecturl",mReg.getString("savelogonurl",mReg.getString("webaddress","")));
					{Message mx = new Message(); Bundle bx = new Bundle();bx.putInt("l",2);bx.putString("text","fullpath received empty murl changed to " + murl);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
				}
					if( !au.toLowerCase().startsWith("http:") && murl.length() > 10){
						int tx = murl.contains("?") ? murl.indexOf("?") : murl.length(); 
						String turl = murl.substring(0,tx);
					
				if( !au.startsWith("/") ){
					if(turl.lastIndexOf("/", 9) < 0 ){
						au = turl + "/" + au;
					}else{
						int d = turl.lastIndexOf("/") > -1 ? turl.lastIndexOf("/") : turl.length();
						au = turl.substring(0, d) + "/" + au;
					}
					}else{
							int d = turl.indexOf("/", 10) > -1 ? turl.indexOf("/", 10) : turl.length();
							au = turl.substring(0, d) + au;
					}
					}
					{Message mx = new Message(); Bundle bxb = new Bundle(); bxb.putString("text", "fullpath("+  au+") url("+url+") from("+murl+")"); mx.setData(bxb);logoly.sendMessageDelayed(mx,pRate);}
					return au;
					//{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("text","");mx.setData(bx);logoly.sendMessageDelayed(mx,100);}
					//{Message mx = new Message(); Bundle bx = new Bundle();bx.putString("sftring",);bx.putString(,);bx.putLong(,);mx.setData(bx);setrefHandler.sendMessageDelayed(mx,100);}
			}
	
}