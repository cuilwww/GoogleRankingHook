package com.baidu.mobads.production.f;

import android.app.Activity;
import android.content.Context;
import com.baidu.mobads.interfaces.IXAdConstants4PDK.SlotType;
import java.util.HashMap;

public class d extends com.baidu.mobads.vo.d {
    private Boolean a;

    public d(Context context, Activity activity, SlotType slotType, Boolean bool) {
        super(context, activity, slotType);
        this.a = bool;
        if (c().booleanValue()) {
            this.b = "http://mobads.baidu.com/ads/index.htm";
        }
    }

    protected HashMap a() {
        HashMap hashMap = new HashMap();
        hashMap.put("xyz", "hihihi");
        return hashMap;
    }

    public String b() {
        return c().booleanValue() ? super.b() : "http://211.151.146.65:8080/wlantest/shanghai_sun/mock_ad_server_intersitial_video.json";
    }

    public Boolean c() {
        return this.a;
    }
}
