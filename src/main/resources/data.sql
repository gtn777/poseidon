insert into Users(fullname, username, password, role) values("Administrator", "administrator", "$2a$10$NTMzJWnIDQyV62VMWR.CMuVedBFx0ob7XNtVQmG3B7TyHaj8eu30e", "ADMIN");
insert into Users(fullname, username, password, role) values("User", "user", "$2a$10$NTMzJWnIDQyV62VMWR.CMuVedBFx0ob7XNtVQmG3B7TyHaj8eu30e", "USER");
insert into Users(fullname, username, password, role) values("Bob", "bob", "$2a$10$E1ovBtxyvpgcRptBB/Uo0.lbJpLdfJvWx642OVeltzcpZ/6YF024m", "USER");
insert into Users(fullname, username, password, role) values("Admin", "admin", "$2a$10$E1ovBtxyvpgcRptBB/Uo0.lbJpLdfJvWx642OVeltzcpZ/6YF024m", "ADMIN");
insert into BidList(account, type, bidQuantity) values("HSBC", "type 1", 25);
insert into BidList(account, type, bidQuantity) values("BNP", "Offshore", 40);
insert into BidList(account, type, bidQuantity) values("HSBC", "type 7", 12);
insert into CurvePoint(CurveId, term, value) values(12, 14, 10);
insert into CurvePoint(CurveId, term, value) values(22, 24, 20);
insert into CurvePoint(CurveId, term, value) values(32, 34, 30);
