package com.tencent.connect.c;

import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import com.tencent.open.a.j;
import com.tencent.open.d.u;

final class f implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ Handler b;

    f(String str, Handler handler) {
        this.a = str;
        this.b = handler;
    }

    public void run() {
        Bitmap a = d.a(this.a, 140);
        if (a != null) {
            String a2;
            String str = Environment.getExternalStorageDirectory() + "/tmp/";
            String str2 = "share2qq_temp" + u.d(this.a) + ".jpg";
            if (d.b(this.a, 140, 140)) {
                j.b("openSDK_LOG.AsynScaleCompressImage", "out of bound,compress!");
                a2 = d.a(a, str, str2);
            } else {
                j.b("openSDK_LOG.AsynScaleCompressImage", "not out of bound,not compress!");
                a2 = this.a;
            }
            j.b("openSDK_LOG.AsynScaleCompressImage", "-->destFilePath: " + a2);
            if (a2 != null) {
                Message obtainMessage = this.b.obtainMessage(101);
                obtainMessage.obj = a2;
                this.b.sendMessage(obtainMessage);
                return;
            }
        }
        Message obtainMessage2 = this.b.obtainMessage(102);
        obtainMessage2.arg1 = 3;
        this.b.sendMessage(obtainMessage2);
    }
}
