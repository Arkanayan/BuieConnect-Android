package me.arkanayan.buieconnect.models;

import android.app.Presentation;
import android.content.Context;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import me.arkanayan.buieconnect.exceptions.UserDetailsNotPresent;
import me.arkanayan.buieconnect.utils.Prefs;

/**
 * Created by arka on 4/10/16.
 */
public class User {

    @SerializedName("is_alumnus")
    @Expose
    private Boolean isAlumnus;
    @SerializedName("google_sub")
    @Expose
    private String googleSub;
    @SerializedName("firstName")
    @Expose
    private String firstName;
/*    @SerializedName("reg_date")
    @Expose
    private Date regDate;*/
    @SerializedName("is_admin")
    @Expose
    private Boolean isAdmin;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("admission_year")
    @Expose
    private int admissionYear;
    @SerializedName("department_name")
    @Expose
    private String departmentName;
    @SerializedName("current_semester")
    @Expose
    private int currentSemester;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("univ_roll")
    @Expose
    private long univRoll;
    @SerializedName("passout_year")
    @Expose
    private int passoutYear;
    @SerializedName("gcm_reg_id")
    @Expose
    private String gcmRegId;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("verified")
    @Expose
    private Boolean verified;
    @SerializedName("url")
    @Expose
    private String url;

    /**
     *
     * @return
     * The isAlumnus
     */
    public String getIsAlumnus() {
        return isAlumnus ? "true" : "false";
    }

/*    public boolean getIsAlumnus() {
        return isAlumnus;
    }*/

    /**
     *
     * @param isAlumnus
     * The is_alumnus
     */
    public void setIsAlumnus(Boolean isAlumnus) {
        this.isAlumnus = isAlumnus;
    }

    /**
     *
     * @return
     * The googleSub
     */
    public String getGoogleSub() {
        return googleSub;
    }

    /**
     *
     * @param googleSub
     * The google_sub
     */
    public void setGoogleSub(String googleSub) {
        this.googleSub = googleSub;
    }

    /**
     *
     * @return
     * The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     * The firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

/*
    */
/**
     *
     * @return
     * The regDate
     *//*

    public Date getRegDate() {
        return regDate;
    }

    */
/**
     *
     * @param regDate
     * The reg_date
     *//*

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }
*/

    /**
     *
     * @return
     * The isAdmin
     */
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     *
     * @param isAdmin
     * The is_admin
     */
    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The admissionYear
     */
    public Object getAdmissionYear() {
        return admissionYear;
    }

    /**
     *
     * @param admissionYear
     * The admission_year
     */
    public void setAdmissionYear(int admissionYear) {
        this.admissionYear = admissionYear;
    }

    /**
     *
     * @return
     * The departmentName
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     *
     * @param departmentName
     * The department_name
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     *
     * @return
     * The currentSemester
     */
    public Object getCurrentSemester() {
        return currentSemester;
    }

    /**
     *
     * @param currentSemester
     * The current_semester
     */
    public void setCurrentSemester(int currentSemester) {
        this.currentSemester = currentSemester;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The univRoll
     */
    public long getUnivRoll() {
        return univRoll;
    }

    /**
     *
     * @param univRoll
     * The univ_roll
     */
    public void setUnivRoll(long univRoll) {
        this.univRoll = univRoll;
    }

    /**
     *
     * @return
     * The passoutYear
     */
    public int getPassoutYear() {
        return passoutYear;
    }

    /**
     *
     * @param passoutYear
     * The passout_year
     */
    public void setPassoutYear(int passoutYear) {
        this.passoutYear = passoutYear;
    }

    /**
     *
     * @return
     * The gcmRegId
     */
    public String getGcmRegId() {
        return gcmRegId;
    }

    /**
     *
     * @param gcmRegId
     * The gcm_reg_id
     */
    public void setGcmRegId(String gcmRegId) {
        this.gcmRegId = gcmRegId;
    }

    /**
     *
     * @return
     * The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     * The lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return
     * The verified
     */
    public Boolean getVerified() {
        return verified;
    }

    /**
     *
     * @param verified
     * The verified
     */
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    /**
     *
     * @return
     * The url
     */
    public String getUrl() {
        return url;
    }

    /**
     *
     * @param url
     * The url
     */
    public void setUrl(String url) {
        this.url = url;
    }


    public static User loadUserFromPreference(Context context) throws UserDetailsNotPresent {


        Prefs prefs = Prefs.getInstance(context);

        if (prefs.getBoolean(Prefs.Key.IS_USER_DETAILS_PRESENT)) {
            /*
            User user = new User();
            user.setFirstName(prefs.getString(Prefs.Key.FIRST_NAME));
            user.setLastName(prefs.getString(Prefs.Key.LAST_NAME));
            user.setAdmissionYear(prefs.getInt(Prefs.Key.ADMISSION_YEAR));
            user.setCurrentSemester(prefs.getInt(Prefs.Key.CURRENT_SEM));
            user.setDepartmentName(prefs.getString(Prefs.Key.DEPT_NAME));
            user.setEmail(prefs.getString(Prefs.Key.EMAIL));
            user.setGcmRegId(prefs.getString(Prefs.Key.GCM_REG_ID));
            user.setUrl(prefs.getString(Prefs.Key.URL));
            user.setId(prefs.getInt(Prefs.Key.USER_ID));
            user.setGoogleSub(prefs.getString(Prefs.Key.GOOGLE_SUB));
            user.setIsAdmin(prefs.getBoolean(Prefs.Key.IS_ADMIN));
            user.setVerified(prefs.getBoolean(Prefs.Key.IS_VERIFIED));
            user.setUnivRoll(prefs.getLong(Prefs.Key.UNIV_ROLL));
            user.setIsAlumnus(prefs.getBoolean(Prefs.Key.IS_ALUMNUS));
            user.setPassoutYear(prefs.getInt(Prefs.Key.PASSOUT_YEAR));*/
            Gson gson = new Gson();
            String json = prefs.getString(Prefs.Key.USER);

            return gson.fromJson(json, User.class);
        } else {
            throw new UserDetailsNotPresent();
        }
    }

    public static void storeUser(Context context ,User user) {

        Prefs prefs = Prefs.getInstance(context);

        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefs.put(Prefs.Key.USER, json);
        prefs.put(Prefs.Key.IS_USER_DETAILS_PRESENT, true);
    }

}