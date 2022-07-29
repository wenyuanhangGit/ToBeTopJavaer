package com.wen.fingerprint;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

class Test {
    final static int ZKFP_ERR_OK = 0;
    final static int MAX_IMAGE_SIZE = 500 * 500;
    final static int MAX_TEMPLATE_SIZE = 2048;
    final static int ENROLLCNT = 3;
    final static int TEST_TIME = 10000;//ms

    public static long m_hDevice;
    public static long m_hDBCache;
    public static int m_nLastRegTempLen;
    public static int m_tid;
    public static int m_enrollIdx;
    public static int m_imgFPWidth = 0;
    public static int m_imgFPHeight = 0;
    public static int m_bIdentify = 0;
    public static int m_bRegister = 0;

    public static byte[] m_szLastRegTemplate = new byte[MAX_TEMPLATE_SIZE];
    public static byte[][] m_arrPreRegTemps = new byte[3][MAX_TEMPLATE_SIZE];
    public static int[] m_arrPreTempsLen = new int[3];

    public static int byteArrayToInt(byte[] bytes) {
        int number = bytes[0] & 0xFF;
        number |= ((bytes[1] << 8) & 0xFF00);
        number |= ((bytes[2] << 16) & 0xFF0000);
        number |= ((bytes[3] << 24) & 0xFF000000);
        return number;
    }

    public static byte[] readFile(File file) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            int length = inputStream.available();
            System.out.println(file + " length = " + length);
            byte[] buffer = new byte[length];
            inputStream.read(buffer);
            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void writeFile(byte[] bfile, String filePath) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            file = new File(filePath);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {

                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void DoVerify(long handle, byte[] temp, int len) {
        if (m_nLastRegTempLen > 0)    //enrolled template
        {
            if (m_bIdentify == 1) {
                int ret = ZKFP_ERR_OK;
                int[] tid = new int[1];
                tid[0] = 0;
                int[] score = new int[1];
                score[0] = 0;
                ret = ZKFPService.IdentifyFP(temp, tid, score);
                if (ZKFP_ERR_OK != ret) {
                    System.out.println("Identify failed, ret=" + ret);
                } else {
                    System.out.println("Identify succ, tid=" + tid[0] + ",score=" + score[0]);
                }
            } else {
                int ret = ZKFPService.MatchFP(m_szLastRegTemplate, temp);

                System.out.println("-------------------------");
                System.out.println("ret : " + ret);
                System.out.println("template byte array :" + Arrays.toString(m_szLastRegTemplate));
                System.out.println("template : " + ZKFPService.BlobToBase64(m_szLastRegTemplate, m_szLastRegTemplate.length));
                System.out.println("finger byte array :" + Arrays.toString(temp));
                System.out.println("finger : " + ZKFPService.BlobToBase64(temp, temp.length));

                if (ZKFP_ERR_OK >= ret) {
                    System.out.println("Verify failed,ret=" + ret);
                } else {
                    System.out.println("Verify succ, score=" + ret);
                }
            }
        } else {
            System.out.println("Please register first\n");
        }
    }

    public static int DoRegister(long handle, byte[] temp, int len) {
        int ret = 0;
        if (m_enrollIdx >= ENROLLCNT) {
            m_enrollIdx = 0;    //restart enroll
            return 1;
        }
        if (m_enrollIdx > 0) {
            ret = ZKFPService.MatchFP(m_arrPreRegTemps[m_enrollIdx - 1], temp);
            System.out.println("ZKFPM_DBMatch ret=" + ret);
            if (ZKFP_ERR_OK >= ret) {
                m_enrollIdx = 0;
                System.out.println("Resigter failed, please place the same finger\n");
                return 1;
            }
        }
        m_arrPreTempsLen[m_enrollIdx] = len;
        m_arrPreRegTemps[m_enrollIdx] = new byte[MAX_TEMPLATE_SIZE];
        System.arraycopy(temp, 0, m_arrPreRegTemps[m_enrollIdx], 0, len);
        if (++m_enrollIdx >= ENROLLCNT) {
            byte[] szRegTemp = new byte[MAX_TEMPLATE_SIZE];
            int[] cbRegTemp = new int[1];
            cbRegTemp[0] = MAX_TEMPLATE_SIZE;
            ret = ZKFPService.GenRegFPTemplate(m_arrPreRegTemps[0], m_arrPreRegTemps[1], m_arrPreRegTemps[2], szRegTemp, cbRegTemp);
            m_enrollIdx = 0;
            if (ZKFP_ERR_OK == ret) {
                byte[] out = new byte[3096];
                //ret =base64_encode((const unsigned char *)szRegTemp,  (unsigned long) cbRegTemp,(unsigned char *)out);
                //WriteFile(out,ret,(char*)"data/template.txt");
                ret = ZKFPService.DBAdd(m_tid++, szRegTemp);
                if (ZKFP_ERR_OK == ret) {
                    int[] result = new int[1];
                    result[0] = 0;
                    System.arraycopy(szRegTemp, 0, m_szLastRegTemplate, 0, cbRegTemp[0]);
                    m_nLastRegTempLen = cbRegTemp[0];
                    ret = ZKFPService.DBCount();
                    System.out.println("Register tid=" + (m_tid - 1) + " succ, DBCount=" + ret);
                } else {
                    System.out.println("DBAdd failed, ret=" + ret);
                }
            } else {
                System.out.println("Register failed, GenRegFPTemplate=" + ret);
            }
            return 1;
        } else {
            System.out.println("Please still place " + (ENROLLCNT - m_enrollIdx) + "times\n");
            return 0;
        }
    }

    public static void main(String[] args) {
        int ret = 0;
        if ((ret = ZKFPService.Initialize()) != ZKFP_ERR_OK) {
            System.out.println("Device Init failed, ret=" + ret);
            return;
        }
        System.out.println("device count=" + ZKFPService.GetDeviceCount());
        if ((m_hDevice = ZKFPService.OpenDevice(0)) == 0) {
            System.out.println("Open device failed\n");
            ZKFPService.Finalize();
            return;
        }
        m_hDBCache = ZKFPService.DBInit();
        if (0 == m_hDBCache) {
            System.out.println("Create DBCache failed\n");
            ZKFPService.CloseDevice(m_hDevice);
            ZKFPService.Finalize();
            return;
        }

        byte[] paramValue = new byte[4];
        int[] cbParamValue = new int[1];
        cbParamValue[0] = 4;
        ZKFPService.GetParameter(m_hDevice, 1, paramValue, cbParamValue);
        m_imgFPWidth = byteArrayToInt(paramValue);
        System.out.println("width=" + m_imgFPWidth);

        paramValue = new byte[4];
        cbParamValue[0] = 4;
        ZKFPService.GetParameter(m_hDevice, 2, paramValue, cbParamValue);
        m_imgFPHeight = byteArrayToInt(paramValue);
        System.out.println("height=" + m_imgFPHeight);

        paramValue = new byte[4];
        cbParamValue[0] = 4;
        ZKFPService.GetParameter(m_hDevice, 106, paramValue, cbParamValue);
        int imageSize = byteArrayToInt(paramValue);
        System.out.println("imageSize=" + imageSize);

        System.out.println("Init Succ\n");
        byte[] m_pImgBuf = new byte[MAX_IMAGE_SIZE];
        m_nLastRegTempLen = 0;
        m_tid = 1;
        m_enrollIdx = 0;
        String select;
        while (true) {
            System.out.println("\nPlease input key\n['r' or 'R']: Register\n['v' or 'V']: Verify\n['i' or 'I']: Identify\n['e' or 'E']: Exit\n");
            Scanner scann = new Scanner(System.in);
            select = scann.next();
            if (select.equals("R") || select.equals("r")) //Register
            {
                m_bRegister = 1;
                m_bIdentify = 0;
                System.out.println("Doing resiger, Please place finger 3 times");
            } else if (select.equals("V") || select.equals("v"))  //Verify
            {
                m_bRegister = 0;
                m_bIdentify = 0;
                System.out.println("Doing verify, Please place finger");
            } else if (select.equals("I") || select.equals("i")) //Identify
            {
                m_bRegister = 0;
                m_bIdentify = 1;
                System.out.println("Doing identify, Please place finger");
            } else if (select.equals("E") || select.equals("e")) {
                break;
            } else {
                System.out.println("Invalid key");
                continue;
            }

            int count = 0;
            int[] tempLen = new int[1];
            while (true) {
                byte[] szTemplate = new byte[MAX_TEMPLATE_SIZE];
                tempLen[0] = MAX_TEMPLATE_SIZE;
                ret = ZKFPService.AcquireTemplate(m_hDevice, m_pImgBuf, szTemplate, tempLen);
                if (ZKFP_ERR_OK == ret) {
//                    try {
//                        Writer out = new FileWriter(new File("/opt/ZKFingerSDK/Java/img" + count));
//                        out.write(ZKFPService.BlobToBase64(m_pImgBuf, m_pImgBuf.length));
//                    } catch (Exception e) {
//                        System.out.println(e);
//                    }
                    String imageName = "data/image-" + (count++) + ".bmp";
                    System.out.println("get picture " + imageName);
                    ZKFPService.WriteBitmap(m_pImgBuf, m_imgFPWidth, m_imgFPHeight, imageName);
                    if (m_bRegister == 1) {//Register
                        if (1 == DoRegister(m_hDBCache, szTemplate, tempLen[0])) {
                            break;
                        }
                    } else {   //Identify

                        String s = ZKFPService.BlobToBase64(m_szLastRegTemplate, m_szLastRegTemplate.length);
                        byte[] b = new byte[m_szLastRegTemplate.length];
                        ZKFPService.Base64ToBlob(s, b, m_szLastRegTemplate.length);

                        int i = ZKFPService.MatchFP(b, szTemplate);
                        System.out.println("---------------------------------");
                        System.out.println(i);

                        DoVerify(m_hDBCache, szTemplate, tempLen[0]);
                        break;
                    }
                }
            }
        }
        if (m_hDevice != 0) {
            ZKFPService.CloseDevice(m_hDevice);
            ZKFPService.Finalize();
        }
        return;
    }
}
