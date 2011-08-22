package tv.acfun;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tv.acfun.util.GetLinkandTitle;
import tv.acfun.util.Util;



import acfun.domain.Acfun;
import acfun.domain.Video;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class HomeActivity extends Activity {
	private ListView homelistview;
	private List<Map<String, Object>> data;
	private ListViewAdaper adaper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homelayout);
		Button ref_btn = (Button) findViewById(R.id.home_refresh_btn);
		homelistview = (ListView) findViewById(R.id.homelistviw);
		homelistview.setCacheColorHint(0);
		Buttonlistener listener = new Buttonlistener();
		ref_btn.setOnClickListener(listener);
		
		InitListView(true);
	}

	public void InitListView(final boolean first) {
		new Thread(){
			public void run(){		
				try {
					data = getListData();
					sleep(1000);
					runOnUiThread(new Runnable() {
						public void run() {
						if(first){
							getParent().findViewById(R.id.bottom_bar).setVisibility(View.VISIBLE);
							getParent().findViewById(R.id.contentbody).setVisibility(View.VISIBLE);
							getParent().findViewById(R.id.start_an).setVisibility(View.GONE);
							Util.backScreen(getParent());
							adaper = new ListViewAdaper(HomeActivity.this,data);
							homelistview.setAdapter(adaper);
						}else{
							adaper.setData(data);
							adaper.notifyDataSetChanged();
						}
						} 
					});	
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		}.start();
	}
	 private List<Map<String, Object>> getListData() throws IOException {
		 
	        data = new ArrayList<Map<String, Object>>();      
	        GetLinkandTitle linkandTitle = new GetLinkandTitle();
	        List<Video> videos =  linkandTitle.getHomeVideoandTitle(Acfun.getAcfun());
	        if(!videos.isEmpty()){
	        for(Video video:videos){
	        	Map<String, Object> map = new HashMap<String, Object>();
	        	map.put("title", video.getVideotitle());
	        	map.put("link", video.getVideolink());
	        	data.add(map);
	        }
	        }
	       return data;
	    }
	 
	
	private final class Buttonlistener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.home_refresh_btn:
				InitListView(false);
				break;

			default:
				break;
			}
		}
		
	}
}