package dictionary.vietnamese.tudien.viet.activity;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import dictionary.vietnamese.tudien.viet.NextDictUtils.CLog;
import dictionary.vietnamese.tudien.viet.NextDictUtils.KeyboardUtils;
import dictionary.vietnamese.tudien.viet.NextDictUtils.Utils;
import dictionary.vietnamese.tudien.viet.R;
import dictionary.vietnamese.tudien.viet.configuration.Configruation;
import dictionary.vietnamese.tudien.viet.configuration.IntentFilterConfig;
import dictionary.vietnamese.tudien.viet.database.ManagerDictDatabase;
import dictionary.vietnamese.tudien.viet.fragments.BaseFragment;
import dictionary.vietnamese.tudien.viet.fragments.FavoriteFragment;
import dictionary.vietnamese.tudien.viet.fragments.HistoryFragment;
import dictionary.vietnamese.tudien.viet.fragments.HomeFragment;
import dictionary.vietnamese.tudien.viet.model.resultApi.ListDictResult;
import dictionary.vietnamese.tudien.viet.service.ChatHeadService;
import dictionary.vietnamese.tudien.viet.service.standOut.StandOutWindow;
import dictionary.vietnamese.tudien.viet.service.standOut.WidgetsWindow;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static int OVERLAY_PERMISSION_REQ_CODE_CHATHEAD = 1234;
    private String word;
    public static final int REQUEST_SETTING = 1;
    public static final int REQUEST_MEANING = 2;
    public static int mCurrentPage;
    private SharedPreferences sharedPreferences;
    private Toolbar toolbar;
    private BaseFragment fragment;
    private ManagerDictDatabase managerDictDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(Configruation.Pref, MODE_PRIVATE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (sharedPreferences.getInt(Configruation.KEY_LANGUAGE, Configruation.KEY_VI) == Configruation.KEY_VI) {
            toolbar.setTitle(getString(R.string.app_name_vi_ru));
        } else {
            toolbar.setTitle(getString(R.string.app_name_ru_vi));
        }
        try {
            stopService(new Intent(this, ChatHeadService.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (mCurrentPage == Configruation.HOME_SEARCH_WORD) {
                    KeyboardUtils.showDelayedKeyboard(MainActivity.this, HomeFragment.edSearch);
                } else {
                    Utils.hideKeyboard(MainActivity.this);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Utils.hideKeyboard(MainActivity.this);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        InitData();
    }

    void handleSendText(Intent intent) {
        word = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (word != null) {
            showPopup(word);
            finish();
        }
    }

    private void InitData() {
        managerDictDataSource = new ManagerDictDatabase(this);
        StandOutWindow.closeAll(this, WidgetsWindow.class);
        fragment = new HomeFragment();
        changeFragment(fragment);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        } else if (action != null && action.equals(IntentFilterConfig.COLORDICT_INTENT_ACTION_SEARCH)) {
            word = intent.getStringExtra(IntentFilterConfig.EXTRA_QUERY);
            if (word == null) {
                word = "";
            }
            showPopup(word);
        } else if (action != null && action.equals(IntentFilterConfig.FLEXIDICT_INTENT_ACTION_LOOKUP)) {
            word = intent.getStringExtra(IntentFilterConfig.EXTRA_QUERY);
            if (word == null) {
                word = "";
            }
            showPopup(word);
        } else if (action != null && action.equals(IntentFilterConfig.MEGADICT_INTENT_ACTION_LOOKUP)) {
            word = intent.getStringExtra(IntentFilterConfig.EXTRA_QUERY);
            if (word == null) {
                word = "";
            }
            showPopup(word);
        } else if (action != null && action.equals(IntentFilterConfig.NEXTDICT_INTENT_ACTION_LOOKUP)) {
            word = intent.getStringExtra(IntentFilterConfig.EXTRA_QUERY);
            if (word == null) {
                word = "";
            }
            showPopup(word);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuLanguage = menu.findItem(R.id.action_language);
        if (sharedPreferences.getInt(Configruation.KEY_LANGUAGE, Configruation.KEY_VI) == Configruation.KEY_VI) {
            menuLanguage.setIcon(R.mipmap.flag_vietnam);
            toolbar.setTitle(getString(R.string.app_name_vi_ru));
        } else {
            menuLanguage.setIcon(R.mipmap.flag_us);
            toolbar.setTitle(getString(R.string.app_name_ru_vi));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_language) {
//            startActivityForResult(new Intent(getApplicationContext(), SettingActivity.class), REQUEST_SETTING);
//            mCurrentPage = Configruation.HOME_SETTING;
            if (sharedPreferences.getInt(Configruation.KEY_LANGUAGE, Configruation.KEY_VI) == Configruation.KEY_VI) {
                sharedPreferences.edit().putInt(Configruation.KEY_LANGUAGE, Configruation.KEY_RU).apply();
                item.setIcon(R.mipmap.flag_us);
                toolbar.setTitle(getString(R.string.app_name_ru_vi));
                changeDict(Configruation.KEY_RU);
            } else {
                sharedPreferences.edit().putInt(Configruation.KEY_LANGUAGE, Configruation.KEY_VI).apply();
                item.setIcon(R.mipmap.flag_vietnam);
                toolbar.setTitle(getString(R.string.app_name_vi_ru));
                changeDict(Configruation.KEY_VI);
            }
            if (mCurrentPage == Configruation.HOME_SEARCH_WORD) {
                ((HomeFragment) fragment).adapter.clear();
                HomeFragment.edSearch.setText("");
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void changeDict(int keyDict) {
        managerDictDataSource.open();
        List<ListDictResult.ListDictInfo> list = managerDictDataSource.getAllDict();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (keyDict == Configruation.KEY_VI) {
                    if (list.get(i).id.equals("vi_en")) {
                        managerDictDataSource.addChecked(list.get(i).id, 1);
                    } else {
                        managerDictDataSource.addChecked(list.get(i).id, 0);
                    }
                } else {
                    if (list.get(i).id.equals("en_vi")) {
                        managerDictDataSource.addChecked(list.get(i).id, 1);
                    } else {
                        managerDictDataSource.addChecked(list.get(i).id, 0);
                    }
                }

            }
        }

        managerDictDataSource.close();
    }

    public DrawerLayout drawer;

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_lookup) {
            clearBackStack(getSupportFragmentManager());
            fragment = new HomeFragment();
            changeFragment(fragment);
            mCurrentPage = Configruation.HOME_SEARCH_WORD;

        } else if (id == R.id.nav_history) {
            clearBackStack(getSupportFragmentManager());
            fragment = new HistoryFragment();
            changeFragment(fragment);
            mCurrentPage = Configruation.HOME_HISTORY;

        } else if (id == R.id.nav_favorite) {
            clearBackStack(getSupportFragmentManager());
            fragment = new FavoriteFragment();
            changeFragment(fragment);
            mCurrentPage = Configruation.HOME_FAVORITE;
        } else if (id == R.id.nav_popupDict) {
            showPopup(word);
            mCurrentPage = Configruation.HOME_POPUP_DICT;
        } else if (id == R.id.nav_setting) {
            mCurrentPage = Configruation.HOME_SETTING;
            startActivityForResult(new Intent(getApplicationContext(), SettingActivity.class), REQUEST_SETTING);
            overridePendingTransition(R.anim.animation,
                    R.anim.animation2);
        }
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeFragment(Fragment targetFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    public void clearBackStack(FragmentManager manager) {
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager
                    .getBackStackEntryAt(0);
            manager.popBackStack(first.getId(),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    private void showPopup(String word) {
        ManagerDictDatabase managerDictDatabase = new ManagerDictDatabase(MainActivity.this);
        managerDictDatabase.open();
        ListDictResult.ListDictInfo listDictInfo = managerDictDatabase.getDictIfChecked();
        managerDictDatabase.close();
        if (listDictInfo != null) {
            if (Utils.canDrawOverlays(MainActivity.this))
                startChatHead(word);
            else {
                requestPermission(OVERLAY_PERMISSION_REQ_CODE_CHATHEAD);
//            startChatHead();
            }
            finish();
        } else {
//            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//            builder.setMessage(getString(R.string.dictNotFound));
//            builder.setNegativeButton(getString(R.string.late), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                    if (mCurrentPage == Configruation.HOME_SEARCH_WORD) {
//                        KeyboardUtils.showDelayedKeyboard(MainActivity.this, HomeFragment.edSearch);
//                    }
//                }
//            });
//            builder.setPositiveButton(getString(R.string.downNow), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    Utils.hideKeyboard(MainActivity.this);
//                    EventBus.getDefault().post(new ChangeMenuEvent(Configruation.HOME_SELECT_DICT));
//                }
//            });
//            builder.setCancelable(false);
//            builder.show();
        }


//        Intent intent = new Intent(MainActicity.this, TestActivity.class);
//        startActivity(intent);
//        finish();
    }

    private void startChatHead(String word) {
        SharedPreferences sharedPreferences = getSharedPreferences(Configruation.Pref_Popup, MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(Configruation.KEY_ISPOPUP_OPENING, true).apply();
        CLog.d(TAG, "isopenning: " + sharedPreferences.getBoolean(Configruation.KEY_ISPOPUP_OPENING, false));
        startService(new Intent(this, ChatHeadService.class).putExtra("word", word));

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission(int requestCode) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mCurrentPage == Configruation.HOME_SEARCH_WORD) {
            if (requestCode == REQUEST_SETTING || requestCode == REQUEST_MEANING) {
                if (resultCode == RESULT_OK) {
//                    HomeFragment.edSearch.setFocusable(true);
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.showSoftInput(HomeFragment.edSearch, InputMethodManager.SHOW_IMPLICIT);
                    KeyboardUtils.showDelayedKeyboard(MainActivity.this, HomeFragment.edSearch);
                    if (requestCode == REQUEST_MEANING) {
                        HomeFragment.edSearch.setText("");
                    }
                }
//                    EventBus.getDefault().post(new HideShowKeyBoardEvent(HideShowKeyBoardEvent.TYPE_SHOW));
            }
        }
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE_CHATHEAD) {
            if (!Utils.canDrawOverlays(MainActivity.this)) {
                needPermissionDialog(requestCode);
            } else {
                startChatHead(word);
            }

        }

    }

    private void needPermissionDialog(final int requestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(getString(R.string.needPermission));
        builder.setPositiveButton(getString(R.string.ok),
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermission(requestCode);
                    }
                });
        builder.setNegativeButton(getString(R.string.cancel), new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setCancelable(false);
        builder.show();
    }


}
