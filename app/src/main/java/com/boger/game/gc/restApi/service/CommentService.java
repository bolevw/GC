package com.boger.game.gc.restApi.service;

import com.boger.game.gc.model.CommentModel;
import com.boger.game.gc.model.GetCommentModel;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by liubo on 16/8/2.
 */

public interface CommentService {


    /**
     * 创建评论
     *
     * @param databaseName
     * @param model
     * @return
     */
    @POST("1.1/classes/{class}")
    Observable<Void> comment(@Path("class") String databaseName, @Body CommentModel model);


    /**
     * 获取评论
     *
     * @param databaseName
     * @param url
     * @return
     */
    @GET("1.1/classes/{class}")
    Observable<GetCommentModel> getComment(@Path("class") String databaseName, @Query("where") String url);

    @PUT("1.1/classes/{class}/{objectId}")
    Observable<Void> admire(@Path("class") String databaseName, @Path("objectId") String id, @Body CommentModel model);
}
