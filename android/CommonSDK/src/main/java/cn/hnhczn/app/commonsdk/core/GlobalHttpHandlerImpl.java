/*
 * Copyright 2018 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.hnhczn.app.commonsdk.core;

import android.content.Context;
import android.text.TextUtils;

import com.jess.arms.http.GlobalHttpHandler;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.LogUtils;

import cn.hnhczn.app.commonsdk.utils.Kits;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ================================================
 * 展示 {@link GlobalHttpHandler} 的用法
 * <p>
 * Created by JessYan on 04/09/2017 17:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class GlobalHttpHandlerImpl implements GlobalHttpHandler {
    private Context context;

    public GlobalHttpHandlerImpl(Context context) {
        this.context = context;
    }

    /**
     * 这里可以先客户端一步拿到每一次 Http 请求的结果, 可以先解析成 Json, 再做一些操作, 如检测到 token 过期后
     * 重新请求 token, 并重新执行请求
     *
     * @param httpResult 服务器返回的结果 (已被框架自动转换为字符串)
     * @param chain      {@link okhttp3.Interceptor.Chain}
     * @param response   {@link Response}
     * @return
     */
    @Override
    public Response onHttpResultResponse(String httpResult, Interceptor.Chain chain, Response response) {
        /* 这里如果发现 token 过期, 可以先请求最新的 token, 然后在拿新的 token 放入 Request 里去重新请求
        注意在这个回调之前已经调用过 proceed(), 所以这里必须自己去建立网络请求, 如使用 Okhttp 使用新的 Request 去请求
        create a new request and modify it accordingly using the new token
        Request newRequest = chain.request().newBuilder().header("token", newToken)
                             .build();

        retry the request

        response.body().close();
        如果使用 Okhttp 将新的请求, 请求成功后, 再将 Okhttp 返回的 Response return 出去即可
        如果不需要返回新的结果, 则直接把参数 response 返回出去即可 */
        try {
            String session = response.headers().get("Set-cookie");
            if (!TextUtils.isEmpty(session)) {
                String JSESSIONID = session.substring(0, session.indexOf(";"));
                String oldSession = DataHelper.getStringSF(context, chain.request().url().host());
                LogUtils.debugInfo("Response host---" + chain.request().url().host());
                LogUtils.debugInfo("Response newSession---" + JSESSIONID);
                LogUtils.debugInfo("Response oldSession---" + oldSession);
                if ((JSESSIONID.indexOf("JSESSIONID") != -1) && (TextUtils.isEmpty(oldSession) || !JSESSIONID.equals(oldSession))) {
                    //保存sessionID --->登陆
                    DataHelper.setStringSF(context, chain.request().url().host(), JSESSIONID);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return response;
        }
        return response;
    }

    /**
     * 这里可以在请求服务器之前拿到 {@link Request}, 做一些操作比如给 {@link Request} 统一添加 token 或者 header 以及参数加密等操作
     *
     * @param chain   {@link okhttp3.Interceptor.Chain}
     * @param request {@link Request}
     * @return
     */
    @Override
    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
        /* 如果需要再请求服务器之前做一些操作, 则重新返回一个做过操作的的 Request 如增加 Header, 不做操作则直接返回参数 request
        return chain.request().newBuilder().header("token", tokenId)
                              .build(); */
        HttpUrl url = null;
        String domainName = request.headers().get("Domain-Name");
        if (!Kits.Empty.check(domainName)) {
            url = RetrofitUrlManager.getInstance().fetchDomain(domainName);
        }
        if (url == null) {
            url = chain.request().url();
        }
        String JSESSIONID = DataHelper.getStringSF(context, url.host());
        LogUtils.debugInfo("Before host---" + url.host());
        LogUtils.debugInfo("Before oldSession---" + JSESSIONID);
        if (!TextUtils.isEmpty(JSESSIONID)) {
            return chain.request().newBuilder().header("cookie", JSESSIONID).build();
        }
        return request;
    }
}
