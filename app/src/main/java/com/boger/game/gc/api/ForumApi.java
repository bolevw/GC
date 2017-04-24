package com.boger.game.gc.api;

import android.text.TextUtils;
import android.util.Log;

import com.boger.game.gc.api.http.Fields;
import com.boger.game.gc.api.web.GetWebObservable;
import com.boger.game.gc.base.BaseSub;
import com.boger.game.gc.model.ArticleCoverListModel;
import com.boger.game.gc.model.ArticleCoverModel;
import com.boger.game.gc.model.ChildrenModuleCoverModel;
import com.boger.game.gc.model.ForumIndexHeaderModel;
import com.boger.game.gc.model.ForumIndexModel;
import com.boger.game.gc.model.PostBodyModel;
import com.boger.game.gc.model.PostDetailHeaderModel;
import com.boger.game.gc.model.PostDetailModel;
import com.boger.game.gc.model.UserMessageModel;
import com.boger.game.gc.ui.activity.PostDetailActivity;

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

    private static final String TAG = "ForumApi";

    /**
     * 获取论坛分类
     *
     * @param url
     * @param subscriber
     */
    public static void getForumDetail(String url, Subscriber<ForumIndexModel> subscriber) {
        GetWebObservable.getInstance(Urls.BASE_URL + url)
                .map(new Func1<Document, ForumIndexModel>() {
                    @Override
                    public ForumIndexModel call(Document document) {
                        Element el = document.body();
                        Log.d(TAG, "call() called with: document = [" + el.toString() + "]");
                        String title = "";
                        title = getTitle(el, title);

                        String imgSrc = null;
                        imgSrc = getImgSrc(el, imgSrc);

                        String themeCount = getThemeCount(el);
                        String todayCount = getTodayCount(el);
                        ForumIndexHeaderModel headerModel = new ForumIndexHeaderModel(title, imgSrc, themeCount, todayCount);
                        String videoUrl = getVideoUrl(el);
                        Log.d(TAG, "call() called with: document = [" + videoUrl + "]");


                        final ArrayList<ChildrenModuleCoverModel> res = new ArrayList<ChildrenModuleCoverModel>();
                        getChildrenModule(el, res);

                        final ArticleCoverListModel articleCoverListModel = new ArticleCoverListModel();
                        getArticleCoverList(el, articleCoverListModel);
                        return new ForumIndexModel(headerModel, videoUrl, res, articleCoverListModel);
                    }

                    private String getTodayCount(Element el) {
                        Elements els = el.getElementsByAttributeValue(Fields.WebField.CLASS, "m-infoBBS__detail").get(0).getElementsByTag(Fields.WebField.EM);
                        if (els.size() > 3) {
                            return els.get(3).text();
                        }
                        return "0";
                    }

                    private String getThemeCount(Element el) {
                        Elements els = el.getElementsByAttributeValue(Fields.WebField.CLASS, "m-infoBBS__detail").get(0).getElementsByTag(Fields.WebField.EM);
                        if (els.size() > 2) {
                            return els.get(2).text();
                        }
                        return "0";
                    }

                    private void getArticleCoverList(Element el, ArticleCoverListModel articleCoverListModel) {
                        ArrayList<ArticleCoverModel> list = new ArrayList<ArticleCoverModel>();
                        Elements els = el.getElementsByAttributeValue(Fields.WebField.CLASS, Fields.WebField.M_FORUMLIST_ITEM);
                        if (els.size() <= 0) {
                            return;
                        }
                        for (Element ele : els) {
                            ArticleCoverModel model = new ArticleCoverModel();
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

                        articleCoverListModel.setList(list);
                        articleCoverListModel.setNextPageUrls(nextPage.get(0).getElementsByTag(Fields.WebField.LI).get(2).getElementsByTag(Fields.WebField.A).attr(Fields.WebField.HREF));
                    }

                    private String getImgSrc(Element el, String imgSrc) {
                        return el.getElementsByAttributeValue(Fields.WebField.CLASS, "m-infoBBS__pic").get(0).getElementsByTag(Fields.WebField.IMG).attr(Fields.WebField.SRC);
                    }

                    private String getTitle(Element el, String title) {
                        Elements els = el.getElementsByAttributeValue(Fields.WebField.CLASS, "m-menu__title");
                        return els.text();
                    }

                    private String getVideoUrl(Element element) {
                        if (element.toString().contains("m-video__more")) {
                            Elements elements = element.getElementsByAttributeValue(Fields.WebField.CLASS, "m-video__more");
                            Log.d(TAG, "getVideoUrl() called with: el = [" + elements.size() + elements.toString() + "]");
                            if (elements == null || elements.size() == 0) {
                                return null;
                            } else {
                                if (elements.get(0) != null) {
                                    return elements.get(0).attr(Fields.WebField.HREF);
                                } else {
                                    return null;
                                }
                            }
                        } else {
                            return null;
                        }
                    }

                    private void getChildrenModule(Element el, ArrayList<ChildrenModuleCoverModel> res) {
                        if (el.toString().contains(Fields.GroupCategory.H_5)) {
                            Elements els = el.getElementsByAttributeValue(Fields.WebField.CLASS, Fields.WebField.M_CHANNEL_LIST_ITEM);
                            if (els == null || els.size() <= 0) {
                                return;
                            }
                            for (Element ele : els) {
                                ChildrenModuleCoverModel detailModel = new ChildrenModuleCoverModel();

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
                    }

                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public static void getPostList(String urls, Subscriber<ArticleCoverListModel> subscriber) {
        GetWebObservable.getInstance(Urls.BASE_URL + urls)
                .map(new Func1<Document, ArticleCoverListModel>() {
                    @Override
                    public ArticleCoverListModel call(Document document) {
                        ArticleCoverListModel itemModel = new ArticleCoverListModel();
                        ArrayList<ArticleCoverModel> list = new ArrayList<ArticleCoverModel>();
                        Element el = document.body();
                        Elements els = el.getElementsByAttributeValue(Fields.WebField.CLASS, Fields.WebField.M_FORUMLIST_ITEM);
                        for (Element ele : els) {
                            ArticleCoverModel model = new ArticleCoverModel();
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


    public static void getPostDetail(String urls, final boolean getNextPage, BaseSub<PostBodyModel, PostDetailActivity> subscriber) {
        GetWebObservable.getInstance(Urls.BASE_URL + urls).map(new Func1<Document, PostBodyModel>() {
            @Override
            public PostBodyModel call(Document document) {

                PostBodyModel body = new PostBodyModel();
                List<PostDetailModel> detailModels = new ArrayList<PostDetailModel>();
                Log.e(TAG, "call: " + document.toString());
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
                            headerModel.setContent(commentEl.toString());
                            body.setHeader(headerModel);
                        } else {
                            PostDetailModel detail = new PostDetailModel();
                            detail.setContent(TextUtils.isEmpty(commentEl.text()) ? "null" : commentEl.toString());
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
