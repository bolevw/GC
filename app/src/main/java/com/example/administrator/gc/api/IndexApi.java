package com.example.administrator.gc.api;

import android.util.Log;

import com.example.administrator.gc.api.http.Fields;
import com.example.administrator.gc.api.web.GetWebObservable;
import com.example.administrator.gc.model.GroupItemModel;
import com.example.administrator.gc.model.HotRankingModel;
import com.example.administrator.gc.model.IndexModel;
import com.example.administrator.gc.model.PreviewGroup;

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


    /**
     * 获取首页数据
     *
     * @param url
     * @param subscriber
     */
    public static void getIndex(final String url, Subscriber<IndexModel> subscriber) {

/*        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(HttpClient.getClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(new StringConverter())
                .build();

        Observable<String> observable = retrofit.create(IndexService.class).get();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);*/

        GetWebObservable.getInstance(url).map(new Func1<Document, IndexModel>() {
            @Override
            public IndexModel call(Document document) {
                IndexModel model = new IndexModel();
                List<PreviewGroup> previewGroups = new ArrayList<PreviewGroup>();
                List<HotRankingModel> hotRankingModels = new ArrayList<HotRankingModel>();

                Document doc = document;
                Element bodyE = doc.body();
                hotRankingModels = getHotRankingLit(bodyE);
                previewGroups = getPreviewGroupList(bodyE);

                model.setPreviewGroupList(previewGroups);
                model.setHotRankingList(hotRankingModels);
                Log.d("web", model.toString());
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

}
