package com.example.administrator.gc.api;

import android.util.Log;

import com.example.administrator.gc.api.http.Fields;
import com.example.administrator.gc.api.web.GetWebObservable;
import com.example.administrator.gc.model.ForumItemDetailModel;
import com.example.administrator.gc.model.ForumPostListItemModel;
import com.example.administrator.gc.model.ForumPostPageListItemModel;

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


    public static final void getPostList(String urls, Subscriber<ForumPostPageListItemModel> subscriber) {
        GetWebObservable.getInstance(Urls.BASE_URL + "/" + urls)
                .map(new Func1<Document, ForumPostPageListItemModel>() {
                    @Override
                    public ForumPostPageListItemModel call(Document document) {
                        ForumPostPageListItemModel itemModel = new ForumPostPageListItemModel();
                        ArrayList<ForumPostListItemModel> list = new ArrayList<ForumPostListItemModel>();
                        Element el = document.body();
                        Elements els = el.getElementsByAttributeValue(Fields.WebField.CLASS, Fields.WebField.M_FORUMLIST_ITEM);
                        for (Element ele : els) {
                            ForumPostListItemModel model = new ForumPostListItemModel();
                            Elements tagAEls = ele.getElementsByTag(Fields.WebField.A);
                            Elements tagPEls = ele.getElementsByTag(Fields.WebField.P);


                            Element element = tagPEls.get(1);
                            Elements spanEls = element.getElementsByTag(Fields.WebField.SPAN);
                            String authName = spanEls.get(0).getElementsByTag(Fields.WebField.EM).get(0).text();
                            String date = spanEls.get(0).getElementsByTag(Fields.WebField.EM).get(1).text();

                            String commentCount = spanEls.get(1).getElementsByTag(Fields.WebField.EM).get(1).text();

                            model.setName(tagPEls.get(0).text());
                            model.setUrls(tagAEls.attr(Fields.WebField.HREF));
                            model.setAuthName(authName);
                            model.setDate(date);
                            model.setCommentCount(commentCount);

                            Log.d("ForumPostListItemModel", model.toString());
                            list.add(model);
                        }

                        Elements nextPage = el.getElementsByAttributeValue(Fields.WebField.CLASS, "m-forumList__page");

                        itemModel.setList(list);
                        itemModel.setNextPageUrls(nextPage.get(0).getElementsByTag(Fields.WebField.LI).get(2).getElementsByTag(Fields.WebField.A).attr(Fields.WebField.HREF));

                        Log.d("nexturls", nextPage.get(0).getElementsByTag(Fields.WebField.LI).get(2).getElementsByTag(Fields.WebField.A).attr(Fields.WebField.HREF));

                        return itemModel;
                    }

                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public static final void getPostDetail(String urls, Subscriber<String> subscriber) {
        GetWebObservable.getInstance(Urls.BASE_URL + "/" + urls).map(new Func1<Document, String>() {
            @Override
            public String call(Document document) {

                return document.body().text();
            }

        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
