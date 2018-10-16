
package com.example.gabrielgeoffroy.lecteurrss;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LectureEcriture {
    /**
     * Écrit la liste de chaines dans un fichier dans le storage local
     *
     * @param NomFichier Le nom du fichier
     * @param context Le contexte de l'application
     * @param c La liste de chaines à sauvegarder
     * @throws IOException
     * @throws ClassNotFoundException
     *
     * @author David Poissant-Samson
     */
    public void Ecriture(String NomFichier, Context context,  List<Chaine> c) throws IOException, ClassNotFoundException {

        FileOutputStream fos = context.openFileOutput( NomFichier, Context.MODE_PRIVATE );
        ObjectOutputStream od = new ObjectOutputStream( fos );
        od.writeObject( c);
        od.close();
    }

    /**
     * Lit le fichier dans le storage local et retourne la liste de chaines
     * @param NomFichier Le nom du fichier
     * @param context Le contexte de l'application
     * @return Liste de chaines
     * @throws IOException
     * @throws ClassNotFoundException
     *
     * @author David Poissant-Samson
     */
    public List<Chaine> Lecture(String NomFichier, Context context) throws IOException, ClassNotFoundException {
        List<Chaine> Ch;
        FileInputStream fis = context.openFileInput( NomFichier );
        ObjectInputStream oi = new ObjectInputStream( fis );
        Ch =  (List<Chaine>)oi.readObject();
        oi.close();
        return  Ch;
    }
}