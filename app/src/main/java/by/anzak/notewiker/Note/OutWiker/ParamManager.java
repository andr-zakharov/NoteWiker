package by.anzak.notewiker.Note.OutWiker;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Класс параметров заметки OutWiker
 */
public class ParamManager {

    private Map<String, Map<String, String>> groups;
    private File folder;
    private boolean updated = false;

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
        groups = new HashMap<>();
        Map currentGroup = new HashMap();
        String line;

        Pattern groupPattern = Pattern.compile("\\[*\\]");
        Pattern parametrPattern = Pattern.compile(".=.");
        Pattern spacePattern = Pattern.compile(" ");

        File pageopt = new File(folder, "__page.opt");
        BufferedReader breader = null;

        try {
            breader = new BufferedReader(new FileReader(pageopt));

            while ((line = breader.readLine()) != null) {

                // Если строка задает группу параметров ( типа [groupName] ), то переключение текущей группы.
                if (groupPattern.matcher(line).find()) {
                    String groupName = line.substring(1, line.length() - 1);
                    currentGroup = new HashMap<String, String>();
                    groups.put(groupName, currentGroup);
                    continue;
                }

                // Если строка задает параметр (типа key = value), то сохранение параметра
                if (parametrPattern.matcher(line).find()) {
                    String[] keyValue = spacePattern.matcher(line).replaceAll("").split("=", 0);
                    currentGroup.put(keyValue[0], keyValue[1]);
                }
            }

        } finally {
            if (breader != null) breader.close();
        }
    }

    /* Возвращает значение параметра */
    @Nullable
    public String getParam(String group, String param) {
        Map<String, String> groupMap = groups.get(group);
        if (groupMap == null) {
            return null;
//            throw new IllegalArgumentException("Group not found");
        }
        return groupMap.get(param);
    }

    /* Устанавливает новое значение параметра (или сохраняет новый параметр) */
    public void setParam(String group, String param, String value) {
        Map<String, String> groupMap = groups.get(group);
        // Создание группы параметров, если её еще нету
        if (groupMap == null) {
            groupMap = new HashMap<>();
            groups.put(group, groupMap);
        }
        // Запись параметра и его значения
        groupMap.put(param, value);
        updated = true;
    }

    /* Сохраняет значение параметров в файл */
    public void saveParams() throws IOException {
        File pageopt = new File(folder, "__page.opt");
        FileWriter writer = null;
        try {
            writer = new FileWriter(pageopt);

            for (Map.Entry<String, Map<String, String>> group: groups.entrySet()){
                writer.write("["+group.getKey()+"]\n");

                for (Map.Entry<String, String> parametr : group.getValue().entrySet()){
                    writer.write(parametr.getKey() + " = " + parametr.getValue()+"\n");
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

}
