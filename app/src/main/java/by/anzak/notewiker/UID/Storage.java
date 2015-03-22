package by.anzak.notewiker.UID;

/**
 * Хранение и доступ к парам UID - link, где:
 * UID - уникальный номер заметки
 * link - ссылка на заметку
 */
public interface Storage {

    public void set(String UID, String link);
    public String getLink(String UID);
}
