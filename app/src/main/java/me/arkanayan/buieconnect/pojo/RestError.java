
package me.arkanayan.buieconnect.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.GsonConverterFactory;


public class RestError {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("errors")
    @Expose
    private List<Object> errors = new ArrayList<Object>();
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * @return The code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @param code The code
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * @return The errors
     */
    public List<Object> getErrors() {
        return errors;
    }

    /**
     * @param errors The errors
     */
    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }
    /**
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.getMessage();
    }

    public static RestError getErrorObj(ResponseBody errorBody) throws IOException {

        Converter<ResponseBody, ?> converter =
                GsonConverterFactory.create().responseBodyConverter(RestError.class,
                        RestError.class.getAnnotations(), null);

        return (RestError) converter.convert(errorBody);
    }
}