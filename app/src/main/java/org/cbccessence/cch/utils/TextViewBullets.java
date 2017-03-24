
package org.cbccessence.cch.utils;
import android.content.Context;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.BulletSpan;
import android.util.AttributeSet;
import android.widget.TextView;


public class TextViewBullets extends TextView
{
    private boolean m_modifyingText = false;

    public TextViewBullets(Context context)
    {
        super(context);
        init();
    }

    public TextViewBullets(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public TextViewBullets(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    private void init()
    {
        addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                //Do nothing here... we don't care
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                //Do nothing here... we don't care
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (m_modifyingText)
                    return;

                underlineText();
            }
        });

        underlineText();
    }

    private void underlineText()
    {
        if (m_modifyingText)
            return;

        m_modifyingText = true;

        SpannableString content = new SpannableString(getText());
        content.setSpan(new BulletSpan(15), 0, content.length(), 0);
        setText(content);

        m_modifyingText = false;
    }
}