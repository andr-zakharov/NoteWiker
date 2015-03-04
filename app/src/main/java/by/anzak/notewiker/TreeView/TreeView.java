package by.anzak.notewiker.TreeView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Observable;
import java.util.Observer;

import by.anzak.notewiker.Note.Note;
import by.anzak.notewiker.Note.NoteHeaderViewFactory;

/**
 * Created by Andrey on 01.03.2015.
 */
public class TreeView implements Observer{

    private LinearLayout treeLayout;
    private LayoutInflater layoutInflater;
    private Context context;
    private View.OnClickListener changeNoteListener, showNoteListener;
    private Tree tree;

    public TreeView(Context context, LayoutInflater layoutInflater) {
        this.context = context;
        this.layoutInflater = layoutInflater;
    }

    public void setModel(Tree tree){
        this.tree = tree;
        tree.addObserver(this);
    }

    public void setChangeNoteListener(View.OnClickListener changeNoteListener){
        this.changeNoteListener = changeNoteListener;
    }

    public void setShowNoteListener(View.OnClickListener showNoteListener){
        this.showNoteListener = showNoteListener;
    }

    public View createView() {

        treeLayout = new LinearLayout(context);
        treeLayout.setOrientation(LinearLayout.VERTICAL);
        updateView();

        return treeLayout;
    }

    public void updateView() {

        treeLayout.removeAllViews();
        NoteHeaderViewFactory viewCreator = new NoteHeaderViewFactory(layoutInflater, null);
        View v;
        int level = 0;

        for (Note nte : tree.getParents()) {
            v = viewCreator.createNoteView(nte, level++, changeNoteListener, showNoteListener);
            treeLayout.addView(v);
        }

        treeLayout.addView(viewCreator.createCurrentNoteView(tree.getCurrent(), level++, changeNoteListener, showNoteListener));

        for (Note nte : tree.getChildren()) {
            v = viewCreator.createNoteView(nte, level, changeNoteListener, showNoteListener);
            treeLayout.addView(v);
        }

    }


    @Override
    public void update(Observable observable, Object o) {
        updateView();
    }
}
