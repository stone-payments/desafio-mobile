package com.germano.desafiostone.activities;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.germano.desafiostone.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class EmptyListTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void emptyListTest() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerview_product), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(9, click()));

        ViewInteraction relativeLayout = onView(
                allOf(withId(R.id.relative_cart), isDisplayed()));
        relativeLayout.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navegar para cima"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.btn_checkout), withText("Adicionar ao carrinho"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Navegar para cima"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.btn_checkout), withText("Adicionar ao carrinho"), isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("Navegar para cima"),
                        withParent(withId(R.id.toolbar)),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withContentDescription("Navegar para cima"),
                        withParent(allOf(withId(R.id.toolbar),
                                withParent(withId(R.id.toolbar_layout)))),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.recyclerview_product), isDisplayed()));
        recyclerView2.perform(actionOnItemAtPosition(6, click()));

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(R.id.btn_checkout), withText("Adicionar ao carrinho"), isDisplayed()));
        appCompatTextView3.perform(click());

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.imageview_delete), isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.imageview_delete), isDisplayed()));
        appCompatImageView2.perform(click());

        ViewInteraction appCompatImageView3 = onView(
                allOf(withId(R.id.imageview_delete), isDisplayed()));
        appCompatImageView3.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.textview_checkout), withText("Realizar pagamento"), isDisplayed()));
        appCompatButton.perform(click());

    }

}
