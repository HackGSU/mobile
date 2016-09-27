package com.hackgsu.fall2016.android.activities;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.hackgsu.fall2016.android.R;
import com.hackgsu.fall2016.android.fragments.*;
import com.ncapdevi.fragnav.FragNavController;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnTabSelectListener {
	private AppBarLayout      appbar;
	private BottomBar         bottomBar;
	private FragNavController fragNavController;
	private BaseFragment      lastHomeFragment;
	private Toolbar           toolbar;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		DrawerLayout          drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();

		appbar = (AppBarLayout) findViewById(R.id.appbar);

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		final List<Fragment> fragments = new ArrayList<>();

		fragments.add(new AnnouncementsFragment());
		fragments.add(new ScheduleFragment());
		fragments.add(new FacilityMapFragment());
		fragments.add(new MentorsFragment());
		fragments.add(new SponsorsFragment());

		fragNavController = new FragNavController(getSupportFragmentManager(), R.id.fragment_frame, fragments);
		fragNavController.setNavListener(new FragNavController.NavListener() {
			@Override
			public void onTabTransaction (Fragment fragment, int index) {
				if (index < 3) { lastHomeFragment = (BaseFragment) fragments.get(index); }
			}

			@Override
			public void onFragmentTransaction (Fragment fragment) {

			}
		});
		fragNavController.switchTab(FragNavController.TAB1);

		bottomBar = (BottomBar) findViewById(R.id.bottomBar);
		bottomBar.setOnTabSelectListener(this);
	}

	@Override
	public void onBackPressed () {
		// TODO: 9/27/16 : Handle the case where when the user is in a fragment other than the three main fragments, instead of closing the app, it will return to home
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		}
		else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu (Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected (MenuItem item) {
		int id = item.getItemId();

		// TODO: 9/27/16 : Toggle icon and actually mute notifications
		if (id == R.id.action_mute_notifications) { return true; }

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings ("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected (MenuItem item) {
		handleAction(item.getItemId());

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	@Override
	public void onTabSelected (@IdRes int tabId) {
		handleAction(tabId);
	}

	private void handleAction (@IdRes int id) {
		// TODO: 9/27/16 : Animate background color change
		switch (id) {
			case R.id.nav_home:
				showBottomBar();
				bottomBar.selectTabAtPosition(lastHomeFragment.getTabIndex());
				lastHomeFragment.selectTab(fragNavController);
				appbar.setBackgroundResource(lastHomeFragment.getPrimaryColor());
				break;
			case R.id.tab_announcements:
				fragNavController.switchTab(FragNavController.TAB1);
				appbar.setBackgroundResource(R.color.announcementsPrimary);
				break;
			case R.id.tab_schedule:
				fragNavController.switchTab(FragNavController.TAB2);
				appbar.setBackgroundResource(R.color.schedulePrimary);
				break;
			case R.id.tab_facility_map:
				fragNavController.switchTab(FragNavController.TAB3);
				appbar.setBackgroundResource(R.color.facilityMapPrimary);
				break;
			case R.id.nav_mentors:
				fragNavController.switchTab(FragNavController.TAB4);
				appbar.setBackgroundResource(R.color.mentorsPrimary);
				hideBottomBar();
				break;
			case R.id.nav_sponsors:
				fragNavController.switchTab(FragNavController.TAB5);
				appbar.setBackgroundResource(R.color.sponsorsPrimary);
				hideBottomBar();
				break;
			case R.id.nav_code_of_conduct:
				break;
			case R.id.nav_send_feedback:
				break;
			default:
				break;
		}
	}

	private void showBottomBar () { bottomBar.animate().translationY(0).setDuration(500).start(); }

	private void hideBottomBar () { bottomBar.animate().translationY(bottomBar.getHeight()).setDuration(500).start(); }
}