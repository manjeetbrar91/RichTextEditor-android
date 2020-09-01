package com.mj.richedittext;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import jp.wasabeef.richeditor.RichEditor;
import top.defaults.colorpicker.ColorPickerPopup;

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
        richEditor.setTextColor(textColor);
        richEditor.setTextBackgroundColor(textBackgroundColor);
        richEditor.setPlaceholder("Type your text...");
        int childCount = lnrOptions.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = lnrOptions.getChildAt(i);
            if (childAt instanceof ImageButton) {
                childAt.setOnClickListener(this);
                childAt.setTag(0);
            }
        }
        richEditor.focusEditor();
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
        int id = view.getId();
        if (id == R.id.ibtnUndo) {
            richEditor.undo();
            changeButtonColor(view);
        } else if (id == R.id.ibtnRedo) {
            richEditor.redo();
            changeButtonColor(view);
        } else if (id == R.id.ibtnBold) {
            richEditor.setBold();
            changeButtonColor(view);
        } else if (id == R.id.ibtnItalic) {
            richEditor.setItalic();
            changeButtonColor(view);
        } else if (id == R.id.ibtnUnderline) {
            richEditor.setUnderline();
            changeButtonColor(view);
        } else if (id == R.id.ibtnStrike) {
            richEditor.setStrikeThrough();
            changeButtonColor(view);
        } else if (id == R.id.ibtnSubScript) {
            richEditor.setSubscript();
            changeButtonColor(view);
        } else if (id == R.id.ibtnSuperScript) {
            richEditor.setSuperscript();
            changeButtonColor(view);
        } else if (id == R.id.ibtnNumberList) {
            richEditor.setNumbers();
            changeButtonColor(view);
        } else if (id == R.id.ibtnBulletList) {
            richEditor.setBullets();
            changeButtonColor(view);
        } else if (id == R.id.ibtnTextColor) {
            if (view instanceof ImageButton) {
                selectColor((ImageButton) view, textColor);
            }
        } else if (id == R.id.ibtnBGColor) {//richEditor.setTextBackgroundColor(Color.BLACK);
            if (view instanceof ImageButton) {
                selectColor((ImageButton) view, textBackgroundColor);
            }
        } else if (id == R.id.ibtnLink) {
            insertLink();
            changeButtonColor(view);
        }

    }

    public void setHint(String placeholder) {
        richEditor.setPlaceholder(placeholder);
    }

    public String getText() {
        return richEditor.getHtml();
    }

    public void setMinimumHeight(int px) {
        richEditor.setEditorHeight(px);
    }

    public void setText(String text) {
        richEditor.setHtml(text);

    }

    public void setOnTextChangeListener(RichEditor.OnTextChangeListener listener) {
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

    private int textColor = Color.BLACK;
    private int textBackgroundColor = Color.WHITE;

    private void selectColor(final ImageButton v, int initialColor) {

        new ColorPickerPopup.Builder(context)
                // .initialColor(initialColor) // Set initial color
                .enableBrightness(true) // Enable brightness slider or not
                .enableAlpha(true) // Enable alpha slider or not
                .okTitle("OK")
                .cancelTitle("CANCEL")
                .showIndicator(true)
                .showValue(false)
                .build()
                .show(new ColorPickerPopup.ColorPickerObserver() {

                    @Override
                    public void onColorPicked(int color) {
                        v.setImageTintList(ColorStateList.valueOf(color));
                        if (v.getId() == R.id.ibtnTextColor) {
                            textColor = color;
                            richEditor.setTextColor(color);
                        }
                        if (v.getId() == R.id.ibtnBGColor) {
                            textBackgroundColor = color;
                            richEditor.setTextBackgroundColor(color);
                        }
                    }

                });
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
