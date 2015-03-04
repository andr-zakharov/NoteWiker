package by.anzak.notewiker.ContentReader.OutWiker;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


/**
 * Created by andrey on 28.08.2014.
 * TODO Нужен рефакторинг
 */
public class NoteWebClient extends WebViewClient {

    Context context;
    String currentPage;

    public NoteWebClient(Context context) {
        this.context = context;
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /** пока не реализована логика обработки якорей, этот метод просто его отрезает **/
        url = cutAnchor(url);

        /** если ссылка вида page:// , ищем соответствующую uid страницу **/
        if (url.startsWith("page://")) {
            /**
            url = url.substring(7);
            url = BaseUIDMap.getInstance(context).getLink(url);
            if (url != null) {
                try {
                    Note note = new Note(new File(url), 0);
                    if (note.getNote() != null) {
                        url = "file://" + note.getNote().getAbsolutePath();
                        view.loadUrl(url);
                        return true;
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
            Toast.makeText(context, R.string.exception_file_not_found, Toast.LENGTH_SHORT).show();
            return true;
             **/
        return false;
        }

        /** ссылка вида file:// **/
        if (url.startsWith("file://")) {
//            url = url.substring(7);
            String urlFile;
            urlFile = findFile(url);
            if (urlFile != null) {
                view.loadUrl(urlFile);
                return true;
            }
        }

        /** ссылка на якорь, заглушка **/
        if (url.startsWith("#")) {
            return true;
        }

        /** прочие ссылки отправляются на обработку стандартному WebClient **/
        return false;

    }

    private String findFile(String url) {

        /*

        String href = url.replace("file://", "");

        Note note;
        File file = new File(href);

        // это правильный путь на папку заметки?
        // Это правильный путь на обычную папку?
        if (file.isDirectory() && file.canRead()) {
            try {
                note = new Note(href, 0);
                if (note.getNote() != null) {
                    return "file://" + note.getNote().getAbsolutePath();
                } else return "file://" + href;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        // Это правильный путь на файл?
        if (file.isFile()) {
            return url;
        }

        // переделаем путь ссылки
        String urlNew;

        String cur = currentPage.substring(7);
        File curFile = new File(cur);
        String curFolder = curFile.getParent();

        urlNew = url.replace(curFolder, getRootPath());
        if (urlNew.startsWith("file://")) href = urlNew.substring(7);
        file = new File(href);

        // это правильный путь на папку заметки?
         // Это правильный путь на обычную папку?
        if (file.isDirectory() && file.canRead()) {
            try {
                note = new Note(href, 0);
                if (note.getNote() != null) {
                    return "file://" + note.getNote().getAbsolutePath();
                } else return "file://" + href;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        // Это правильный путь на файл?
        if (file.isFile()) {
            return urlNew;
        }
        */

        return null;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
//        super.onPageFinished(view, url);
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        currentPage = url;

    }

    private String cutAnchor(String href) {
        /** попытка найти и удалить якорь **/
        int pos = href.indexOf("/#");
        if (pos > -1) {
            return href.substring(0, pos);
        }
        return href;
    }

}


