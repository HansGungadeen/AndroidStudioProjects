package com.example.polyblog;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

//    androidx.appcompat.widget.Toolbar mMainToolbar;
//
//    FirebaseAuth mAuth;
//    FirebaseFirestore mFirestore;
//
//    public static String currentUserId;
//
//    BottomNavigationView bottomNav;
//
////    HomeFragment homeFragment;
////    NotificationFragment notificationFragment;
////    AccountFragment accountFragment;
////    public static BlogPostFragment blogPostFragment;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        mAuth = FirebaseAuth.getInstance();
//        mFirestore = FirebaseFirestore.getInstance();
//
//        FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
//
//        if (mCurrentUser != null){
//
//            mMainToolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.main_toolbar);
//            setSupportActionBar(mMainToolbar);
//            getSupportActionBar().setTitle("PolyBlog");
//            getSupportActionBar().getThemedContext();
//            mMainToolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));
//
//            bottomNav = findViewById(R.id.bottom_nav);
////            ButtomNavigationHelper.removeShiftMode(bottomNav);
//
////            homeFragment = new HomeFragment();
////            notificationFragment = new NotificationFragment();
////            accountFragment = new AccountFragment();
////            blogPostFragment = new BlogPostFragment();
//
//
//
//        }
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseUser mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (mCurrentUser == null){
//            sendToLogin();
//        } else{
//            currentUserId = mAuth.getCurrentUser().getUid();
//
//            mFirestore.collection("Users").document(currentUserId).get().addOnCompleteListener((task -> {
//                if(task.isSuccessful()){
//                    if (!task.getResult().exists()){
//                        sendToSettings();
//                        finish();
//                    }
//                }else {
//                    String errorMessage = task.getException().getMessage();
//                    Toast.makeText(MainActivity.this,"Error: " + errorMessage, Toast.LENGTH_LONG).show();
//                }
//            }));
//        }
//    }
//
//    private void sendToSettings() {
//        Intent toSettings = new Intent(MainActivity.this,SetupActivity.class);
//        startActivity(toSettings);
//    }
//
//    private void sendToLogin() {
//        Intent toLogin = new Intent(MainActivity.this,LoginActivity.class);
//        startActivity(toLogin);
//        finish();
//    }
//}
//
//
////remove shiftmode
////class ButtomNavigationHelper{
////    static void removeShiftMode(BottomNavigationView view){
////        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
////        try{
////            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
////            shiftingMode.setAccessible(true);
////            shiftingMode.setBoolean(menuView, false);
////            shiftingMode.setAccessible(false);
////            for (int i = 0; i < menuView.getChildCount(); i++) {
////                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
////                item.setShiftingMode(false);
////                // set once again checked value, so view will be updated
////                item.setChecked(item.getItemData().isChecked());
////            }
////        } catch (NoSuchFieldException e) {
////            Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
////        } catch (IllegalAccessException e) {
////            Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
////        }
////    }
}
}