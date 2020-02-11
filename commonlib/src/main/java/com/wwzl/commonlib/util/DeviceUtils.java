package com.wwzl.commonlib.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by ghr on 2016/p7/p7.
 */
public class DeviceUtils {
    /*
     * 获取应用名
     */
    public TelephonyManager phone;
    public WifiManager wifi;
    static PackageManager pm;
    static String packname;

    /*  public DeviceUtils(Context context) {
          phone = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
          wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
          pm = context.getPackageManager();
          packname = context.getPackageName();
          appPremission();
      }*/
    private static int stateBarHeight;

    public static int getStateBarHeight(Context context) {
        Class c = null;
        try {
            if (stateBarHeight == 0) {
                c = Class.forName("com.android.internal.R$dimen");
                Object obj = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = Integer.parseInt(field.get(obj).toString());
                stateBarHeight = context.getResources().getDimensionPixelSize(x);
                return stateBarHeight;
            } else {
                return stateBarHeight;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static boolean isGpsOpen(Context context) {
        return ((LocationManager) context.getSystemService(LOCATION_SERVICE)).isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static String[] appPremission() {
        try {
            PackageInfo packinfo = pm.getPackageInfo(packname,
                    PackageManager.GET_PERMISSIONS);
            // 获取到所有的权限
            return packinfo.requestedPermissions;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
        return null;
    }


    public static String getVersionName(Context context) {
        String versionName = null;
        try {
            //获取包管理者
            PackageManager pm = context.getPackageManager();
            //获取packageInfo
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            //获取versionName
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /*
     * 获取应用版本
     */
    public static int getVersionCode(Context context) {

        int versionCode = 0;
        try {
            //获取包管理者
            PackageManager pm = context.getPackageManager();
            //获取packageInfo
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            //获取versionCode
            versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }


    public static String getMobileUniqueCode(Context context) {
        //读取保存的在sd卡中的唯一标识符
        String uniqueCode = null;
        // 用于生成最终的唯一标识符
        StringBuilder s = new StringBuilder();
        //判断是否已经生成过,
        if (uniqueCode != null && !"".equals(uniqueCode)) {
            return uniqueCode;
        }
        try {
            //获取IMES(也就是常说的DeviceId)
            uniqueCode = getIMIEStatus(context);
            s.append(uniqueCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            //获取设备的MACAddress地址 去掉中间相隔的冒号
            uniqueCode = getLocalMac(context).replace(":", "");
            s.append(uniqueCode);
        } catch (Exception e) {
            e.printStackTrace();
        } // }
        //如果以上搜没有获取相应的则自己生成相应的UUID作为相应设备唯一标识符
        if (s.length() <= 0) {
            UUID uuid = UUID.randomUUID();
            uniqueCode = uuid.toString().replace("-", "");
            s.append(uniqueCode);
        }
        //为了统一格式对设备的唯一标识进行md5加密 最终生成32位字符串
        String md5 = getMD5(s.toString(), false);
        return md5;
    }

    /**
     * 获取设备的DeviceId(IMES) 这里需要相应的权限<br/> * 需要 READ_PHONE_STATE 权限 * * @param context * @return
     */
    private static String getIMIEStatus(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint({"MissingPermission", "HardwareIds"})
        String deviceId = tm != null ? tm.getDeviceId() : null;
        return deviceId;
    }

    /**
     * 获取设备MAC 地址 由于 6.0 以后 WifiManager 得到的 MacAddress得到都是 相同的没有意义的内容
     * * 所以采用以下方法获取Mac地址
     * * @param context
     * * @return
     */
    @SuppressLint("HardwareIds")
    private static String getLocalMac(Context context) {
       /* WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        assert wifi != null;
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();*/
        String macAddress = null;
        StringBuilder buf = new StringBuilder();
        NetworkInterface networkInterface = null;
        try {
            networkInterface = NetworkInterface.getByName("eth1");
            if (networkInterface == null) {
                networkInterface = NetworkInterface.getByName("wlan0");
            }
            if (networkInterface == null) {
                return "";
            }
            byte[] addr = networkInterface.getHardwareAddress();
            for (byte b : addr) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            macAddress = buf.toString();
        } catch (SocketException e) {
            e.printStackTrace();
            return "";
        }
        return macAddress;
    }

    /**
     * 对挺特定的 内容进行 md5 加密 * @param msg 加密明文
     * * @param upperCase 加密以后的字符串是是大写还是小写 true 大写 false 小写
     * * @return
     */
    public static String getMD5(String message, boolean upperCase) {
        String md5str = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] input = message.getBytes();
            byte[] buff = md.digest(input);
            md5str = bytesToHex(buff, upperCase);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5str;
    }


    public static String bytesToHex(byte[] bytes, boolean upperCase) {
        StringBuilder md5str = new StringBuilder();
        int digital;
        for (byte aByte : bytes) {
            digital = aByte;
            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        if (upperCase) {
            return md5str.toString().toUpperCase();
        }
        return md5str.toString().toLowerCase();
    }

    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    Log.i("前台", appProcess.processName);
                    return false;
                } else {
                    Log.i("后台", appProcess.processName);
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 判断当前界面是否是桌面
     */
    public static boolean isHome(Context context) {
        String packageName = getTopApp(context);
        return getHomes(context).contains(packageName);
    }

    private static String getTopApp(Context context) {
        //android5.0以上只能使用该方式
        UsageStatsManager m = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        if (m != null) {
            long now = System.currentTimeMillis();
            //获取一小时之内的应用数据
            List<UsageStats> stats = m.queryUsageStats(UsageStatsManager.INTERVAL_BEST, now - 60 * 1000 * 60, now);
            String topActivity = "";
            //取得最近运行的一个app，即当前运行的app
            if ((stats != null) && (!stats.isEmpty())) {
                int j = 0;
                for (int i = 0; i < stats.size(); i++) {
                    if (stats.get(i).getLastTimeUsed() > stats.get(j).getLastTimeUsed()) {
                        j = i;
                    }
                }
                topActivity = stats.get(j).getPackageName();
            }
            return topActivity;
        }
        return null;
    }

    /**
     * 获得属于桌面的应用的应用包名称
     *
     * @return 返回包含所有包名的字符串列表
     */
    private static List<String> getHomes(Context context) {
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo ri : resolveInfo) {
            names.add(ri.activityInfo.packageName);
        }
        return names;
    }

    /**
     * 获取外网IP地址
     *
     * @return
     */
    public static String getNetIp() {
        String IP = "";
        try {
            String address = "http://ip.taobao.com/service/getIpInfo2.php?ip=myip";
            URL url = new URL(address);

            //URLConnection htpurl=url.openConnection();

            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setUseCaches(false);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("user-agent",
                    "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.7 Safari/537.36"); //设置浏览器ua 保证不出现503

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = connection.getInputStream();

                // 将流转化为字符串
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(in));

                String tmpString = "";
                StringBuilder retJSON = new StringBuilder();
                while ((tmpString = reader.readLine()) != null) {
                    retJSON.append(tmpString + "\n");
                }

                JSONObject jsonObject = new JSONObject(retJSON.toString());
                String code = jsonObject.getString("code");

                if (code.equals("0")) {
                    JSONObject data = jsonObject.getJSONObject("data");
//                    IP = data.getString("ip") + "(" + data.getString("country")
//                            + data.getString("area") + "区"
//                            + data.getString("region") + data.getString("city")
//                            + data.getString("isp") + ")";
                    IP = data.getString("ip");

                    LogUtils.e("您的IP地址是：" + IP);
                } else {
                    IP = "";
                    LogUtils.e("IP接口异常，无法获取IP地址！");
                }
            } else {
                IP = "";
                LogUtils.e("网络连接异常，无法获取IP地址！");
            }
        } catch (Exception e) {
            IP = "";
            LogUtils.e("获取IP地址时出现异常，异常信息是：" + e.toString());
        }
        return IP;
    }
}
