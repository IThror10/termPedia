-- Users
call data.add_user(0);

-- Terms
call data.add_term('{"Term" : "OOP", "Description" : "Object Oriented Programming"}');
call data.add_term('{"Term" : "Singleton", "Description" : "Single object pattern"}');

-- Lit
call data.add_lit('{"Book" : {"Name" : "Go4", "Type" : "book", "Year" : 2010, "Authors" : ["ABCD", "DEF"]}}');
call data.add_lit('{"Book" : {"Name" : "White Fang", "Type" : "Novel", "Year" : 1906, "Authors" : ["London J."]}}');

---- Terms Tags Connection
--call data.add_tag_term('{"TID" : 1, "Tag" : "IT"}');
--call data.add_tag_term('{"TID" : 1, "Tag" : "Medicine"}');
--call data.add_tag_term('{"TID" : 1, "Tag" : "Pattern"}');
--call data.add_tag_term('{"TID" : 2, "Tag" : "Pattern"}');
--call data.add_tag_term('{"TID" : 2, "Tag" : "Anti Pattern"}');
--
---- Terms Lit Connection
--call data.add_lit_term('{"TID" : 1, "LID" : 1}');
--call data.add_lit_term('{"TID" : 2, "LID" : 1}');
--call data.add_lit_term('{"TID" : 1, "LID" : 2}');
--
---- Terms Lit Rates
--call data.rate_lit_term('{"TID" : 1, "LID" : 1, "Mark" : 2}', 0);
--call data.rate_lit_term('{"TID" : 1, "LID" : 1, "Mark" : 5}', 0);
--
---- Terms Tags Rates
--call data.rate_tag_term('{"TID" : 1, "Tag" : "IT", "Mark" : 5}', 0);