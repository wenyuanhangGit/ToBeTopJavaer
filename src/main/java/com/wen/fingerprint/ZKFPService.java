package com.wen.fingerprint;

/**
 * the algorithm of fingerprint 10.0
 *
 * @author Bruce
 */
public class ZKFPService {


    static {
        System.loadLibrary("zkfp");
    }

    static public native int Initialize();

    static public native int Finalize();

    static public native int GetDeviceCount();

    static public native long OpenDevice(int index);

    static public native int CloseDevice(long handle);

    static public native int GetCapParams(long handle, int[] a, int[] b);

    static public native int SetParameter(long handle, int a, byte[] b, int c);

    static public native int GetParameter(long handle, int a, byte[] b, int[] c);

    static public native int AcquireTemplate(long hanle, byte[] a, byte[] b, int[] c);

    static public native int AcquireImage(long hanle, byte[] a);


    static public native int GenRegFPTemplate(byte[] q, byte[] b, byte[] c, byte[] d, int[] e);


    static public native long DBInit();

    static public native int DBFree(long hanle);

    static public native int DBAdd(int a, byte[] b);

    static public native int DBDel(int a);

    static public native int DBClear();

    static public native int DBCount();


    public static native int VerifyFPByID(int a, byte[] b);

    public static native int MatchFP(byte[] a, byte[] b);

    public static native int IdentifyFP(byte[] a, int[] b, int[] c);

    public static native String BlobToBase64(byte[] a, int b);

    public static native int Base64ToBlob(String a, byte[] b, int c);

    public static native int DBSetParameter(long hanle, int a, int b);

    public static native int DBGetParameter(long hanle, int a, int[] b);

    public static native int WriteBitmap(byte[] imageRaw, int Width, int Height, String file);


}

