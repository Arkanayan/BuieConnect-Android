package me.arkanayan.buieconnect;

import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import me.arkanayan.buieconnect.activities.MainActivity;
import me.arkanayan.buieconnect.exceptions.UserDetailsNotPresent;
import me.arkanayan.buieconnect.models.User;

/**
 * Created by arka on 4/11/16.
 */

public class UserOperationsTest extends ActivityInstrumentationTestCase2<MainActivity> {

    MainActivity mActivity;
    public final String TAG = this.getClass().getSimpleName();
    private User mUser;

    public UserOperationsTest() {
        super(MainActivity.class);

    }

    public UserOperationsTest(Class<MainActivity> activityClass) {
        super(activityClass);

    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
        mUser = new User();
        mUser.setFirstName("TestFirstName");
        mUser.setLastName("TestLastName");
        mUser.setPassoutYear(2016);
        mUser.setAdmissionYear(2016);
        mUser.setRegDate(new Date());
        mUser.setIsAdmin(true);
        mUser.setVerified(false);
        mUser.setId(2);
        mUser.setDepartmentName("CSE");
        mUser.setGoogleSub("332423423");
        mUser.setIsAlumnus(false);
        mUser.setEmail("2342@jldsj.com");
        mUser.setGcmRegId("w34jlkjlwkj");
        mUser.setCurrentSemester(7);
        mUser.setUrl("http://google.com");
    }


    // Tests loading and storing from/to shared preferences
    public void testPrefsLoadAndStoreUser() throws UserDetailsNotPresent {


        User.storeUser(getActivity(), mUser);

        User newUser = User.loadUserFromPreference(getActivity());

        // Log.d(TAG, "testStoreUser: FirstName: " + newUser.getFirstName() + " Url: " + newUser.getUrl() );
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
//        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
//        Log.d(TAG, "testLoadAndStoreUser: RegDate: " + user.getRegDate().toString());

        assertNotNull(newUser);
    }

}
