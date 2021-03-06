/*
 * XmlGui application.
 * Written by Frank Ableson for IBM Developerworks
 * June 2010
 * Use the code as you wish -- no warranty of fitness, etc, etc.
 */
package org.cbccessence.cch.popupquestions;

import org.cbccessence.R;
import org.digitalcampus.oppia.application.MobileLearning;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class XmlGuiImage extends LinearLayout {
	ImageView label;
	private Bitmap bitmap;
	private WebView label2;
	
	@SuppressLint("NewApi")
	public XmlGuiImage(final Context context,String labelText,String initialText) {
		super(context);
		label = new ImageView(context);
		label2=new WebView(context);
		 if(labelText.endsWith("gif")){
			 label2.getSettings().setAllowFileAccess(true);
			 label2.getSettings().setJavaScriptEnabled(true);
			 label2.getSettings().setBuiltInZoomControls(true);
			 label2.loadUrl("file:///"+MobileLearning.POC_ROOT+labelText);
			 System.out.println(labelText);
			 label2.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
		 }else{
			 bitmap = BitmapFactory.decodeFile(MobileLearning.POC_ROOT+labelText);
			 label.setImageBitmap(bitmap);
		 }
		 
		label.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
		label.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				final Dialog nagDialog = new Dialog(context);
	            nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
	            nagDialog.setCancelable(false);
	            nagDialog.setContentView(R.layout.image_view_dialog);
	            ImageButton btnClose = (ImageButton)nagDialog.findViewById(R.id.imageButton_close);
	            ImageView ivPreview = (ImageView)nagDialog.findViewById(R.id.imageView_largerImage);
	            ivPreview.setImageBitmap(bitmap);

	            btnClose.setOnClickListener(new OnClickListener() {
	                @Override
	                public void onClick(View arg0) {
	                    nagDialog.dismiss();
	                }
	            });
	            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
	            lp.copyFrom(nagDialog.getWindow().getAttributes());
	            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
	            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
	            nagDialog.show();
	            nagDialog.getWindow().setAttributes(lp);
				
			}
	    	
	    });
		if(labelText.endsWith("gif")){
			this.addView(label2);
		}else{
			this.addView(label);
		}
		
	}

	public XmlGuiImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	

}
