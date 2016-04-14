package me.arkanayan.buieconnect.services;

import me.arkanayan.buieconnect.ServiceGenerator;
import me.arkanayan.buieconnect.interfaces.UserInterface;
import me.arkanayan.buieconnect.models.User;
import retrofit2.Call;

/**
 * Created by arka on 4/10/16.
 */
public class UserService {
    private final String TAG = this.getClass().getSimpleName();

    private UserInterface mUser;

    public UserService(String authToken) {
        mUser = ServiceGenerator.createService(UserInterface.class, authToken);
    }

    public Call<User> getUserCall() {

        return mUser.getUser();
    }

    public Call<User> updateUser(User user) {
        return mUser.updateUser(user);
    }
}
