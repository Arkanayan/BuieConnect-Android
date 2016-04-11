package me.arkanayan.buieconnect;

import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import me.arkanayan.buieconnect.activities.MainActivity;

/**
 * Created by arka on 4/11/16.
 */

public class LoadAndStoreUserTest extends ActivityUnitTestCase<MainActivity> {

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

    public void testStoreUser() {
        assertEquals(2, 2);

    }

}
