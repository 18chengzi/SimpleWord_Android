package com.bubble.simpleword.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bubble.simpleword.R;
import com.bubble.simpleword.menu.SettingsFragment;
import com.bubble.simpleword.service.ServiceFloatWord;
import com.bubble.simpleword.util.Util;

/**
 * <p>Title: FloatWindowBigView</p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @version 1.0   
 * @since JDK 1.8.0_45
 * @author bubble
 * @date 2015-8-30 下午1:02:52
 */
public class ViewBigFloatWindow extends LinearLayout {
    public static int viewWidth;  
  
    public static int viewHeight;  
  
    public ViewBigFloatWindow(final Context context) {  
        super(context);  
        LayoutInflater.from(context).inflate(R.layout.word_float_window_big, this);  
        View view = findViewById(R.id.big_window_layout);  
        viewWidth = view.getLayoutParams().width;  
        viewHeight = view.getLayoutParams().height;  
        Button close = (Button) findViewById(R.id.close);  
        Button back = (Button) findViewById(R.id.back);  
        close.setOnClickListener(new OnClickListener() {  
            @Override  
            public void onClick(View v) {  
                // 点击关闭悬浮窗的时候，移除所有悬浮窗，并停止Service  
                MyWindowManager.removeBigFloatWord(context);  
                MyWindowManager.removeSmallFloatWord(context);  
                Intent intent = new Intent(getContext(), ServiceFloatWord.class);  
                context.stopService(intent);  
            }  
        });  
        back.setOnClickListener(new OnClickListener() {  
            @Override  
            public void onClick(View v) {  
                // 点击返回的时候，移除大悬浮窗，创建小悬浮窗  
                MyWindowManager.removeBigFloatWord(context);  
                MyWindowManager.createSmallFloatWord(context,Util.getSharedPreferences(context).getInt(SettingsFragment.KEY_SEEKBAR_WIDTH_FLOAT_WORD, SettingsFragment.WIDTH_FLOAT_WORD));  
            }  
        });  
    }  
    
    /**
     * <p>Description: </p>
     * @author bubble
     * @date 2015-8-31 下午5:56:04
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) { 
    	switch (event.getKeyCode()) {
        case KeyEvent.KEYCODE_BACK:
        	MyWindowManager.removeBigFloatWord(getContext());
    		MyWindowManager.createSmallFloatWord(getContext(),Util.getSharedPreferences(getContext()).getInt(SettingsFragment.KEY_SEEKBAR_WIDTH_FLOAT_WORD, SettingsFragment.WIDTH_FLOAT_WORD));
            return true;
        default:
        	return super.dispatchKeyEvent(event); 
        }
	} 
    
    /**
     * <p>Description: </p>
     * @author bubble
     * @date 2015-9-7 下午8:25:34
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	int x = (int) event.getX();
        int y = (int) event.getY();
        Rect rect = new Rect();
        this.getGlobalVisibleRect(rect);	//获取视图在屏幕坐标中的可视区域
        if ( ! rect.contains(x, y) ) {
        	MyWindowManager.removeBigFloatWord(getContext());
    		MyWindowManager.createSmallFloatWord(getContext(),Util.getSharedPreferences(getContext()).getInt(SettingsFragment.KEY_SEEKBAR_WIDTH_FLOAT_WORD, SettingsFragment.WIDTH_FLOAT_WORD));
        }
    	return super.onTouchEvent(event);
    }
}
