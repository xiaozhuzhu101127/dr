package com.topjet.crediblenumber.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.topjet.crediblenumber.BaseActivity;
import com.topjet.crediblenumber.R;
import com.topjet.crediblenumber.util.FileUtils;
import com.topjet.crediblenumber.util.ShapeUtils;

public class ViolateCategoryActivity extends BaseActivity implements OnClickListener {
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_violate_category);
		super.onCreate(savedInstanceState);
		
		tv_title = (TextView)this.findViewById(R.id.tv_title);
		tv_title.setText("违章查询");
	
	}

	@Override
	protected void ioHandler(Message msg) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doError(Message msg) {
		// TODO Auto-generated method stub

	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.searchViolate:			
			String state = Environment.getExternalStorageState();  
			if (state.equals(Environment.MEDIA_MOUNTED)) {  
			    String saveDir = FileUtils.violateImagePath.toString();
			    File dir = new File(saveDir);  
			    if (!dir.exists()) {  
			        dir.mkdir();  
			    }  
			   File file = new File(saveDir, "violate.jpg");  
			    file.delete();  
			    if (!file.exists()) {  
			        try {  
			            file.createNewFile();  
			        } catch (IOException e) {  
			            e.printStackTrace();  
			            Toast.makeText(ViolateCategoryActivity.this,  
			                    "没有SDCARD",  
			                    Toast.LENGTH_LONG).show();  
			            return;  
			        }  
			    }  
			    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
			    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));  
			    startActivityForResult(intent, 1);  
			}else{
				 Toast.makeText(ViolateCategoryActivity.this,  
						 "没有SDCARD", Toast.LENGTH_LONG)  
				            .show();  
			}
			
//			Intent it  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//			startActivityForResult(it, 1);			
			break;
		 case R.id.resultViolate:			
			startActivity(new Intent(this,ViolateListAcitivity.class));		
		    break;
		 case R.id.btn_back:
				finish();
				break;
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==1){		
			  Bitmap photo = null;
//               Uri uri = data.getData();  
//				if (uri != null) {  
//				   photo = BitmapFactory.decodeFile(uri.getPath());  
//				}  
//				if (photo == null) {  
//				     Bundle bundle = data.getExtras();  
//					if (bundle != null) {  
//					    photo = (Bitmap) bundle.get("data");  
//					} 
//				}  
//
//			
//				if(photo!=null){
				//	 photo = ThumbnailUtils.extractThumbnail(photo, 600, 400);
			
				
//				 try {
//						photo.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(getviolateImage()));
//					} catch (FileNotFoundException e) {
//						// TODO Auto-generated catch block
//						Log.e("ViolateCategoryActivity", e.getMessage());
//						e.printStackTrace();
//					}
					Intent it  = new Intent(this,ViolateAcitivity.class);
				//	 byte buff[] = ShapeUtils.Bitmap2Bytes(photo);//这里的LZbitmap是Bitmap类的,跟第一个方法不同
				//	it.putExtra("bitmap",buff);
				    this.startActivity(it);
				
					
//				}
				
			
			
		}
		
	}
	
//	public  File getviolateImage(){
//		if(!violateImagePath.exists()) {
//			violateImagePath.mkdirs();
//		}		
//		if (violateImage.exists()) {
//			violateImage.delete();
//		}
//		return violateImage;
//	}

}
