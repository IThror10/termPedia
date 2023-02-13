package com.TermPedia;

import com.TermPedia.dto.literature.Literature;
import com.TermPedia.dto.JsonBuilder;
import com.TermPedia.dto.exceptions.FormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class JsonBuilderTest {
    private final QueryTestObjectsFactory factory;
    public JsonBuilderTest() {
        factory = new QueryTestObjectsFactory();
    }
    @Test
    void addInt() {
        JsonBuilder builder1 = new JsonBuilder();
        JsonBuilder builder2 = new JsonBuilder();

        String singleIntRes = builder1.addInt("AAA", 1).getData();
        String twoIntsRes = builder2.addInt("AAA", 1).addInt("BBB", -2).getData();

        assertEquals("{\"AAA\" : 1}", singleIntRes);
        assertEquals("{\"AAA\" : 1, \"BBB\" : -2}", twoIntsRes);
    }

    @Test
    void addStr() throws Exception {
        JsonBuilder builder1 = new JsonBuilder();
        JsonBuilder builder2 = new JsonBuilder();

        String singleStringRes = builder1.addStr("AAA", "aaa").getData();
        String multiStringsRes = builder2.addStr("AAA", "aaa").addStr("BBB", "bbb").addStr("CCC", null).getData();

        assertEquals("{\"AAA\" : \"aaa\"}", singleStringRes);
        assertEquals("{\"AAA\" : \"aaa\", \"BBB\" : \"bbb\", \"CCC\" : null}", multiStringsRes);
        assertThrows(FormatException.class, () -> builder1.addStr("DDD", "d\"dd"));
        assertThrows(FormatException.class, () -> builder1.addStr("EEE", "e\\ee"));
    }

    @Test
    void addBook() throws Exception {
        JsonBuilder builder1 = new JsonBuilder();
        JsonBuilder builder2 = new JsonBuilder();
        JsonBuilder builder3 = new JsonBuilder();

        String meta = "\"Name\" : \"Kamnem po golove\", \"Type\" : \"Album\", \"Year\" : 1996";
        String add1 = "\"Authors\" : [%s]".formatted("\"Mihail G.\"");
        String add2 = "\"Authors\" : [%s]".formatted("\"Mihail G.\", \"Andrew K.\"");
        String add3 = "\"Authors\" : []";

        String[] singleAuthor = {"Mihail G."}, twoAuthors = {"Mihail G.", "Andrew K."},
                noAuthors = {}, wrongAuthors = {"DD\"DD"};
        Literature singleAuthorAlbum = factory.createAlbum(singleAuthor);
        Literature twoAuthorsAlbum = factory.createAlbum(twoAuthors);
        Literature noAuthorsAlbum = factory.createAlbum(noAuthors);
        Literature wrongAuthorsAlbum = factory.createAlbum(wrongAuthors);


        String noAuthorsRes = builder1.addLiterature(noAuthorsAlbum).getData();
        String singleAuthorRes = builder2.addLiterature(singleAuthorAlbum).getData();
        String twoAuthorsRes = builder3.addLiterature(twoAuthorsAlbum).getData();


        assertEquals("{\"Book\" : {%s, %s}}".formatted(meta, add1), singleAuthorRes);
        assertEquals("{\"Book\" : {%s, %s}}".formatted(meta, add2), twoAuthorsRes);
        assertEquals("{\"Book\" : {%s, %s}}".formatted(meta, add3), noAuthorsRes);
        assertThrows(FormatException.class, () -> builder1.addLiterature(wrongAuthorsAlbum));
    }
}