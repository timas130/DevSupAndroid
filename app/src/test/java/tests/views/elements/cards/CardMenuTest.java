package tests.views.elements.cards;

import android.view.View;
import android.widget.TextView;

import com.sup.dev.android.androiddevsup.R;

import tests._sup_android.TestAndroid;
import tests._sup_android.stubs.support.CallbackStub;
import tests._sup_android.TestApplication;

import com.sup.dev.android.views.elements.cards.CardMenu;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(application = TestApplication.class)
public class CardMenuTest {

    private CardMenu card;
    private View view;
    private TextView vText;

    @Before
    public void before() {
        TestAndroid.init();
        card = new CardMenu();
    }

    @Test
    public void init() {
        instanceView();
        assertEquals(0, vText.getText().length());
    }

    @Test
    public void setText() {
        card.setText(R.string.test);
        instanceView();
        assertEquals("test",vText.getText().toString());
    }

    @Test
    public void setEnabled() {
        card.setOnClick(new CallbackStub());

        card.setEnabled(false);
        instanceView();
        assertFalse(vText.isEnabled());

        card.setEnabled(true);
        instanceView();
        assertTrue(vText.isEnabled());
    }

    @Test
    public void setTextColor() {
        card.setTextColor(0xF0F0F0F0);

        instanceView();
        assertEquals(0xF0F0F0F0, vText.getCurrentTextColor());
    }

    private void instanceView() {
        view = card.instanceView(RuntimeEnvironment.systemContext);
        card.bindView(view);
        vText = view.findViewById(R.id.text);
    }

}
