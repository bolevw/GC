package com.boger.game.gc.api;

import android.util.Log;

import com.boger.game.gc.api.http.Fields;
import com.boger.game.gc.api.web.GetWebObservable;
import com.boger.game.gc.base.ApiCallBack;
import com.boger.game.gc.model.VideoBannerModel;
import com.boger.game.gc.model.VideoChannelModel;
import com.boger.game.gc.model.VideoChannelTitleModel;
import com.boger.game.gc.model.VideoIndexItemModel;
import com.boger.game.gc.model.VideoIndexModel;
import com.boger.game.gc.model.VideoPlayerCoverModel;

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
 * Created by Administrator on 2016/4/27.
 */
public class VideoApi {
    private static final String TAG = "VideoApi";

    public static void getVideoList(String url, ApiCallBack<VideoIndexModel> sub) {
        GetWebObservable
                .getInstance(url)
                .map(new Function<Document, VideoIndexModel>() {
                    @Override
                    public VideoIndexModel apply(@NonNull Document document) throws Exception {
                        VideoIndexModel result = new VideoIndexModel();
                        Log.d(TAG, "call() called with: document = [" + document.body().toString() + "]");
                        Element el = document.body();
                        List<VideoBannerModel> banners = getBanners(el);
                        List<VideoIndexItemModel> items = getItems(el);
                        result.setBanners(banners);
                        result.setItems(items);
                        Log.d(TAG, "call() called with: document = [" + result.toString() + "]");
                        return result;
                    }

                    private List<VideoIndexItemModel> getItems(Element el) {
                        Elements elements = el.getElementsByAttributeValue(Fields.WebField.CLASS, "fun-videos");
                        if (elements.size() > 0) {
                            List<VideoIndexItemModel> list = new ArrayList<VideoIndexItemModel>();
                            for (Element node : elements) {
                                VideoIndexItemModel itemModel = new VideoIndexItemModel();
                                String channelTitle = node.getElementsByTag("h3").get(0).text();
                                String channelHref = node.getElementsByTag("a").get(0).attr("href");
                                VideoChannelTitleModel titleModel = new VideoChannelTitleModel(channelTitle, channelHref);
                                itemModel.setTitle(titleModel);
                                List<VideoChannelModel> videoList = new ArrayList<VideoChannelModel>();
                                Elements videoNode = node.getElementsByAttributeValue(Fields.WebField.CLASS, "video-list_item");
                                if (videoNode.size() > 0) {
                                    for (Element videoItem : videoNode) {
                                        String cover = videoItem.getElementsByAttributeValue(Fields.WebField.CLASS, "video-cover").get(0).attr("src");
                                        String title = videoItem.getElementsByAttributeValue(Fields.WebField.CLASS, "video-title").get(0).text();
                                        String time = videoItem.getElementsByAttributeValue(Fields.WebField.CLASS, "video-duration").get(0).text();
                                        String href = videoItem.getElementsByTag("a").attr("href");
                                        String user = videoItem.getElementsByAttributeValue(Fields.WebField.CLASS, "user-name").get(0).text();
                                        String num = videoItem.getElementsByAttributeValue(Fields.WebField.CLASS, "play-time").get(0).text();
                                        VideoChannelModel videoChannelModel = new VideoChannelModel(href, user, num, cover, time, title);
                                        videoList.add(videoChannelModel);
                                    }
                                }
                                itemModel.setList(videoList);
                                list.add(itemModel);
                            }
                            return list;
                        } else {
                            return null;
                        }
                    }

                    private List<VideoBannerModel> getBanners(Element el) {
                        Elements elements = el.getElementsByAttributeValue(Fields.WebField.CLASS, "swiper-slide");
                        if (elements.size() > 0) {
                            List<VideoBannerModel> list = new ArrayList<VideoBannerModel>();
                            for (Element element : elements) {
                                String href = element.getElementsByTag("a").get(0).attr("href");
                                String cover = element.getElementsByTag("img").get(0).attr("src");
                                String title = element.getElementsByTag("p").text();
                                VideoBannerModel model = new VideoBannerModel(cover, href, title);
                                list.add(model);
                            }
                            return list;
                        } else {
                            return null;
                        }
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sub);
    }


    public static void getVideo(String url, ApiCallBack<VideoPlayerCoverModel> subscriber) {
        GetWebObservable
                .getInstance(url)
                .map(new Function<Document, VideoPlayerCoverModel>() {
                    @Override
                    public VideoPlayerCoverModel apply(@NonNull Document document) throws Exception {
                        VideoPlayerCoverModel model = new VideoPlayerCoverModel();
                        Element el = document.body();
                        Log.d(TAG, "call() called with: document = [" + el.toString() + "]");
                        String src = el.getElementsByTag("video").get(0).attr("src");
                        String poster = el.getElementsByTag("video").get(0).attr("poster");
                        model.setPoster(poster);
                        model.setSrc(src);
                        return model;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    public static void getRecVideoList(String url, ApiCallBack<List<VideoChannelModel>> subscriber) {
        GetWebObservable
                .getInstance(url)
                .map(new Function<Document, List<VideoChannelModel>>() {
                    @Override
                    public List<VideoChannelModel> apply(@NonNull Document document) throws Exception {
                        Element element = document.body();
                        return getResult(element);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private static List<VideoChannelModel> getResult(Element element) {
        List<VideoChannelModel> result = new ArrayList<>();
        Elements elements = element.getElementsByAttributeValue(Fields.WebField.CLASS, "tab-cont tab-cont-show");
        if (elements.size() > 0) {
            Elements itemNode = elements.get(0).getElementsByTag("li");
            for (Element el : itemNode) {
                String href = el.getElementsByTag("a").get(0).attr("href");
                String cover = el.getElementsByTag("img").get(0).attr("data-echo");
                String title = el.getElementsByTag("p").get(0).text();
                String user = el.getElementsByAttributeValue(Fields.WebField.CLASS, "video-user").get(0).text();
                String num = el.getElementsByAttributeValue(Fields.WebField.CLASS, "video-num").get(0).text();
                VideoChannelModel mo = new VideoChannelModel(href, user, num, cover, "", title);
                result.add(mo);
            }
        }

        return result;
    }
   /* public static void requestVideo(String url, Subscriber<String> subscriber) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(HttpClient.getClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(new StringConverter())
                .build();

        rx.Observable observable = retrofit.create(VideoService.class).getVideoList();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);


    }*/
}
