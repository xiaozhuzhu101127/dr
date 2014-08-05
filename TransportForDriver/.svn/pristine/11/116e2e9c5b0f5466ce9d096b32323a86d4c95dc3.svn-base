package com.topjet.crediblenumber.util;

import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.activity.MessageActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

/**
 * <pre>
 * Copyright:	Copyright (c)2009  
 * Company:		杭州龙驹信息科技
 * Author:		HuLingwei
 * Create at:	2013-9-25 上午10:33:18  
 *  
 * 修改历史:
 * 日期    作者    版本  修改描述
 * ------------------------------------------------------------------
 * 
 * </pre>
 */

public class NotificationHelper {
	private Context mContext;

	public NotificationHelper(Context context) {
		mContext = context;
	}

	public void showNotification() {

		// define sound URI, the sound to be played when there's a notification
		Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		// intent triggered, you can add other intent for other actions
//		Intent intent = new Intent(mContext, MessageActivity.class);
//		PendingIntent pIntent = PendingIntent.getActivity(mContext, 0, intent, 0);

		final NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        builder.setSmallIcon(R.drawable.setting_about_ysx);
        builder.setContentTitle("新的消息");
        builder.setTicker("新的消息");
        builder.setContentText("您有新的消息！");
        builder.setSound(soundUri);
        //0不需要图标
//        builder.addAction(R.drawable.ysx, "查看", pIntent)
//		.addAction(0, "Remind", pIntent);
        final Intent notificationIntent = new Intent(mContext, MessageActivity.class);
        final PendingIntent pi = PendingIntent.getActivity(mContext, 0, notificationIntent, 0);
        builder.setContentIntent(pi);
        final Notification notification = builder.build();
        // notification.flags |= Notification.FLAG_FOREGROUND_SERVICE;
        // notification.flags |= Notification.FLAG_NO_CLEAR;
        // notification.flags |= Notification.FLAG_ONGOING_EVENT;
        //查看后消失
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
		NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(0, notification);
	}

	public void cancelNotification(int notificationId) {

		if (Context.NOTIFICATION_SERVICE != null) {
			String ns = Context.NOTIFICATION_SERVICE;
			NotificationManager nMgr = (NotificationManager) mContext.getSystemService(ns);
			nMgr.cancel(notificationId);
		}
	}
}
