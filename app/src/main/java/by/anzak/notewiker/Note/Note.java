package by.anzak.notewiker.Note;

import android.graphics.Bitmap;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * Класс заметки
 */
public abstract class Note implements Serializable{

    /* возвращает заголовок заметки */
    public abstract String getTitle();

    /* возвращает папку заметки */
    public abstract File getFolder();

    /* возвращает иконку заметки */
    public abstract Bitmap getIcon();

    /* возвращает папку для прикрепленных файлов */
    public abstract File getAttachFolder();

    /* возвращает коллекцию прикрепленных файлов */
    public abstract List<File> getAttachList();

    /* возвращает коллекцию заметок - наследников */
    public abstract List<File> getChildren();

    /* возвращает содержимое заметки */
    public abstract Object getContent();

}

