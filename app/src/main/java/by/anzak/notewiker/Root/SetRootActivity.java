package by.anzak.notewiker.Root;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.io.File;

import by.anzak.notewiker.Constants;
import by.anzak.notewiker.Note.Folder;
import by.anzak.notewiker.Note.Note;
import by.anzak.notewiker.PrefManager;
import by.anzak.notewiker.R;
import by.anzak.notewiker.TreeView.Tree;
import by.anzak.notewiker.TreeView.TreeView;

public class SetRootActivity extends ActionBarActivity {

    private Tree tree;
    private LinearLayout treeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_root);
        treeLayout = (LinearLayout) findViewById(R.id.treeLayout);

        // загрузка сохраненной модели дерева, если она была, или создание новой
        if (savedInstanceState != null){
            tree = (Tree) savedInstanceState.get(Constants.INTENT_DIRECTORY);
        } else {
            Note note = new Folder(new File("/"));
            tree = new Tree(note);
        }

        TreeView treeViewFactory = new TreeView(getBaseContext(), getLayoutInflater());
        treeViewFactory.setModel(tree);
        ChangeNoteListener cnl = new ChangeNoteListener();
        treeViewFactory.setChangeNoteListener(cnl);
        treeViewFactory.setShowNoteListener(cnl);
        View treeView = treeViewFactory.createView();
        treeLayout.addView(treeView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_root, menu);
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

    public void saveRoot(View v){
        PrefManager pm = new PrefManager(getBaseContext());
        pm.setRootFolder(tree.getCurrent().getFolder());
        setResult(RESULT_OK);
        finish();
    }

    public void finishActivity(View v){
        setResult(RESULT_CANCELED);
        finish();
    }

    private class ChangeNoteListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // изменение модели так же вызывает изменение вью
            tree.setCurrent((Note) v.getTag());
        }
    }
}
