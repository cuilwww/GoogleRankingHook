package com.ndk.test;

import com.google.ccc.abuse.droidguard.droidguasso.Droidguasso;
import com.google.protobuf.ByteString;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by apple on 16/12/8.
 */
public class DroidguassoHelper {

    private static final String TAG = "GmsDroidguassoHelper";

    private char[] HEX_CHARS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static byte[] guasso(byte[] digest) {
        List<String> substrs = new ArrayList<>();
        addFilesInPath("/vendor/lib/egl", substrs);
        addFilesInPath("/system/lib/egl", substrs);
        Collections.sort(substrs);
        substrs.add(initialBytesDigest(new File("/system/lib/egl/egl.cfg")));
        String eglInfo = hexDigest(substrs.toString().getBytes());


        float[] floats = new float[]{0.35502917f, 0.47196686f, 0.24689609f, 0.66850024f, 0.7746259f, 0.5967446f, 0.06270856f, 0.19201201f, 0.35090452f, 0.5573558f, 0.470259f, 0.9866341f};
        if (digest != null && digest.length >= 48) {
            ByteBuffer order = ByteBuffer.wrap(digest).order(ByteOrder.LITTLE_ENDIAN);
            for (int i = 0; i < 12; i++) {
                floats[i] = ((float) Math.abs(order.getInt())) / 2.14748365E9f;
            }
        }

        Droidguasso dg = new Droidguasso();
        dg.render(floats);

        return ("5=" + eglInfo + "\n7=" + dg.getGpu() + "\n8=" + dg.getHash1() + "\n9=" + dg.getHash2() + "\n").getBytes();
    }

    private static String initialBytesDigest(File file) {
        try {
            FileInputStream is = new FileInputStream(file);
            byte[] bytes = new byte[(int) Math.min(1024, file.length())];
            is.read(bytes);
            is.close();
            return hexDigest(bytes);
        } catch (Exception e) {
            return "";
        }
    }

    public static MessageDigest a(String str) {
        int i = 0;
        while (i < 2) {
            try {
                MessageDigest instance = MessageDigest.getInstance(str);
                if (instance != null) {
                    return instance;
                }
                i++;
            } catch (NoSuchAlgorithmException e) {
            }
        }
        return null;
    }


    private static String hexDigest(byte[] bytes) {
        MessageDigest a = a("SHA-1");
        if (a != null) {
            return a(a.digest(bytes), false).toLowerCase();
        }
        return "";
    }

    private static final char[] d = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String a(byte[] bArr, boolean z) {
        int length = bArr.length;
        StringBuilder stringBuilder = new StringBuilder(length * 2);
        int i = 0;
        while (i < length && (!z || i != length - 1 || (bArr[i] & 255) != 0)) {
            stringBuilder.append(d[(bArr[i] & 240) >>> 4]);
            stringBuilder.append(d[bArr[i] & 15]);
            i++;
        }
        return stringBuilder.toString();
    }



    private static void addFilesInPath(String path, List list) {
        final File parent = new File(path);
        if (parent.isDirectory()) {
            final File[] listFiles = parent.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    return filename.endsWith(".so");
                }
            });
            for (File file : listFiles) {
                list.add(file.getName() + "/" + file.length() + "/" + initialBytesDigest(file));
            }
        }
    }
}
