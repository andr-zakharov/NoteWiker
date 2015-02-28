package by.anzak.notewiker.Note;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import by.anzak.notewiker.Note.OutWiker.OutWikerNote;

/**
 * Created by Andrey on 27.01.2015.
 * Класс хранит список заметок, отображаемых в виде дерева заметок на экране.
 * Родительские заметки - сверху вниз по дереву
 * Текущая заметка - это текущая заметка
 * Дети - наследники текущей заметки
 * При смене текущей заметки класс не пересоздается, а меняет свое содержимое.
 */
public class Tree implements Serializable{


    private static final long serialVersionUID = 8957602872479065712L;

    private List<Note> parents;

    public Tree(Note note) {
       parents = new ArrayList<>();
        parents.add(note);
    }

    /**
     * @param noteTree - коллекция заметок по иерархии сверху до текущей
     */
    public Tree(List<Note> noteTree){
        this.parents = noteTree;
    }

    public List<Note> getParents(){
        return parents.subList(0, (parents.size() == 0) ? 0 : parents.size()-1);
    }

    public boolean setCurrent(Note current) {
        int cur = parents.indexOf(current);
        if (cur == parents.size()-1) {
            return false;
        }
        if (cur < 0) {
            parents.add(current);
        } else {
            parents = parents.subList(0, cur + 1);
        }
        return true;
    }

    public Note getCurrent() {
        return parents.get(parents.size() - 1);
    }

    public List<Note> getChildren(){
        List<File> lf = getCurrent().getChildren();
        List<Note> ln = new ArrayList<>();
        for (File f : lf){
            ln.add(new OutWikerNote(f));
        }
        return ln;
    }

}
