package by.anzak.notewiker.Note.OutWiker;

import java.io.File;
import java.io.FileFilter;

/** Фильтр директорий для структуры OutWiker
 * Отсеивает системные папки android, а также служебные папки OutWiker.
 */
class DirsFilter implements FileFilter {

    @Override
    public boolean accept(File pathname) {

        try {
            String name = pathname.getName();
            if (
                    pathname.isDirectory() &&
                            // pathname.canWrite() &&
                            !name.equals("lost+found") &&
                            !name.equals("LOST.DIR") &&
                            !name.startsWith("__") &&
                            !name.startsWith(".")
                    ) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
