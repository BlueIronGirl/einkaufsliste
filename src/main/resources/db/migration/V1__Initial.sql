INSERT INTO einkaufszettel_owner ( einkaufszettel_id, user_id )
SELECT  einkaufszettel_id, user_id
FROM    einkaufszettel_user
where einkaufszettel_id not in (select einkaufszettel_id from einkaufszettel_owner)
