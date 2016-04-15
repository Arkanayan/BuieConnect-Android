package me.arkanayan.buieconnect.services;

import java.util.List;

import me.arkanayan.buieconnect.ServiceGenerator;
import me.arkanayan.buieconnect.apis.NoticesApi;
import me.arkanayan.buieconnect.models.Notice;
import retrofit2.Call;

/**
 * Created by arka on 4/15/16.
 */
public class NoticeService {

    public String TAG = this.getClass().getSimpleName();

    private NoticesApi noticeApi;

    public NoticeService() {
        this.noticeApi = ServiceGenerator.createService(NoticesApi.class);
    }

    public Call<List<Notice>> getNoticesCall() {

        return noticeApi.getNotices();
    }

    public Call<Notice> getNoticeCall(int id) {

        return noticeApi.getNotice(id);
    }
}
