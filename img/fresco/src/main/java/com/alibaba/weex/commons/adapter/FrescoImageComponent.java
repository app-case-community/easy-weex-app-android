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
import com.facebook.drawee.generic.RoundingParams;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.common.Constants;
import com.taobao.weex.ui.action.BasicComponentData;
import com.taobao.weex.ui.component.WXImage;
import com.taobao.weex.ui.component.WXVContainer;
import com.taobao.weex.utils.WXResourceUtils;
import com.taobao.weex.utils.WXViewUtils;

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
    public FrescoImageView getHostView() {
        return (FrescoImageView) super.getHostView();
    }

    @Override
    public void setResize(String resize) {
        getHostView().getHierarchy().setActualImageScaleType(getImageScaleType(resize));
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

    @Override
    public void setBorderColor(String key, String borderColor) {
        if (!TextUtils.isEmpty(borderColor)) {
            int colorInt = WXResourceUtils.getColor(borderColor);
            RoundingParams roundingParams = getHostView().getHierarchy().getRoundingParams();
            if (roundingParams == null) {
                roundingParams = new RoundingParams();
            }
            roundingParams.setBorderColor(colorInt);
            getHostView().getHierarchy().setRoundingParams(roundingParams);
        }
    }

    @Override
    public void setBorderWidth(String key, float borderWidth) {
        if (borderWidth > 0) {
            float val = WXViewUtils.getRealSubPxByWidth(borderWidth, getInstance().getInstanceViewPortWidth());
            RoundingParams roundingParams = getHostView().getHierarchy().getRoundingParams();
            if (roundingParams == null) {
                roundingParams = new RoundingParams();
            }
            roundingParams.setBorderWidth(val);
            getHostView().getHierarchy().setRoundingParams(roundingParams);
        }
    }

    @Override
    public void setBorderRadius(String key, float borderRadius) {
        if (borderRadius > 0) {
            float val = WXViewUtils.getRealSubPxByWidth(borderRadius, getInstance().getInstanceViewPortWidth());
            float[] radii = {val, val, val, val, val, val, val, val};

            RoundingParams roundingParams = getHostView().getHierarchy().getRoundingParams();
            if (roundingParams == null) {
                roundingParams = new RoundingParams();
            } else {
                float[] cornersRadii = roundingParams.getCornersRadii();
                if (cornersRadii != null) {
                    radii = cornersRadii;
                }
            }
            switch (key) {
                case Constants.Name.BORDER_TOP_LEFT_RADIUS:
                    radii[0] = radii[1] = val;
                    break;
                case Constants.Name.BORDER_TOP_RIGHT_RADIUS:
                    radii[2] = radii[3] = val;
                    break;
                case Constants.Name.BORDER_BOTTOM_RIGHT_RADIUS:
                    radii[4] = radii[5] = val;
                    break;
                case Constants.Name.BORDER_BOTTOM_LEFT_RADIUS:
                    radii[6] = radii[7] = val;
                    break;
            }
            roundingParams.setCornersRadii(radii);
            getHostView().getHierarchy().setRoundingParams(roundingParams);
        }
    }
}
