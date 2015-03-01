package by.anzak.notewiker.ContentReader.OutWiker;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import by.anzak.notewiker.ContentReader.ContentViewFactory;
import by.anzak.notewiker.Note.Note;
import by.anzak.notewiker.R;

/**
 * Created by Andrey on 01.03.2015.
 */
public class NoteViewFactory {

    private static NoteViewFactory instance;

    public static NoteViewFactory getInstance(){
        if (instance == null) instance = new NoteViewFactory();
        return instance;
    }

    private NoteViewFactory(){
    }

    public View createNoteView(@NotNull Context context, @NotNull Note note){

        View view = View.inflate(context, R.layout.note_view, null);

        // получить view с содержимым заметки используя фабрику
        ContentViewFactory factory = ContentViewFactory.getInstance();
        View content = factory.createContentView(context, note);

        if (content != null) {
            // добавить View с содержимым заметки в общий View
            ViewGroup contentGroup = (ViewGroup) view.findViewById(R.id.content);
            contentGroup.addView(content);
            contentGroup.addView(new WebView(context));
        }

        // Настройка заголовка
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(note.getTitle());

        // Настройка вложений
        ImageView attachs_icon = (ImageView) view.findViewById(R.id.attachs_icon);
        TextView attachs_count = (TextView) view.findViewById(R.id.attachs_count);

        if (note.getAttachList() == null || note.getAttachList().size() == 0) {
            attachs_icon.setVisibility(View.GONE);
            attachs_count.setVisibility(View.GONE);
        } else {
            attachs_count.setText(String.valueOf(note.getAttachList().size()));
        }

        return view;
    }
}
