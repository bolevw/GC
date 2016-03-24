package com.example.administrator.gc.api;

import android.util.Log;

import com.example.administrator.gc.api.http.Fields;
import com.example.administrator.gc.api.web.GetWebObservable;
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
                List<PreviewGroup> previewGroups = new ArrayList<PreviewGroup>();
                List<HotRankingModel> hotRankingModels = new ArrayList<HotRankingModel>();
                IndexModel model = new IndexModel();

                Document doc = document;
                Element e = doc.body();

                Elements mostPopularEl = e.getElementsByAttributeValue("data-list", Fields.GroupGategory.mostPopular);
                for (Element element : mostPopularEl) {
                    Log.d("web", "element " + element.toString());
                    HotRankingModel hotRankingModel = new HotRankingModel();
                    hotRankingModel.setUrls(element.getElementsByTag(Fields.WebField.A).get(0).attr(Fields.WebField.HREF));
                    hotRankingModel.setImageSrc(element.getElementsByTag(Fields.WebField.IMG).get(0).attr(Fields.WebField.SRC));
                    hotRankingModel.setName(element.getElementsByTag(Fields.WebField.P).get(0).text());
                    hotRankingModels.add(hotRankingModel);
                    Log.d("web", "name" + hotRankingModel.getName());

                }

                Log.d("web", "elements " + mostPopularEl.toString());
            /*    for (Element element : es4) {
                    if (element.toString().contains(Fields.GroupGategory.mostPopular)) {
                        Log.d("web", element.toString());
*//*
                        Elements es3 = e.getElementsByAttributeValue("class", "m-channel__list--con");

                        for (Element el : es3) {
                            HotRankingModel hotRankingModel = new HotRankingModel();

                            Elements imgEl = el.getElementsByTag("img");
                            Elements hrefEl = el.getElementsByTag("a");
                            Elements nameEl = el.getElementsByTag("p");

                            hotRankingModel.setImageSrc(imgEl.get(0).attr("src"));
                            hotRankingModel.setName(nameEl.get(0).text().toString());
                            hotRankingModel.setUrls(hrefEl.get(0).attr("href"));

                            hotRankingModels.add(hotRankingModel);
                            Log.d("hotRankingModel", "img" + hotRankingModel.getImageSrc() + " href" + hotRankingModel.getUrls() + " name " + hotRankingModel.getName() + " size " + previewGroups.size());

                        }*//*
                    } else if (element.toString().contains(Fields.GroupGategory.mobileGame)) {

                    } else if (element.toString().contains(Fields.GroupGategory.recommend)) {

                    }
                }*/
    /*            for (Element el : es2) {


                    for (Element el2 : es3) {
                        if (el2.toString().contains("img")) {
                            String herf = el2.attr("href");
                            PreviewGroup group = new PreviewGroup();
                            group.setUrls(herf);
                            Log.d("web href", herf);
                            Elements es4 = el2.getElementsByTag("img");
                            for (Element el3 : es4) {
                                String imgsrc = el3.attr("src");
                                group.setImageSrc(imgsrc);
                            }
                        } else {

                        }

                    }
                }*/

             /*   for (Element el : es2) {
                    Elements es3 = el.getElementsByTag("p");
                    for (Element el1 : es3) {
                        String groupName = el.attr("p");
                        Log.d("web el1 ", "group name " + groupName);
                    }

                    String imgSrc = el.attr("img");
                }*/
                return model;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


}
