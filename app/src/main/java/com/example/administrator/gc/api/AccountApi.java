package com.example.administrator.gc.api;

import android.util.Log;

import com.example.administrator.gc.api.http.Fields;
import com.example.administrator.gc.api.web.GetWebObservable;
import com.example.administrator.gc.base.BaseSub;
import com.example.administrator.gc.model.PersonalHomePageModel;
import com.example.administrator.gc.ui.activity.PersonalHomePageActivity;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/3/29.
 */
public class AccountApi {


    private static final String TAG = "AccountApi";

    public static void getLogin(String url, Subscriber<String> subscriber) {
        GetWebObservable.getInstance(url).map(new Func1<Document, String>() {
            @Override
            public String call(Document document) {
                Element bodyEl = document.body();
                Log.d(TAG, "body - >" + bodyEl.toString());
                return document.toString();
            }

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }


    public static void getHomePage(String url, BaseSub<PersonalHomePageModel, PersonalHomePageActivity> subscriber) {
        GetWebObservable.getInstance(Urls.BASE_URL + "/" + url)
                .map(new Func1<Document, PersonalHomePageModel>() {
                    @Override
                    public PersonalHomePageModel call(Document document) {
                        Element element = document.body();
                        PersonalHomePageModel model = new PersonalHomePageModel();

                        Elements wrapperEls = element.getElementsByAttributeValue(Fields.WebField.CLASS, "wrapper");

                        Elements imgEls = wrapperEls.get(0).getElementsByTag(Fields.WebField.IMG);
                        String wrapper = imgEls.get(0).attr(Fields.WebField.SRC);
                        String userPhoto = imgEls.get(1).attr(Fields.WebField.SRC);
                        String username = wrapperEls.get(0).getElementsByTag("h3").get(0).text();

                        Elements itemELs = wrapperEls.get(0).getElementsByAttributeValue(Fields.WebField.CLASS, "m-personal__item");
                        model.setMoney(itemELs.get(0).getElementsByTag(Fields.WebField.EM).text());
                        model.setPrestige(itemELs.get(1).getElementsByTag(Fields.WebField.EM).text());
                        model.setGrass(itemELs.get(2).getElementsByTag(Fields.WebField.EM).text());

                        model.setUserLevel(username);
                        model.setUserLevel(wrapperEls.get(0).getElementsByAttributeValue(Fields.WebField.CLASS, "m-personal__level").get(0).text());
                        model.setAvatarSrc(userPhoto);
                        model.setBgSrc(wrapper);
                        model.setThemeUrl(wrapperEls.get(0).getElementsByAttributeValue(Fields.WebField.CLASS, "m-personal__enter").get(0).getElementsByTag(Fields.WebField.A).get(0).attr(Fields.WebField.HREF));
                        model.setFriendUrl(wrapperEls.get(0).getElementsByAttributeValue(Fields.WebField.CLASS, "m-personal__enter").get(0).getElementsByTag(Fields.WebField.A).get(1).attr(Fields.WebField.HREF));

                        Log.d("personalHomePageModel", model.toString());
                        return model;
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
