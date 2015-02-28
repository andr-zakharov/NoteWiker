package by.anzak.notewiker.ContentReader;

import java.io.IOException;

import by.anzak.notewiker.Note.OutWiker.OutWikerNote;
import by.anzak.notewiker.Note.OutWiker.ParamManager;

/**
 * Created by Andrey on 30.01.2015.
 */
public class NoteSaver {

    ParamManager options;
    private String groupGeneral = "General";
    private String type = "type";

    public boolean saveNote(OutWikerNote note) throws IOException {

        /* сохранение содержимого (контента) */

        /* сохранение изображения*/


        /* сохранение файла настроек */
        if (options.isUpdated()) {
            options.setParam(groupGeneral, type, note.getType().toString());
            options.saveParams();
        }


        return true;
    }

}
