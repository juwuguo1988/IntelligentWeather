/*
 *
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.kunminx.architecture.data.response;

/**
 * TODO: 专用于数据层返回结果给 domain 层或 ViewModel 用，原因如下：
 * <p>
 * liveData 是专用于页面开发的、用于解决生命周期安全问题的组件，
 * 有时候数据并非一定是通过 liveData 来分发给页面，也可能是通过别的组件去通知给非页面的东西，
 * 这时候 repo 方法中内定通过 liveData 分发就不太合适，不如一开始就规定不在数据层通过 liveData 返回结果。
 * <p>
 * 如果这样说还不理解的话，详见《如何让同事爱上架构模式、少写 bug 多注释》的解析
 * https://xiaozhuanlan.com/topic/8204519736
 * <p>
 * Create by KunMinX at 2020/7/20
 */
public class DataResult<T> {
    private String code;
    private T data;
    private String msg;
    private String traceId;
    private Object extraData;
    private boolean success;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public Object getExtraData() {
        return extraData;
    }

    public void setExtraData(Object extraData) {
        this.extraData = extraData;
    }

    public DataResult(T entity, boolean responseStatus) {
        data = entity;
        success = responseStatus;
    }

    public T getResult() {
        return data;
    }

    public boolean getResponseStatus() {
        return success;
    }

    public interface Result<T> {
        void onResult(DataResult<T> dataResult);
    }
}
