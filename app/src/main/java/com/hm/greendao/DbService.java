package com.hm.greendao;

import android.content.Context;
import android.util.Log;

import com.hm.bean.DaoSession;
import com.hm.bean.Note;
import com.hm.bean.NoteDao;

import java.util.List;

/**
 * Created by Administrator on 2016/4/11.
 */
public class DbService {

    private static final String TAG = DbService.class.getSimpleName();
    private static DbService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private NoteDao noteDao;
    private DbService() {
    }
    public static DbService getInstance(Context context) {
        if (instance == null) {
            instance = new DbService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = MyApplication.getDaoSession(context);
            instance.noteDao = instance.mDaoSession.getNoteDao();
        }
        return instance;
    }
    public Note loadNote(long id) {
        return noteDao.load(id);
    }

    public List<Note> loadAllNote(){
        return noteDao.loadAll();
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<Note> queryNote(String where, String... params){
        return noteDao.queryRaw(where, params);
    }


    /**
     * insert or update note
     * @param note
     * @return insert or update note id
     */
    public long saveNote(Note note){
        return noteDao.insertOrReplace(note);
    }


    /**
     * insert or update noteList use transaction
     * @param list
     */
    public void saveNoteLists(final List<Note> list){
        if(list == null || list.isEmpty()){
            return;
        }
        noteDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    Note note = list.get(i);
                    noteDao.insertOrReplace(note);
                }
            }
        });

    }

    /**
     * delete all note
     */
    public void deleteAllNote(){
        noteDao.deleteAll();
    }

    /**
     * delete note by id
     * @param id
     */
    public void deleteNote(long id){
        noteDao.deleteByKey(id);
        Log.i(TAG, "delete");
    }

    public void deleteNote(Note note){
        noteDao.delete(note);
    }

}
