package com.example.administrator.gc.api;

import com.example.administrator.gc.api.http.Fields;
import com.example.administrator.gc.api.web.GetWebObservable;
import com.example.administrator.gc.model.GameItemModel;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/3/29.
 */
public class AllGameApi {
    private static final String TAG = "AllGameApi";

    public static void getAllGame(String urls, Subscriber<List<GameItemModel>> subscriber) {
        GetWebObservable.getInstance(urls).map(new Func1<Document, List<GameItemModel>>() {
            @Override
            public List<GameItemModel> call(Document document) {
                ArrayList<GameItemModel> list = new ArrayList<GameItemModel>();
                Element bodyEl = document.body();
                Elements divEs = bodyEl.getElementsByAttributeValue(Fields.WebField.CLASS, Fields.AllGame.TAG);
                for (Element el : divEs) {
                    GameItemModel item = new GameItemModel();
                    Elements tagAEl = el.getElementsByTag(Fields.WebField.A);
                    Elements tagImg = el.getElementsByTag(Fields.WebField.IMG);
                    Elements tagP = el.getElementsByTag(Fields.WebField.P);

                    item.setName(tagP.text().toString());
                    item.setImageSrc(tagImg.attr(Fields.WebField.SRC));
                    item.setUrls(tagAEl.attr(Fields.WebField.HREF));
                    list.add(item);
                }

                return list;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
