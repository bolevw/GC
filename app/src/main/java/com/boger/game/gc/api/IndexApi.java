package com.boger.game.gc.api;

import android.util.Log;

import com.boger.game.gc.api.http.Fields;
import com.boger.game.gc.api.web.GetWebObservable;
import com.boger.game.gc.model.ForumModel;
import com.boger.game.gc.model.GroupItemModel;
import com.boger.game.gc.model.HotRankingModel;
import com.boger.game.gc.model.IndexModel;
import com.boger.game.gc.model.PreviewGroup;

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
public class IndexApi {

    private static final String TAG = "IndexApi";

    /**
     * 获取首页数据
     *
     * @param url
     * @param subscriber
     */
    public static void getIndex(final String url, Subscriber<IndexModel> subscriber) {

        GetWebObservable.getInstance(url).map(new Func1<Document, IndexModel>() {
            @Override
            public IndexModel call(Document document) {
                IndexModel model = new IndexModel();
                List<PreviewGroup> previewGroups = new ArrayList<PreviewGroup>();
                List<HotRankingModel> hotRankingModels = new ArrayList<HotRankingModel>();
                Element bodyE = document.body();
                hotRankingModels = getHotRankingLit(bodyE);
                previewGroups = getPreviewGroupList(bodyE);

                model.setPreviewGroupList(previewGroups);
                model.setHotRankingList(hotRankingModels);
                return model;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private static List<HotRankingModel> getHotRankingLit(Element bodyE) {
        List<HotRankingModel> hotRankingModels = new ArrayList<>();
        Elements mostPopularEs = bodyE.getElementsByAttributeValue(Fields.WebField.DATA_LIST, Fields.GroupCategory.mostPopular);
        for (Element element : mostPopularEs) {
            Elements itemEs = element.getElementsByTag(Fields.WebField.LI);
            for (Element itemE : itemEs) {

                Elements tagAEl = itemE.getElementsByTag(Fields.WebField.A);
                Elements tagImg = itemE.getElementsByTag(Fields.WebField.IMG);
                Elements tagP = itemE.getElementsByTag(Fields.WebField.P);

                HotRankingModel hotRankingModel = new HotRankingModel();
                hotRankingModel.setName(tagP.text().toString());
                hotRankingModel.setUrls(tagAEl.attr(Fields.WebField.HREF));
                hotRankingModel.setImageSrc(tagImg.attr(Fields.WebField.SRC));

                hotRankingModels.add(hotRankingModel);
            }
        }
        Log.e(TAG, "getHotRankingLit: " + hotRankingModels.toString());
        return hotRankingModels;
    }

    private static List<PreviewGroup> getPreviewGroupList(Element bodyE) {
        List<PreviewGroup> previewGroups = new ArrayList<>();
        PreviewGroup recommendGroup = new PreviewGroup();
        recommendGroup = getPreviewGroup(bodyE, Fields.GroupCategory.recommend);
        previewGroups.add(recommendGroup);

        PreviewGroup mobileGroup = new PreviewGroup();
        mobileGroup = getPreviewGroup(bodyE, Fields.GroupCategory.mobileGame);
        previewGroups.add(mobileGroup);

        PreviewGroup webGameGroup = new PreviewGroup();
        webGameGroup = getPreviewGroup(bodyE, Fields.GroupCategory.webGame);
        previewGroups.add(webGameGroup);

        PreviewGroup standAloneGroup = new PreviewGroup();
        standAloneGroup = getPreviewGroup(bodyE, Fields.GroupCategory.standAloneGame);
        previewGroups.add(standAloneGroup);

        PreviewGroup duowanCenterGroup = new PreviewGroup();
        duowanCenterGroup = getPreviewGroup(bodyE, Fields.GroupCategory.duowanCenter);
        previewGroups.add(duowanCenterGroup);

        Log.e(TAG, "getPreviewGroupList: " + previewGroups.toString());
        return previewGroups;
    }

    private static PreviewGroup getPreviewGroup(Element bodyE, String fields) {
        PreviewGroup group = new PreviewGroup();
        group.setName(fields);
        Elements urlEs = bodyE.getElementsByTag(Fields.WebField.A);
        if (urlEs.toString().contains(fields)) {
            Element urlEl = urlEs.get(0);
            group.setUrls(urlEl.attr(Fields.WebField.HREF));
        }
        List<GroupItemModel> groupItemModels = new ArrayList<>();
        Elements mostPopularEs = bodyE.getElementsByAttributeValue(Fields.WebField.DATA_LIST, fields);
        for (Element element : mostPopularEs) {
            Elements itemEs = element.getElementsByTag(Fields.WebField.LI);
            for (Element itemE : itemEs) {
                Elements tagAEl = itemE.getElementsByTag(Fields.WebField.A);
                Elements tagImg = itemE.getElementsByTag(Fields.WebField.IMG);
                Elements tagP = itemE.getElementsByTag(Fields.WebField.P);

                GroupItemModel groupItemModel = new GroupItemModel();
                groupItemModel.setName(tagP.text().toString());
                groupItemModel.setUrl(tagAEl.attr(Fields.WebField.HREF));
                groupItemModel.setImageSrc(tagImg.attr(Fields.WebField.SRC));
                groupItemModels.add(groupItemModel);
            }
        }

        group.setGroupItemList(groupItemModels);

        return group;
    }


    public static void getIndexDetail(Subscriber<List<ForumModel>> subscriber) {
        GetWebObservable.getInstance(Urls.INDEX_URL)
                .map(new Func1<Document, List<ForumModel>>() {

                    @Override
                    public List<ForumModel> call(Document document) {
                        ArrayList<ForumModel> list = new ArrayList<ForumModel>();
                        Element bodyEl = document.body();

                        Elements divEs = bodyEl.getElementsByAttributeValue(Fields.WebField.CLASS, Fields.AllGame.TAG);
                        for (Element el : divEs) {
                            ForumModel item = new ForumModel();
                            Elements tagAEl = el.getElementsByTag(Fields.WebField.A);
                            Elements tagImg = el.getElementsByTag(Fields.WebField.IMG);
                            Elements tagP = el.getElementsByTag(Fields.WebField.P);
                            Elements tagEm = el.getElementsByAttributeValue(Fields.WebField.CLASS, Fields.WebField.CHANNEL_NUM);

                            item.setForumName(tagP.text().toString());
                            item.setImageSrc(tagImg.attr(Fields.WebField.SRC));
                            item.setForumCount(tagEm.text().toString());
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
