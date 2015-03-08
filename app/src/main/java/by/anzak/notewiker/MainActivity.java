package by.anzak.notewiker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.io.File;

import by.anzak.notewiker.Note.Note;
import by.anzak.notewiker.Note.OutWiker.OutWikerNote;
import by.anzak.notewiker.TreeView.Tree;
import by.anzak.notewiker.TreeView.TreeView;


public class MainActivity extends ActionBarActivity {

    private Tree tree;
    boolean mDualPane;

    private LinearLayout treeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* инициализация */
        setContentView(R.layout.activity_main);
        treeLayout = (LinearLayout) findViewById(R.id.treeLayout);
        PrefManager prefManager = new PrefManager(this);

        // загрузка сохраненной модели дерева, если она была, или создание новой
        if (savedInstanceState != null){
            tree = (Tree) savedInstanceState.get(Constants.INTENT_DIRECTORY);
        } else {
            Note note = new OutWikerNote(new File(prefManager.getRootFolder()));
            tree = new Tree(note);
        }

        mDualPane = (findViewById(R.id.content) != null);

        // создание вью дерева и добавление его на экран
        TreeView treeViewFactory = new TreeView(getBaseContext(), getLayoutInflater());
        treeViewFactory.setModel(tree);
        treeViewFactory.setChangeNoteListener(new ChangeNoteListener());
        treeViewFactory.setShowNoteListener(new ShowNoteListener());
        View treeView = treeViewFactory.createView();
        treeLayout.addView(treeView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Constants.INTENT_DIRECTORY, tree);
        super.onSaveInstanceState(outState);
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

    public void showNote(Note note){

        Intent intent = new Intent(this, ReaderActivity.class);
        intent.putExtra(Constants.INTENT_DIRECTORY, note);
        startActivityForResult(intent, Constants.REQUEST_CODE_NOTE_ACTIVITY);

    }

    private class ShowNoteListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
//            View view = NoteViewFactory.getInstance().createNoteView(getBaseContext(), (Note) v.getTag());
//            if (view != null) treeLayout.addView(view);
            showNote((Note) v.getTag());

        }
    }

    private class ChangeNoteListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // изменение модели так же вызывает изменение вью
            tree.setCurrent((Note) v.getTag());
        }
    }


}
