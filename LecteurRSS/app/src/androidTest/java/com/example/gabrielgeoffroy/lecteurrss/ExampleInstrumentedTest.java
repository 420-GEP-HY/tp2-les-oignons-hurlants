package com.example.gabrielgeoffroy.lecteurrss;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.gabrielgeoffroy.lecteurrss", appContext.getPackageName());
    }

    /**
     * FluxAdapterTests
     */

    @Test
    public void FluxAdapter_Constructeur_isCorrect(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        final Chaine c = new Chaine();

        List<Chaine> list = new ArrayList<Chaine>(){};

        try {
            FluxAdapter fa = new FluxAdapter(appContext,0,list);
            c.lien = "https://www.jeanlepine.com/flux_laptop.xml";
            String urlImage = "http://www.laptopservice.fr/themes/laptop/img/ordinateur-portable.png";
            URL url = new URL(urlImage);
            c.image = fa.getBitmapFromUrl(url);
            list.add(c);
            fa = new FluxAdapter(appContext,0,list);
            Boolean checkList = list.containsAll(fa.chaines);
            Boolean checkImage = c.image.sameAs(fa.bitmap);
            assertEquals(true,checkList);
            assertEquals(true,checkImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void FluxAdapter_getBitmapFromUrl_isCorrect() {
        try {
            String urlImage = "https://i.ytimg.com/vi/SfLV8hD7zX4/maxresdefault.jpg";
            URL url = null;
            url = new URL(urlImage);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true);
            connection.connect();

            InputStream input = connection.getInputStream();

            Bitmap image = BitmapFactory.decodeStream(input);
            Context appContext = InstrumentationRegistry.getTargetContext();
            FluxAdapter fa = new FluxAdapter(appContext, 0, null);
            Boolean b = image.sameAs(fa.getBitmapFromUrl(url));
            assertEquals(true, b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void FluxAdapter_getBitmapFromUrl_isIncorrect() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        String urlImage = "https://i.ytimg.com/vi/SfLV8hD7zX4/maxresdefault.jpg";
        String urlImage2 = "https://s7d1.scene7.com/is/image/PETCO/puppy-090517-dog-featured-355w-200h-d";
        URL url = null;
        URL url2 = null;
        Bitmap image = null;
        FluxAdapter fa = new FluxAdapter(appContext, 0, null);
        try {
            url = new URL(urlImage);
            url2 = new URL(urlImage2);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true);
            connection.connect();

            InputStream input = connection.getInputStream();

            image = BitmapFactory.decodeStream(input);
            Boolean b = image.sameAs(fa.getBitmapFromUrl(url2));
            assertEquals(false, b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void FluxAdapter_getBitmapFromUrl_isNull() {
        String urlPasImage = "https://www.google.ca/";
        Context appContext = InstrumentationRegistry.getTargetContext();
        URL url = null;
        FluxAdapter fa = new FluxAdapter(appContext, 0, null);
        try {
            url = new URL(urlPasImage);
            Bitmap resultat = fa.getBitmapFromUrl(url);
            assertEquals(null, resultat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}