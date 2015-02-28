package by.anzak.notewiker.Note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import by.anzak.notewiker.R;

/**
 * Фабрика View заметок.
 * Создает графический элемент с заголовком заметки для отображения в дереве заметок.
 */
public class NoteViewFactory {

    private LayoutInflater layoutInflater;
    private ViewGroup root;

    public NoteViewFactory(LayoutInflater inflater, ViewGroup root) {
        this.layoutInflater = inflater;
        this.root = root;

    }

    /* Создает вью заметки для отображения в дереве */
    public View getNoteView(Note note, int level, View.OnClickListener changeNoteListener, View.OnClickListener showNoteListener) {
        View view;

        view = layoutInflater.inflate(R.layout.note, root);

        view.setTag(note);

        LinearLayout rootLayout = (LinearLayout) view.findViewById(R.id.rootlayout);
        rootLayout.setBackgroundColor(layoutInflater.getContext().getResources().getColor(R.color.item_background));
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView children_count = (TextView) view.findViewById(R.id.children_count);
        ImageView icon = (ImageView) view.findViewById(R.id.image);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(10 * level, 0, 0, 0);
        rootLayout.setLayoutParams(lp);

        title.setText(note.getTitle());
        children_count.setText(String.valueOf(note.getChildren().size()));
        icon.setImageBitmap(note.getIcon());

            /* На вьюхи вешаются слушатели и соответствующая заметка */
        title.setOnClickListener(showNoteListener);
        title.setTag(note);
        children_count.setOnClickListener(changeNoteListener);
        children_count.setTag(note);

        return view;
    }

    public View getCurrentNoteView(Note note, int level, View.OnClickListener changeNoteListener, View.OnClickListener showNoteListener) {
        View v = getNoteView(note, level, changeNoteListener, showNoteListener);
        (v.findViewById(R.id.rootlayout)).setBackgroundColor(layoutInflater.getContext().getResources().getColor(R.color.item_background_highlighted));
        return v;
    }

}
