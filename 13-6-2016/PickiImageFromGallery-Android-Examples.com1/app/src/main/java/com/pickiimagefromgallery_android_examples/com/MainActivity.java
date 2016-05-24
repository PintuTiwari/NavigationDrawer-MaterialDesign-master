package com.pickiimagefromgallery_android_examples.com;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.BlobContainerPermissions;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import java.io.File;
import java.io.FileInputStream;

public class MainActivity extends Activity {
	public static final String storageConnectionString =
			"DefaultEndpointsProtocol=http;" +
					"AccountName=pockethcm;" +
					"AccountKey=zkbqSBuSsi7Ujvyuh52f+Az8MsVqM6UIZK48e5aqYmrUFtnkDEdkcMT2eiXQprh1RKOmHQkwVyJyi31zJUdSYg==";
	private static final int IMG_RESULT = 1;

	//<add name="BlobStorage" connectionString="DefaultEndpointsProtocol=https;AccountName=pockethcm;AccountKey=zkbqSBuSsi7Ujvyuh52f+Az8MsVqM6UIZK48e5aqYmrUFtnkDEdkcMT2eiXQprh1RKOmHQkwVyJyi31zJUdSYg==" />	    private static int IMG_RESULT = 1;
	String ImageDecode;
	private static final int SELECT_PICTURE =1 ;
	String FilePath;
	ImageView imageViewLoad;
	Button LoadImage, LoadImage2;
	Intent intent;
	String[] FILE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		imageViewLoad = (ImageView) findViewById(R.id.imageView1);
		LoadImage = (Button) findViewById(R.id.button1);
		LoadImage2 = (Button) findViewById(R.id.button2);

		LoadImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent chooser = new Intent(Intent.ACTION_GET_CONTENT);
				Uri uri = Uri.parse(Environment.getDownloadCacheDirectory().getPath().toString());
				chooser.addCategory(Intent.CATEGORY_OPENABLE);
				chooser.setDataAndType(uri, "*/*");
				try {
					startActivityForResult(chooser, SELECT_PICTURE);
				}
				catch (android.content.ActivityNotFoundException ex)
				{
					//Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
				}

			}
		});

		LoadImage2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				new MyTask().execute();

			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				Uri uri = data.getData();
				FilePath = getPath(uri);
				Log.d("FilePath", FilePath.toString());//proof documents
				Bitmap bitmap= BitmapFactory.decodeFile(FilePath);
				//imageViewLoad.setImageBitmap(bitmap);
				//messageText.setText("Uploading file path:" + FilePath);
			}
		}

	}
	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	private class MyTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			//Here starts the code for Azure Storage Blob
			try {
				// Retrieve storage account from connection-string
				CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

				// Create the blob client
				CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

				// Get a reference to a container
				// The container name must be lower case
				CloudBlobContainer container = blobClient.getContainerReference("photos");

				// Create the container if it does not exist
				container.createIfNotExists();

				// Create a permissions object
				BlobContainerPermissions containerPermissions = new BlobContainerPermissions();

				// Include public access in the permissions object
				containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);

				// Set the permissions on the container
				container.uploadPermissions(containerPermissions);

				// Create or overwrite the "myimage.jpg" blob with contents from a local file
				CloudBlockBlob blob = container.getBlockBlobReference("image_1055026155.jpg");
				File source = new File(FilePath);
				blob.upload(new FileInputStream(source.getAbsolutePath()), source.length());
				Log.d("Testing", "Done");
			} catch (Exception e) {
				Log.e("SARATH", e.toString());
			}
			return null;
		}
	}


}