package by.anzak.notewiker.UID;

import android.content.Context;

import java.io.File;
import java.util.List;

import by.anzak.notewiker.Note.OutWiker.OutWikerNote;
import by.anzak.notewiker.PrefManager;

/**
 * Created by Andrey on 22.03.2015.
 */
public class UIDManager {

    private Storage storage;
    private Context context;

    public UIDManager(Context context) {
        this.context = context;
        this.storage = new PrefStorage(context);
    }

    public void set(String uid, String link){
        storage.set(uid, link);
    }

    public String getLink(String uid){
        return storage.getLink(uid);
    }

    /** Обновление пар uid - link в хранилище.
     */
    public void update(){
        String root = new PrefManager(context).getRootFolder();
        OutWikerNote rn = new OutWikerNote(new File(root));
        updateUID(rn);
    }

    /** Рекурсивное обновление пар uid - link
     *  1. Получает заметку
     *  2. Запрашивает uid
     *  3. Если есть -> сохраняет пару
     *  4. Вызывает себя же для всех заметок - детей.
     * @param note
     */
    private void updateUID(OutWikerNote note){
        String uid =  note.getSettings().getParam("General", "uid");
        if (uid != null) {
            String link = note.getFolder().getAbsolutePath();
            set(uid, link);
        }

        List<File> chldrn = note.getChildren();
        OutWikerNote n;
        for (File cld : chldrn){
            n = new OutWikerNote(cld);
            updateUID(n);
        }
    }


}
