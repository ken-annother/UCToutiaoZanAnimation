package com.nicekun.uctoutiaozananimation;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UCZanBitmapFactory {
    private static String[] allowPictureSuffixs = new String[]{".png", ".jpg"};

    public static Bitmap from(Context context, int resId) {
        return BitmapFactory.decodeResource(context.getResources(), resId);
    }

    public static Map<String, Bitmap> fromAssets(Context context, String assetsDir) {
        HashMap<String, Bitmap> result = new HashMap<>();
        AssetManager assets;
        try {
            assets = context.getAssets();
            String[] list = assets.list(assetsDir);
            if (list != null && list.length > 0) {
                Set<String> pics = new HashSet<>();
                for (String file : list) {
                    String[] fileSplit = spliteFileNameSuffix(file);
                    if (fileSplit != null) {
                        if (checkFileNameSuffixCorrect(fileSplit[1])) {
                            pics.add(fileSplit[0]);
                        }
                    }
                }

                for (String picName : pics) {

                    for (String suffix : allowPictureSuffixs) {
                        try {
                            InputStream stream = assets.open(assetsDir + "/" +  picName + suffix);
                            Bitmap bitmap = BitmapFactory.decodeStream(stream);
                            result.put(picName, bitmap);
                            break;
                        } catch (IOException ignored) {

                        }
                    }

                }
            }
        } catch (IOException e) {
            Log.e(UCZanUtils.TAG, "UCZanBitmapFactory - fromAssets ERROR : " + e.getMessage());
        }

        return result;
    }


    private static boolean checkFileNameSuffixCorrect(String fsuffix) {
        if (fsuffix == null) {
            return false;
        }

        for (String suffix : allowPictureSuffixs) {
            if (fsuffix.equals(suffix)) {
                return true;
            }
        }

        return false;
    }


    private static String[] spliteFileNameSuffix(String fileName) {
        if (fileName != null && fileName.length() > 2) {
            int pos = fileName.lastIndexOf(".");
            if (pos != -1) {
                String picName = fileName.substring(0, pos);
                String picSuffix = fileName.substring(pos, fileName.length());
                return new String[]{picName, picSuffix};
            }
        }
        return null;
    }

    public static Map<String, Bitmap> fromAssetsZipFile(Context context, String zipFileName) {
        return null;
    }
}
