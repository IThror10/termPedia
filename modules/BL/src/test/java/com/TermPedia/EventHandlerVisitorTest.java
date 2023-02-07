package com.TermPedia;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.Book;
import com.TermPedia.dto.Term;
import com.TermPedia.events.result.EventStatus;
import com.TermPedia.events.data.*;
import com.TermPedia.events.user.AuthorizeEvent;
import com.TermPedia.events.user.RegisterEvent;
import com.TermPedia.dto.users.User;
import com.TermPedia.events.visitors.EventHandlerVisitor;
import com.TermPedia.factory.command.CommandFactory;
import com.TermPedia.factory.command.EventHandler;
import com.TermPedia.factory.command.ReqAuthHandler;
import com.TermPedia.factory.query.QueryFactory;
import com.TermPedia.factory.query.TermsSearcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import org.mockito.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class EventHandlerVisitorTest {
    @Mock
    private QueryFactory queryFactory;
    @Mock
    private CommandFactory commandFactory;

    MockedStatic<QueryFactory> queryMocked;
    MockedStatic<CommandFactory> commandMocked;
    EventHandlerVisitorTest() {
        try {
            queryMocked = Mockito.mockStatic(QueryFactory.class);
            queryMocked.when(QueryFactory::instance).thenReturn(Mockito.mock(QueryFactory.class));
        } catch (Exception e) {}
        try {
            commandMocked = Mockito.mockStatic(CommandFactory.class);
            commandMocked.when((CommandFactory::instance)).thenReturn(Mockito.mock(CommandFactory.class));
        } catch (Exception e) {}

        queryFactory = QueryFactory.instance();
        commandFactory = CommandFactory.instance();
    }
    @Test
    void visitRegisterEvent() throws Exception {
        //Arrange
        EventHandlerVisitor visitor = new EventHandlerVisitor();
        RegisterEvent newUser = new RegisterEvent("login", "password", "email");
        RegisterEvent userExists = new RegisterEvent("admin", "password", "email");

        //Mock
        ReqAuthHandler handler = Mockito.mock(ReqAuthHandler.class);
        when(commandFactory.createReqAuthHandler()).thenReturn(handler);
        when(handler.register(newUser)).thenReturn(CommandTestObjectsFactory.getAuthEventResult());
        when(handler.register(userExists)).thenThrow(ActionsException.class);

        //Act
        Executable registerExistingUser = () -> visitor.visitRegisterEvent(userExists);
        visitor.visitRegisterEvent(newUser);

        //Assert
        assertEquals(2, newUser.getResult().getUserData().userID());
        assertThrows(ActionsException.class, registerExistingUser);
    }

    @Test
    void visitAuthorizeEvent() throws Exception {
        //Arrange
        User user = new User("admin", 0);
        EventHandlerVisitor visitor = new EventHandlerVisitor();

        AuthorizeEvent userExists = new AuthorizeEvent("login", "password");
        AuthorizeEvent unknownUser = new AuthorizeEvent("unknown", "login");

        //Mock
        ReqAuthHandler handler = Mockito.mock(ReqAuthHandler.class);
        when(commandFactory.createReqAuthHandler()).thenReturn(handler);
        when(handler.authorize(userExists)).thenReturn(CommandTestObjectsFactory.getAuthEventResult());
        when(handler.authorize(unknownUser)).thenThrow(ActionsException.class);

        //Act
        Executable wrongLoginData = () -> visitor.visitAuthorizeEvent(unknownUser);
        visitor.visitAuthorizeEvent(userExists);

        //Assert
        assertEquals(2, userExists.getResult().getUserData().userID());
        assertThrows(ActionsException.class, wrongLoginData);
    }

    @Test
    void visitAddTermEvent() throws Exception {
        //Arrange
        EventHandlerVisitor visitor = new EventHandlerVisitor();
        AddTermEvent termExists = new AddTermEvent("OOP", "Object Oriented", 0);
        AddTermEvent newTerm = new AddTermEvent("Triangle", "Geometry Figure", 0);

        //Mock
        EventHandler handler = Mockito.mock(EventHandler.class);
        when(commandFactory.createEventHandler()).thenReturn(handler);
        when(handler.accept(any(DataEvent.class))).thenReturn(new EventStatus(true));

        TermsSearcher searcher = Mockito.mock(TermsSearcher.class);
        when(queryFactory.createTermSearcher()).thenReturn(searcher);
        when(searcher.termExists(any(Term.class))).thenReturn(false).thenReturn(true);

        //Act
        Executable addTermException = () -> visitor.visitAddTermEvent(termExists);
        visitor.visitAddTermEvent(newTerm);

        //Assert
        assertTrue(newTerm.getResult().getStatus());
        assertThrows(ActionsException.class, addTermException);
    }

    @Test
    void visitAddLitToTermEvent() throws Exception {
        //Arrange
        Book book = QueryTestObjectsFactory.emptyBook();
        EventHandlerVisitor visitor = new EventHandlerVisitor();
        AddLitToTermEvent event = new AddLitToTermEvent("OOP", book, 0);

        //Mock
        EventHandler handler = Mockito.mock(EventHandler.class);
        when(commandFactory.createEventHandler()).thenReturn(handler);
        when(handler.accept(any(DataEvent.class))).thenReturn(new EventStatus(true));

        //Act
        visitor.visitAddLitToTermEvent(event);

        //Assert
        assertTrue(event.getResult().getStatus());
    }

    @Test
    void visitAddTagToTermEvent() throws Exception {
        //Arrange
        EventHandlerVisitor visitor = new EventHandlerVisitor();
        AddTagToTermEvent event = new AddTagToTermEvent("OOP", "IT", 0);

        //Mock
        EventHandler handler = Mockito.mock(EventHandler.class);
        when(commandFactory.createEventHandler()).thenReturn(handler);
        when(handler.accept(any(DataEvent.class))).thenReturn(new EventStatus(true));

        //Act
        visitor.visitAddTagToTermEvent(event);

        //Assert
        assertTrue(event.getResult().getStatus());
    }

    @Test
    void visitChangeTermLitRatingEvent() throws Exception {
        //Arrange
        Book book = QueryTestObjectsFactory.emptyBook();
        EventHandlerVisitor visitor = new EventHandlerVisitor();
        ChangeTermLitRatingEvent event = new ChangeTermLitRatingEvent("OOP", book, 5, 0);

        //Mock
        EventHandler handler = Mockito.mock(EventHandler.class);
        when(commandFactory.createEventHandler()).thenReturn(handler);
        when(handler.accept(any(DataEvent.class))).thenReturn(new EventStatus(true));

        //Act
        visitor.visitChangeTermLitRatingEvent(event);

        //Assert
        assertTrue(event.getResult().getStatus());
    }

    @Test
    void visitChangeTermTagRatingEvent() throws Exception {
        //Arrange
        EventHandlerVisitor visitor = new EventHandlerVisitor();
        ChangeTermTagRatingEvent event = new ChangeTermTagRatingEvent("OOP", "IT", 5, 0);

        //Mock
        EventHandler handler = Mockito.mock(EventHandler.class);
        when(commandFactory.createEventHandler()).thenReturn(handler);
        when(handler.accept(any(DataEvent.class))).thenReturn(new EventStatus(true));

        //Act
        visitor.visitChangeTermTagRatingEvent(event);

        //Assert
        assertTrue(event.getResult().getStatus());
    }
}