/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.alibaba.weex.commons.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.ImageView;
import com.facebook.drawee.drawable.ScalingUtils;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXImage;
import com.taobao.weex.ui.component.WXVContainer;

/**
 * Created by sospartan on 8/19/16.
 */
public class FrescoImageComponent extends WXImage {

    public FrescoImageComponent(WXSDKInstance instance, WXVContainer parent, BasicComponentData basicComponentData) {
        super(instance, parent, basicComponentData);
    }

    @Override
    protected ImageView initComponentHostView(@NonNull Context context) {
        FrescoImageView view = new FrescoImageView(context);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        return view;
    }

    @Override
    public void setResize(String resize) {
        FrescoImageView view = (FrescoImageView) getHostView();
        view.getHierarchy().setActualImageScaleType(getImageScaleType(resize));
    }

    private ScalingUtils.ScaleType getImageScaleType(String resizeMode) {
        ScalingUtils.ScaleType scaleType = ScalingUtils.ScaleType.FIT_XY;
        if (TextUtils.isEmpty(resizeMode)) {
            return scaleType;
        }

        switch (resizeMode) {
            case "cover":
                scaleType = ScalingUtils.ScaleType.CENTER_CROP;
                break;
            case "contain":
                scaleType = ScalingUtils.ScaleType.FIT_CENTER;
                break;
            case "stretch":
                scaleType = ScalingUtils.ScaleType.FIT_XY;
                break;
            default:
                break;
        }
        return scaleType;
    }
}
