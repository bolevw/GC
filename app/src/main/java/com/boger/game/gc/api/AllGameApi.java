package com.boger.game.gc.api;

import com.boger.game.gc.api.http.Fields;
import com.boger.game.gc.api.web.GetWebObservable;
import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.model.GameItemModel;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Administrator on 2016/3/29.
 */
public class AllGameApi {
    private static final String TAG = "AllGameApi";

    public static void getAllGame(String urls, ApiCallBack<List<GameItemModel>> callBack) {
        GetWebObservable
                .getInstance(urls)
                .map(new Function<Document, List<GameItemModel>>() {
                    @Override
                    public List<GameItemModel> apply(@NonNull Document document) throws Exception {
                        ArrayList<GameItemModel> list = getGameItemModels(document);
                        return list;
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    @android.support.annotation.NonNull
    private static ArrayList<GameItemModel> getGameItemModels(@NonNull Document document) {
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

}
