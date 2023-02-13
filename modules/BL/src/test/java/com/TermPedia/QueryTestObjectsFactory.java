package com.TermPedia;

import com.TermPedia.dto.literature.Literature;
import com.TermPedia.dto.exceptions.ActionsException;

import java.util.Arrays;
import java.util.Vector;

public class QueryTestObjectsFactory {
    public Literature createAlbum(String[] authors) throws ActionsException {
        Vector<String> _authors = new Vector<>(Arrays.stream(authors).toList());
        return new Literature("Kamnem po golove", "Album", 1996, _authors);
    }
}
