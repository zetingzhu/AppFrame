package frame.zzt.com.appframe.notification;

import android.content.Context;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;

import frame.zzt.com.appframe.MyApplication;

/**
 * This class is used for save ignored and excluded application list.
 * Their notification will not be pushed to remote device.
 * IgnoreList is a single class.
 */
public final class IgnoreList {
    // Debugging
    private static final String TAG = "AppManager/IgnoreList";

    // EXCLUSION_LIST, will be processed specially.
    // Filter out useless/unimportant notification to save BT bandwidth
    private static final String[] EXCLUSION_LIST = {
            // MTK
            "com.mediatek.mtklogger",
            "com.mediatek.bluetooth",
            "com.mediatek.security",

            // Google
            "android",
            "com.android.providers.downloads",
            "com.android.bluetooth",
            "com.android.music",
            "com.google.android.music",
            "com.google.android.gms",

            // 3rd party apps
            "com.htc.music",
            "com.lge.music",
            "com.sec.android.app.music",
            "com.sonyericsson.music",
            "com.ijinshan.mguard"
    };

    // The file to save IgnoreList
    private static final String SAVE_FILE_NAME = "IgnoreList";

    private static final IgnoreList INSTANCE = new IgnoreList();

    private HashSet<String> mIgnoreList = null;

    private Context mContext = null;

    private IgnoreList() {
        Log.i(TAG, "IgnoreList(), IgnoreList created!");

        mContext = MyApplication.getInstance().getApplicationContext();
    }

    /**
     * Return the instance of IgnoreList class.
     *
     * @return the IgnoreList instance
     */
    public static IgnoreList getInstance() {
        return INSTANCE;
    }

    /**
     * Return the ignored application list.
     *
     * @return the ignore list
     */
    public HashSet<String> getIgnoreList() {
        if (mIgnoreList == null) {
            loadIgnoreListFromFile();
        }

        Log.i(TAG, "getIgnoreList(), mIgnoreList = " + mIgnoreList.toString());
        return mIgnoreList;
    }

    @SuppressWarnings("unchecked")
    private void loadIgnoreListFromFile() {
        Log.i(TAG, "loadIgnoreListFromFile(),  file_name= " + SAVE_FILE_NAME);

        if (mIgnoreList == null) {
            try {
                Object obj = (new ObjectInputStream(mContext.openFileInput(SAVE_FILE_NAME)))
                        .readObject();
                mIgnoreList = (HashSet<String>) obj;
            } catch (ClassNotFoundException exception) {
                exception.printStackTrace();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        if (mIgnoreList == null) {
            mIgnoreList = new HashSet<String>();
        }
    }

    /**
     * Add a package name from ignore list.
     *
     * @param name The package name to be added from ignore list.
     */
    public void addIgnoreItem(String name) {
        if (mIgnoreList == null) {
            loadIgnoreListFromFile();
        }
        if (!mIgnoreList.contains(name)) {
            mIgnoreList.add(name);
        }
    }

    /**
     * Remove a package name from ignore list.
     *
     * @param name The package name to be removed from ignore list.
     */
    public void removeIgnoreItem(String name) {
        if (mIgnoreList == null) {
            loadIgnoreListFromFile();
        }
        if (mIgnoreList.contains(name)) {
            mIgnoreList.remove(name);
        }

    }

    /**
     * Save ignore list to file system.
     */
    public void saveIgnoreList() {
        FileOutputStream fileoutputstream;
        ObjectOutputStream objectoutputstream;

        try {
            fileoutputstream = mContext.openFileOutput(SAVE_FILE_NAME, Context.MODE_PRIVATE);
            objectoutputstream = new ObjectOutputStream(fileoutputstream);
            objectoutputstream.writeObject(mIgnoreList);
            objectoutputstream.close();
            fileoutputstream.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return;
        }
    }

    /**
     * Save ignored applications to file.
     *
     * @param ignoreList ignored applications list
     */
    public void saveIgnoreList(HashSet<String> ignoreList) {
        Log.i(TAG, "setIgnoreList(),  file_name= " + SAVE_FILE_NAME);

        FileOutputStream fileoutputstream;
        ObjectOutputStream objectoutputstream;

        try {
            fileoutputstream = mContext.openFileOutput(SAVE_FILE_NAME, Context.MODE_PRIVATE);
            objectoutputstream = new ObjectOutputStream(fileoutputstream);
            objectoutputstream.writeObject(ignoreList);
            objectoutputstream.close();
            fileoutputstream.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return;
        }

        mIgnoreList = ignoreList;
        Log.i(TAG, "setIgnoreList(),  mIgnoreList= " + mIgnoreList);
    }

    /**
     * Return the exclude application list, these applications will not show to
     * user for selection.
     *
     * @return the exclude application list
     */
    public HashSet<String> getExclusionList() {
        HashSet<String> exclusionList = new HashSet<String>();
        for (String exclusionPackage : EXCLUSION_LIST) {
            exclusionList.add(exclusionPackage);
        }

        Log.i(TAG, "setIgnoreList(),  exclusionList=" + exclusionList);
        return exclusionList;
    }
}
