
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
 * This class is used for save ignored and excluded application list. Their
 * notification will not be pushed to remote device. IgnoreList is a single
 * class.
 */
public final class BlockList {
    // Debugging
    private static final String TAG = "AppManager/BlockList";

    private static final String SAVE_FILE_NAME = "BlockList";

    private static final BlockList INSTANCE = new BlockList();

    private HashSet<CharSequence> mBlockList = null;

    private Context mContext = null;

    private BlockList() {
        Log.i(TAG, "BlockList(), BlockList created!");

        mContext = MyApplication.getInstance().getApplicationContext();
    }

    /**
     * Return the instance of IgnoreList class.
     *
     * @return the IgnoreList instance
     */
    public static BlockList getInstance() {
        return INSTANCE;
    }

    /**
     * Return the ignored application list.
     *
     * @return the ignore list
     */
    public HashSet<CharSequence> getBlockList() {
        if (mBlockList == null) {
            loadBlockListFromFile();
        }

        Log.i(TAG, "getBlockList(), mBlockList = " + mBlockList.toString());
        return mBlockList;
    }

    @SuppressWarnings("unchecked")
    private void loadBlockListFromFile() {
        Log.i(TAG, "loadIgnoreListFromFile(),  file_name= " + SAVE_FILE_NAME);

        if (mBlockList == null) {
            try {
                Object obj = (new ObjectInputStream(mContext.openFileInput(SAVE_FILE_NAME)))
                        .readObject();
                mBlockList = (HashSet<CharSequence>) obj;
            } catch (ClassNotFoundException exception) {
                exception.printStackTrace();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        if (mBlockList == null) {
            mBlockList = new HashSet<CharSequence>();
        }
    }

    /**
     * Remove a package name from blocking list.
     * @param name The package name to be removed from blocking list.
     */
    public void removeBlockItem(CharSequence name) {
        if (mBlockList == null) {
            loadBlockListFromFile();
        }
        if (mBlockList.contains(name)) {
            mBlockList.remove(name);
        }

    }

    /**
     * Add a package name from blocking list.
     * @param name The package name to be added to blocking list.
     */
    public void addBlockItem(CharSequence name) {
        if (mBlockList == null) {
            loadBlockListFromFile();
        }
        if (!mBlockList.contains(name)) {
            mBlockList.add(name);
        }
    }

    /**
     * Save blocking list to file system.
     */
    public void saveBlockList() {
        FileOutputStream fileoutputstream;
        ObjectOutputStream objectoutputstream;

        try {
            fileoutputstream = mContext.openFileOutput(SAVE_FILE_NAME, Context.MODE_PRIVATE);
            objectoutputstream = new ObjectOutputStream(fileoutputstream);
            objectoutputstream.writeObject(mBlockList);
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
     * @param blockList ignored applications list
     */
    public void saveBlockList(HashSet<CharSequence> blockList) {
        Log.i(TAG, "setIgnoreList(),  file_name= " + SAVE_FILE_NAME);

        FileOutputStream fileoutputstream;
        ObjectOutputStream objectoutputstream;

        try {
            fileoutputstream = mContext.openFileOutput(SAVE_FILE_NAME, Context.MODE_PRIVATE);
            objectoutputstream = new ObjectOutputStream(fileoutputstream);
            objectoutputstream.writeObject(blockList);
            objectoutputstream.close();
            fileoutputstream.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return;
        }

        mBlockList = blockList;
        Log.i(TAG, "setIgnoreList(),  mIgnoreList= " + mBlockList);
    }
}
