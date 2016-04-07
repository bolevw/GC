package com.example.administrator.gc.api;

import android.util.Log;

import com.example.administrator.gc.api.http.Fields;
import com.example.administrator.gc.api.web.GetWebObservable;
import com.example.administrator.gc.model.ForumItemDetailModel;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/4/7.
 */
public class ForumApi {

    public static final void getForum(String url, Subscriber<ArrayList<ForumItemDetailModel>> subscriber) {
        GetWebObservable.getInstance(Urls.BASE_URL + "/" + url)
                .map(new Func1<Document, ArrayList<ForumItemDetailModel>>() {
                    @Override
                    public ArrayList<ForumItemDetailModel> call(Document document) {
                        ArrayList<ForumItemDetailModel> res = new ArrayList<ForumItemDetailModel>();
                        Element el = document.body();
                        Log.d("HTML", el.toString());
                        if (el.toString().contains(Fields.GroupCategory.H_5)) {

                            Elements els = el.getElementsByAttributeValue(Fields.WebField.CLASS, Fields.WebField.M_CHANNEL_LIST_ITEM);
                            for (Element ele : els) {
                                ForumItemDetailModel detailModel = new ForumItemDetailModel();

                                Elements tagAs = ele.getElementsByTag(Fields.WebField.A);
                                Elements tagImgs = ele.getElementsByTag(Fields.WebField.IMG);
                                Elements tagH4 = ele.getElementsByTag(Fields.WebField.H_4);
                                Elements tagNameCount = tagH4.get(0).getElementsByTag(Fields.WebField.EM);
                                Elements tagP = ele.getElementsByTag(Fields.WebField.P).get(0).getElementsByTag(Fields.WebField.SPAN);

                                Elements themeTagCount = tagP.get(0).getElementsByTag(Fields.WebField.EM);
                                Elements postCountTag = tagP.get(1).getElementsByTag(Fields.WebField.EM);

                                detailModel.setName(tagH4.text());
                                detailModel.setUrls(tagAs.attr(Fields.WebField.HREF));
                                detailModel.setImageUrls(tagImgs.attr(Fields.WebField.SRC));
                                detailModel.setNewCount(tagNameCount.text());
                                detailModel.setThemeCount(themeTagCount.text());
                                detailModel.setPostCount(postCountTag.text());

                                res.add(detailModel);
                                Log.d("detailModel", detailModel.toString());
                            }

                        }

                        return res;
                    }

                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public static final void getPost(String urls, Subscriber<String> subscriber) {
        GetWebObservable.getInstance(Urls.BASE_URL + "/" + urls)
                .map(new Func1<Document, String>() {
                    @Override
                    public String call(Document document) {
                        return null;
                    }

                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
