# RichTextEditor-android

# Gradle

     dependencies {
	         implementation 'com.github.manjeetbrar91:RichTextEditor-android:1.0.1'
     }

# Usage

    <com.mj.richedittext.RichEditText
        android:id="@+id/mainEditor"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical" />
In Activity

	RichEditText mainEditor = findViewById(R.id.mainEditor);
        
	mainEditor.setHint("Start typing");
        
	String text = mainEditor.getText();
        
	mainEditor.setText("xxxxx");
        
	mainEditor.showMenuBar();
        
	mainEditor.hideMenuBar();
        
	mainEditor.setMinimumHeight(200);
