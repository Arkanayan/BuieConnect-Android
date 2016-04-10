package me.arkanayan.buieconnect.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    @SerializedName("reg_date")
    @Expose
    private String regDate;
    @SerializedName("is_admin")
    @Expose
    private Boolean isAdmin;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("admission_year")
    @Expose
    private Object admissionYear;
    @SerializedName("department_name")
    @Expose
    private Object departmentName;
    @SerializedName("current_semester")
    @Expose
    private Object currentSemester;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("univ_roll")
    @Expose
    private Object univRoll;
    @SerializedName("passout_year")
    @Expose
    private Object passoutYear;
    @SerializedName("gcm_reg_id")
    @Expose
    private Object gcmRegId;
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
    public Boolean getIsAlumnus() {
        return isAlumnus;
    }

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

    /**
     *
     * @return
     * The regDate
     */
    public String getRegDate() {
        return regDate;
    }

    /**
     *
     * @param regDate
     * The reg_date
     */
    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

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
    public void setAdmissionYear(Object admissionYear) {
        this.admissionYear = admissionYear;
    }

    /**
     *
     * @return
     * The departmentName
     */
    public Object getDepartmentName() {
        return departmentName;
    }

    /**
     *
     * @param departmentName
     * The department_name
     */
    public void setDepartmentName(Object departmentName) {
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
    public void setCurrentSemester(Object currentSemester) {
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
    public Object getUnivRoll() {
        return univRoll;
    }

    /**
     *
     * @param univRoll
     * The univ_roll
     */
    public void setUnivRoll(Object univRoll) {
        this.univRoll = univRoll;
    }

    /**
     *
     * @return
     * The passoutYear
     */
    public Object getPassoutYear() {
        return passoutYear;
    }

    /**
     *
     * @param passoutYear
     * The passout_year
     */
    public void setPassoutYear(Object passoutYear) {
        this.passoutYear = passoutYear;
    }

    /**
     *
     * @return
     * The gcmRegId
     */
    public Object getGcmRegId() {
        return gcmRegId;
    }

    /**
     *
     * @param gcmRegId
     * The gcm_reg_id
     */
    public void setGcmRegId(Object gcmRegId) {
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

}