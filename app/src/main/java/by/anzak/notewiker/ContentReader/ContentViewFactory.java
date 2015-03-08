package by.anzak.notewiker.ContentReader;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.File;

import by.anzak.notewiker.ContentReader.OutWiker.NoteWebClient;
import by.anzak.notewiker.Note.Note;
import by.anzak.notewiker.Note.OutWiker.OutWikerNote;

/**
 * Простая фабрика View с содержимым заметки
 * Created by Andrey on 01.03.2015.
 */
public class ContentViewFactory {

    private static ContentViewFactory instance = null;

    public static ContentViewFactory getInstance() {
        if (instance == null) return new ContentViewFactory();
        return instance;
    }

    private ContentViewFactory() {
    }

    @Nullable
    public View createContentView(Context context, Note note) {
        if (note instanceof OutWikerNote) {
            System.out.println("Note is a instance of OutWikerNote");
            return createOutWikerView(context, (OutWikerNote) note);
        }
        System.out.println("Note is NOT a instance of OutWikerNote");
        return null;
    }

    private View createOutWikerView(Context context, OutWikerNote note){

            File contentFile = (File) note.getContent();
            if (contentFile == null) return null;

            WebView view = new WebView(context);
            view.setWebViewClient(new NoteWebClient(context));
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            WebSettings settings = view.getSettings();
            settings.setDefaultTextEncodingName("utf-8");
            settings.setJavaScriptCanOpenWindowsAutomatically(false);
            settings.setSupportMultipleWindows(false);
            settings.setSupportZoom(true);
            settings.setJavaScriptEnabled(true);
            view.canGoBack();
            view.setBackgroundColor(0x00000000);
            view.loadUrl("file://" + contentFile);

            return view;

    }
}
