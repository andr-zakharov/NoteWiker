package by.anzak.notewiker.TreeView;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import by.anzak.notewiker.Note.Note;
import by.anzak.notewiker.Note.OutWiker.OutWikerNote;

/**
 * Created by Andrey on 27.01.2015.
 * Класс хранит список заметок, отображаемых в виде дерева заметок на экране.
 * Родительские заметки - сверху вниз по дереву
 * Текущая заметка - это текущая заметка
 * Дети - наследники текущей заметки
 * При смене текущей заметки класс не пересоздается, а меняет свое содержимое.
 */
public class Tree extends Observable implements Parcelable {

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
        setChanged();
        notifyObservers();
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
        Collections.sort(ln);
        return ln;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(parents);
    }

    public static final Creator<Tree> CREATOR = new Creator<Tree>() {
        @Override
        public Tree createFromParcel(Parcel parcel) {
            return new Tree(parcel);
        }

        @Override
        public Tree[] newArray(int i) {
            return new Tree[0];
        }
    };

    private Tree(Parcel parcel){
        parents = (List<Note>) parcel.readSerializable();
    }
}
