package com.tristan.hansangdemos.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.backup.FullBackupDataOutput;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by tristan on 2015/8/3 0003.
 */
public class PhotoUtils {
    /**
     * 因为处理不同
     *
     * @param takePhotoCode Uri originalUri = data.getData();
     * image=ImageUtil.getBitmapFromUrl(originalUri.toString());
     * *********************************************************************************
     * @param imageCode     Bundle extras = data.getExtras(); image = (Bitmap)
     * extras.get("data");
     * @param tempFile      拍照时的临时文件 需要zoom时
     * *
     */

    public final static int TAKEPHOTO = 520;
    public final static int PICKPHTOO = 521;
    public final static int ZOOMPHOTO = 110;

    public final static String NO_SDCARD = "请插入sd卡!";


    public static boolean getPhoto(final Activity activity,
                                   final int takePhotoCode, final int pickPhotoCode, final String fileName) {
        final CharSequence[] items = {"相册", "拍照"};
        AlertDialog dlg = new AlertDialog.Builder(activity).setTitle("选择图片")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 1) {
                            takePhoto(activity, takePhotoCode, fileName);
                        } else {
                            pickPhoto(activity, pickPhotoCode);
                        }
                    }
                }).create();
        dlg.show();
        return true;
    }

    /**
     * @param activity
     * @param takePhotoCode
     * @param fileName      注意需要后缀 。png或。jpg
     * @return
     */
    public static boolean takePhoto(Activity activity, int takePhotoCode, String fileName) {

        Intent getImageByCamera = new Intent(
                "android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
//        File baseFile = getPublicStoragePath(activity);
        File baseFile = getDiskCacheDir(activity,"imgCache");
        if (baseFile == null)
            return false;
        if (!baseFile.exists()){
            baseFile.mkdirs();
        }
        File file = new File(baseFile, fileName);
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(file));
        activity.startActivityForResult(getImageByCamera, takePhotoCode);
        return true;
    }

//    public static File getPublicStoragePath(Context context) {
//        String state = Environment.getExternalStorageState();
//        if (state.equals(Environment.MEDIA_MOUNTED)) {
//            File path = Environment
//                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
//            Log.i("-->>", "dcim path = " + path);
//            return path;
//        } else {
//            Toast.makeText(context, "请插入SD卡！", Toast.LENGTH_SHORT).show();
//            return null;
//        }
//    }

    public static boolean pickPhoto(Activity activity, int imageCode) {
//        Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
//        getImage.addCategory(Intent.CATEGORY_OPENABLE);
//        getImage.setType("image/jpeg");
        Intent getImage = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(getImage, imageCode);
        return true;
    }


    /**
     * 图片切割完调用 如果还需要 Bitmap 调用getLocalImage
     *
     * @param path
     * @param rw
     * @param rh
     * @param compress
     * @return
     */
    public static File onPhotoZoom(String path, int rw, int rh, int compress) {

        File f = new File(path);
        Bitmap btp = PhotoUtils.getLocalImage(f, rw, rh);
        compressImage(btp, f, compress);
        return f;
    }


    public static File onPhotoFromCamera(Activity activity, String fileName) {

        File baseFile = getDiskCacheDir(activity,"imgCache");
        if (baseFile == null)
            return null;
        if (!baseFile.exists()){
            baseFile.mkdirs();
        }
        return new File(baseFile, fileName);
    }

    /**
     * 选择图片后得到原始大图的文件路径。
     *
     * @param activity
     * @param data
     * @return
     */
    public static File onPhotoFromPick(Activity activity, Intent data) {

        Uri uri = data.getData();
        if (TextUtils.isEmpty(uri.getAuthority())) {
            return null;
        }
        Cursor cursor = activity.getContentResolver().query(
                uri,
                new String[]{MediaStore.Images.Media.DATA},
                null,
                null,
                null);
        //返回 没找到选择图片
        if (null == cursor) {
            return null;
        }
        //光标移动至开头 获取图片路径
        cursor.moveToFirst();
        String pathImage = cursor.getString(cursor
                .getColumnIndex(MediaStore.Images.Media.DATA));
        return new File(pathImage);

    }


//    /**
//     * 通过URI 获取真实路劲
//     *
//     * @param activity
//     * @param contentUri
//     * @return
//     */
//    public static String getRealPathFromURI(Activity activity, Uri contentUri) {
//        Cursor cursor = null;
//        String result = contentUri.toString();
//
//        String[] proj = {MediaStore.MediaColumns.DATA};
//        cursor = activity.managedQuery(contentUri, proj, null, null, null);
//        if (cursor == null)
//            throw new NullPointerException("reader file field");
//        if (cursor != null) {
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//            cursor.moveToFirst();
//            result = cursor.getString(column_index);
//            if (Integer.parseInt(Build.VERSION.SDK) < 14) {
//                cursor.close();
//            }
//        }
//        return result;
//    }

    /**
     * 图片压缩 上传图片时建议compress为30
     *
     * @param bm
     * @param f
     */
    public static void compressImage(Bitmap bm, File f, int compress) {
        if (bm == null)
            return;
        File file = f;
        try {
            if (file.exists()) {
                file.delete();
                Log.i("-->>", "delete file");
            }
            file.createNewFile();
            OutputStream outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, compress,
                    outStream);
            outStream.flush();
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 由本地文件获取希望大小的文件
     *
     * @param f
     * @return
     */
    public static Bitmap getLocalImage(File f, int swidth, int sheight) {
        File file = f;
        if (file.exists()) {
            try {
                file.setLastModified(System.currentTimeMillis());
                FileInputStream in = new FileInputStream(file);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(in, null, options);
                int sWidth = swidth;
                int sHeight = sheight;
                int mWidth = options.outWidth;
                int mHeight = options.outHeight;
                int s = 1;
                while ((mWidth / s > sWidth * 2) || (mHeight / s > sHeight * 2)) {
                    s *= 2;
                }
                options = new BitmapFactory.Options();
                options.inSampleSize = s;
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.inPurgeable = true;
                options.inInputShareable = true;
                try {
                    // 4. inNativeAlloc 属性设置为true，可以不把使用的内存算到VM里
                    BitmapFactory.Options.class.getField("inNativeAlloc")
                            .setBoolean(options, true);
                } catch (Exception e) {
                }
                in.close();
                // 再次获取
                in = new FileInputStream(file);
                Bitmap bitmap = BitmapFactory.decodeStream(in, null, options);
                in.close();
                return bitmap;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * aspectY Y对于X的比例 outputX X 的宽
     * *
     */
    public static File photoZoom(Activity activity, Uri inputUri,
                                 int photoResoultCode, int aspectX, int aspectY) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(inputUri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        if (aspectY > 0) {
            intent.putExtra("aspectX", aspectX);
            intent.putExtra("aspectY", aspectY);
        }
        intent.putExtra("scale", aspectX == aspectY);
        File baseFile = getDiskCacheDir(activity, "imgCache");
        if (!baseFile.exists()){
            baseFile.mkdirs();
        }
        File temp = new File(baseFile, "crop.jpg");
        if (temp.exists()) {
            temp.delete();
        }
        try {
            temp.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri outUri = Uri.fromFile(temp);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); //
        activity.startActivityForResult(intent, photoResoultCode);
        return temp;
    }
}
