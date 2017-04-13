package com.boger.game.gc.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.boger.game.gc.api.http.Fields;
import com.boger.game.gc.api.web.GetWebObservable;
import com.boger.game.gc.model.SquareItemModel;
import com.boger.game.gc.model.SquareListModel;

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
 * Created by Administrator on 2016/3/23.
 */
public class SquareApi {

    private static final String TAG = "SquareApi";

    /**
     * 获取首页数据
     *
     * @param subscriber
     */
    public static void getIndex(Subscriber<List<SquareListModel>> subscriber) {


        GetWebObservable
                .getInstance(Urls.INDEX_URL)
                .map(new Func1<Document, List<SquareListModel>>() {
                    @Override
                    public List<SquareListModel> call(Document document) {
                        Element bodyE = document.body();
                        List<SquareListModel> result = new ArrayList<SquareListModel>();
                        result = getSquareData(bodyE);
                        return result;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private static List<SquareListModel> getSquareData(Element bodyE) {
        List<SquareListModel> result = new ArrayList<>();
        String[] searchKey = new String[]{
                "最受欢迎游戏专区",
                "推荐游戏专区",
                "手机网游专区",
                "网页游戏广场",
                "电玩&单机广场",
                "多玩中心广场"
        };

        for (int i = 0; i < searchKey.length; i++) {
            String squareTitle = searchKey[i];
            Elements elements = bodyE.getElementsByAttributeValue(Fields.WebField.DATA_LIST, squareTitle);
            SquareListModel squareListModel = new SquareListModel();
            squareListModel.setTitle(squareTitle);
            List<SquareItemModel> squareItemModels = getList(elements);
            squareListModel.setList(squareItemModels);
            result.add(squareListModel);
        }
        Log.d(TAG, "getSquareData() = [" + result.toString() + "]");
        return result;
    }

    @NonNull
    private static List<SquareItemModel> getList(Elements elements) {
        List<SquareItemModel> squareItemModels = new ArrayList<>();
        for (Element element : elements) {
            Elements itemEs = element.getElementsByTag(Fields.WebField.LI);
            for (Element itemE : itemEs) {

                Elements tagAEl = itemE.getElementsByTag(Fields.WebField.A);
                Elements tagImg = itemE.getElementsByTag(Fields.WebField.IMG);
                Elements tagP = itemE.getElementsByTag(Fields.WebField.P);
                Elements tagEm = itemE.getElementsByAttributeValue(Fields.WebField.CLASS, Fields.WebField.CHANNEL_NUM);

                SquareItemModel squareItemModel = new SquareItemModel();
                squareItemModel.setTitle(tagP.text().toString());
                squareItemModel.setHrefUrl(tagAEl.attr(Fields.WebField.HREF));
                squareItemModel.setImgSrc(tagImg.attr(Fields.WebField.SRC));
                squareItemModel.setForumNum(tagEm.text());

                squareItemModels.add(squareItemModel);
            }
        }
        return squareItemModels;
    }
}
