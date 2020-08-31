package com.mj.richedittext;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import jp.wasabeef.richeditor.RichEditor;

public class RichEditText extends LinearLayout implements View.OnClickListener {
    private Context context;
    private AttributeSet attrs;

    public RichEditText(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public RichEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        init();
    }

    public RichEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.attrs = attrs;
        init();
    }

    public RichEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        this.attrs = attrs;
        init();
    }

    private RichEditor richEditor;
    private LinearLayout lnrOptions;

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_rich_edit_text, null, false);
        richEditor = view.findViewById(R.id.richEditor);
        richEditor.setEditorHeight(200);
        lnrOptions = view.findViewById(R.id.lnrOptions);
        int childCount = lnrOptions.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = lnrOptions.getChildAt(i);
            if (childAt instanceof ImageButton) {
                childAt.setOnClickListener(this);
                childAt.setTag(0);
            }
        }
        addView(view);
    }


    @Override
    public void onClick(View view) {

        if (view.getTag() != null) {
            int tag = (int) view.getTag();
            if (tag == 1) {
                view.setTag(0);
            } else {
                view.setTag(1);
            }
        } else {
            view.setTag(1);
        }
        switch (view.getId()) {

            case R.id.ibtnUndo: {
                richEditor.undo();
                changeButtonColor(view);
                break;
            }

            case R.id.ibtnRedo: {
                richEditor.redo();
                changeButtonColor(view);
                break;
            }
            case R.id.ibtnBold: {
                richEditor.setBold();
                changeButtonColor(view);
                break;
            }

            case R.id.ibtnItalic: {
                richEditor.setItalic();
                changeButtonColor(view);
                break;
            }

            case R.id.ibtnUnderline: {
                richEditor.setUnderline();
                changeButtonColor(view);
                break;
            }

            case R.id.ibtnStrike: {
                richEditor.setStrikeThrough();
                changeButtonColor(view);
                break;
            }
            case R.id.ibtnSubScript: {
                richEditor.setSubscript();
                changeButtonColor(view);
                break;
            }

            case R.id.ibtnSuperScript: {
                richEditor.setSuperscript();
                changeButtonColor(view);
                break;
            }

            case R.id.ibtnNumberList: {
                richEditor.setNumbers();
                changeButtonColor(view);
                break;
            }

            case R.id.ibtnBulletList: {
                richEditor.setBullets();
                changeButtonColor(view);
                break;
            }

            case R.id.ibtnTextColor: {
                richEditor.setTextColor(Color.RED);
                break;
            }
            case R.id.ibtnBGColor: {
                richEditor.setTextBackgroundColor(Color.BLACK);
                break;
            }
            case R.id.ibtnLink: {
                insertLink();
                changeButtonColor(view);
                break;
            }


        }

    }

    public String getText() {
        return richEditor.getHtml();
    }

    public void setText(String htmlText) {
        richEditor.setHtml(htmlText);

    }

    void setOnTextChangeListener(RichEditor.OnTextChangeListener listener) {
        richEditor.setOnTextChangeListener(listener);
    }

    public void setReadOnly(boolean readOnly) {
        richEditor.setInputEnabled(!readOnly);
    }

    public void hideMenuBar() {
        lnrOptions.setVisibility(View.GONE);
    }

    public void showMenuBar() {
        lnrOptions.setVisibility(View.VISIBLE);
    }

    private void insertLink() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_insert_link, null, false);
        builder.setView(view);
        final EditText link = view.findViewById(R.id.link);
        final EditText linkTitle = view.findViewById(R.id.linkTitle);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String strLink = link.getText().toString().trim();
                String strLinkTitle = linkTitle.getText().toString().trim();
                if (strLinkTitle.isEmpty()) {
                    strLinkTitle = strLink;
                }
                if (!strLink.isEmpty()) {
                    richEditor.insertLink(strLinkTitle, strLink);
                }

            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();


    }

    private void changeButtonColor(View view) {
//        if (view instanceof ImageButton) {
//            int tag = (int) view.getTag();
//            if (tag == 1) {
//                view.setBackgroundColor(Color.WHITE);
//                ((ImageButton) view).setImageTintList(ColorStateList.valueOf(Color.BLACK));
//            } else {
//                view.setBackgroundColor(Color.BLACK);
//                ((ImageButton) view).setImageTintList(ColorStateList.valueOf(Color.WHITE));
//            }
//        }
    }

}
