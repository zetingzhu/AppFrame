package com.zzt.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.annotation.RawRes;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

/**
 * getDrawable        : 获取 Drawable
 * getIdByName        : 根据名字获取 ID
 * getStringIdByName  : 根据名字获取 string ID
 * getColorIdByName   : 根据名字获取 color ID
 * getDimenIdByName   : 根据名字获取 dimen ID
 * getDrawableIdByName: 根据名字获取 dimen ID
 * getMipmapIdByName  : 根据名字获取 mipmap ID
 * getLayoutIdByName  : 根据名字获取 layout ID
 * getStyleIdByName   : 根据名字获取 style ID
 * getAnimIdByName    : 根据名字获取 anim ID
 * getMenuIdByName    : 根据名字获取 menu ID
 * copyFileFromAssets : 从 assets 中拷贝文件
 * readAssets2String  : 从 assets 中读取字符串
 * readAssets2List    : 从 assets 中按行读取字符串
 * copyFileFromRaw    : 从 raw 中拷贝文件
 * readRaw2String     : 从 raw 中读取字符串
 * readRaw2List       : 从 raw 中按行读取字符串
 */
public final class ResourceUtils {

    private static final int BUFFER_SIZE = 8192;

    private ResourceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Return the drawable by identifier.
     *
     * @param id The identifier.
     * @return the drawable by identifier
     */
    public static Drawable getDrawable(Context mContext, @DrawableRes int id) {
        return ContextCompat.getDrawable(mContext, id);
    }

    /**
     * Return the id identifier by name.
     *
     * @param name The name of id.
     * @return the id identifier by name
     */
    public static int getIdByName(Context mContext, String name) {
        return mContext.getResources().getIdentifier(name, "id", mContext.getPackageName());
    }

    /**
     * Return the string identifier by name.
     *
     * @param name The name of string.
     * @return the string identifier by name
     */
    public static int getStringIdByName(Context mContext, String name) {
        return mContext.getResources().getIdentifier(name, "string", mContext.getPackageName());
    }

    /**
     * Return the color identifier by name.
     *
     * @param name The name of color.
     * @return the color identifier by name
     */
    public static int getColorIdByName(Context mContext, String name) {
        return mContext.getResources().getIdentifier(name, "color", mContext.getPackageName());
    }

    /**
     * Return the dimen identifier by name.
     *
     * @param name The name of dimen.
     * @return the dimen identifier by name
     */
    public static int getDimenIdByName(Context mContext, String name) {
        return mContext.getResources().getIdentifier(name, "dimen", mContext.getPackageName());
    }

    /**
     * Return the drawable identifier by name.
     *
     * @param name The name of drawable.
     * @return the drawable identifier by name
     */
    public static int getDrawableIdByName(Context mContext, String name) {
        return mContext.getResources().getIdentifier(name, "drawable", mContext.getPackageName());
    }

    /**
     * Return the mipmap identifier by name.
     *
     * @param name The name of mipmap.
     * @return the mipmap identifier by name
     */
    public static int getMipmapIdByName(Context mContext, String name) {
        return mContext.getResources().getIdentifier(name, "mipmap", mContext.getPackageName());
    }

    /**
     * Return the layout identifier by name.
     *
     * @param name The name of layout.
     * @return the layout identifier by name
     */
    public static int getLayoutIdByName(Context mContext, String name) {
        return mContext.getResources().getIdentifier(name, "layout", mContext.getPackageName());
    }

    /**
     * Return the style identifier by name.
     *
     * @param name The name of style.
     * @return the style identifier by name
     */
    public static int getStyleIdByName(Context mContext, String name) {
        return mContext.getResources().getIdentifier(name, "style", mContext.getPackageName());
    }

    /**
     * Return the anim identifier by name.
     *
     * @param name The name of anim.
     * @return the anim identifier by name
     */
    public static int getAnimIdByName(Context mContext, String name) {
        return mContext.getResources().getIdentifier(name, "anim", mContext.getPackageName());
    }

    /**
     * Return the menu identifier by name.
     *
     * @param name The name of menu.
     * @return the menu identifier by name
     */
    public static int getMenuIdByName(Context mContext, String name) {
        return mContext.getResources().getIdentifier(name, "menu", mContext.getPackageName());
    }

    /**
     * Copy the file from assets.
     *
     * @param assetsFilePath The path of file in assets.
     * @param destFilePath   The path of destination file.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean copyFileFromAssets(Context mContext, final String assetsFilePath, final String destFilePath) {
        boolean res = true;
        try {
            String[] assets = mContext.getAssets().list(assetsFilePath);
            if (assets != null && assets.length > 0) {
                for (String asset : assets) {
                    res &= copyFileFromAssets(mContext, assetsFilePath + "/" + asset, destFilePath + "/" + asset);
                }
            } else {
                res = FileIOUtils.writeFileFromIS(
                        destFilePath,
                        mContext.getAssets().open(assetsFilePath)
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }

    /**
     * Return the content of assets.
     *
     * @param assetsFilePath The path of file in assets.
     * @return the content of assets
     */
    public static String readAssets2String(Context mContext, final String assetsFilePath) {
        return readAssets2String(mContext, assetsFilePath, null);
    }

    /**
     * Return the content of assets.
     *
     * @param assetsFilePath The path of file in assets.
     * @param charsetName    The name of charset.
     * @return the content of assets
     */
    public static String readAssets2String(Context mContext, final String assetsFilePath, final String charsetName) {
        try {
            InputStream is = mContext.getAssets().open(assetsFilePath);
            byte[] bytes = ConvertUtils.inputStream2Bytes(is);
            if (bytes == null) return "";
            if (StringUtils.isSpace(charsetName)) {
                return new String(bytes);
            } else {
                try {
                    return new String(bytes, charsetName);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return "";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Return the content of file in assets.
     *
     * @param assetsPath The path of file in assets.
     * @return the content of file in assets
     */
    public static List<String> readAssets2List(Context mContext, final String assetsPath) {
        return readAssets2List(mContext, assetsPath, "");
    }

    /**
     * Return the content of file in assets.
     *
     * @param assetsPath  The path of file in assets.
     * @param charsetName The name of charset.
     * @return the content of file in assets
     */
    public static List<String> readAssets2List(Context mContext, final String assetsPath,
                                               final String charsetName) {
        try {
            return ConvertUtils.inputStream2Lines(mContext.getResources().getAssets().open(assetsPath), charsetName);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    /**
     * Copy the file from raw.
     *
     * @param resId        The resource id.
     * @param destFilePath The path of destination file.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean copyFileFromRaw(Context mContext, @RawRes final int resId, final String destFilePath) {
        return FileIOUtils.writeFileFromIS(
                destFilePath,
                mContext.getResources().openRawResource(resId)
        );
    }

    /**
     * Return the content of resource in raw.
     *
     * @param resId The resource id.
     * @return the content of resource in raw
     */
    public static String readRaw2String(Context mContext, @RawRes final int resId) {
        return readRaw2String(mContext, resId, null);
    }

    /**
     * Return the content of resource in raw.
     *
     * @param resId       The resource id.
     * @param charsetName The name of charset.
     * @return the content of resource in raw
     */
    public static String readRaw2String(Context mContext, @RawRes final int resId, final String charsetName) {
        InputStream is = mContext.getResources().openRawResource(resId);
        byte[] bytes = ConvertUtils.inputStream2Bytes(is);
        if (bytes == null) return null;
        if (StringUtils.isSpace(charsetName)) {
            return new String(bytes);
        } else {
            try {
                return new String(bytes, charsetName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "";
            }
        }
    }

    /**
     * Return the content of resource in raw.
     *
     * @param resId The resource id.
     * @return the content of file in assets
     */
    public static List<String> readRaw2List(Context mContext, @RawRes final int resId) {
        return readRaw2List(mContext, resId, "");
    }

    /**
     * Return the content of resource in raw.
     *
     * @param resId       The resource id.
     * @param charsetName The name of charset.
     * @return the content of file in assets
     */
    public static List<String> readRaw2List(Context mContext, @RawRes final int resId,
                                            final String charsetName) {
        return ConvertUtils.inputStream2Lines(mContext.getResources().openRawResource(resId), charsetName);
    }
}
