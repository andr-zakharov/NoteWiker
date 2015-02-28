package by.anzak.notewiker;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.io.File;

import by.anzak.notewiker.Note.Note;
import by.anzak.notewiker.Note.NoteViewFactory;
import by.anzak.notewiker.Note.OutWiker.OutWikerNote;
import by.anzak.notewiker.Note.Tree;


public class MainActivity extends ActionBarActivity {

    private Tree tree;
    private LayoutInflater layoutInflater;
    private View.OnClickListener changeNoteListener;
    private View.OnClickListener showNoteListener;
    private LinearLayout treeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* инициализация */
        layoutInflater = getLayoutInflater();
        setContentView(R.layout.activity_main);
        treeLayout = (LinearLayout) findViewById(R.id.treeLayout);
        PrefManager prefManager = new PrefManager(this);

        changeNoteListener = new ChangeNoteListener();
        showNoteListener = new ShowNoteListener();

        /* загрузка сохраненного дерева, если оно было, или создание нового */
        if (savedInstanceState != null){
            tree = (Tree) savedInstanceState.get("TREE");
        } else {
            Note note = new OutWikerNote(new File(prefManager.getRootFolder()));
            tree = new Tree(note);
        }

        makeTreeViews(tree);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* Отрисовка заметок в виде дерева (UI) */
    private void makeTreeViews(Tree tree){
        NoteViewFactory viewCreator = new NoteViewFactory(layoutInflater, null);
        View v;
        int level = 0;

        for (Note nte : tree.getParents()){
            v = viewCreator.getNoteView(nte, level++, changeNoteListener, showNoteListener);
            treeLayout.addView(v);
        }

        treeLayout.addView(viewCreator.getCurrentNoteView(tree.getCurrent(), level++, changeNoteListener, showNoteListener));
//        viewCreator.getCurrentNoteView(tree.getCurrent(), level++, changeNoteListener, showNoteListener);

        for (Note nte : tree.getChildren()){
            v = viewCreator.getNoteView(nte, level, changeNoteListener, showNoteListener);
            treeLayout.addView(v);
        }
    }


    private class ShowNoteListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

        }
    }

    private class ChangeNoteListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (tree.setCurrent((Note) v.getTag())) { // если текущая заметка поменялась
                treeLayout.removeAllViews(); // перерисовка GUI дерева
                makeTreeViews(tree);
            }
        }
    }


}
