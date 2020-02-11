package com.wwzl.commonlib.config;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;

import androidx.lifecycle.LifecycleOwner;

import com.wwzl.commonlib.application.AppApplication;
import com.wwzl.commonlib.lifecycle.SimpleLifecycleObserver;


/**
 * 摇一摇功能
 *
 * @author weibin.chen
 * @since 2019/2/12 14:50
 */
public class ShakeSensorWorker extends SimpleLifecycleObserver implements SensorEventListener {
    /**
     * 速度阈值，当摇晃速度达到这值后产生作用
     */
    private static final int SPEED_LIMIT = 4000;
    /**
     * 两次检测的时间间隔
     */
    private static final int UPDATE_INTERVAL_TIME = 70;
    /**
     * 触发摇一摇之后, 下次能触发的时间间隔
     */
    private static final int DELAY_ON_SHAKE = 1000;

    /**
     * 传感器管理器
     */
    private SensorManager sensorManager;
    /**
     * 重力感应监听器
     */
    private OnShakeListener mOnShakeListener;
    /**
     * 手机上一个位置时重力感应坐标
     */
    private float lastX;
    private float lastY;
    private float lastZ;
    /**
     * 上次检测时间
     */
    private long lastUpdateTime;
    /**
     * 界面是否回来了
     */
    private boolean resumed = true;
    /**
     * 是否开启摇一摇传感器
     */
    private volatile boolean mSensorsEnabled = false;
    /**
     * 触发摇一摇之后, 下次能触发的时间间隔
     */
    private int mDelayNextTime = DELAY_ON_SHAKE;
    private Handler mHandler = new Handler();

    public ShakeSensorWorker(LifecycleOwner owner, OnShakeListener onShakeListener) {
        super(owner);
        setOnShakeListener(onShakeListener);
        init();
        enable();
    }

    private void init() {
        // 获得传感器管理器
        sensorManager = (SensorManager) AppApplication.getApp().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            // 获得重力传感器
            Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    public void onResume() {
        resumed = true;
    }

    @Override
    public void onPause() {
        resumed = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disable();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (!mSensorsEnabled) {
            return;
        }
        // 现在检测时间
        long currentUpdateTime = System.currentTimeMillis();
        // 两次检测的时间间隔
        long timeInterval = currentUpdateTime - lastUpdateTime;
        // 判断是否达到了检测时间间隔
        if (timeInterval < UPDATE_INTERVAL_TIME) {
            return;
        }
        // 现在的时间变成last时间
        lastUpdateTime = currentUpdateTime;

        // 获得x,y,z坐标
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        // 获得x,y,z的变化值
        float deltaX = x - lastX;
        float deltaY = y - lastY;
        float deltaZ = z - lastZ;

        // 将现在的坐标变成last坐标
        lastX = x;
        lastY = y;
        lastZ = z;

        double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) / timeInterval * 10000;
        // 达到速度阀值，发出提示
        if (speed >= SPEED_LIMIT && resumed) {
            disable();
            if (mOnShakeListener != null) {
                mOnShakeListener.onShake();
            }
            startPostDelayed();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    /**
     * 开启传感器检测
     */
    public void enable() {
        mSensorsEnabled = true;
    }

    /**
     * 关闭传感器检测
     */
    public void disable() {
        mSensorsEnabled = false;
    }

    /**
     * 延迟开启传感器检测
     */
    private void startPostDelayed() {
        mHandler.postDelayed(mStartPostDelayedRunnable, mDelayNextTime);
    }

    private Runnable mStartPostDelayedRunnable = new Runnable() {
        @Override
        public void run() {
            enable();
        }
    };

    public void setOnShakeListener(OnShakeListener onShakeListener) {
        mOnShakeListener = onShakeListener;
    }

    public void setDelayNextTime(int delay) {
        mDelayNextTime = delay;
    }

    public interface OnShakeListener {
        void onShake();
    }
}