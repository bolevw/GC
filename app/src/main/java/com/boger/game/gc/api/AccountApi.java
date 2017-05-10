package com.boger.game.gc.api;


import com.boger.game.gc.api.http.Fields;
import com.boger.game.gc.api.web.GetWebObservable;
import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.model.PersonalHomePageModel;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Administrator on 2016/3/29.
 */
public class AccountApi {
    private static final String TAG = "AccountApi";

    public static void getHomePage(String url, ApiCallBack<PersonalHomePageModel> callBack) {
        GetWebObservable
                .getInstance(Urls.BASE_URL + url)
                .map(new Function<Document, PersonalHomePageModel>() {
                    @Override
                    public PersonalHomePageModel apply(@NonNull Document document) throws Exception {
                        PersonalHomePageModel model = getHomeModel(document);
                        return model;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    @android.support.annotation.NonNull
    private static PersonalHomePageModel getHomeModel(@NonNull Document document) {
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

        model.setUsername(username);
        model.setUserLevel(wrapperEls.get(0).getElementsByAttributeValue(Fields.WebField.CLASS, "m-personal__level").get(0).text());
        model.setAvatarSrc(userPhoto);
        model.setBgSrc(wrapper);
        model.setThemeUrl(wrapperEls.get(0).getElementsByAttributeValue(Fields.WebField.CLASS, "m-personal__enter").get(0).getElementsByTag(Fields.WebField.A).get(0).attr(Fields.WebField.HREF));
        model.setFriendUrl(wrapperEls.get(0).getElementsByAttributeValue(Fields.WebField.CLASS, "m-personal__enter").get(0).getElementsByTag(Fields.WebField.A).get(1).attr(Fields.WebField.HREF));
        return model;
    }

}
