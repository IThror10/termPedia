package com.TermPedia.controllers;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.term.Term;
import com.TermPedia.commands.events.data.AddTermEvent;
import com.TermPedia.handlers.CommandHandler;
import com.TermPedia.handlers.QueryHandler;
import com.TermPedia.queries.terms.FindTermByNameQuery;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/v1/term")
public class TermController {
//    @GetMapping(produces = APPLICATION_JSON_VALUE)
//    public ResponseEntity getTerms(
//            @RequestParam(name="name") String name,
//            @RequestParam(name="search_amount", required = false, defaultValue = "10") int getAmount,
//            @RequestParam(name="skip_amount", required = false, defaultValue = "0") int skipAmount
//    ) {
//        FindTermByNameQuery query = new FindTermByNameQuery(name, getAmount, skipAmount);
//        QueryHandler handler = new QueryHandler();
//        try {
//            handler.handle(query);
//            List<Map<String, String>> terms = new ArrayList<>(getAmount);
//            for (Term term: query.getResult().getTerms())
//                terms.add(term.toMap());
//            return ResponseEntity.ok(terms);
//        } catch (ActionsException e) {
////            ResponseEntity.created("/user")
//            return null;
//        }
//    }

//    fetch('/term', { method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify({name: 'New Term', description: 'Some kind of description', UserID: 0})}).then(console.log)
//    @PostMapping
//    public String addTerm(@RequestBody Map<String, String> message) {
//        AddTermEvent event = new AddTermEvent(
//                message.get("name"),
//                message.get("description"),
//                Integer.parseInt(message.get("UserID"))
//        );
//        CommandHandler handler = new CommandHandler();
//        try {
//            handler.handle(event);
//            return event.getData().toString();
//        } catch (ActionsException e) {
//            return HttpStatus.UNAUTHORIZED.toString();
//        }
//
//    }

    @GetMapping("/{TermId}/tag")
    public ResponseEntity getTagByTerm(
            @PathVariable Integer TermId,
            @RequestAttribute(value = "uid", required = false) Integer userId
    ) {
        return ResponseEntity.ok(userId == null ? "null" : userId.toString());
    }
}
