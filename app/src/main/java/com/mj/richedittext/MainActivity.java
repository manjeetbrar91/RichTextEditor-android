package com.mj.richedittext;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import jp.wasabeef.richeditor.RichEditor;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RichEditText findViewById = findViewById(R.id.preview);
        RichEditText mainEditor = findViewById(R.id.mainEditor);
        mainEditor.setHint("Start typing");
        String text = mainEditor.getText();
        mainEditor.setText("xxxxx");
        mainEditor.showMenuBar();
        mainEditor.hideMenuBar();
        mainEditor.setMinimumHeight(200);
        findViewById.setReadOnly(true);
        findViewById.hideMenuBar();
        mainEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {

                findViewById.setText(text);
            }
        });
    }

}