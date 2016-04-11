package me.arkanayan.buieconnect;

import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import me.arkanayan.buieconnect.activities.MainActivity;
import me.arkanayan.buieconnect.exceptions.UserDetailsNotPresent;
import me.arkanayan.buieconnect.models.User;

/**
 * Created by arka on 4/11/16.
 */

public class LoadAndStoreUserTest extends ActivityInstrumentationTestCase2<MainActivity> {

    MainActivity mActivity;
    public final String TAG = this.getClass().getSimpleName();

    public LoadAndStoreUserTest() {
        super(MainActivity.class);

    }

    public LoadAndStoreUserTest(Class<MainActivity> activityClass) {
        super(activityClass);

    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
    }

    public void testLoadAndStoreUser() throws UserDetailsNotPresent {
        User user = new User();
        user.setFirstName("TestFirstName");
        user.setLastName("TestLastName");
        user.setPassoutYear(2016);
        user.setAdmissionYear(2016);
        user.setRegDate(new Date());
        user.setIsAdmin(true);
        user.setVerified(false);
        user.setId(2);
        user.setDepartmentName("CSE");
        user.setGoogleSub("332423423");
        user.setIsAlumnus(false);
        user.setEmail("2342@jldsj.com");
        user.setGcmRegId("w34jlkjlwkj");
        user.setCurrentSemester(7);
        user.setUrl("http://google.com");

        User.storeUser(getActivity(), user);

        User newUser = User.loadUserFromPreference(getActivity());

        // Log.d(TAG, "testStoreUser: FirstName: " + newUser.getFirstName() + " Url: " + newUser.getUrl() );

        assertNotNull(newUser);

    }

}
