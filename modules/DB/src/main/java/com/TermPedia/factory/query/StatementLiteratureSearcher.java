package com.TermPedia.factory.query;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.exceptions.NotFoundException;
import com.TermPedia.dto.literature.*;
import com.TermPedia.dto.users.UserRating;
import com.TermPedia.factory.adapters.ISearchAdapter;
import com.TermPedia.factory.query.common.BooksRequests;
import com.TermPedia.queries.lit.*;
import com.TermPedia.queries.results.lit.*;
import com.TermPedia.queries.results.user.UserRatingResult;
import com.TermPedia.queries.user.UserTermLitRatingQuery;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class StatementLiteratureSearcher implements LiteratureSearcher {
    private final ISearchAdapter searcher;
    private final BooksRequests builder;
    private final static Logger logger;
    static { logger = Logger.getLogger("QueryDB"); }

    public StatementLiteratureSearcher(@NotNull ISearchAdapter searcher, @NotNull BooksRequests builder) {
        this.builder = builder;
        this.searcher = searcher;
    }
    @Override
    public LiteratureQueryResult searchByBookName(FindLitByLikeNameQuery settings) throws ActionsException {
        String query = builder.bookSearchQuery(settings);
        try {
            List<Literature> books = new ArrayList<>(settings.getSearchAmount());
            searcher.execute(query);
            while (searcher.next())
                books.add(new Literature(
                        searcher.getInt("lid"),
                        searcher.getString("name"),
                        searcher.getString("type"),
                        searcher.getInt("year"),
                        jsonArrToVector(searcher.getString("authors"))
                ));
            return new LiteratureQueryResult(books);
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        } finally {
            searcher.closeConnection();
        }
    }

    @Override
    public TagLiteratureQueryResult searchByTagName(FindLitByTagQuery settings) throws ActionsException {
        String query = builder.anySearchQuery(settings);
        try {
            List<TagLiterature> books = new ArrayList<>(settings.getSearchAmount());
            searcher.execute(query);
            while (searcher.next())
                books.add(new TagLiterature(
                        searcher.getInt("lid"),
                        searcher.getString("name"),
                        searcher.getString("type"),
                        searcher.getInt("year"),
                        jsonArrToVector(searcher.getString("authors")),
                        searcher.getDouble("rating")
                ));
            return new TagLiteratureQueryResult(books);
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        } finally {
            searcher.closeConnection();
        }
    }

    @Override
    public LiteratureQueryResult searchByAuthorName(FindLitByAuthorNameQuery settings) throws ActionsException {
        String query = builder.authorSearchQuery(settings);
        try {
            List<Literature> books = new ArrayList<>(settings.getSearchAmount());
            searcher.execute(query);
            while (searcher.next())
                books.add(new Literature(
                        searcher.getInt("lid"),
                        searcher.getString("name"),
                        searcher.getString("type"),
                        searcher.getInt("year"),
                        jsonArrToVector(searcher.getString("authors"))
                ));
            return new LiteratureQueryResult(books);
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        } finally {
            searcher.closeConnection();
        }
    }

    @Override
    public RatedLiteratureQueryResult searchByTermName(FindLitByLikeTermNameQuery settings) throws ActionsException {
        String query = builder.termSearchQuery(settings);
        try {
            List<RatedLiterature> books = new ArrayList<>(settings.getSearchAmount());
            searcher.execute(query);
            while (searcher.next())
                books.add(new RatedLiterature(
                        searcher.getInt("lid"),
                        searcher.getString("name"),
                        searcher.getString("type"),
                        searcher.getInt("year"),
                        jsonArrToVector(searcher.getString("authors")),
                        searcher.getDouble("rating"),
                        searcher.getInt("rates_amount")
                ));
            return new RatedLiteratureQueryResult(books);
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        } finally {
            searcher.closeConnection();
        }
    }


    @Override
    public UserRatingResult getUserTermLitRating(UserTermLitRatingQuery settings) throws ActionsException {
        String query = builder.userTermLitRatingQuery(settings);
        try {
            int rating = 0;
            searcher.execute(query);
            if (searcher.next()) {
                if (searcher.getInt("status") == -1)
                    throw new NotFoundException("Lit-TermId connection doesn't exist");
                else
                    rating = searcher.getInt("rating");
            }
            return new UserRatingResult(
                new UserRating(
                    settings.getTermId(),
                    settings.getLitId(),
                    rating
            ));
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        } finally {
            searcher.closeConnection();
        }
    }

    @Override
    public RatedLiteratureQueryResult searchByTermId(FindLitByTermIdQuery settings) throws ActionsException {
        String query = builder.termIdSearchQuery(settings);
        try {
            List<RatedLiterature> books = new ArrayList<>(settings.getSearchAmount());
            searcher.execute(query);
            while (searcher.next())
                books.add(new RatedLiterature(
                        searcher.getInt("lid"),
                        searcher.getString("name"),
                        searcher.getString("type"),
                        searcher.getInt("year"),
                        jsonArrToVector(searcher.getString("authors")),
                        searcher.getDouble("rating"),
                        searcher.getInt("rates_amount")
                ));
            return new RatedLiteratureQueryResult(books);
        } catch (ActionsException e) {
            throw e;
        } catch (Exception e) {
            logger.warning(e.getMessage());
            throw new ActionsException("Something went wrong. Try again later.");
        } finally {
            searcher.closeConnection();
        }
    }

    private List<String> jsonArrToVector(@NotNull String json) {
        List<String> vector = new ArrayList<>(5);

        int start_index = json.indexOf('"'), end_index = json.indexOf('"', start_index + 1);
        while (end_index != -1 && start_index != -1 && start_index < end_index) {
            vector.add(json.substring(start_index + 1, end_index));
            start_index = json.indexOf('"', end_index + 1);
            end_index = json.indexOf('"', start_index + 1);
        }
        return vector;
    }
}
