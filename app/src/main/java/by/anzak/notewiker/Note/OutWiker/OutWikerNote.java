package by.anzak.notewiker.Note.OutWiker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import by.anzak.notewiker.ContentReader.ContentReader;
import by.anzak.notewiker.Note.Note;

/**
 * Основной объект заметки формата OutWiker
 */
public class OutWikerNote implements Note, ContentReader, Serializable, Comparable {

    private static final long serialVersionUID = 5422739599880394547L;
    private ContentReader reader;
    private static final DirsFilter dirsFilter = new DirsFilter();

    private final File folder;
    private ParamManager settings = null;
    private String title;
    Bitmap icon;


    public enum Type {HTML, TEXT, WIKI}

    public OutWikerNote(File folder) {
        this.folder = folder;
        title = folder.getName();
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public File getFolder() {
        return folder;
    }

    @Override
    public Bitmap getIcon() {
        if (icon == null) {
            File icon = new File(getFolder(), "/__icon.png");
            if (icon.isFile() && icon.canRead()) {
                return BitmapFactory.decodeFile(icon.getAbsolutePath());
            }
        }
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    @Override
    public File getAttachFolder() {
        File dir = new File(folder, "__attach");
        if (dir.exists() && dir.isDirectory()) return dir;
        return null;
    }

    @Override
    public List<File> getAttachList() {
        return Arrays.asList(getAttachFolder().listFiles());
    }

    @Override
    public List<File> getChildren() {
        List<File> children = Arrays.asList(getFolder().listFiles(dirsFilter));
        return children;
    }

    public synchronized ParamManager getSettings() {
        if (settings == null) {
            settings = new ParamManager(folder);
        }
        return settings;
    }

    @Nullable
    public Type getType() {
        Type type = null;

            /* проверка к какому типу относится заметка по тегу файла конфиурации */
        String typeStr = getSettings().getParam("General", "type");
        if ("wiki".equals(typeStr)) type = Type.WIKI;
        if ("html".equals(typeStr)) type = Type.HTML;
        if ("text".equals(typeStr)) type = Type.TEXT;

        //TODO сделать проверку, к какому типу относится заметка по наличию файлов

        return type;
    }

    /**
     * The order of a by.anzak.notewiker.Note in the folder.
     *
     * @return order number or -1 if order not specified
     */
    public int getOrder() {
        try {
            return Integer.valueOf(getSettings().getParam("General", "order"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void setOrder(int order) {
        getSettings().setParam("General", "order", String.valueOf(order));
    }

    public String getUID() {
        return getSettings().getParam("General", "uid");
    }

    public void setUID(String uid) {
        getSettings().setParam("General", "uid", uid);
    }

    public Object getContent() {
        if (reader == null) {
            //TODO Сделать создание ридеров. Сделать фабрику.
        }
        return reader.getContent();
    }

    public File getContentFile() {
        File note;
        note = new File(getFolder(), "/__page.text");
        if (note.exists()) return note;
        note = new File(getFolder(), "/__context.html");
        if (note.exists()) return note;
        return null;
    }

    @Override
    public int compareTo(@NotNull Object another) {
        int order;
        if (!(another instanceof OutWikerNote)) {
            return 0;
        }
        order = getOrder() - ((OutWikerNote) another).getOrder();
        if (order == 0) {
            order = getTitle().compareTo(((OutWikerNote) another).getTitle());
        }
        return order;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
