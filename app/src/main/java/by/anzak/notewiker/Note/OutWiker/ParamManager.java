package by.anzak.notewiker.Note.OutWiker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Класс параметров заметки OutWiker.
 */
public class ParamManager {

    private File folder;
    private boolean updated = false;

    private static final Pattern groupPattern = Pattern.compile("\\[*\\]");
    private static final Pattern parametrPattern = Pattern.compile(".=.");
    private static final Pattern spacePattern = Pattern.compile(" ");

    private List<Group> groups = new ArrayList<>();

    /**
     * @param folder - папка заметки
     */
    public ParamManager(File folder) {
        this.folder = folder;
        try {
            readParams(folder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Считывает параметры из файла */
    private void readParams(File folder) throws IOException {
        groups = new ArrayList<>();
        File pageopt = new File(folder, "__page.opt");
        if (!pageopt.isFile() || !pageopt.canRead()) return;

        String line;
        String value;
        String curGroup = null;
        BufferedReader breader = null;

        try {
            breader = new BufferedReader(new FileReader(pageopt));

            while ((line = breader.readLine()) != null) {

                // Если строка задает группу параметров ( типа [groupName] ), то переключение текущей группы.
                if (groupPattern.matcher(line).find()) {
                    String groupName = line.substring(1, line.length() - 1);
                    curGroup = groupName;
                    continue;
                }

                // Если строка задает параметр (типа key = value), то сохранение параметра
                if (parametrPattern.matcher(line).find()) {
                    String[] keyValue = spacePattern.matcher(line).replaceAll("").split("=", 0);
                    if (keyValue.length > 0) {
                        value = (keyValue.length > 1) ? keyValue[1] : "";
                        setParam(curGroup, keyValue[0], value);
                    }
                }
            }

        } finally {
            if (breader != null) breader.close();
        }
    }

    /* Возвращает значение параметра */
    public String getParam(String group, String param) {
        if (group == null || group.isEmpty()) return null;
        if (param == null || param.isEmpty()) return null;

        Group g = getGroup(group);
        if (g == null) return null;

        Param p = g.getParam(param);
        if (p == null) return null;

        String value = p.value;
        if (value == null) return "";
        return value;
    }

    /* Устанавливает новое значение параметра (или сохраняет новый параметр) */
    public void setParam(String group, String param, String value) {
        if (group == null || group.isEmpty()) return;
        if (param == null || param.isEmpty()) return;
        if (value == null) return;

        Group g = getGroup(group);
        g.setParam(param, value);

        updated = true;
    }

    /* Сохраняет значение параметров в файл */
    public void saveParams() throws IOException {
        if (!updated) return;
        File pageopt = new File(folder, "__page.opt");
        FileWriter writer = null;
        try {
            writer = new FileWriter(pageopt);

            for (Group g : groups){
                writer.write("["+g.key+"]\n");

                for (Param p : g.params){
                    writer.write(p.key + " = " + p.value+"\n");
                }

                writer.write("\n");
            }

            writer.flush();
        } finally {
            if (writer != null) writer.close();
        }
        updated = false;
    }

    public boolean isUpdated(){
        return updated;
    }

    private Group getGroup(String key){
        if (key == null) return null;
        for (Group g : groups){
            if (key.equals(g.key)) return g;
        }
        Group g = new Group();
        groups.add(g);
        return g;
    }

    // группа параметров
    class Group{
        String key;
        List<Param> params = new ArrayList<>();

        private Param getParam(String key){
            if (key == null) return null;
            for (Param p : params){
                if (key.equals(p.key)) return p;
            }
            return null;
        }

        private void setParam(String key, String value){
            Param p = getParam(key);
            if (p == null) {
                params.add(new Param(key, value));
            } else {
                p.value = value;
            }
        }

    }

    // параметры одной группы
    class Param{
        String key;
        String value;

        public Param(String key, String value){
            this.key = key;
            this.value = value;
        }

    }

}
