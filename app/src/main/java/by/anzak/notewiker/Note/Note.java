package by.anzak.notewiker.Note;

import android.graphics.Bitmap;

import java.io.File;
import java.util.List;

/**
 * Интерфейс заметки
 */
public interface Note {

    /* возвращает заголовок заметки */
    public String getTitle();

    /* возвращает папку заметки */
    public File getFolder();

    /* возвращает иконку заметки */
    public Bitmap getIcon();

    /* возвращает папку для прикрепленных файлов */
    public File getAttachFolder();

    /* возвращает коллекцию прикрепленных файлов */
    public List<File> getAttachList();

    /* возвращает коллекцию заметок - наследников */
    public List<File> getChildren();

    /* возвращает содержимое заметки */
    public Object getContent();

}

