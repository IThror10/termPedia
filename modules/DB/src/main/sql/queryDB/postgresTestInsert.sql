INSERT INTO data.users values (1);
INSERT INTO data.users values (2);

-- Terms
call data.add_term('{"Term" : "OOP", "Description" : "Object Oriented Programming"}');
call data.add_term('{"Term" : "Singleton", "Description" : "Single object pattern"}');

-- Terms Tags Connection
call data.add_tag_term('{"Term" : "OOP", "Tag" : "IT"}');
call data.add_tag_term('{"Term" : "OOP", "Tag" : "Medicine"}');
call data.add_tag_term('{"Term" : "OOP", "Tag" : "Pattern"}');
call data.add_tag_term('{"Term" : "Singleton", "Tag" : "Pattern"}');
call data.add_tag_term('{"Term" : "Singleton", "Tag" : "Anti Pattern"}');

-- Terms Lit Connection
call data.add_lit_term('{"Term" : "Singleton", "Book" : {"Name" : "Go4", "Type" : "book", "Year" : 2010, "Authors" : ["ABCD", "DEF"]}}');
call data.add_lit_term('{"Term" : "OOP", "Book" : {"Name" : "Go4", "Type" : "book", "Year" : 2010, "Authors" : ["ABCD", "DEF"]}}');
call data.add_lit_term('{"Term" : "OOP", "Book" : {"Name" : "White Fang", "Type" : "Novel", "Year" : 1906, "Authors" : ["London J."]}}');

-- Terms Tags Rates
INSERT INTO data.term_tag_rates values(1, 'OOP', 'IT', 5);
INSERT INTO data.term_tag_rates values(1, 'OOP', 'Pattern', 4);
INSERT INTO data.term_tag_rates values(1, 'Singleton', 'Pattern', 4);
INSERT INTO data.term_tag_rates values(1, 'Singleton', 'Anti Pattern', 5);
INSERT INTO data.term_tag_rates values(2, 'OOP', 'Medicine', 1);
INSERT INTO data.term_tag_rates values(2, 'OOP', 'IT', 3);

call data.rate_lit_term('{"Term" : "OOP", "Book" : {"Name" : "Go4", "Type" : "book", "Year" : 2010, "Authors" : ["ABCD", "DEF"]}, "Mark" : 4}', 1);
call data.rate_lit_term('{"Term" : "OOP", "Book" : {"Name" : "White Fang", "Type" : "Novel", "Year" : 1906, "Authors" : ["London J."]}, "Mark" : 1}', 1);
call data.rate_lit_term('{"Term" : "OOP", "Book" : {"Name" : "Go4", "Type" : "book", "Year" : 2010, "Authors" : ["ABCD", "DEF"]}, "Mark" : 4}', 2);
call data.rate_lit_term('{"Term" : "Singleton", "Book" : {"Name" : "Go4", "Type" : "book", "Year" : 2010, "Authors" : ["ABCD", "DEF"]}, "Mark" : 3}', 2);