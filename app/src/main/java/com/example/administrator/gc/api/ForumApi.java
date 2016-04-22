package com.example.administrator.gc.api;

import android.text.TextUtils;
import android.util.Log;

import com.example.administrator.gc.api.http.Fields;
import com.example.administrator.gc.api.web.GetWebObservable;
import com.example.administrator.gc.base.BaseSub;
import com.example.administrator.gc.model.ForumItemDetailModel;
import com.example.administrator.gc.model.ForumPostListItemModel;
import com.example.administrator.gc.model.ForumPostPageListItemModel;
import com.example.administrator.gc.model.PostBodyModel;
import com.example.administrator.gc.model.PostDetailHeaderModel;
import com.example.administrator.gc.model.PostDetailModel;
import com.example.administrator.gc.model.UserMessageModel;
import com.example.administrator.gc.ui.activity.PostDetailActivity;

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

                            list.add(model);
                        }

                        Elements nextPage = el.getElementsByAttributeValue(Fields.WebField.CLASS, "m-forumList__page");

                        itemModel.setList(list);
                        itemModel.setNextPageUrls(nextPage.get(0).getElementsByTag(Fields.WebField.LI).get(2).getElementsByTag(Fields.WebField.A).attr(Fields.WebField.HREF));

                        return itemModel;
                    }

                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public static final void getPostDetail(String urls, final boolean getNextPage, BaseSub<PostBodyModel, PostDetailActivity> subscriber) {
        GetWebObservable.getInstance(Urls.BASE_URL + "/" + urls).map(new Func1<Document, PostBodyModel>() {
            @Override
            public PostBodyModel call(Document document) {

                PostBodyModel body = new PostBodyModel();
                List<PostDetailModel> detailModels = new ArrayList<PostDetailModel>();

                Element element = document.body();

                Elements els = element.getElementsByAttributeValue(Fields.WebField.CLASS, "m-comment__item m-comment__item--top");

                boolean first = true;
                boolean nextPage = getNextPage;

                for (Element itemEle : els) {
                    Elements userInfoEls = itemEle.getElementsByAttributeValue(Fields.WebField.CLASS, "comment-authorInfo");
                    Elements commentEls = itemEle.getElementsByAttributeValue(Fields.WebField.CLASS, "comment-detail");
                    for (int i = 0; i < userInfoEls.size(); i++) {
                        Element itemEle2 = userInfoEls.get(i);
                        Element commentEl = commentEls.get(i);
                        Elements picEls = itemEle2.getElementsByAttributeValue(Fields.WebField.CLASS, "comment-authorInfo__pic");
                        String picSrc = picEls.get(0).getElementsByTag(Fields.WebField.IMG).get(0).attr(Fields.WebField.SRC);
                        Elements userEls = itemEle2.getElementsByAttributeValue(Fields.WebField.CLASS, "comment-authorInfo__detail");
                        String userUrl = userEls.get(0).getElementsByTag(Fields.WebField.A).get(0).attr(Fields.WebField.HREF);
                        String userName = userEls.get(0).getElementsByTag(Fields.WebField.A).get(0).text();
                        String date = userEls.get(0).getElementsByTag(Fields.WebField.P).get(0).text();

                        UserMessageModel userMessageModel = new UserMessageModel();
                        userMessageModel.setUserName(userName);
                        userMessageModel.setUserPhotoSrc(picSrc);
                        userMessageModel.setUserHomePageUrl(userUrl);
                        userMessageModel.setDate(date);

                        if (first && !nextPage) {
                            first = false;
                            nextPage = true;

                            PostDetailHeaderModel headerModel = new PostDetailHeaderModel();
                            headerModel.setHeader(userMessageModel);
                            headerModel.setTitle(element.getElementsByAttributeValue(Fields.WebField.CLASS, "m-infoBBS__title").get(0).text());
                            headerModel.setContent(commentEl.text());
                            body.setHeader(headerModel);
                        } else {
                            PostDetailModel detail = new PostDetailModel();
                            detail.setContent(TextUtils.isEmpty(commentEl.text()) ? "null" : commentEl.text());
                            detail.setUserMessageModel(userMessageModel);
                            detailModels.add(detail);
                        }
                    }
                }
                Elements nextPageEls = document.body().getElementsByAttributeValue(Fields.WebField.CLASS, "m-forumList__page");

                body.setNextPageUrl(nextPageEls.get(0).getElementsByTag(Fields.WebField.LI).get(2).getElementsByTag(Fields.WebField.A).attr(Fields.WebField.HREF));
                body.setCommentList(detailModels);

                return body;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
